package com.wzy.springboot;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rometools.rome.feed.synd.SyndEntry;
import com.wzy.springboot.activeMQ.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.mail.dsl.Mail;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/*注解RestController才会根据路径访问到控制层业务代码*/
@Controller
@SpringBootApplication
@EnableCaching  //开启缓存支持
public class DemoApplication {


    // 在某配置类中添加如下内容
    // 监听的http请求的端口,需要在application配置中添加http.port=端口号  如80
    @Value("${http.port}")
    Integer httpPort;
    //正常启用的https端口 如443
    @Value("${server.port}")
    Integer httpsPort;
    //自动获取https://spring.io/blog.atom的资源
    @Value("https://spring.io/blog.atom")
    Resource resource;
    @Value("${book.author}")
    private String bookAuthor;
    @Value("${book.name}")
    private String bookName;
    @Autowired
    private AuthorSettings authorSettings;
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DemoApplication.class);
        /*打印自定义banner,文件来自于banner.txt*/
        app.setBannerMode(Banner.Mode.LOG);
        ConfigurableApplicationContext context = app.run(args);

        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        // Send a message with a POJO - the template reuse the message converter
        System.out.println("Sending an email message.");
        jmsTemplate.convertAndSend("mailbox", new Email("info@example.com", "Hello"));
    }

    @RequestMapping(value = "/")
    String index() {
        return "Author is:" + authorSettings.getName() + " and his/her age is:" + authorSettings.getAge();
    }

    @RequestMapping(value = "/thymeleaf")
    public String index2(Model model) {
        Person single = new Person("周沐兰", 23);
        model.addAttribute("singlePerson", single);
        List<Person> list = new ArrayList<>();
        list.add(new Person("周一", 23));
        list.add(new Person("周二", 21));
        list.add(new Person("周三", 22));
        list.add(new Person("周四", 21));
        list.add(new Person("周五", 23));
        model.addAttribute("people", list);
        return "index";
    }

    /***
     * ssl证书配置
     */
   /* @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("*//*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }


    @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //Connector监听的http的端口号
        connector.setPort(httpPort);
        connector.setSecure(false);
        //监听到http的端口号后转向到的https的端口号
        connector.setRedirectPort(httpsPort);
        return connector;
    }*/
    @Bean
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    //使用FluentAPI和Pollers做默认的轮询方式
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(500).get();
    }

    @Bean
    public FeedEntryMessageSource feedEntryMessageSource() throws IOException {
        FeedEntryMessageSource messageSource = new FeedEntryMessageSource(resource.getURL(), "news");
        return messageSource;
    }

    @Bean
    public IntegrationFlow myFlow() throws IOException {
        return IntegrationFlows.from(feedEntryMessageSource())
                .<SyndEntry, String>route(Payload ->
                                Payload.getCategories().get(0).getName(),
                        mapping -> mapping.channelMapping("releases", "releaseChannel")
                                .channelMapping("engineering", "engineeringChannel")
                                .channelMapping("news", "newsChannel"))
                .get();
    }

    @Bean
    public IntegrationFlow releasesFlow() {
        return IntegrationFlows.from(MessageChannels.queue("releaseChannel", 10)) //从消息通道releasesChannel开始获取数据。
                .<SyndEntry, String>transform(
                        payload -> "《" + payload.getTitle() + "》 " + payload.getLink()) //使用transform方法进行数据转换。payload类型为SyndEntry，将其转换为字符串类型，并自定义数据的格式。
                .handle(Files.outboundAdapter(new File("e:/springblog")) //用handle方法处理file的出站适配器。Files类是由Spring Integration Java DSL提供的 Fluent API用来构造文件输出的适配器。
                        .fileExistsMode(FileExistsMode.APPEND)
                        .charset("UTF-8")
                        .fileNameGenerator(message -> "releases.txt")
                        .get())
                .get();
    }

    @Bean
    public IntegrationFlow engineeringFlow() {
        return IntegrationFlows.from(MessageChannels.queue("engineeringChannel", 10)) //从消息通道releasesChannel开始获取数据。
                .<SyndEntry, String>transform(
                        payload -> "《" + payload.getTitle() + "》 " + payload.getLink()) //使用transform方法进行数据转换。payload类型为SyndEntry，将其转换为字符串类型，并自定义数据的格式。
                .handle(Files.outboundAdapter(new File("e:/springblog")) //用handle方法处理file的出站适配器。Files类是由Spring Integration Java DSL提供的 Fluent API用来构造文件输出的适配器。
                        .fileExistsMode(FileExistsMode.APPEND)
                        .charset("UTF-8")
                        .fileNameGenerator(message -> "engineering.txt")
                        .get())
                .get();
    }

    @Bean
    public IntegrationFlow newsFlow() {
        return
                IntegrationFlows.from(MessageChannels.queue("newsChannel", 10))
                        .<SyndEntry, String>transform(
                                payload -> "《" + payload.getTitle() + "》" +
                                        payload.getLink())
                        .enrichHeaders(                   //通过enricherHeader来增加消息头的信息
                                Mail.headers()
                                        .subject("来自Spring的新闻")
                                        .to("1945261381@qq.com")
                                        .from("1945261381@qq.com"))
                        .handle(Mail.outboundAdapter("smtp.qq.com")  //邮件发送的相关信息通过Spring Integration JavaDSL提供的Mail的headers方法来构造。
                                        .port(25)
                                        .protocol("smtp")
                                        .credentials("1945261381@qq.com", "******") //使用handle方法来定义邮件发送的出站适配器，使用Spring Integration Java DSL提供的Mail。来构造，这里使用3642489@qq.com给自己发送邮件
                                        .javaMailProperties(p -> p.put("mail.debug", false)),
                                e -> e.id("smtpOut"))
                        .get();

    }


}

package com.wzy.springboot;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @Value("${book.author}")
	private String bookAuthor;
	@Value("${book.name}")
	private String bookName;
	@Autowired
	private AuthorSettings authorSettings;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DemoApplication.class);
		/*打印自定义banner,文件来自于banner.txt*/
		app.setBannerMode(Banner.Mode.LOG);
		app.run(args);
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

}

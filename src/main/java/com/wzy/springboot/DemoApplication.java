package com.wzy.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*注解RestController才会根据路径访问到控制层业务代码*/
@RestController
@SpringBootApplication
public class DemoApplication {

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

}

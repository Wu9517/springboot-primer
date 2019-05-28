package com.wzy.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/*注解RestController才会根据路径访问到控制层业务代码*/
@Controller
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

}

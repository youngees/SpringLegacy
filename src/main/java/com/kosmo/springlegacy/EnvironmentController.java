package com.kosmo.springlegacy;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EnvironmentController {
	
	@RequestMapping("/environment/main1")
	public String main1(Model model) {
		
		//1. 스프링 컨테이너 생성 
		ConfigurableApplicationContext ctx = new GenericXmlApplicationContext();
		//2. Environment 객체 생성 
		ConfigurableEnvironment env = ctx.getEnvironment();
		//3. 외부파일을 읽어올 준비를 한다. 
		MutablePropertySources propertySources = env.getPropertySources();
		
		String adminIdStr = "";
		String adminPwStr = "";
		
		try {
			/*
			classpath : 해당 키워드는 프로젝트를 배포했을 때 classes폴더
				하위로 export되는 경로를 가리킨다. 우리가 작성했던 java 파일이나
				그 외 xml파일등이 포함된다.
			*/
			String envPath = "classpath:EnvAdmin.properties";
			//4. 외부파일의 경로를 지정한 후 addLast()로 프로퍼티 소스를 추가한다. 
			propertySources.addLast(new ResourcePropertySource(envPath));
			//5. 파일의 내용을 읽어 변수에 저장한다. 
			adminIdStr = env.getProperty("admin.id");
			adminPwStr = env.getProperty("admin.pw");	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//모델 객체에 데이터 저장
		model.addAttribute("adminID", adminIdStr);
		model.addAttribute("adminPW", adminPwStr);
		
		//뷰 호출
		return "05Environment/main1";
	}
}

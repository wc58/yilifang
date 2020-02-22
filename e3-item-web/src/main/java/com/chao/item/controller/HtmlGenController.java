/**
 * 
 */
package com.chao.item.controller;

import java.io.FileWriter;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**  
* Title: HtmlGenController.java  
* @author ChaoSir 
* @date 2019年10月10日  
* @version 1.0  
* 利用freemarker于生产html页面
*/
@Controller
@Scope("prototype")
public class HtmlGenController {

	@Autowired
	private FreeMarkerConfigurer  configurer;
			
	@RequestMapping("/getHtml")
	@ResponseBody
	public String getHtml() throws Exception{
		
		//注入freemarkerConfigurer配置器
		//获得配置器
		Configuration configuration = configurer.getConfiguration();
		//获得模板
		Template template = configuration.getTemplate("hello.ftl");
		//注入属性
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("hello", "1000");
		//输出文件地址
		FileWriter fileWriter = new FileWriter("E:\\temp\\out\\spring-hello.html");
		//适用模板process输出
		template.process(map, fileWriter);
		//关闭资源
		fileWriter.close();
		//返回状态
		return "OK";
	}
	
}

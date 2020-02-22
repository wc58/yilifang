/**
 * 
 */
package com.chao.freemarker;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import javassist.expr.NewArray;

/**
 * Title: FreemarkerTest.java
 * 
 * @author ChaoSir
 * @date 2019年10月10日
 * @version 1.0
 * @param <V>
 */
public class FreemarkerTest<V> {

	@Test
	public void testName() throws Exception {

		// 创建configuration，参数configuration的版本号
		Configuration configuration = new Configuration(Configuration.getVersion());
		// 指定ftl目录
		configuration.setDirectoryForTemplateLoading(new File("E:\\JavaCode2019\\e3-item-web\\src\\main\\webapp\\ftl"));
		// 设置编码
		configuration.setDefaultEncoding("utf-8");
		// 加载一个模板
		Template template = configuration.getTemplate("test.ftl");
		// 创建map或pojo
		HashMap<String, Object> map = new HashMap<String, Object>();

		// 属性注入
		map.put("id", "属性：1");
		map.put("name", "属性：超");
		map.put("age", "属性：17");
		map.put("titel", "属性：超哥哥");
		// pojo注入
		ChaoPojo pojo = new ChaoPojo("pojo：2", "pojo：chao", "pojo：18");
		map.put("pojo", pojo);
		// 集合注入
		ArrayList<ChaoPojo> list = new ArrayList<ChaoPojo>();
		list.add(new ChaoPojo("list：3", "list：chao", "list：18"));
		list.add(new ChaoPojo("list：4", "list：chao", "list：18"));
		list.add(new ChaoPojo("list：5", "list：chao", "list：18"));
		map.put("list", list);
		//判断是否为null
		map.put("falg", "null");

		// 写入文件位置
		FileWriter fileWriter = new FileWriter("E:\\temp\\out\\test.jsp");
		// 调用process输出文件
		template.process(map, fileWriter);
		// 关闭资源
		fileWriter.close();
	}

}

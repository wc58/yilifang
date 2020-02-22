/**
 * 
 */
package com.chao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chao.utils.FastDFSUtil;
import com.chao.utils.JsonUtils;

/**
 * Title: PictureController.java
 * 
 * @author ChaoSir
 * @date 2019年9月22日
 * @version 1.0
 */
@Controller
@Scope("prototype")
public class PictureController {

	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL = null;
	
	@RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String uploadPicture(MultipartFile uploadFile) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//获得字节流和后缀名
			byte[] bytes = uploadFile.getBytes();
			String name = uploadFile.getOriginalFilename();
			String exName = name.substring(name.lastIndexOf(".")+1);
			//读取配置文件
			FastDFSUtil fastDFSUtil = new FastDFSUtil("classpath:config/client.conf");
			//使用工具进行上传
			String uploadFileByByte = fastDFSUtil.uploadFileByByte(bytes,exName);
			IMAGE_SERVER_URL = IMAGE_SERVER_URL + uploadFileByByte;
			System.out.println(IMAGE_SERVER_URL);
			result.put("error", 0);
			result.put("url", IMAGE_SERVER_URL);
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			result.put("error", 1);
			result.put("url", "图片上传失败！");
			return JsonUtils.objectToJson(result);
		}
	}

}

/**
 * 
 */
package com.chao.FastDFX;

import static org.junit.Assert.*;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.springframework.beans.FatalBeanException;

import com.alibaba.dubbo.remoting.Client;
import com.chao.utils.FastDFSUtil;

/**  
* Title: FastDFXTest.java  
* @author ChaoSir 
* @date 2019年9月22日  
* @version 1.0  
*/
public class FastDFXTest {

	@Test
	public void testName() throws Exception {
		ClientGlobal.init("E:\\JavaCode2019\\e3-manager-web\\src\\test\\resources\\config\\client.conf");
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storageServer = null; 
		StorageClient storageClient = new StorageClient(trackerServer,storageServer);
		String[] upload_file = storageClient.upload_file("F:\\壁纸\\动态壁纸\\2b小姐姐尼尔.mp4", "mp4", null);
		for (String string : upload_file) {
			System.out.println(string);
		}
	}
	@Test
	public void testNam2e() throws Exception {
		FastDFSUtil dfsUtil = new FastDFSUtil("classpath:config/client.conf");
		String upLoadFileByPath = dfsUtil.upLoadFileByPath("F:\\壁纸\\鬼刀18\\1_Adam_4k.jpg", "jpg");
		System.out.println(upLoadFileByPath);
	}
	
}

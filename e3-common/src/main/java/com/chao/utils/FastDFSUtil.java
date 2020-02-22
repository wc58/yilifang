/**
 * 
 */
package com.chao.utils;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**  
* Title: FastDFSUtil.java  
* @author ChaoSir 
* @date 2019年9月22日  
* @version 1.0  
*/
public class FastDFSUtil {

	private TrackerClient trackerClient = null;
	private TrackerServer trackerServer = null;
	private StorageClient storageClient = null;
	private StorageServer storageServer = null;
	
	public FastDFSUtil(String conf) throws FileNotFoundException, IOException, MyException {
		if (conf.contains("classpath:")) {
			conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
		}
		ClientGlobal.init(conf);
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		storageClient = new StorageClient(trackerServer,storageServer);
	}
	
	public String upLoadFileByPath(String fileName, String extName) throws IOException, MyException {
		String[] upload_file = storageClient.upload_file(fileName, extName, null);
		return StringUtils.join(upload_file,"/");
	}
	public String upLoadFileByPath(String fileName) throws IOException, MyException {
		return upLoadFileByPath( fileName,null);
	}
	
	public String uploadFileByByte(byte[] fileByte ,String exname) throws IOException, MyException {
		String[] upload_file = storageClient.upload_file(fileByte, exname, null);
		return  StringUtils.join(upload_file,"/");
	}
	public String uploadFileByByte(byte[] fileByte) throws IOException, MyException {
		return uploadFileByByte(fileByte);
	}
	
}

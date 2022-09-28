package com.khs.service;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;

import com.khs.dto.Thumb;
import com.khs.dto.Upload;

public interface FileService {
	public Upload fileupload(FileItem item);
	public void filedownload(HttpServletRequest req, HttpServletResponse res);
	public int filedel(Map<String,String> m);
	public Thumb thumbnail(String sfn,File file,FileItem item);
	public Upload fileupload(MultipartFile files);
}

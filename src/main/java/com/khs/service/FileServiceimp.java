package com.khs.service;

import java.io.*;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;

import com.khs.dao.FileDao;
import com.khs.dto.Thumb;
import com.khs.dto.Upload;

import net.coobird.thumbnailator.Thumbnails;


public class FileServiceimp implements FileService {
	FileDao dao = new FileDao();
	@Override
	public Upload fileupload(FileItem item) {
		//첨부파일 : 바이너리파일
		long l = item.getSize();
		Upload up = new Upload();
		//System.out.println("파일용량 :" + l);
		if(l > 0) {
			String fp = "d:/JAVAkhs2/upload/";
			String fn = item.getName();
			
			String s_fn = fn.substring(0,(fn.lastIndexOf(".")));
			String s_ex = fn.substring(fn.lastIndexOf(".")+1);
			
			//중복된 파일을 업로드 하지 않기 위해 UID값 생성
			UUID uid = UUID.randomUUID();
			String sfn = s_fn + "_" + uid +"."+s_ex;
			//업로드 파일 저장
			File file = new File(fp + sfn);
				try {
					item.write(file);
					//이미지파일타입 확인
					String ft = item.getContentType();
					String ft1 = ft.substring(0,ft.indexOf("/"));
					//System.out.println(ft1);
					up.setFilename(fn);
					up.setFilepath(fp);
					up.setSavefilename(sfn);
					up.setFilesize(String.valueOf(l));
					up.setFiletype(ft);
					if(ft1.equals("image")) {
						up.setThumb(thumbnail(sfn,file,item));
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return up;
	}
	public Upload fileupload(MultipartFile files){
		//첨부파일 : 바이너리파일
		long l = files.getSize();
		Upload up = new Upload();
		if(l > 0) {
			String fn = files.getOriginalFilename();
			String s_fn = fn.substring(0,(fn.lastIndexOf(".")));
			String s_ex = fn.substring(fn.lastIndexOf(".")+1);
			String fp ="d:/JAVAKHS2/upload/";
			//중복된 파일을 업로드 하지 않기 위해 UID값 생성
			UUID uid = UUID.randomUUID();
			String sfn = s_fn + "_" + uid +"."+s_ex;
			//업로드 파일 저장
			File file = new File(fp + sfn);
				try {
					files.transferTo(file);
					//이미지파일타입 확인
					String ft = files.getContentType();
					String ft1 = ft.substring(0,ft.indexOf("/"));
					//System.out.println(ft1);
					up.setFilename(fn);
					up.setFilepath(fp);
					up.setSavefilename(sfn);
					up.setFilesize(String.valueOf(l));
					up.setFiletype(ft);
					if(ft1.equals("image")) {
						up.setThumb(thumbnail(sfn,file,files));
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return up;
	}
	private Thumb thumbnail(String sfn, File file,MultipartFile files) {
		String thumbfile = "thumb_"+sfn;
		String tfp = "d:/JAVAkhs2/upload/thumbnail/";
		File thumb = new File(tfp+thumbfile);
		Thumb th = new Thumb();
		try {
			files.transferTo(thumb);;
			Thumbnails.of(file).size(200,200).toFile(thumb);
			th.setFilename(thumbfile);
			th.setFilepath(tfp);
			//th.setFiletype(item.getContentType());
			//파일사이즈구하기
			th.setFilesize(String.valueOf(thumb.length()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return th;
	}
	@Override
	public Thumb thumbnail(String sfn,File file,FileItem item) {
		//썸네일 파일 저장
		String thumbfile = "thumb_"+sfn;
		String tfp = "d:/JAVAkhs2/upload/thumbnail/";
		File thumb = new File(tfp+thumbfile);
		Thumb th = new Thumb();
		try {
			item.write(thumb);
			Thumbnails.of(file).size(200,200).toFile(thumb);
			th.setFilename(thumbfile);
			th.setFilepath(tfp);
			//th.setFiletype(item.getContentType());
			//파일사이즈구하기
			th.setFilesize(String.valueOf(thumb.length()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return th;
	}

	@Override
	public void filedownload(HttpServletRequest req, HttpServletResponse res) {
		String path = req.getParameter("fpath");
		String name = req.getParameter("fname");
		String sname = req.getParameter("fsname");
		File f = new File(path+"/"+name);
		
		try {
			InputStream  in = new FileInputStream(f);
			OutputStream os = res.getOutputStream();
			res.reset();//이미 열려있는 출력스트림을 비움
			res.setHeader("Cache-Control", "no-cache");
			res.addHeader("Content-disposition", "attachment; fileName="+ URLEncoder.encode(name,"UTF-8"));
			byte[] fb = new byte[(int)f.length()];
			
			//출력스트림 담기
			int rb = 0;
			while( 0 < (rb = in.read(fb))) {
				os.write(fb,0,rb);
				os.flush();
			}
			in.close();
			os.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int filedel(Map<String,String> m) {
		int rs = 0;
		//db레코드 삭제
		//dao.filedel(no);
		//파일삭제
		String sf = m.get("s");
		String fp = m.get("p");
		String fn = m.get("t");
		
		if(!fn.equals("0")) {
			//썸네일삭제
			File f1 = new File(fp+"thumbnail/"+fn);
			if(f1.exists()) {
				f1.delete();
			}
		}
		if(!sf.equals("0") && !fp.equals("0")) {
			
		}
		File f = new File(fp+sf);
		if(f.exists()) {
			f.delete();
			rs =1;
		}
		
		return rs;
	}

}

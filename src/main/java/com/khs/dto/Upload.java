package com.khs.dto;

public class Upload {
	private String seqno;
	private String filename;
	private String savefilename;
	private String filepath;
	private String filetype;
	private String filesize;
	private Thumb thumb;
	
	public Upload(Thumb thumb) {
		super();
		this.thumb = thumb;
	}
	public Upload() {
		super();
	}
	
	
	public String getSeqno() {
		return seqno;
	}
	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getSavefilename() {
		return savefilename;
	}
	public void setSavefilename(String savefilename) {
		this.savefilename = savefilename;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public Thumb getThumb() {
		return thumb;
	}

	public void setThumb(Thumb thumb) {
		this.thumb = thumb;
	}
	
}

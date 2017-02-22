package cn.teachcourse.assets.bean;

import java.io.Serializable;

public class NewsInfoBean implements Serializable {
	private String id;
	private String title;
	private String content;
	private String full_title;
	private String pdate;
	private String src;
	private String img_width;
	private String img_length;
	private String img;
	private String url;
	private String pdate_src;
	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImg_width() {
		return img_width;
	}

	public void setImg_width(String img_width) {
		this.img_width = img_width;
	}

	public String getFull_title() {
		return full_title;
	}

	public void setFull_title(String full_title) {
		this.full_title = full_title;
	}

	public String getPdate() {
		return pdate;
	}

	public void setPdate(String pdate) {
		this.pdate = pdate;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getImg_length() {
		return img_length;
	}

	public void setImg_length(String img_length) {
		this.img_length = img_length;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPdate_src() {
		return pdate_src;
	}

	public void setPdate_src(String pdate_src) {
		this.pdate_src = pdate_src;
	}

	public NewsInfoBean() {
		init();
	}

	public void init() {

	}

	public NewsInfoBean(String title, String content, String img, String url) {
		super();
		this.title = title;
		this.content = content;
		this.img = img;
		this.url = url;
	}

}

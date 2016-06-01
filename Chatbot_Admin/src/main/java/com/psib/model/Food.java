package com.psib.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Food")
public class Food implements Serializable {

	@Id
	@Column(name = "Id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name", nullable = false, length = 4000)
	private String name;
	
	@Column(name = "urlrelate", nullable = false, length = 500)
	private String urlRelate;

	@Column(name = "thumbpath")
	private String thumbPath;

	@Column(name = "rate")
	private String rate;
	
	@Column(name = "source")
	private String source;
	
	@Column(name = "numOfSearch", columnDefinition = "Integer default '0'")
	private int numOfSearch;

	public Food() {
		// TODO Auto-generated constructor stub
	}

	public Food(long id, String name, String urlRelate, String thumbPath, String rate, String source, int numOfSearch) {
		super();
		this.id = id;
		this.name = name;
		this.urlRelate = urlRelate;
		this.thumbPath = thumbPath;
		this.rate = rate;
		this.source = source;
		this.numOfSearch = numOfSearch;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlRelate() {
		return urlRelate;
	}

	public void setUrlRelate(String urlRelate) {
		this.urlRelate = urlRelate;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getNumOfSearch() {
		return numOfSearch;
	}

	public void setNumOfSearch(int numOfSearch) {
		this.numOfSearch = numOfSearch;
	}

	
}

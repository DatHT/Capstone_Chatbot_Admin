package com.psib.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "FileServer")
public class FileServer implements Serializable {

	@Id
	@Column(name = "Id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int Id;

	@Column(name = "name", nullable = false, length = 4000)
	String name;
	
	@Column(name = "url", nullable = false)
	String url;
	
	public FileServer() {
		// TODO Auto-generated constructor stub
	}

	public FileServer(int id, String name, String url) {
		super();
		Id = id;
		this.name = name;
		this.url = url;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}

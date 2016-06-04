package com.psib.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author DatHT
 * Jun 4, 2016
 */
@Entity
@Table(name = "LexicalCategory")
public class LexicalCategory implements Serializable {

	@Id
	@Column(name = "Id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	@Column(name = "name", nullable = false, length = 512)
	private String name;
	
	@Column(name = "lastModify")
	private Date lastModify;

	public LexicalCategory() {
		// TODO Auto-generated constructor stub
	}

	

	public LexicalCategory(int id, String name, Date lastModify) {
		super();
		Id = id;
		this.name = name;
		this.lastModify = lastModify;
	}


	/**
	 * @return the lastModify
	 */
	public Date getLastModify() {
		return lastModify;
	}
	
	/**
	 * @param lastModify the lastModify to set
	 */
	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
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

}

package com.psib.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Dictionary")
public class Dictionary implements Serializable {

	@Id
	@Column(name = "Id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int Id;
	
	@Column(name = "name", nullable = false, length = 512)
	String name;

	@Column(name = "dictionaryId")
	int dictionaryId;
	
	public Dictionary() {
		// TODO Auto-generated constructor stub
	}

	public Dictionary(int id, String name, int dictionaryId) {
		super();
		Id = id;
		this.name = name;
		this.dictionaryId = dictionaryId;
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

	public int getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(int dictionaryId) {
		this.dictionaryId = dictionaryId;
	}
	
	
	
}

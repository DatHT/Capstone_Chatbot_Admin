package com.psib.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Synonym")
public class Synonym implements Serializable {

	@Id
	@Column(name = "Id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	
	@Column(name = "name", nullable = false, length = 512)
	private String name;

	@Column(name = "synonymId")
	private int synonymId;
	
	public Synonym() {
		// TODO Auto-generated constructor stub
	}

	

	public Synonym(int id, String name, int synonymId) {
		super();
		Id = id;
		this.name = name;
		this.synonymId = synonymId;
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



	public int getSynonymId() {
		return synonymId;
	}



	public void setSynonymId(int synonymId) {
		this.synonymId = synonymId;
	}


	
	
	
}

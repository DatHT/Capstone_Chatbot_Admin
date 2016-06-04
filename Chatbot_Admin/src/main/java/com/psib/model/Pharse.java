package com.psib.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Pharse")
public class Pharse implements Serializable{

	@Id
	@Column(name = "Id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int Id;

	@Column(name = "name", nullable = false, length = 512)
	String name;

	@Column(name = "isAsynchronized")
	boolean isAsynchronized;
	
	@Column(name = "pharseId")
	int pharseId;
	
	public Pharse() {
		// TODO Auto-generated constructor stub
	}

	public Pharse(int id, String name, boolean isAsynchronized, int pharseId) {
		super();
		Id = id;
		this.name = name;
		this.isAsynchronized = isAsynchronized;
		this.pharseId = pharseId;
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

	public boolean isAsynchronized() {
		return isAsynchronized;
	}

	public void setAsynchronized(boolean isAsynchronized) {
		this.isAsynchronized = isAsynchronized;
	}

	
	public int getPharseId() {
		return pharseId;
	}

	public void setPharseId(int pharseId) {
		this.pharseId = pharseId;
	}

	
	
}
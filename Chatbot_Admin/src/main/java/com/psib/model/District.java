package com.psib.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "District")
public class District implements Serializable, Comparable<District> {

	private static final long serialVersionUID = -2003822635681401214L;

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name", nullable = false, length = 128)
	private String name;
	
	@Column(name = "nearby")
	private String nearby;

	public District() {
		// TODO Auto-generated constructor stub
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

	public String getNearby() {
		return nearby;
	}

	public void setNearby(String nearby) {
		this.nearby = nearby;
	}

	public District(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public int compareTo(District o) {
		// TODO Auto-generated method stub
		return this.getName().compareTo(o.getName());
	}

}

package com.psib.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.psib.constant.TimeSchedule;

@Entity
@Table(name = "Scheduler")
public class Scheduler implements Serializable {

	private static final long serialVersionUID = 363103024375183509L;

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", nullable = false, length = 128)
	private String name;

	@Column(name = "status", nullable = false, columnDefinition = "Boolean default '0'")
	private boolean status;
	@Column(name = "frequency", nullable = false)
	private String frequency;
	@Column(name = "hour", nullable = false)
	private int hour;
	@Column(name = "minute", nullable = false)
	private int minute;

	public Scheduler() {
		this.frequency = TimeSchedule.EVERYDAY;
		this.hour = 23;
		this.minute = 00;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

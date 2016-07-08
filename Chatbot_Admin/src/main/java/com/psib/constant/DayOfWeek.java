package com.psib.constant;

public enum DayOfWeek {
	Monday("MON"), Tuesday("TUE"), Wednesday("WED"), Thursday("THU"), Friday("FRI"), Saturday("SAT"), Sunday("SUN");

	private String value;

	public String getValue() {
		return value;
	}

	private DayOfWeek(String value) {
		this.value = value;
	}
}

package com.psib.util;

import com.psib.constant.DayOfWeek;

public class CronExpressionUtils {

	public static String daily(String second, String minute, String hour) {
		return CronExpressionUtils.converCronExpression(second, minute, hour, "1/1", "*", null);
	}

	public static String weekly(DayOfWeek weekday) {
		return CronExpressionUtils.converCronExpression(null, null, null, "?", "*", weekday.getValue());
	}

	public static String monthly(String day) {
		return CronExpressionUtils.converCronExpression(null, null, null, day, "1/1", null);
	}

	public static String converCronExpression(String second, String minute, String hour, String day, String month,
			String weekday) {
		String sec = "0";
		if (minute != null) {
			if (0 < Integer.parseInt(second) && Integer.parseInt(second) < 60) {
				sec = second;
			}
		}

		String min = "0";
		if (minute != null) {
			if (0 < Integer.parseInt(minute) && Integer.parseInt(minute) <= 59) {
				min = minute;
			}
		}

		String h = "0";
		if (hour != null) {
			if (0 < Integer.parseInt(hour) && Integer.parseInt(hour) <= 59) {
				h = hour;
			}
		}

		String d = "*";
		if (day != null) {
			if (0 < Integer.parseInt(day) && Integer.parseInt(day) <= 31) {
				d = day;
			}
		}

		String mon = "*";
		if (month != null) {
			mon = month;
		}

		String wd = "?";
		if (weekday != null) {
			wd = weekday;
		}

		return sec + " " + min + " " + h + " " + d + " " + mon + " " + wd;
	}
}

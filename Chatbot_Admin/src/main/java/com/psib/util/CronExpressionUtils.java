package com.psib.util;

import com.psib.common.CronExpressionException;
import com.psib.constant.DayOfWeek;

public class CronExpressionUtils {

	public static String daily(int second, int minute, int hour) throws CronExpressionException {
		return CronExpressionUtils.convertCronExpression(second, minute, hour, "1/1", "*", DayOfWeek.Unknown);
	}

	public static String weekly(DayOfWeek weekday) throws CronExpressionException {
		return CronExpressionUtils.convertCronExpression(0, 0, 0, "?", "*", weekday);
	}
	
	public static String weekly(DayOfWeek weekday, int second, int minute, int hour) throws CronExpressionException {
		return CronExpressionUtils.convertCronExpression(second, minute, hour, "?", "*", weekday);
	}

	public static String monthly(int day) throws CronExpressionException {
		if (0 > day && day > 31) {
			throw new CronExpressionException("Invalid day!");
		}
		return CronExpressionUtils.convertCronExpression(0, 0, 0, String.valueOf(day), "1/1", DayOfWeek.Unknown);
	}
	
	public static String monthly(int day, int second, int minute, int hour) throws CronExpressionException {
		if (0 > day && day > 31) {
			throw new CronExpressionException("Invalid day!");
		}
		return CronExpressionUtils.convertCronExpression(second, minute, hour, String.valueOf(day), "1/1",
				DayOfWeek.Unknown);
	}

	private static String convertCronExpression(int second, int minute, int hour, String day, String month,
			DayOfWeek weekday) throws CronExpressionException {
		if (0 > second && second > 59) {
			throw new CronExpressionException("Invalid second!");
		}

		if (0 > minute && minute > 59) {
			throw new CronExpressionException("Invalid minute!");
		}

		if (0 > hour && hour > 23) {
			throw new CronExpressionException("Invalid hour!");
		}

		return second + " " + minute + " " + hour + " " + day + " " + month + " " + weekday.getValue();
	}
}

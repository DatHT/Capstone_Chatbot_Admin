package com.psib.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.psib.dto.jsonmapper.Entry;

public class CommonUtils {

	public static String[] generateSynonym(String tempString) {
		Integer count = 0;
		String[] elements = tempString.split("\\s+");
		String[] result = new String[elements.length];
		StringBuilder newString = new StringBuilder();
		for (int i = 0; i < elements.length; i++) {
			newString.append(" " + elements[i]);
			result[count] = newString.toString();
			count++;
		}
		return result;
	}

	public static String getLatitude(final String geo) {
		String stringLatLong = StringUtils.substringAfterLast(geo, "%7C");
		return StringUtils.substringBefore(stringLatLong, ",");
	}

	public static String getlongitude(final String geo) {
		String stringLatLong = StringUtils.substringAfterLast(geo, "%7C");
		return StringUtils.substringAfter(stringLatLong, ",");
	}

	public static String reformatTime24H(final String time) {
		return StringUtils.substringBeforeLast(time, ":");
	}

	public static String getMinPrice(final String priceRange) {
		return StringUtils.substringBefore(priceRange, "-").trim();
	}

	public static String getMaxPrice(final String priceRange) {
		String tmp = StringUtils.substringAfter(priceRange, "-").trim();
		return StringUtils.substringBefore(tmp, "V").trim();
	}

	public static String getImgUrl(final String url) {
		String tmp = StringUtils.substringAfter(url, "(");
		return StringUtils.substringBeforeLast(tmp, ")");
	}

	public static boolean checkExistName(String name, List<Entry> list) {

		for (Entry value : list) {
			if (name != null) {
				if (!name.equals("")) {
					if (name.equals(value.getValue())) {
						return true;
					}
				}
			}

		}
		return false;
	}

	public static String getDateStringFormat(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(date);
	}

	/*
	 * Parse Date format form yyyyMMdd to yyyy/MM/dd
	 */
	public static String parseDateFormat(String strDate) {
		DateFormat oldFormat = new SimpleDateFormat("yyyyMMdd");
		Date date;
		try {
			date = oldFormat.parse(strDate);
			DateFormat newFormat = new SimpleDateFormat("yyyy/MM/dd");
			return newFormat.format(date);
		} catch (ParseException e) {
			System.out.println("Wrong date format: " + strDate + "---" + e.getLocalizedMessage());
		}
		return null;
	}

	public static List<String> splitAddresses(List<String> addresses) {
		List<String> splitAddresses = new ArrayList<>();
		for (int i = 0; i < addresses.size(); i++) {
			String temp = handleAddressNameFromDB(addresses.get(i)).trim();
			if (!splitAddresses.contains(temp)) {
				splitAddresses.add(temp);
			}
		}
		return splitAddresses;
	}

	private static String handleAddressNameFromDB(String address) {
		String result = "";
		String patternNumber = "[0-9]";
		int posOfFirstNum = indexOf(Pattern.compile(patternNumber), address);
		int start = 0;
		int end = 0;
		if (address.indexOf("Đường") > 0) {
			start = address.indexOf("Đường") + 5;
		} else {
			if (posOfFirstNum != -1) {
				for (int i = posOfFirstNum; i < address.length(); i++) {
					if (Character.isLetter(address.charAt(i))) {
						if (address.charAt(i - 1) == ' ') {
							start = i;
							break;
						}
					}
				}
			}
		}
		for (int i = start; i < address.length(); i++) {
			if (address.charAt(i) == ',' || address.charAt(i) == '(' || address.charAt(i) == '.') {
				end = i;
				break;
			}
		}
		return (start < end) ? address.substring(start, end) : "";

	}

	private static int indexOf(Pattern pattern, String s) {
		Matcher matcher = pattern.matcher(s);
		return matcher.find() ? matcher.start() : -1;
	}

	public static Set<String> findDuplicatePhrase(String first, String second) {
		Set<String> result = new LinkedHashSet();
		first = first.toLowerCase();
	    second = second.toLowerCase();
	    //split the second string into words
	    List<String> wordsOfSecond = new ArrayList<>(Arrays.asList(second.split(" ")));
	    
	    //remove unrelate worrd
	    String unhandleWord = "thèm lắm lun ngon ngất ngây nè chưa có";
	    for (String word : unhandleWord.split(" ")) {
	        if(wordsOfSecond.contains(word))
	        	wordsOfSecond.remove(word);
	    }
	    
	    
	    //split and compare each word of the first string           
	    for (String word : first.split(" ")) {
	        if(wordsOfSecond.contains(word))
	            result.add(word);
	    }
	    
	    return result;
	}
}

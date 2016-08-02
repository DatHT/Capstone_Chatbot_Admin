package com.psib.util;

import java.io.IOException;
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

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.psib.dto.geolocation.GoogleResponse;
import com.psib.dto.geolocation.Result;
import com.psib.dto.jsonmapper.Entry;

public class CommonUtils {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

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

	public static double splitLong(String url) throws ArrayIndexOutOfBoundsException {
		String[] list = url.split("_");
		String splitLong = null;
		double longitude = 0;
		if (list.length == 3) {
			splitLong = list[list.length - 1].substring(0, list[2].indexOf("."));
			splitLong = splitLong.replace("-", ".");
			longitude = Double.parseDouble(splitLong);
		}
		if (list.length == 4) {
			list = list[list.length - 1].split("%");
			list = list[list.length - 1].split(",");
			splitLong = list[list.length - 1];
			longitude = Double.parseDouble(splitLong);
		}
		if (list.length == 1) {
			int index = list[0].indexOf(",");
			splitLong = list[0].substring(index + 1, list[0].length());
			longitude = Double.parseDouble(splitLong);
		}

		return longitude;
	}

	public static double splitLat(String url) throws ArrayIndexOutOfBoundsException {
		String[] list = url.split("_");
		String splitLat = null;
		double latitude = 0;
		if (list.length == 3) {
			splitLat = list[list.length - 2];
			splitLat = splitLat.replace("-", ".");
			latitude = Double.parseDouble(splitLat);
		}
		if (list.length == 4) {
			list = list[list.length - 1].split("%");
			list = list[list.length - 1].split(",");
			splitLat = list[list.length - 2];
			splitLat = splitLat.replace("7C", "");
			latitude = Double.parseDouble(splitLat);
		}
		if (list.length == 1) {
			int index = list[0].indexOf(",");
			splitLat = list[0].substring(0, index);
			latitude = Double.parseDouble(splitLat);
		}
		return latitude;
	}

	public static String splitAddress(String district, String address) throws IOException {
		if (address.toLowerCase().contains(district.toLowerCase())) {
			address = address.substring(0, address.indexOf(district));
			int last = address.lastIndexOf(",");
			if (last != -1) {
				address = address.substring(0, last);
			}
		}
		return address;
	}

	public static String splitDistrict(String latlong) throws IOException {
		String district = "";
		String address = "";
		GoogleResponse result = new AddressConverter().convertFromLatLong(latlong);
		if (result.getStatus().equals("OK")) {
			for (Result r : result.getResults()) {
				if (r.getTypes().toString().contains("administrative_area_level_2")) {
					address = r.getFormatted_address();
				}
			}
			district = address.split(",")[0];

		} else {
			logger.info(result.getStatus());
		}
		return district;
	}

	public static String splitName(String stringname) throws ArrayIndexOutOfBoundsException, IndexOutOfBoundsException {
		if (stringname.length() > 250) {
			stringname = stringname.substring(0, 250);
			return stringname;
		}
		return stringname;
	}

	public static String htmlEncode(final String string) {
		final StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			final Character character = string.charAt(i);
			if (CharUtils.isAscii(character)) {
				// Encode common HTML equivalent characters
				stringBuffer.append(
						// StringEscapeUtils.escapeHtml4(character.toString()));
						character.toString());
			} else {
				// Why isn't this done in escapeHtml4()?
				stringBuffer.append(String.format("&#x%x;", Character.codePointAt(string, i)));
			}
		}
		return stringBuffer.toString();
	}

	public static String nextPage(String numNextPage, String linkPage, String url) throws IOException {
		String next = "N/A";
		if (numNextPage.indexOf("http") == -1) {
			numNextPage = url + numNextPage;
		}

		// Add New next Page
		numNextPage = numNextPage.substring(linkPage.length());
		// numNextPage = numNextPage.replace('&', '?');

		if (numNextPage.charAt(0) == '/') {
			StringBuilder sb = new StringBuilder(numNextPage);
			sb.deleteCharAt(0);
			numNextPage = sb.toString();
		}
		int end = numNextPage.indexOf("/");
		if (end <= 0) {
			end = numNextPage.lastIndexOf("=");
			next = numNextPage.substring(0, end + 1);
		} else {
			next = numNextPage.substring(0, end);
		}
		return next;
	}

	public static String makeContentPage(String sourcePage, String url) {

		String tmpsource_1 = checkHrefTag(sourcePage, url);
		String tmpsource_2 = checkSrcTag(tmpsource_1, url);
		return tmpsource_2;
	}

	public static String checkSrcTag(String src, String url) {

		if (src.contains("src=\"http://") || src.contains("src=\"www") || src.contains("src=\"//")
				|| src.contains("src=\"https://")) {
			src = src;
		}
		if (src.contains("src=\"\"")) {
			src = src.replaceAll("src=\"\"", "src=\"" + url + "/Content/css/images/image_defaul_128.jpg" + "\"");
		}
		if (src.contains("src=\"/")) {
			src = src.replaceAll("src=\"" + "/", "src=\"" + url + "/");
		}
		if (src.contains("src=\"" + url + "//")) {
			src = src.replaceAll(url + "//", "");
		}
		if (src.contains("src=\"" + url + "http")) {
			src = src.replaceAll(url, "");
		}
		if (src.contains("src=\"media")) {
			src = src.replaceAll("src=\"media", "src=\"//media");
		}
		if (src.contains("src=\"connect")) {
			src = src.replaceAll("src=\"connect", "src=\"//connect");
		}
		if (src.contains("src=\"www")) {
			src = src.replaceAll("src=\"www.", "src=\"//www.");
		}
		if (src.contains("src=\"static")) {
			src = src.replaceAll("src=\"static", "src=\"//static");
		}
		return src;
	}

	public static String checkHrefTag(String src, String url) {
		if (src.contains("href=\"http://") || src.contains("href=\"www") || src.contains("href=\"//")
				|| src.contains("href=\"https://")) {
			src = src;
		}
		if (src.contains("href=\"css")) {
			src = url + "/" + src;
		}
		if (src.contains("href=\"/") || src.contains("href=\"/")) {
			src = src.replaceAll("href=\"" + "/", "href=\"" + url + "/");
		}
		if (src.contains("href=\"" + url + "//")) {
			src = src.replaceAll(url + "//", "");
		}
		if (src.contains("href=\"" + url + "http")) {
			src = src.replaceAll(url, "");
		}
		if (src.contains("href=\"static")) {
			src = src.replaceAll("href=\"static", "href=\"//static");
		}
		if (src.contains("href=\"connect")) {
			src = src.replaceAll("src=\"connect", "src=\"//connect");
		}
		if (src.contains("href=\"www")) {
			src = src.replaceAll("href=\"www", "href=\"//www");
		}
		return src;
	}

	public static String commonUrl(String link) {
		String url = "";
		String[] str_array = link.split("/");
		for (int i = 0; i < 3; i++) {
			url = url + str_array[i] + "/";
		}
		url = url.substring(0, url.length() - 1);

		return url;
	}

	public static Set<String> findDuplicatePhrase(String first, String second) {
		Set<String> result = new LinkedHashSet<String>();
		first = first.toLowerCase();
		second = second.toLowerCase();
		// split the second string into words
		List<String> wordsOfSecond = new ArrayList<>(Arrays.asList(second.split(" ")));

		// remove unrelate worrd
		String unhandleWord = "thèm lắm lun ngon ngất ngây nè chưa có bạn thì ở đâu để chọn món của sao làm gì đó muốn ăn nha còn khác không";
		for (String word : unhandleWord.split(" ")) {
			if (wordsOfSecond.contains(word))
				wordsOfSecond.remove(word);
		}

		// split and compare each word of the first string
		for (String word : first.split(" ")) {
			if (wordsOfSecond.contains(word)) {
				result.add(word);
			}
		}

		return result;
	}
}

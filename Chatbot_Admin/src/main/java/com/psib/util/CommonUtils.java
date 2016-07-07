package com.psib.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.CharUtils;
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
		if(list.length==1){
			int index = list[0].indexOf(",");
	        splitLong = list[0].substring(index+1, list[0].length());
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
		if(list.length==1){
			int index = list[0].indexOf(",");
	        splitLat = list[0].substring(0,index);
	        latitude = Double.parseDouble(splitLat);
		}
		return latitude;
	}

	public static String splitAddress(String addressname) throws IndexOutOfBoundsException {
		String[] listAddress = addressname.split(",");
		String address = "";
		if (listAddress.length == 5) {
			address = listAddress[listAddress.length - 5] + " " + listAddress[listAddress.length - 4];
			if (listAddress[listAddress.length - 3].length() > 15) {
				address = listAddress[listAddress.length - 3] + " " + listAddress[listAddress.length - 2];
			}
		}
		if (listAddress.length == 2) {
			address = listAddress[listAddress.length - 2];
		}
		if (listAddress.length == 3) {
			address = listAddress[listAddress.length - 3];
//			if (listAddress[listAddress.length - 2].toLowerCase().contains("phÆ°á»�ng")
//					|| listAddress[listAddress.length - 2].toLowerCase().contains("p.")) {
//				address = listAddress[listAddress.length - 3] + "," + listAddress[listAddress.length - 2];
//			}
//			if (listAddress[listAddress.length - 2].toLowerCase().contains("quáº­n")
//					|| listAddress[listAddress.length - 2].toLowerCase().contains("q.")) {
//				address = listAddress[listAddress.length - 3];
//			}
		}
		if (listAddress.length == 4) {
			address = listAddress[listAddress.length - 4];
			if(listAddress[listAddress.length - 3].contains("P.")){
				address = listAddress[listAddress.length - 4]+" "+listAddress[listAddress.length - 3];
			}
		}
		if (listAddress.length == 6) {
			address = listAddress[listAddress.length - 6]+ " " +listAddress[listAddress.length - 5]+ " " +listAddress[listAddress.length - 4];
		}
//		else {
//			address = listAddress[listAddress.length - 4] + "," + listAddress[listAddress.length - 3];
//		}

		return address;
	}

	public static String splitDistrict(String addressname) throws IndexOutOfBoundsException {
		String[] listAddress = addressname.split(",");
		String district = "";
		if (listAddress.length == 5) {
			district = listAddress[listAddress.length - 3];
			if (district.length() > 15) {
				district = listAddress[listAddress.length - 1];
			}
			if (district.contains("Q.")) {
				district = district.replace("Q.", "Quáº­n ");
			}
			if (district.contains("Q.") || district.contains("Qu") || district.contains("q.")
					|| district.contains("qu")) {
				return district;
			} else {
				district = "Quáº­n" + district;
			}
		}
		if (listAddress.length == 2) {
			district = listAddress[listAddress.length - 1];
			if (district.contains("Q.")) {
				district = district.replace("Q.", "Quáº­n ");
			}
		}
		if (listAddress.length == 3) {
			district = listAddress[listAddress.length - 2];
			if(district.contains("P.")){
				district = listAddress[listAddress.length - 1];
			}
			if (district.contains("Q.")) {
				district = district.replace("Q.", "Quáº­n ");
			}
		}
		if (listAddress.length == 4) {
			district = listAddress[listAddress.length - 3];
			if(district.contains("P.")){
				district = listAddress[listAddress.length - 2];
			}
			if (district.contains("Q.")) {
				district = district.replace("Q.", "Quáº­n ");
			}
		}
		if (listAddress.length == 6) {
			district = listAddress[listAddress.length - 3];
			if (district.contains("Q.")) {
				district = district.replace("Q.", "Quáº­n ");
			}
		}
//		else {
//			district = listAddress[listAddress.length - 2];
//			if (district.contains("P.") || district.contains("F.") || district.contains("p.") || district.contains("f.")
//					|| (district.contains("phÆ°á»�ng"))) {
//				district = listAddress[listAddress.length - 1];
//			}
//			if (district.contains("Q.")) {
//				district = district.replace("Q.", "Quáº­n ");
//			}
//			if (district.contains("Q.") || district.contains("Qu") || district.contains("q.")
//					|| district.contains("qu")) {
//				return district;
//			} else {
//				district = "Quáº­n" + district;
//			}
//		}

		return district;
	}

	public String splitName(String stringname) throws ArrayIndexOutOfBoundsException, IndexOutOfBoundsException {
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
		if (src.contains("src=\"css")) {
			src = url + "/" + src;
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
		if (src.contains("href=\"/")||src.contains("href=\"/")) {
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
}

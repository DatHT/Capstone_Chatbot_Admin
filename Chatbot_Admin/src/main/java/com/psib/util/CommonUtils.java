package com.psib.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import com.psib.dto.jsonmapper.Entry;

public class CommonUtils {
	public double splitLong(String url) throws ArrayIndexOutOfBoundsException {
		String[] list = url.split("_");
		String splitLat = null;
		String splitLong = null;
		double longitude = 0;
		if (list.length == 3) {
			splitLat = list[list.length - 2];
			splitLat = splitLat.replace("-", ".");
			splitLong = list[list.length - 1].substring(0, list[2].indexOf("."));
			splitLong = splitLong.replace("-", ".");
			longitude = Double.parseDouble(splitLong);
		}

		return longitude;
	}

	public double splitLat(String url) throws ArrayIndexOutOfBoundsException {
		String[] list = url.split("_");
		String splitLat = null;
		String splitLong = null;
		double latitude = 0;
		if (list.length == 3) {
			splitLat = list[list.length - 2];
			splitLat = splitLat.replace("-", ".");
			splitLong = list[list.length - 1].substring(0, list[2].indexOf("."));
			splitLong = splitLong.replace("-", ".");
			latitude = Double.parseDouble(splitLat);
		}
		return latitude;
	}

	public String splitAddress(String addressname) throws IndexOutOfBoundsException {
		String[] listAddress = addressname.split(",");
		String add = "";
		for (int i = 0; i < listAddress.length - 2; i++) {
			add = add + listAddress[i];
		}

		return add;
	}

	public String splitDistrict(String addressname) throws IndexOutOfBoundsException {
		String[] listAddress = addressname.split(",");
		String district = listAddress[listAddress.length - 2];

		return district;
	}

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

	public String splitName(String stringname) throws ArrayIndexOutOfBoundsException, IndexOutOfBoundsException {
		if (stringname.length() > 250) {
			stringname = stringname.substring(0, 250);
			return stringname;
		}
		return stringname;
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
	public static String htmlEncode(final String string) {
        final StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            final Character character = string.charAt(i);
            if (CharUtils.isAscii(character)) {
                // Encode common HTML equivalent characters
                stringBuffer.append(
                        //StringEscapeUtils.escapeHtml4(character.toString()));
                        character.toString());
            } else {
                // Why isn't this done in escapeHtml4()?
                stringBuffer.append(
                        String.format("&#x%x;",
                                Character.codePointAt(string, i)));
            }
        }
        return stringBuffer.toString();
    }
}

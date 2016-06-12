package com.psib.util;

import java.util.List;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import com.psib.dto.jsonmapper.Entry;

public class CommonUtils {
	private double longitude;
    private double latitude;
    private String province;
    private String district;
    private String address;
    private String name;

<<<<<<< HEAD
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
=======
    public CommonUtils() {
    }

    public CommonUtils(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public CommonUtils(String province, String district, String address) {
        this.province = province;
        this.district = district;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CommonUtils(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getProvince() {
        return province;
    }

    public String getDistrict() {
        return district;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public CommonUtils splitLongLat(String url) throws ArrayIndexOutOfBoundsException {
        String[] list = url.split("_");
        String splitLat = null;
        String splitLong = null;
>>>>>>> 32fd3d636da60e3a94e453bb919dc9286905d8ec
        if (list.length == 3) {
            splitLat = list[list.length - 2];
            splitLat = splitLat.replace("-", ".");
            splitLong = list[list.length - 1].substring(0, list[2].indexOf("."));
            splitLong = splitLong.replace("-", ".");
            latitude = Double.parseDouble(splitLat);
<<<<<<< HEAD
        }

        return latitude;
    }

    public String splitAddress(String addressname) throws IndexOutOfBoundsException {
        String[] listAddress = addressname.split(",");
=======
            longitude = Double.parseDouble(splitLong);
        }

        CommonUtils common = new CommonUtils(longitude, latitude);
        return common;
    }

    public CommonUtils splitAddress(String addressname) throws IndexOutOfBoundsException {
        String[] listAddress = addressname.split(",");
        province = listAddress[listAddress.length - 1];
        district = listAddress[listAddress.length - 2];
>>>>>>> 32fd3d636da60e3a94e453bb919dc9286905d8ec
        String add="";
        for (int i = 0; i < listAddress.length - 2; i++) {
            add = add + listAddress[i];
        }
        
<<<<<<< HEAD
        return add;
    }
    public String splitDistrict(String addressname) throws IndexOutOfBoundsException {
        String[] listAddress = addressname.split(",");
        String district = listAddress[listAddress.length - 2];
        
        return district;
=======
        CommonUtils common = new CommonUtils(province, district, add);
        return common;
>>>>>>> 32fd3d636da60e3a94e453bb919dc9286905d8ec
    }

    public String splitName(String stringname) throws ArrayIndexOutOfBoundsException, IndexOutOfBoundsException {
        boolean check = stringname.contains("Mì sườn ");
        boolean check2 = stringname.contains(" Mì cay ông trí");
        if (check) {
            stringname = "Mì sườn";
            return stringname;

        } else if (check2) {
            stringname = " Mì cay ông trí";
            return stringname;
        } else if (stringname.length() > 250) {
            stringname = stringname.substring(0, 250);
            return stringname;
        }
        return stringname;
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
//	public static String getPath() throws UnsupportedEncodingException {
//        String path = new Object(){}.getClass().getClassLoader().getResource("").getPath();
//        String fullPath = URLDecoder.decode(path, "UTF-8");
//        String pathArr[] = fullPath.split("classes/");
//        System.out.println("full path: "+fullPath);
//        System.out.println("path: "+pathArr[0]);
//        fullPath = pathArr[0] + "/web";
//
//        String reponsePath = "";
//        // to read a file from webcontent
//        reponsePath = new File(fullPath).getPath();
//        return reponsePath;
//    }
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
	
	public static boolean checkExistName(String name, List<Entry> list) {
		
		for(Entry value : list) {
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
}

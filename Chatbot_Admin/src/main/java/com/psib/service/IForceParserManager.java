package com.psib.service;

public interface IForceParserManager {
	boolean checkExitAddress(String name);
	int getAddressIDByAddress(String address);
	boolean addNewAddress(int districtID, double latitude, double longitude, String name, String restaurantName);
	int getProductIDByName(String name);
	boolean checkExitsProduct(String link, String name);
	boolean addNewProduct(String name, String rate, String source, String thumbpath, String urlrelate);
	boolean checkExitFoodAddress(int foodid, int addressid);
	boolean addNewFoodAddress(int productId, int addressId, String address, String district, double latidute, double longidute, String name,
            String rate, String restaurantName, String thumbpath, String urlrelate);
	boolean checkExitDistrict(String name);
	int getDistrictIDByDistrictName(String name);
	boolean addNewDistrict(String name);
}

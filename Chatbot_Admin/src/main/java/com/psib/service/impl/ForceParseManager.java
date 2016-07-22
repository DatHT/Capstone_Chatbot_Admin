package com.psib.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.psib.dao.IAddressDao;
import com.psib.dao.IDistrictDao;
import com.psib.dao.IFileServerDao;
import com.psib.dao.IProductDetailDao;
import com.psib.dto.configuration.ConfigDTO;
import com.psib.dto.configuration.ConfigDTOList;
import com.psib.dto.configuration.PageDTO;
import com.psib.dto.configuration.PageDTOList;
import com.psib.dto.configuration.PageDataDTO;
import com.psib.model.Address;
import com.psib.model.District;
import com.psib.model.ProductDetail;
import com.psib.service.IForceParseManager;
import com.psib.util.CommonUtils;
import com.psib.util.LatitudeAndLongitudeWithPincode;
import com.psib.util.SpringPropertiesUtil;
import com.psib.util.XMLUtils;

@Service
public class ForceParseManager implements IForceParseManager {

	private String xmlFilePathFolder;

	@Autowired
	private IProductDetailDao productManager;
	@Autowired
	private IAddressDao addressManager;
	@Autowired
	private IDistrictDao districtManager;
	@Autowired
	private IFileServerDao fileServerDao;

	private String num_of_exits = SpringPropertiesUtil.getProperty("num_exist");

	public String getPageConfigFilePath() throws IOException {
		if (xmlFilePathFolder == null) {
			xmlFilePathFolder = fileServerDao.getByName(SpringPropertiesUtil.getProperty("xml_file_path")).getUrl();
		}
		return xmlFilePathFolder + "/pageconfig.xml";
	}

	public String getParserConfigFilePath() throws IOException {
		if (xmlFilePathFolder == null) {
			xmlFilePathFolder = fileServerDao.getByName(SpringPropertiesUtil.getProperty("xml_file_path")).getUrl();
		}
		return xmlFilePathFolder + "/parserconfig.xml";
	}

	@Override
	public String dynamicParse(String numPage, int numOfPage, String url) {
		// TODO Auto-generated method stub
		long startTime = System.nanoTime();
		WebDriver driver = new FirefoxDriver();
		try {
			// lay url page
			String xmlFilePath = getPageConfigFilePath();
			PageDTOList tmpPage = XMLUtils.unmarshallPage(xmlFilePath);

			List<PageDTO> pageConfigs = tmpPage.getConfig();

			PageDTO pageConfig = new PageDTO();
			for (PageDTO pageCfg : pageConfigs) {
				if (pageCfg.getSite().equals(url)) {
					pageConfig = pageCfg;
					break;
				}
			}
			xmlFilePath = getParserConfigFilePath();
			ConfigDTOList tmp = XMLUtils.unmarshall(xmlFilePath);
			List<ConfigDTO> configs = tmp.getConfig();
			System.out.println("Config Size: " + configs.size());

			ConfigDTO config = new ConfigDTO();
			for (ConfigDTO cfg : configs) {
				if (cfg.getSite().equals(url)) {
					config = cfg;
					break;
				}
			}

			System.out.println("Page Config Link: " + pageConfig.getSite());
			System.out.println("Page Config Xpath: " + pageConfig.getXpath());

			driver.manage().deleteAllCookies();
			driver.get(pageConfig.getLinkPage());
			System.out.println("Link PAGE: " + pageConfig.getLinkPage());
			By selBy = By.tagName("body");
			int initialHeight = driver.findElement(selBy).getSize().getHeight();
			for (int temp = 1; temp < numOfPage; temp++) {
				initialHeight = driver.findElement(selBy).getSize().getHeight();

				// Scroll to bottom
				((JavascriptExecutor) driver).executeScript("scroll(0," + initialHeight + ");");

				System.out.println("Sleeping... wleepy " + temp);
				Thread.sleep(2000);
			}
			System.out.println("XPath Link: " + pageConfig.getXpath());

			String restaurantName = "", address = "", productName = "", userRate = "", map = "";
			String thumbpath = "";

			List<PageDataDTO> pageList = new ArrayList<PageDataDTO>();
			List<WebElement> despageUrls = driver.findElements(By.xpath(pageConfig.getXpath()));
			List<WebElement> productNames = driver.findElements(By.xpath(pageConfig.getFoodName()));
			List<WebElement> images = driver.findElements(By.xpath(pageConfig.getImage()));
			for (int i = 0; i < despageUrls.size(); i++) {
				PageDataDTO page = new PageDataDTO();
				page.setNextPageUrl(despageUrls.get(i).getAttribute("href"));
				page.setProductName(productNames.get(i).getText());
				page.setImageLink(images.get(i).getAttribute("src"));
				pageList.add(page);
			}
			System.out.println("--Number of record: " + pageList.size());

			int countAdded = 0;
			int countExits = 0;
			for (int i = 0; i < pageList.size(); i++) {
				String pageDescriptionLink = pageList.get(i).getNextPageUrl();
				productName = pageList.get(i).getProductName();
				String newProductName = CommonUtils.splitName(productName);
				thumbpath = pageList.get(i).getImageLink();

				System.out.println("==Parsing Page URL: " + pageDescriptionLink);
				System.out.println("-----Parsing Page Content-----");

				if (pageDescriptionLink.indexOf(url) == -1) {
					pageDescriptionLink = url + pageDescriptionLink;
				}

				try {
					driver.get(pageDescriptionLink);
				} catch (FailingHttpStatusCodeException e) {
					System.out.println("some thing wrong");
				}
				restaurantName = driver.findElement(By.xpath(config.getName())).getText();
				address = driver.findElement(By.xpath(config.getAddress())).getText();
				if (config.getUserRate() != "N/A") {
					userRate = driver.findElement(By.xpath(config.getUserRate())).getText();
				}

				List<WebElement> listimg = driver.findElements(By.tagName("a"));
				String imgVal = "";
				for (WebElement elem : listimg) {
					if (elem.getAttribute("data-lat") != null && elem.getAttribute("data-lng") != null) {
						imgVal = elem.getAttribute("data-lat") + "," + elem.getAttribute("data-lng");
						map = imgVal;
					}
				}
				if (imgVal == "")
					listimg = driver.findElements(By.tagName("img"));
				for (WebElement element : listimg) {
					imgVal = element.getAttribute("src");
					if (imgVal != null) {

						if (imgVal.contains("_106-") || imgVal.contains("_106.") || imgVal.contains("7C10.")) {
							map = imgVal;
						}
					}
				}
				double latitude = 0;
				double longitude = 0;
				if (map == "" || map == null || map.isEmpty()) {
					String latLongs[] = LatitudeAndLongitudeWithPincode.getLatLongPositions(address);

					if (latLongs == null) {
						latitude = 0;
						longitude = 0;
					} else {
						latitude = Double.parseDouble(latLongs[0]);
						longitude = Double.parseDouble(latLongs[1]);
					}
				}
				if (map != null && !map.isEmpty() && map != "") {
					latitude = CommonUtils.splitLat(map);
					longitude = CommonUtils.splitLong(map);
				}
				String district = CommonUtils.splitDistrict(address);
				String newAddress = CommonUtils.splitAddress(address);

				double rate = 0;
				if (!userRate.equals("")) {
					rate = Double.parseDouble(userRate);
					if (rate <= 5) {
						rate = rate * 2;
					}
				}
				District districtDAO = new District();
				Address addressDAO = new Address();
				ProductDetail productDetails = new ProductDetail();

				long districtID = districtManager.checkExitDistrict(district);
				if (districtID == 0) {

					districtDAO.setName(district);
					districtID = districtManager.inserDistrict(districtDAO);

					System.out.println("Add district done");
				}
				if (districtID != 0) {
					addressDAO.setDistrictId(districtID);
					addressDAO.setName(newAddress);
					addressDAO.setLatitude(latitude);
					addressDAO.setLongitude(longitude);
					addressDAO.setRestaurantName(restaurantName);
				}
				long addressID = addressManager.checkAddressExist(addressDAO);
				if (addressID == 0) {
					addressID = addressManager.inserAddress(addressDAO);
					System.out.println("Add address done");
				}
				if (addressID != 0) {
					productDetails.setProductName(newProductName);
					productDetails.setAddressName(address);
					productDetails.setDistrictName(district);
					productDetails.setLatitude(latitude);
					productDetails.setLongitude(longitude);
					productDetails.setRate(rate);
					productDetails.setRestaurantName(restaurantName);
					productDetails.setThumbPath(thumbpath);
					productDetails.setUrlRelate(pageDescriptionLink);
					productDetails.setAddressId(addressID);
					productDetails.setSource(url);

					ProductDetail productDAO = productManager.checkProductExist(productDetails);
					if (productDAO == null) {
						productManager.insertProductDetail(productDetails);
						countAdded++;
						System.out.println("Add to database success");
					} else {
						countExits++;
						System.out.println("Cannot add to database");
						int exist = Integer.parseInt(num_of_exits);
						if (countExits == exist) {
							System.out.println("more than " + exist + " exist record, closing your parser");
							driver.close();
							return "done";
						}
					}
				}
			}
			driver.close();
			System.out.println("Sucessfull Added Record: " + countAdded);
			System.out.println("Exist Record: " + countExits);
			long estimatedTime = System.nanoTime() - startTime;
			double seconds = (double) estimatedTime / 1000000000.0;
			System.out.println("Elapsed time: " + seconds);
			return "done";
		} catch (Exception ex) {
			System.out.println("STOP PARSE");
		}
		return "done";
	}

	@Override
	public String staticParse(String numPage, String noPage, String url) {
		// TODO Auto-generated method stub
		long startTime = System.nanoTime();
		WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_38, false);
		try {
			int numOfPage = 0;
			int noOfPage = 0;
			int countAdded = 0;
			int countExist = 0;
			if (numPage != null && !numPage.isEmpty()) {
				numOfPage = Integer.parseInt(numPage);
			} else {
				noOfPage = Integer.parseInt(noPage);
			}
			System.out.println(numOfPage);
			System.out.println(noOfPage);

			// lay url page
			String xmlFilePath = getPageConfigFilePath();
			PageDTOList tmpPage = XMLUtils.unmarshallPage(xmlFilePath);

			List<PageDTO> pageConfigs = tmpPage.getConfig();

			PageDTO pageConfig = new PageDTO();
			for (PageDTO pageCfg : pageConfigs) {
				if (pageCfg.getSite().equals(url)) {
					pageConfig = pageCfg;
					break;
				}
			}
			xmlFilePath = getParserConfigFilePath();
			ConfigDTOList tmp = XMLUtils.unmarshall(xmlFilePath);
			List<ConfigDTO> configs = tmp.getConfig();
			System.out.println("Config Size: " + configs.size());

			ConfigDTO config = new ConfigDTO();
			for (ConfigDTO cfg : configs) {
				if (cfg.getSite().equals(url)) {
					config = cfg;
					break;
				}
			}

			System.out.println("Page Config Link: " + pageConfig.getSite());
			System.out.println("Page Config Xpath: " + pageConfig.getXpath());

			int no = 1;
			if (numOfPage > 0) {
				while (no <= numOfPage) {
					if (no == 1) {
						System.out.println("no = 1");
						driver.get(pageConfig.getLinkPage());
					} else {
						System.out.println("no = " + no);
						if (pageConfig.getNextPage().equals("N/A")) {
							System.out.println("STOP BY N/A");
							break;
						}
						try {
							if (pageConfig.getNextPage().indexOf('=') == -1) {
								driver.get(pageConfig.getLinkPage() + pageConfig.getNextPage() + "/" + no);
							} else {
								driver.get(pageConfig.getLinkPage() + pageConfig.getNextPage() + no);
							}
						} catch (FailingHttpStatusCodeException e) {
							System.out.println("Error Occurred");
							return "fail";
						}
					}
					no++;

					String restaurantName = "", address = "", productName = "", userRate = "", map = "";
					String thumbpath = "";
					// list
					List<PageDataDTO> pageList = new ArrayList<PageDataDTO>();
					List<WebElement> despageUrls = driver.findElements(By.xpath(pageConfig.getXpath()));
					List<WebElement> productNames = driver.findElements(By.xpath(pageConfig.getFoodName()));
					List<WebElement> images = driver.findElements(By.xpath(pageConfig.getImage()));
					for (int i = 0; i < despageUrls.size(); i++) {
						PageDataDTO page = new PageDataDTO();
						page.setNextPageUrl(despageUrls.get(i).getAttribute("href"));
						page.setProductName(productNames.get(i).getText());
						page.setImageLink(images.get(i).getAttribute("src"));
						pageList.add(page);
					}
					System.out.println("--Number of record: " + pageList.size());
					for (int i = 0; i < pageList.size(); i++) {
						String pageDescriptionLink = pageList.get(i).getNextPageUrl();
						productName = pageList.get(i).getProductName();
						String newProductName = CommonUtils.splitName(productName);
						thumbpath = pageList.get(i).getImageLink();

						System.out.println("==Parsing Page URL: " + pageDescriptionLink);
						System.out.println("-----Parsing Page Content-----");

						if (pageDescriptionLink.indexOf(url) == -1) {
							pageDescriptionLink = url + pageDescriptionLink;
						}

						try {
							driver.get(pageDescriptionLink);
						} catch (FailingHttpStatusCodeException e) {
							System.out.println("some thing wrong");
						}
						restaurantName = driver.findElement(By.xpath(config.getName())).getText();
						address = driver.findElement(By.xpath(config.getAddress())).getText();
						if (config.getUserRate() != "N/A") {
							userRate = driver.findElement(By.xpath(config.getUserRate())).getText();
						}

						List<WebElement> listimg = driver.findElements(By.tagName("a"));
						String imgVal = "";
						for (WebElement elem : listimg) {
							if (elem.getAttribute("data-lat") != null && elem.getAttribute("data-lng") != null) {
								imgVal = elem.getAttribute("data-lat") + "," + elem.getAttribute("data-lng");
								map = imgVal;
							}
						}
						if (imgVal == "")
							listimg = driver.findElements(By.tagName("img"));
						for (WebElement element : listimg) {
							imgVal = element.getAttribute("src");
							if (imgVal != null) {

								if (imgVal.contains("_106-") || imgVal.contains("_106.") || imgVal.contains("7C10.")) {
									map = imgVal;
								}
							}
						}
						double latitude = 0;
						double longitude = 0;
						if (map == "" || map == null || map.isEmpty()) {
							String latLongs[] = LatitudeAndLongitudeWithPincode.getLatLongPositions(address);

							if (latLongs == null) {
								latitude = 0;
								longitude = 0;
							} else {
								latitude = Double.parseDouble(latLongs[0]);
								longitude = Double.parseDouble(latLongs[1]);
							}
						}
						if (map != null && !map.isEmpty() && map != "") {
							latitude = CommonUtils.splitLat(map);
							longitude = CommonUtils.splitLong(map);
						}
						String district = CommonUtils.splitDistrict(address);
						String newAddress = CommonUtils.splitAddress(address);

						double rate = 0;
						if (!userRate.equals("")) {
							rate = Double.parseDouble(userRate);
							if (rate <= 5) {
								rate = rate * 2;
							}
						}
						District districtDAO = new District();
						Address addressDAO = new Address();
						ProductDetail productDetails = new ProductDetail();

						long districtID = districtManager.checkExitDistrict(district);
						if (districtID == 0) {

							districtDAO.setName(district);
							districtID = districtManager.inserDistrict(districtDAO);
						}
						addressDAO.setDistrictId(districtID);
						addressDAO.setName(newAddress);
						addressDAO.setLatitude(latitude);
						addressDAO.setLongitude(longitude);
						addressDAO.setRestaurantName(restaurantName);
						long addressID = addressManager.checkAddressExist(addressDAO);
						if (addressID == 0) {
							addressID = addressManager.inserAddress(addressDAO);
						}
						productDetails.setProductName(newProductName);
						productDetails.setAddressName(address);
						productDetails.setDistrictName(district);
						productDetails.setLatitude(latitude);
						productDetails.setLongitude(longitude);
						productDetails.setRate(rate);
						productDetails.setRestaurantName(restaurantName);
						productDetails.setThumbPath(thumbpath);
						productDetails.setUrlRelate(pageDescriptionLink);
						productDetails.setAddressId(addressID);
						productDetails.setSource(url);

						ProductDetail productDAO = productManager.checkProductExist(productDetails);
						if (productDAO == null) {
							productManager.insertProductDetail(productDetails);
							countAdded++;
							System.out.println("Add to database success");
						} else {
							countExist++;
							System.out.println("Cannot add to database");
							int exist = Integer.parseInt(num_of_exits);
							if (countExist == exist) {
								System.out.println("more than " + exist + " exist record, closing your parser");
								driver.close();
								return "done";
							}
						}
					}
				}
			} else {
				System.out.println("no = " + noOfPage);
				driver.get(pageConfig.getLinkPage());
				try {
					if (pageConfig.getNextPage().indexOf('=') == -1) {
						driver.get(pageConfig.getLinkPage() + pageConfig.getNextPage() + "/" + noOfPage);
					} else {
						driver.get(pageConfig.getLinkPage() + pageConfig.getNextPage() + noOfPage);
					}
				} catch (FailingHttpStatusCodeException e) {
					System.out.println("Error Occurred");
					return "fail";
				}
				System.out.println("XPath Link: " + pageConfig.getXpath());

				String restaurantName = "", address = "", productName = "", userRate = "", map = "";
				String thumbpath = "";
				// list
				List<PageDataDTO> pageList = new ArrayList<PageDataDTO>();
				List<WebElement> despageUrls = driver.findElements(By.xpath(pageConfig.getXpath()));
				List<WebElement> productNames = driver.findElements(By.xpath(pageConfig.getFoodName()));
				List<WebElement> images = driver.findElements(By.xpath(pageConfig.getImage()));
				for (int i = 0; i < despageUrls.size(); i++) {
					PageDataDTO page = new PageDataDTO();
					page.setNextPageUrl(despageUrls.get(i).getAttribute("href"));
					page.setProductName(productNames.get(i).getText());
					page.setImageLink(images.get(i).getAttribute("src"));
					pageList.add(page);
				}
				System.out.println("--Number of record: " + pageList.size());
				for (int i = 0; i < pageList.size(); i++) {
					String pageDescriptionLink = pageList.get(i).getNextPageUrl();
					productName = pageList.get(i).getProductName();
					String newProductName = CommonUtils.splitName(productName);
					thumbpath = pageList.get(i).getImageLink();

					System.out.println("==Parsing Page URL: " + pageDescriptionLink);
					System.out.println("-----Parsing Page Content-----");

					if (pageDescriptionLink.indexOf(url) == -1) {
						pageDescriptionLink = url + pageDescriptionLink;
					}

					try {
						driver.get(pageDescriptionLink);
					} catch (FailingHttpStatusCodeException e) {
						System.out.println("some thing wrong");
					}
					restaurantName = driver.findElement(By.xpath(config.getName())).getText();
					address = driver.findElement(By.xpath(config.getAddress())).getText();
					if (config.getUserRate() != "N/A") {
						userRate = driver.findElement(By.xpath(config.getUserRate())).getText();
					}

					List<WebElement> listimg = driver.findElements(By.tagName("a"));
					String imgVal = "";
					for (WebElement elem : listimg) {
						if (elem.getAttribute("data-lat") != null && elem.getAttribute("data-lng") != null) {
							imgVal = elem.getAttribute("data-lat") + "," + elem.getAttribute("data-lng");
							map = imgVal;
						}
					}
					if (imgVal == "")
						listimg = driver.findElements(By.tagName("img"));
					for (WebElement element : listimg) {
						imgVal = element.getAttribute("src");
						if (imgVal != null) {

							if (imgVal.contains("_106-") || imgVal.contains("_106.") || imgVal.contains("7C10.")) {
								map = imgVal;
							}
						}
					}
					double latitude = 0;
					double longitude = 0;
					if (map == "" || map == null || map.isEmpty()) {
						String latLongs[] = LatitudeAndLongitudeWithPincode.getLatLongPositions(address);

						if (latLongs == null) {
							latitude = 0;
							longitude = 0;
						} else {
							latitude = Double.parseDouble(latLongs[0]);
							longitude = Double.parseDouble(latLongs[1]);
						}
					}
					if (map != null && !map.isEmpty() && map != "") {
						latitude = CommonUtils.splitLat(map);
						longitude = CommonUtils.splitLong(map);
					}
					String district = CommonUtils.splitDistrict(address);
					String newAddress = CommonUtils.splitAddress(address);

					double rate = 0;
					if (!userRate.equals("")) {
						rate = Double.parseDouble(userRate);
						if (rate <= 5) {
							rate = rate * 2;
						}
					}
					District districtDAO = new District();
					Address addressDAO = new Address();
					ProductDetail productDetails = new ProductDetail();

					long districtID = districtManager.checkExitDistrict(district);
					if (districtID == 0) {

						districtDAO.setName(district);
						districtID = districtManager.inserDistrict(districtDAO);

						System.out.println("Add district done");
					}
					addressDAO.setDistrictId(districtID);
					addressDAO.setName(newAddress);
					addressDAO.setLatitude(latitude);
					addressDAO.setLongitude(longitude);
					addressDAO.setRestaurantName(restaurantName);
					long addressID = addressManager.checkAddressExist(addressDAO);
					if (addressID == 0) {
						addressID = addressManager.inserAddress(addressDAO);
						System.out.println("Add address done");
					}
					productDetails.setProductName(newProductName);
					productDetails.setAddressName(address);
					productDetails.setDistrictName(district);
					productDetails.setLatitude(latitude);
					productDetails.setLongitude(longitude);
					productDetails.setRate(rate);
					productDetails.setRestaurantName(restaurantName);
					productDetails.setThumbPath(thumbpath);
					productDetails.setUrlRelate(pageDescriptionLink);
					productDetails.setAddressId(addressID);
					productDetails.setSource(url);

					ProductDetail productDAO = productManager.checkProductExist(productDetails);
					if (productDAO == null) {
						productManager.insertProductDetail(productDetails);
						countAdded++;
						System.out.println("Add to database success");
					} else {
						countExist++;
						System.out.println("Cannot add to database");
						int exist = Integer.parseInt(num_of_exits);
						if (countExist == exist) {
							System.out.println("more than " + exist + " exist record, closing your parser");
							driver.close();
							return "done";
						}
					}
				}
			}
			driver.close();
			System.out.println("Sucessfull Added Record: " + countAdded);
			System.out.println("Exist Record: " + countExist);
			long estimatedTime = System.nanoTime() - startTime;
			double seconds = (double) estimatedTime / 1000000000.0;
			System.out.println("Elapsed time: " + seconds);
			return "done";
		} catch (Exception ex) {
			System.out.println("STOP PARSING");
		}
		return "done";
	}
}

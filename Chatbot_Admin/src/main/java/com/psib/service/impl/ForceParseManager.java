package com.psib.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(ForceParseManager.class);

	private String num_of_exits = SpringPropertiesUtil.getProperty("num_exist");
	private String num_of_page = SpringPropertiesUtil.getProperty("num_page");
	private String num_of_scroll = SpringPropertiesUtil.getProperty("num_scroll");
	private String rating_coefficient = SpringPropertiesUtil.getProperty("rating_coefficient");

	@Override
	public String timerAutomaticParse() throws IOException {
		int numOfScroll = Integer.parseInt(num_of_scroll);
		String numOfPage = num_of_page;
		String pageConfigXML = getPageConfigFilePath();
		logger.info("Page config: " + pageConfigXML);

		String xmlFilePath = pageConfigXML;
		if (!new File(xmlFilePath).exists()) {
			File file = new File(xmlFilePath);
			file.createNewFile();
			xmlFilePath = file.getPath();
		}
		PageDTOList pages = XMLUtils.unmarshallPage(xmlFilePath);
		if (pages == null) {
			pages = new PageDTOList();
			PageDTO page = new PageDTO();
			page.getPages();

		}
		List<PageDTO> pageCfig = pages.getConfig();
		for (int i = 0; i < pageCfig.size(); i++) {
			if (pageCfig.get(i).getNextPage().equals("N/A")) {
				dynamicParse(numOfScroll, pageCfig.get(i).getSite());
			} else {
				String noPage = "";
				staticParse(numOfPage, noPage, pageCfig.get(i).getSite());
			}
		}
		return "done";
	}

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
	public String dynamicParse(int numOfPage, String url) {
		// TODO Auto-generated method stub
		long startTime = System.nanoTime();
		WebDriver driver = new FirefoxDriver();
		String handler = driver.getWindowHandle();
		System.setProperty("WindowsHandler", handler);
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
			logger.info("Config Size: " + configs.size());

			ConfigDTO config = new ConfigDTO();
			for (ConfigDTO cfg : configs) {
				if (cfg.getSite().equals(url)) {
					config = cfg;
					break;
				}
			}

			logger.info("Page Config Link: " + pageConfig.getSite());
			logger.info("Page Config Xpath: " + pageConfig.getXpath());

			driver.manage().deleteAllCookies();
			driver.get(pageConfig.getLinkPage());
			logger.info("Link PAGE: " + pageConfig.getLinkPage());
			By selBy = By.tagName("body");
			int initialHeight = driver.findElement(selBy).getSize().getHeight();
			for (int temp = 1; temp < numOfPage; temp++) {
				initialHeight = driver.findElement(selBy).getSize().getHeight();

				// Scroll to bottom
				((JavascriptExecutor) driver).executeScript("scroll(0," + initialHeight + ");");

				logger.info("Sleeping... wleepy " + temp);
				Thread.sleep(2000);
			}
			logger.info("XPath Link: " + pageConfig.getXpath());

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
			int total = 0;
			total = pageList.size();
			logger.info("--Number of record: " + pageList.size());

			int countAdded = 0;
			int countExist = 0;
			
			for (int i = 0; i < pageList.size(); i++) {
				String pageDescriptionLink = pageList.get(i).getNextPageUrl();
				productName = pageList.get(i).getProductName();
				String newProductName = CommonUtils.splitName(productName);
				thumbpath = pageList.get(i).getImageLink();

				logger.info("==Parsing Page URL: " + pageDescriptionLink);
				logger.info("-----Parsing Page Content-----");

				if (pageDescriptionLink.indexOf(url) == -1) {
					pageDescriptionLink = url + pageDescriptionLink;
				}

				try {
					driver.get(pageDescriptionLink);
				} catch (FailingHttpStatusCodeException e) {
					logger.info("some thing wrong");
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
				String latlong = "" + latitude + "," + longitude;
				String district = CommonUtils.splitDistrict(latlong);
				String newAddress = CommonUtils.splitAddress(district, address);
				
				double rate = 0;
				double rateCoe = Double.parseDouble(config.getRatingCoefficient());
				double rateCoefficient = Double.parseDouble(rating_coefficient);
				if (!userRate.equals("")) {
					rate = (Double.parseDouble(userRate)*rateCoefficient)/rateCoe;
				}
				District districtDAO = new District();
				Address addressDAO = new Address();
				ProductDetail productDetails = new ProductDetail();

				if (!district.isEmpty() && !address.isEmpty() && !newAddress.isEmpty()) {
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
						logger.info("Add to database success");
					} else {
						countExist++;
						logger.info("Cannot add to database");
						int exist = Integer.parseInt(num_of_exits);
						if (countExist == exist) {
							logger.info("more than " + exist + " exist record, closing your parser");
							driver.close();
							return "done";
						}
					}
				}
			}
			driver.close();
			logger.info("Sucessfull Added Record: " + countAdded);
			logger.info("Exist Record: " + countExist);
			logger.info("Add fail: "+ (total - (countAdded+countExist)));
			long estimatedTime = System.nanoTime() - startTime;
			double seconds = (double) estimatedTime / 1000000000.0;
			logger.info("Elapsed time: " + seconds);
			return "done";
		} catch (Exception ex) {
			logger.info("STOP PARSE "+ex.getMessage());
		}
		return "done";
	}

	@Override
	public String staticParse(String numPage, String noPage, String url) {
		// TODO Auto-generated method stub
		long startTime = System.nanoTime();
		WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_38, false);
		logger.info("Thread Id: "+Thread.currentThread().getId());
		try {
			int numOfPage = 0;
			int noOfPage = 0;
			int countAdded = 0;
			int countExist = 0;
			int total = 0;
			if (numPage != null && !numPage.isEmpty()) {
				numOfPage = Integer.parseInt(numPage);
			} else {
				noOfPage = Integer.parseInt(noPage);
			}
			logger.info("" + numOfPage);
			logger.info("" + noOfPage);

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
			logger.info("Config Size: " + configs.size());

			ConfigDTO config = new ConfigDTO();
			for (ConfigDTO cfg : configs) {
				if (cfg.getSite().equals(url)) {
					config = cfg;
					break;
				}
			}

			logger.info("Page Config Link: " + pageConfig.getSite());
			logger.info("Page Config Xpath: " + pageConfig.getXpath());

			int no = 1;
			if (numOfPage > 0) {
				while (no <= numOfPage) {
					if (no == 1) {
						logger.info("no = 1");
						driver.get(pageConfig.getLinkPage());
					} else {
						logger.info("no = " + no);
						if (pageConfig.getNextPage().equals("N/A")) {
							logger.info("STOP BY N/A");
							break;
						}
						try {
							if (pageConfig.getNextPage().indexOf('=') == -1) {
								driver.get(pageConfig.getLinkPage() + pageConfig.getNextPage() + "/" + no);
							} else {
								driver.get(pageConfig.getLinkPage() + pageConfig.getNextPage() + no);
							}
						} catch (FailingHttpStatusCodeException e) {
							logger.info("Error Occurred");
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
					total = pageList.size();
					logger.info("--Number of record: " + pageList.size());
					for (int i = 0; i < pageList.size(); i++) {
						String pageDescriptionLink = pageList.get(i).getNextPageUrl();
						productName = pageList.get(i).getProductName();
						String newProductName = CommonUtils.splitName(productName);
						thumbpath = pageList.get(i).getImageLink();

						logger.info("==Parsing Page URL: " + pageDescriptionLink);
						logger.info("-----Parsing Page Content-----");

						if (pageDescriptionLink.indexOf(url) == -1) {
							pageDescriptionLink = url + pageDescriptionLink;
						}

						try {
							driver.get(pageDescriptionLink);
						} catch (FailingHttpStatusCodeException e) {
							logger.info("some thing wrong");
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
						String latlong = "" + latitude + "," + longitude;
						String district = CommonUtils.splitDistrict(latlong);
						String newAddress = CommonUtils.splitAddress(district, address);
						double rate = 0;
						double rateCoe = Double.parseDouble(config.getRatingCoefficient());
						double rateCoefficient = Double.parseDouble(rating_coefficient);
						if (!userRate.equals("")) {
							rate = (Double.parseDouble(userRate)*rateCoefficient)/rateCoe;
						}
						District districtDAO = new District();
						Address addressDAO = new Address();
						ProductDetail productDetails = new ProductDetail();
						if (!district.isEmpty() && !address.isEmpty() && !newAddress.isEmpty()) {
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
								logger.info("Add to database success");
							} else {
								countExist++;
								logger.info("Cannot add to database");
								int exist = Integer.parseInt(num_of_exits);
								if (countExist == exist) {
									logger.info("more than " + exist + " exist record, closing your parser");
									driver.close();
									return "done";
								}
							}
						}
					}
				}
			} else {
				logger.info("no = " + noOfPage);
				driver.get(pageConfig.getLinkPage());
				try {
					if (pageConfig.getNextPage().indexOf('=') == -1) {
						driver.get(pageConfig.getLinkPage() + pageConfig.getNextPage() + "/" + noOfPage);
					} else {
						driver.get(pageConfig.getLinkPage() + pageConfig.getNextPage() + noOfPage);
					}
				} catch (FailingHttpStatusCodeException e) {
					logger.info("Error Occurred");
					return "fail";
				}
				logger.info("XPath Link: " + pageConfig.getXpath());

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
				total = pageList.size();
				logger.info("--Number of record: " + pageList.size());
				for (int i = 0; i < pageList.size(); i++) {
					String pageDescriptionLink = pageList.get(i).getNextPageUrl();
					productName = pageList.get(i).getProductName();
					String newProductName = CommonUtils.splitName(productName);
					thumbpath = pageList.get(i).getImageLink();

					logger.info("==Parsing Page URL: " + pageDescriptionLink);
					logger.info("-----Parsing Page Content-----");

					if (pageDescriptionLink.indexOf(url) == -1) {
						pageDescriptionLink = url + pageDescriptionLink;
					}

					try {
						driver.get(pageDescriptionLink);
					} catch (FailingHttpStatusCodeException e) {
						logger.info("some thing wrong");
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
					String latlong = "" + latitude + "," + longitude;
					String district = CommonUtils.splitDistrict(latlong);
					String newAddress = CommonUtils.splitAddress(latlong, address);

					double rate = 0;
					double rateCoe = Double.parseDouble(config.getRatingCoefficient());
					double rateCoefficient = Double.parseDouble(rating_coefficient);
					if (!userRate.equals("")) {
						rate = (Double.parseDouble(userRate)*rateCoefficient)/rateCoe;
					}
					District districtDAO = new District();
					Address addressDAO = new Address();
					ProductDetail productDetails = new ProductDetail();

					if (!district.isEmpty() && !address.isEmpty() && !newAddress.isEmpty()) {
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
							logger.info("Add to database success");
						} else {
							countExist++;
							logger.info("Cannot add to database");
							int exist = Integer.parseInt(num_of_exits);
							if (countExist == exist) {
								logger.info("more than " + exist + " exist record, closing your parser");
								driver.close();
								return "done";
							}
						}
					}
				}
			}
			driver.close();
			logger.info("Sucessfull Added Record: " + countAdded);
			logger.info("Exist Record: " + countExist);
			logger.info("Add fail: "+ (total - (countAdded+countExist)));
			long estimatedTime = System.nanoTime() - startTime;
			double seconds = (double) estimatedTime / 1000000000.0;
			logger.info("Elapsed time: " + seconds);
			return "done";
		} catch (Exception ex) {
			logger.info("STOP PARSING "+ex.getMessage());
		}
		return "done";
	}

}

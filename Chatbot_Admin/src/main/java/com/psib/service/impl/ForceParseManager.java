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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.psib.controller.CrawlerController;
import com.psib.dao.IAddressDao;
import com.psib.dao.IDistrictDao;
import com.psib.dao.IFileServerDao;
import com.psib.dao.IProductDetailDao;
import com.psib.dto.configuration.ConfigDTO;
import com.psib.dto.configuration.ConfigDTOList;
import com.psib.dto.configuration.PageDTO;
import com.psib.dto.configuration.PageDTOList;
import com.psib.model.Address;
import com.psib.model.District;
import com.psib.model.ProductDetail;
import com.psib.service.IAddressManager;
import com.psib.service.IDistrictManager;
import com.psib.service.IForceParseManager;
import com.psib.service.IProductManager;
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
			// lay xpath foodName
			List<String> pagexpaths = new ArrayList<String>();
			pagexpaths.add(pageConfig.getXpath());
			pagexpaths.add(pageConfig.getFoodName());
			pagexpaths.add(pageConfig.getImage());

			System.out.println("Page Config Link: " + pageConfig.getSite());
			System.out.println("Page Config Xpath: " + pageConfig.getXpath());

			// chuan bi parse
			// parse voi trang foody.
			// goi trinh duyet moi bang selenium

			// page = webClient.getPage(pageConfig.getLinkPage());
			driver.manage().deleteAllCookies();
			driver.get(pageConfig.getLinkPage());
			System.out.println("Link PAGE: " + pageConfig.getLinkPage());
			// tu dong scroll chuot xuong cuoi cung
			By selBy = By.tagName("body");
			int initialHeight = driver.findElement(selBy).getSize().getHeight();
			int currentHeight = 0;
			for (int temp = 1; temp < numOfPage; temp++) {
				initialHeight = driver.findElement(selBy).getSize().getHeight();

				// Scroll to bottom
				((JavascriptExecutor) driver).executeScript("scroll(0," + initialHeight + ");");

				System.out.println("Sleeping... wleepy " + temp);
				Thread.sleep(2000);
				currentHeight = driver.findElement(selBy).getSize().getHeight();
			}

			// no++;
			// HtmlPage page = webClient.getPage(pageConfig.getLinkPage());
			// bat dau lay link de vao trang
			System.out.println("XPath Link: " + pageConfig.getXpath());

			String restaurantName = "", address = "", foodName = "", userRate = "", map = "";
			String thumbpath = "";
			// list
			List<String> pageUrl = new ArrayList<String>();
			List<String> foodResult = new ArrayList<String>();
			List<String> image = new ArrayList<String>();
			int counter = 0;
			List<WebElement> foodnamepage = null;

			for (String foodname : pagexpaths) {
				counter++;
				foodnamepage = driver.findElements(By.xpath(foodname));
				for (WebElement food : foodnamepage) {
					switch (counter) {
					case 1:
						pageUrl.add(food.getAttribute("href"));
						break;
					case 2:
						foodResult.add(food.getText());
						break;
					case 3:
						image.add(food.getAttribute("src"));
					}
				}

			}
			System.out.println("--Number of record: " + foodnamepage.size());

			// for (WebElement data : pages) {
			//
			// }

			// Load file XPath cÃƒÂ³ sÃ¡ÂºÂµn
			int countAdded = 0;
			int countExits = 0;
			for (int i = 0; i < pageUrl.size(); i++) {
				String str = pageUrl.get(i);
				foodName = foodResult.get(i);
				thumbpath = image.get(i);
				System.out.println("==Parsing Page URL: " + str);
				System.out.println("-----Parsing Page Content-----");
				CommonUtils common = new CommonUtils();
				String newProductName = common.splitName(foodName);

				counter++;

				if (str.indexOf(url) == -1) {
					str = url + str;
				}

				// Parse tÃ¡Â»Â«ng page
				try {
					driver.get(str);
				} catch (FailingHttpStatusCodeException e) {
					e.printStackTrace();
				}
				int count = 0;
				int countTam = 0;
				// lay config page
				xmlFilePath = getParserConfigFilePath();
				ConfigDTOList tmp = XMLUtils.unmarshall(xmlFilePath);
				List<ConfigDTO> configs = tmp.getConfig();
				System.out.println("Config Size: " + configs.size());

				// tao list xpath
				List<String> xpaths = new ArrayList<String>();
				ConfigDTO config = new ConfigDTO();
				for (ConfigDTO cfg : configs) {
					if (cfg.getSite().equals(url)) {
						config = cfg;
						break;
					}
				}
				// add xpath
				xpaths.add(config.getName());
				xpaths.add(config.getAddress());
				String rat = config.getUserRate();
				if (rat.equals("N/A")) {
					for (String xpath : xpaths) {
						System.out.println("Xpath: " + xpath + " : " + count);
						count++;
						if (xpath != null) {
							List<WebElement> contents = (List<WebElement>) driver.findElements(By.xpath(xpath));
							for (WebElement content : contents) {
								countTam++;
								switch (count) {
								case 1:
									restaurantName = content.getText();
									break;
								case 2:
									address = content.getText();
									break;
								// case 3:
								// userRate = content.getText();
								// break;
								// case 4:
								// map =
								// content.getAttribute("src");
								// break;
								}
								// contents.add(tmpString);
							}
						}
					}
				}
				int countT = 0;
				if (!rat.equals("N/A")) {
					xpaths.add(config.getUserRate());
					for (String xpath : xpaths) {
						System.out.println("Xpath: " + xpath + " : " + countT);
						countT++;
						if (xpath != null) {
							List<WebElement> contents = (List<WebElement>) driver.findElements(By.xpath(xpath));
							for (WebElement content : contents) {
								countTam++;
								switch (countT) {
								case 1:
									restaurantName = content.getText();
									break;
								case 2:
									address = content.getText();
									break;
								case 3:
									userRate = content.getText();
									break;
								// case 4:
								// map =
								// content.getAttribute("src");
								// break;
								}
								// contents.add(tmpString);
							}
						}
					}
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
					System.out.println("District exist");
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
					productDetails.setUrlRelate(str);
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
			driver.close();
			System.out.println("STOP PARSE");
			return "fail";
		}
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
			int countExits = 0;
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
			// lay xpath foodName
			List<String> pagexpaths = new ArrayList<String>();
			pagexpaths.add(pageConfig.getXpath());
			pagexpaths.add(pageConfig.getFoodName());
			pagexpaths.add(pageConfig.getImage());

			System.out.println("Page Config Link: " + pageConfig.getSite());
			System.out.println("Page Config Xpath: " + pageConfig.getXpath());

			// chuan bi parse
			int no = 1;
			// goi trinh duyet moi bang selenium
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

					String restaurantName = "", address = "", foodName = "", userRate = "", map = "";
					String thumbpath = "";
					// list
					List<String> pageUrl = new ArrayList<String>();
					List<String> foodResult = new ArrayList<String>();
					List<String> image = new ArrayList<String>();
					int counter = 0;
					List<WebElement> results = null;

					for (String pagexpath : pagexpaths) {
						counter++;
						results = driver.findElements(By.xpath(pagexpath));
						for (WebElement result : results) {
							switch (counter) {
							case 1:
								pageUrl.add(result.getAttribute("href"));
								break;
							case 2:
								foodResult.add(result.getText());
								break;
							case 3:
								image.add(result.getAttribute("src"));
							}
						}

					}
					System.out.println("--Number of record: " + results.size());

					// for (WebElement data : pages) {
					//
					// }

					// Load file XPath
					for (int i = 0; i < pageUrl.size(); i++) {
						String str = pageUrl.get(i);
						foodName = foodResult.get(i);
						thumbpath = image.get(i);
						System.out.println("==Parsing Page URL: " + str);
						System.out.println("-----Parsing Page Content-----");
						CommonUtils common = new CommonUtils();
						String newProductName = common.splitName(foodName);
						counter++;

						if (str.indexOf(url) == -1) {
							str = url + str;
						}

						// Parse từng page
						try {
							driver.get(str);
						} catch (FailingHttpStatusCodeException e) {
							e.printStackTrace();
						}
						int count = 0;
						int countTam = 0;
						int countT = 0;
						// lay config page
						xmlFilePath = getParserConfigFilePath();
						ConfigDTOList tmp = XMLUtils.unmarshall(xmlFilePath);
						List<ConfigDTO> configs = tmp.getConfig();
						System.out.println("Config Size: " + configs.size());

						// tao list xpath
						List<String> xpaths = new ArrayList<String>();
						ConfigDTO config = new ConfigDTO();
						for (ConfigDTO cfg : configs) {
							if (cfg.getSite().equals(url)) {
								config = cfg;
								break;
							}
						}
						// add xpath
						xpaths.add(config.getName());
						xpaths.add(config.getAddress());
						String rat = config.getUserRate();
						if (rat.equals("N/A")) {
							for (String xpath : xpaths) {
								System.out.println("Xpath: " + xpath + " : " + count);
								count++;
								if (xpath != null) {
									List<WebElement> contents = (List<WebElement>) driver.findElements(By.xpath(xpath));
									for (WebElement content : contents) {
										countTam++;
										switch (count) {
										case 1:
											restaurantName = content.getText();
											break;
										case 2:
											address = content.getText();
											break;
										// case 3:
										// userRate = content.getText();
										// break;
										// case 4:
										// map =
										// content.getAttribute("src");
										// break;
										}
										// contents.add(tmpString);
									}
								}
							}
						}
						if (!rat.equals("N/A")) {
							xpaths.add(config.getUserRate());
							for (String xpath : xpaths) {
								System.out.println("XpathT: " + xpath + " : " + countT);
								countT++;
								if (xpath != null) {
									List<WebElement> contents = (List<WebElement>) driver.findElements(By.xpath(xpath));
									for (WebElement content : contents) {
										countTam++;
										switch (countT) {
										case 1:
											restaurantName = content.getText();
											break;
										case 2:
											address = content.getText();
											break;
										case 3:
											userRate = content.getText();
											break;
										// case 4:
										// map =
										// content.getAttribute("src");
										// break;
										}
										// contents.add(tmpString);
									}
								}
							}
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
						productDetails.setUrlRelate(str);
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

				String restaurantName = "", address = "", foodName = "", userRate = "", map = "";
				String thumbpath = "";
				// list
				List<String> pageUrl = new ArrayList<String>();
				List<String> foodResult = new ArrayList<String>();
				List<String> image = new ArrayList<String>();
				int counter = 0;
				List<WebElement> foodnamepage = null;

				for (String foodname : pagexpaths) {
					counter++;
					foodnamepage = driver.findElements(By.xpath(foodname));
					for (WebElement food : foodnamepage) {
						switch (counter) {
						case 1:
							pageUrl.add(food.getAttribute("href"));
							break;
						case 2:
							foodResult.add(food.getText());
							break;
						case 3:
							image.add(food.getAttribute("src"));
						}
					}

				}
				System.out.println("--Number of record: " + foodnamepage.size());

				// for (WebElement data : pages) {
				//
				// }

				// Load file XPath cÃƒÂ³ sÃ¡ÂºÂµn
				for (int i = 0; i < pageUrl.size(); i++) {
					String str = pageUrl.get(i);
					foodName = foodResult.get(i);
					thumbpath = image.get(i);
					System.out.println("==Parsing Page URL: " + str);
					System.out.println("-----Parsing Page Content-----");
					CommonUtils common = new CommonUtils();
					String newProductName = common.splitName(foodName);
					counter++;

					if (str.indexOf(url) == -1) {
						str = url + str;
					}

					// Parse tÃ¡Â»Â«ng page
					try {
						driver.get(str);
					} catch (FailingHttpStatusCodeException e) {
						e.printStackTrace();
					}
					int count = 0;
					int countTam = 0;
					// lay config page
					xmlFilePath = getParserConfigFilePath();
					ConfigDTOList tmp = XMLUtils.unmarshall(xmlFilePath);
					List<ConfigDTO> configs = tmp.getConfig();
					System.out.println("Config Size: " + configs.size());

					// tao list xpath
					List<String> xpaths = new ArrayList<String>();
					ConfigDTO config = new ConfigDTO();
					for (ConfigDTO cfg : configs) {
						if (cfg.getSite().equals(url)) {
							config = cfg;
							break;
						}
					}
					// add xpath
					xpaths.add(config.getName());
					xpaths.add(config.getAddress());

					String rat = config.getUserRate();
					if (rat.equals("N/A")) {
						for (String xpath : xpaths) {
							System.out.println("Xpath: " + xpath + " : " + count);
							count++;
							if (xpath != null) {
								List<WebElement> contents = (List<WebElement>) driver.findElements(By.xpath(xpath));
								for (WebElement content : contents) {
									countTam++;
									switch (count) {
									case 1:
										restaurantName = content.getText();
										break;
									case 2:
										address = content.getText();
										break;
									// case 3:
									// userRate = content.getText();
									// break;
									// case 4:
									// map =
									// content.getAttribute("src");
									// break;
									}
									// contents.add(tmpString);
								}
							}
						}
					}
					int countT = 0;
					if (!rat.equals("N/A")) {
						xpaths.add(config.getUserRate());
						for (String xpath : xpaths) {

							System.out.println("Xpath: " + xpath + " : " + countT);
							countT++;
							if (xpath != null) {
								List<WebElement> contents = (List<WebElement>) driver.findElements(By.xpath(xpath));
								for (WebElement content : contents) {
									countTam++;
									switch (count) {
									case 1:
										restaurantName = content.getText();
										break;
									case 2:
										address = content.getText();
										break;
									case 3:
										userRate = content.getText();
										break;
									// case 4:
									// map =
									// content.getAttribute("src");
									// break;
									}
									// contents.add(tmpString);
								}
							}
						}
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
					System.out.println("District exist");
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
					productDetails.setUrlRelate(str);
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
		} catch (

		Exception ex) {
			driver.close();
			System.out.println("STOP PARSING");
			return "fail";
		}
	}
}

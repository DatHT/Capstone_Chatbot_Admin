package com.psib.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.psib.dao.impl.ProductDetailDao;
import com.psib.dto.configuration.ConfigDTO;
import com.psib.dto.configuration.ConfigDTOList;
import com.psib.dto.configuration.PageDTO;
import com.psib.dto.configuration.PageDTOList;
import com.psib.model.Address;
import com.psib.model.District;
import com.psib.model.Product;
import com.psib.model.ProductDetail;
import com.psib.service.IAddressManager;
import com.psib.service.IDistrictManager;
import com.psib.service.IProductManager;
import com.psib.util.CommonUtils;
import com.psib.util.XMLUtils;

@Controller
public class CrawlerController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String pageConfigXML = "D:/Capstone/pageconfig.xml";
	private static final String parserConfigXML = "D:/Capstone/parserconfig.xml";
	private static final Logger logger = LoggerFactory.getLogger(CrawlerController.class);

	@Autowired
	private IProductManager productManager;
	@Autowired
	private IAddressManager addressManager;
	@Autowired
	private IDistrictManager districtManager;

	@RequestMapping(value = "/manualAddFood", method = RequestMethod.GET)
	public String addManual(Model model) {
		return "manualAddFood";
	}

	@RequestMapping(value = "/staticParse", method = RequestMethod.GET)
	public String staticParse(@RequestParam String btnAction, Model model, HttpServletRequest request,
			HttpServletResponse response) throws InterruptedException {
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

		if (btnAction.equals("STOP")) {
			HttpSession session = request.getSession();
			System.out.println("STOP PARSING");
			session.setAttribute("MESSAGE",
					"Automatic Crawler is still running. If you want Force STOP this process, please close Openned FireFox LoadData Windows");
			return "errorPage";
		}
		if (btnAction.equals("StaticParse")) {

			WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_38, false);

			try {
				long startTime = System.nanoTime();
				HttpSession session = request.getSession();
				String numPage = request.getParameter("txtPage");
				String url = request.getParameter("txtLinkPage");
				System.out.println("url =" + url);
				String noPage = request.getParameter("txtNoPage");
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
				String xmlFilePath = pageConfigXML;
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
								session.setAttribute("MESSAGE", "The process has stopped at page " + (no - 1));
								return "errorPage";
							}
						}
						no++;
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
							xmlFilePath = parserConfigXML;
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
										List<WebElement> contents = (List<WebElement>) driver
												.findElements(By.xpath(xpath));
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
									System.out.println("Xpath: " + xpath + " : " + countT);
									countT++;
									if (xpath != null) {
										List<WebElement> contents = (List<WebElement>) driver
												.findElements(By.xpath(xpath));
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
								if (elem.getAttribute("data-lat") != null
												&& elem.getAttribute("data-lng") != null) {
											imgVal = elem.getAttribute("data-lat") + ","
													+ elem.getAttribute("data-lng");
										}
							}
							map = imgVal;
							double latitude = CommonUtils.splitLat(map);
							double longitude = CommonUtils.splitLong(map);

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
							
							boolean checkExistDistrict = districtManager.checkExitDistrict(district);
							if (!checkExistDistrict) {
								countAdded++;
								districtDAO.setName(district);
								long districtID = districtManager.insert(districtDAO);
								addressDAO.setDistrictId(districtID);
								addressDAO.setName(newAddress);
								addressDAO.setLatitude(latitude);
								addressDAO.setLongitude(longitude);
								addressDAO.setRestaurantName(restaurantName);

								long addressID = addressManager.checkExitsAddress(addressDAO);
								if (addressID == 0) {
									addressID = addressManager.insert(addressDAO);
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
										if(productDAO==null){
											productManager.insertProductDetail(productDetails);
										}
									}
								}
								System.out.println("Add to database success");
							}
							else{
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
						session.setAttribute("MESSAGE", "The process has stopped at page " + (noOfPage - 1));
						return "errorPage";
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
						xmlFilePath = parserConfigXML;
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
							if (elem.getAttribute("data-lat") != null
											&& elem.getAttribute("data-lng") != null) {
										imgVal = elem.getAttribute("data-lat") + ","
												+ elem.getAttribute("data-lng");
									}
						}
						map = imgVal;
						double latitude = CommonUtils.splitLat(map);
						double longitude = CommonUtils.splitLong(map);

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
						
						boolean checkExistDistrict = districtManager.checkExitDistrict(district);
						if (!checkExistDistrict) {
							countAdded++;
							districtDAO.setName(district);
							long districtID = districtManager.insert(districtDAO);
							addressDAO.setDistrictId(districtID);
							addressDAO.setName(newAddress);
							addressDAO.setLatitude(latitude);
							addressDAO.setLongitude(longitude);
							addressDAO.setRestaurantName(restaurantName);

							long addressID = addressManager.checkExitsAddress(addressDAO);
							if (addressID == 0) {
								addressID = addressManager.insert(addressDAO);
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
									if(productDAO==null){
										productManager.insertProductDetail(productDetails);
									}
								}
							}
							System.out.println("Add to database success");
						}
						else{
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
				session.setAttribute("MESSAGE", "Force parse success! New data has been inserted to storage!");
				return "success";
			} catch (Exception e) {
				System.out.println("STOP PARSING");
				HttpSession session = request.getSession();
				session.setAttribute("MESSAGE", "STOP! Force Parse Has Been STOP!");
				return "errorPage";
			}
		}
		return "";
	}

	@RequestMapping(value = "/dynamicParse", method = RequestMethod.GET)
	public String dynamicParse(@RequestParam String btnAction, Model model, HttpServletRequest request,
			HttpServletResponse response) throws InterruptedException {
		if (btnAction.equals("STOP")) {
			HttpSession session = request.getSession();
			System.out.println("STOP PARSING");
			session.setAttribute("MESSAGE",
					"Automatic Crawler is still running. If you want Force STOP this process, please close Openned FireFox LoadData Windows");
			return "errorPage";
		}
		if (btnAction.equals("DynamicParse")) {
			WebDriver driver = new FirefoxDriver();
			try {
				long startTime = System.nanoTime();
				HttpSession session = request.getSession();
				String numPage = request.getParameter("txtPage");
				int numOfPage = Integer.parseInt(numPage);
				String url = request.getParameter("txtLinkPage");
				System.out.println("url =" + url);

				// lay url page
				String xmlFilePath = pageConfigXML;
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
					xmlFilePath = parserConfigXML;
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

					List<WebElement> listimg = driver.findElements(By.tagName("img"));

					String imgVal = "";
					String imgvalue = "";
					for (WebElement elem : listimg) {
						imgVal = elem.getAttribute("src");
						if (imgVal.contains("106-") || imgVal.contains("106.")) {
							imgvalue = imgVal;
						}
					}
					map = imgvalue;
					double latitude = CommonUtils.splitLat(map);
					double longitude = CommonUtils.splitLong(map);
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

					boolean checkExistDistrict = districtManager.checkExitDistrict(district);
					if (!checkExistDistrict) {
						countAdded++;
						districtDAO.setName(district);
						long districtID = districtManager.insert(districtDAO);
						addressDAO.setDistrictId(districtID);
						addressDAO.setName(newAddress);
						addressDAO.setLatitude(latitude);
						addressDAO.setLongitude(longitude);
						addressDAO.setRestaurantName(restaurantName);

						long addressID = addressManager.checkExitsAddress(addressDAO);
						if (addressID == 0) {
							addressID = addressManager.insert(addressDAO);
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
								}
							}
						}
						System.out.println("Add to database success");
					} else {
						countExits++;
						System.out.println("Cannot add to database");
					}
				}
				driver.close();
				System.out.println("Sucessfull Added Record: " + countAdded);
				System.out.println("Exist Record: " + countExits);
				long estimatedTime = System.nanoTime() - startTime;
				double seconds = (double) estimatedTime / 1000000000.0;
				System.out.println("Elapsed time: " + seconds);
				session.setAttribute("MESSAGE", "Force parse success! New data has been inserted to storage!");
				return "success";
			} catch (Exception e) {
				System.out.println("STOP PARSE");
				HttpSession session = request.getSession();
				session.setAttribute("MESSAGE", "STOP! Force Parse Has Been STOP!");
				return "errorPage";
			}
		}
		return "";
	}

	@RequestMapping(value = "/crawler", method = RequestMethod.GET)
	public String configData(Model model, HttpServletRequest request, HttpServletResponse respone) throws IOException {
		// String realPath = CommonUtils.getPath();
		// get Config
		String xmlFilePath = parserConfigXML;
		if (!new File(xmlFilePath).exists()) {
			File file = new File(xmlFilePath);
			file.createNewFile();
			xmlFilePath = file.getPath();
		}
		ConfigDTOList configs = XMLUtils.unmarshall(xmlFilePath);
		if (configs == null) {
			configs = new ConfigDTOList();
			ConfigDTO config = new ConfigDTO();
			config.getConfigs();
		}
		HttpSession session = request.getSession();
		String str = XMLUtils.marshallConfigToString(configs);
		String quote = "'";
		char c = quote.charAt(0);
		str = str.replace('"', c);
		System.out.println(str);
		session.setAttribute("INFOCONFIG", str);

		// get Page
		xmlFilePath = pageConfigXML;
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
		str = XMLUtils.marshallPageToString(pages);
		str = str.replace('"', c);

		System.out.println(str);
		session.setAttribute("INFOPAGE", str);

		return "configuration";
	}

	@RequestMapping(value = "/processServlet", method = RequestMethod.GET)
	public String configGuration(@RequestParam String btnAction, Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (btnAction.equals("Set List Page")) {
			String str = request.getParameter("txtURL");
			System.out.println(str);
			HttpSession session = request.getSession();
			String result = "";
			String[] str_array = str.split("/");
			for (int i = 0; i < 3; i++) {
				result = result + str_array[i] + "/";
			}
			result = result.substring(0, result.length() - 1);
			System.out.println(result);
			session.setAttribute("URL", result);
			session.setAttribute("LINKPAGE", str);

			ServletContext servletContext = request.getSession().getServletContext();
			String filePath = servletContext.getRealPath("/resources/");
			System.out.println(filePath);
			String htmlFilePath = filePath + "tmp.html";
			WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_38, false);
			driver.get(str);

			String pageSource = driver.getPageSource();

			String source = CommonUtils.makeContentPage(pageSource, result);
			// String source = pageSource;
			String inputLine;
			StringBuffer res;
			res = new StringBuffer();
			try {
				InputStream stream = new ByteArrayInputStream(source.getBytes(Charset.forName("UTF-8")));
				BufferedReader in;
				in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
				while ((inputLine = in.readLine()) != null) {
					// System.out.println(in.readLine());
					res.append(CommonUtils.htmlEncode(inputLine) + "\n");
					// res.append(inputLine + "\n");
				}
				// System.out.println(res);
				in.close();
			} catch (Exception e) {
				System.out.println("Cannot encoding");
			}

			BufferedWriter bwr = new BufferedWriter(new FileWriter(htmlFilePath));

			// write contents of StringBuffer to a file
			bwr.write(res.toString());

			// flush the stream
			bwr.flush();

			// close the stream
			bwr.close();
			// FileUtils.writeStringToFile(new File(htmlFilePath), source);
			return "setListPage";
		}
		if (btnAction.equals("Set Parser Config")) {
			String str = request.getParameter("txtURL");
			System.out.println(str);
			HttpSession session = request.getSession();
			String result = "";
			String[] str_array = str.split("/");
			for (int i = 0; i < 3; i++) {
				result = result + str_array[i] + "/";
			}
			result = result.substring(0, result.length() - 1);
			System.out.println(result);
			session.setAttribute("URL", result);
			session.setAttribute("LINKPAGE", str);

			ServletContext servletContext = request.getSession().getServletContext();
			String filePath = servletContext.getRealPath("/resources/");
			System.out.println(filePath);
			String htmlFilePath = filePath + "tmp.html";

			WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_38, false);
			driver.get(str);

			String pageSource = driver.getPageSource();
			driver.close();
			String source = CommonUtils.makeContentPage(pageSource, result);
			String inputLine;
			StringBuffer res;
			res = new StringBuffer();
			try {
				InputStream stream = new ByteArrayInputStream(source.getBytes(Charset.forName("UTF-8")));
				BufferedReader in;
				in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
				while ((inputLine = in.readLine()) != null) {
					// System.out.println(in.readLine());
					res.append(CommonUtils.htmlEncode(inputLine) + "\n");
					// res.append(inputLine + "\n");
				}
				// System.out.println(res);
				in.close();
			} catch (Exception e) {
				System.out.println("Cannot encoding");
			}

			BufferedWriter bwr = new BufferedWriter(new FileWriter(htmlFilePath));

			// write contents of StringBuffer to a file
			bwr.write(res.toString());

			// flush the stream
			bwr.flush();

			// close the stream
			bwr.close();
			// FileUtils.writeStringToFile(new File(htmlFilePath), source,
			// "UTF-8");
			return "setParserConfig";
		}
		if (btnAction.equals("AddNewPageList"))

		{
			java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
			java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

			String xpath = request.getParameter("PAGE");
			HttpSession session = request.getSession();
			String url = (String) session.getAttribute("URL");
			String linkPage = (String) session.getAttribute("LINKPAGE");
			String nextPage = request.getParameter("NEXTPAGE");
			String contentPage = request.getParameter("txtPageContent");

			System.out.println("Content: " + contentPage);
			System.out.println("Link Page: " + linkPage);
			System.out.println("Next Page: " + nextPage);

			String next = "N/A";
			if (nextPage != null && !nextPage.isEmpty()) {
				WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_38, false);

				driver.get(linkPage);

				// go to get nextPage

				List<String> pageUrl = new ArrayList<String>();
				List<WebElement> content = driver.findElements(By.xpath(xpath));
				for (WebElement data : content) {
					pageUrl.add(data.getAttribute("href"));
				}

				for (String str : pageUrl) {
					System.out.println(str);
				}
				if (nextPage.contains("id='btnMorePlace'"))
					;
				{
					nextPage = "//*[@id='btnMorePlace']";
				}
				List<WebElement> nextPages = driver.findElements(By.xpath(nextPage));
				System.out.println("Co gi do sai sai000: " + nextPages.toString());
				for (WebElement data : nextPages) {
					System.out.println("Co gi do sai sai111: " + data.getAttribute("href"));
				}
				String numNextPage = "";
				System.out.println("Co gi do sai sai: " + nextPages.get(0).getAttribute("href"));
				if (nextPages.size() > 1) {
					numNextPage = nextPages.get(1).getAttribute("href");
				} else {
					numNextPage = nextPages.get(0).getAttribute("href");
				}
				if (numNextPage.indexOf("http") == -1) {
					numNextPage = url + numNextPage;
				}

				// get Next Page
				if (numNextPage.contains("?")) {
					int index = numNextPage.indexOf("?");
					next = numNextPage.substring(index + 1, numNextPage.length());
					index = next.indexOf("=");
					next = "&" + next.substring(0, index + 1);
				} else {
					numNextPage = numNextPage.substring(linkPage.length());

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
				}
				driver.close();
				System.out.println("NEXTPAGE:" + numNextPage);
				System.out.println(next);
			}
			System.out.println("Next Page: " + next);
			// get numNextPage
			List<String> xpaths = new ArrayList<String>();
			xpaths.add(request.getParameter("PAGE"));
			xpaths.add(request.getParameter("FOODNAME"));
			xpaths.add(request.getParameter("IMAGE"));

			// Parse to List combine links
			// for (String : xpaths) {
			// System.out.println(xpath);
			// }
			PageDTO newPage = new PageDTO(url, linkPage, xpaths.get(0), xpaths.get(1), xpaths.get(2), next);

			String xmlFilePath = pageConfigXML;
			PageDTOList pages = XMLUtils.unmarshallPage(xmlFilePath);
			System.out.println(xmlFilePath);

			boolean add;
			if (pages == null) {
				pages = new PageDTOList();
				PageDTO page = new PageDTO();
				page.getPages();

			}
			boolean exist = false;
			List<PageDTO> checkExist = pages.getConfig();
			int pos = -1;
			for (PageDTO cfg : checkExist) {
				pos++;
				if (cfg.getSite().equals(url)) {
					exist = true;
					break;
				}
			}
			System.out.println(exist);// Existed
			// solution update
			if (exist) {
				pages.getConfig().remove(pos);
			}
			checkExist.add(newPage);
			add = XMLUtils.marshallToFilePage(pages, xmlFilePath);
			System.out.println(add);
			ServletContext servletContext = request.getSession().getServletContext();
			String filePath = servletContext.getRealPath("/resources/");
			System.out.println(filePath);
			String htmlFilePath = filePath + "tmp.html";
			File file = new File(htmlFilePath);
			if (file.exists()) {
				file.delete();
				System.out.println("File deleted");
			}
			if (add) {
				String result = "";
				String[] str_array = contentPage.split("/");
				for (int i = 0; i < 3; i++) {
					result = result + str_array[i] + "/";
				}
				result = result.substring(0, result.length() - 1);
				System.out.println(result);
				session.setAttribute("URL", result);
				session.setAttribute("LINKPAGE", contentPage);

				WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_38, false);
				driver.get(contentPage);

				String pageSource = driver.getPageSource();
				driver.close();
				String source = CommonUtils.makeContentPage(pageSource, result);
				String inputLine;
				StringBuffer res;
				res = new StringBuffer();
				try {
					InputStream stream = new ByteArrayInputStream(source.getBytes(Charset.forName("UTF-8")));
					BufferedReader in;
					in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
					while ((inputLine = in.readLine()) != null) {
						// System.out.println(in.readLine());
						res.append(CommonUtils.htmlEncode(inputLine) + "\n");
						// res.append(inputLine + "\n");
					}
					// System.out.println(res);
					in.close();
				} catch (Exception e) {
					System.out.println("Cannot encoding");
				}

				BufferedWriter bwr = new BufferedWriter(new FileWriter(htmlFilePath));

				// write contents of StringBuffer to a file
				bwr.write(res.toString());

				// flush the stream
				bwr.flush();

				// close the stream
				bwr.close();
				// session.setAttribute("MESSAGE", "New page configuration has
				// been inserted to storage!");
				return "setParserConfig";
			} else {
				session.setAttribute("MESSAGE", "Page configuration fails!");
				return "errorPage";
			}

		}
		if (btnAction.equals("AddNewConfiguration")) {
			HttpSession session = request.getSession();
			String url = (String) session.getAttribute("URL");
			// session.setAttribute("URL", null);
			String rate = "N/A";
			String userRate = request.getParameter("USER_RATE");
			if (userRate == null || userRate.isEmpty() || userRate == "") {
				userRate = rate;
			}
			System.out.println("user rate: " + userRate);
			List<String> xpaths = new ArrayList<String>();
			String addr = request.getParameter("ADDRESS");
			if (addr.contains("id='place-address'")) {
				addr = "//*[@id='place-address']";
			}
			xpaths.add(request.getParameter("NAME"));
			xpaths.add(addr);
			xpaths.add(userRate);

			for (String xpath : xpaths) {
				System.out.println(xpath);
			}

			ConfigDTO newConfig = new ConfigDTO(url, xpaths.get(0), xpaths.get(1), xpaths.get(2));

			// Load file XPath cÃƒÂ³ sÃ¡ÂºÂµn
			String xmlFilePath = parserConfigXML;
			ConfigDTOList configs = XMLUtils.unmarshall(xmlFilePath);

			// ...
			boolean add;
			if (configs == null) {
				configs = new ConfigDTOList();
				ConfigDTO config = new ConfigDTO();
				config.getConfigs();

			}
			boolean exist = false;
			List<ConfigDTO> checkExist = configs.getConfig();
			int pos = -1;
			for (ConfigDTO cfg : checkExist) {
				pos++;
				if (cfg.getSite().equals(url)) {
					exist = true;
					break;
				}
			}
			System.out.println("EXIST" + exist + pos);// Existed
			// solution update
			if (exist) {
				checkExist.remove(pos);
			}
			checkExist.add(newConfig);
			add = XMLUtils.marshallToFile(configs, xmlFilePath);
			session.setAttribute("CONFIG", newConfig.getSite());
			System.out.println(add);
			ServletContext servletContext = request.getSession().getServletContext();
			String filePath = servletContext.getRealPath("/resources/");
			System.out.println(filePath);
			String htmlFilePath = filePath + "tmp.html";
			File file = new File(htmlFilePath);
			if (file.exists()) {
				file.delete();
				System.out.println("File deleted");
			}
			if (add) {
				session.setAttribute("MESSAGE", "New page configuration has been inserted to storage!");
				return "success";
			} else {
				session.setAttribute("MESSAGE", "Page configuration fails!");
				return "errorPage";
			}
		}
		return "";
	}
}

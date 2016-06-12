/**
 * 
 */
package com.psib.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.psib.dto.configuration.ConfigDTO;
import com.psib.dto.configuration.ConfigDTOList;
import com.psib.dto.configuration.PageDTO;
import com.psib.dto.configuration.PageDTOList;
import com.psib.model.Address;
import com.psib.model.District;
import com.psib.model.Product;
import com.psib.model.ProductAddress;
import com.psib.service.IAddressManager;
import com.psib.service.IDistrictManager;
import com.psib.service.IFoodManager;
import com.psib.service.IProductAddressManager;
import com.psib.service.IProductManager;
import com.psib.util.CommonUtils;
import com.psib.util.XMLUtils;

/**
 * @author DatHT June 4, 2016
 */

@Controller
public class DataConfigController {

	private static final Logger logger = LoggerFactory.getLogger(DataConfigController.class);

	@Autowired
	private IAddressManager addressManager;
	@Autowired
	private IProductAddressManager productAddressManager;
	@Autowired
	private IProductManager productManager;
	@Autowired
	private IDistrictManager districtManager;

	@RequestMapping(value = "/dataConfig", method = RequestMethod.GET)
	public String loadProduct(Model model) {
		model.addAttribute("ACTIVE", "dataConfig");
		return "dataConfig";
	}

	@RequestMapping(value = "/manualAddFood", method = RequestMethod.GET)
	public String addManual(Model model) {
		return "manualAddFood";
	}

	@RequestMapping(value = "/ForceParse", method = RequestMethod.GET)
	public String forceParse(@RequestParam String btnAction, Model model, HttpServletRequest request,
			HttpServletRequest response) throws InterruptedException {
		if (btnAction.equals("ForceParse")) {
			long startTime = System.nanoTime();
			HttpSession session = request.getSession();
			String numPage = request.getParameter("txtPage");
			int numOfPage = Integer.parseInt(numPage);
			// get ThreadId-> Id
			Thread thread = Thread.currentThread();
			long threadId = thread.getId();
			session.setAttribute("THREADID", threadId);
			System.out.println(thread.getName());
			String url = request.getParameter("txtLinkPage");
			System.out.println("url =" + url);
			// lay config page
			String xmlFilePath = "D:/Capstone/parserconfig.xml";
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
			xpaths.add(config.getUserRate());
			xpaths.add(config.getMap());
			for (String xpath : xpaths) {
				System.out.println("Xpath: " + xpath);
			}
			// lay url page
			xmlFilePath = "D:/Capstone/pageconfig.xml";
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
			// parse voi trang foody.
			// goi trinh duyet moi bang selenium

			// page = webClient.getPage(pageConfig.getLinkPage());
			WebDriver driver = new FirefoxDriver();
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
			String orginalLink = pageConfig.getXpath();
			System.out.println("XPath Link: " + pageConfig.getXpath());
			List<WebElement> pages = driver.findElements(By.xpath(orginalLink));

			String restaurantName = null, address = null, foodName = null, userRate = null, map = null;
			String thumbpath = null;
			// list
			List<String> pageUrl = new ArrayList<String>();
			List<String> foodResult = new ArrayList<String>();
			List<String> image = new ArrayList<String>();
			int counter = 0;
			for (String foodname : pagexpaths) {
				counter++;
				List<WebElement> foodnamepage = driver.findElements(By.xpath(foodname));
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

			// for (WebElement data : pages) {
			//
			// }
			System.out.println("Food Name: " + foodResult);
			System.out.println("Page URL: " + pageUrl);
			System.out.println("Image: " + image);

			// Load file XPath có sẵn
			for (int i = 0; i < pageUrl.size(); i++) {
				String str = pageUrl.get(i);
				foodName = foodResult.get(i);
				thumbpath = image.get(i);
				System.out.println("Parsing Page URL: " + str);
				System.out.println("Parsing Name: " + foodName);
				CommonUtils common = new CommonUtils();
				String newProductName = common.splitName(foodName);
				System.out.println("New Name: " + newProductName);
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
				int count = 0, countTam = 0;
				for (String xpath : xpaths) {
					System.out.println(xpath + count);
					count++;
					if (xpath != null) {
						List<WebElement> contents = (List<WebElement>) driver.findElements(By.xpath(xpath));
						for (WebElement content : contents) {
							String tmpString;
							countTam++;
							tmpString = content.getText();
							switch (count) {
							case 1:
								restaurantName = tmpString;
								break;
							case 2:
								address = tmpString;
								break;
							case 3:
								userRate = tmpString;
								break;
							case 4:
								map = content.getAttribute("src");
								break;
							}
							// contents.add(tmpString);
						}
					}
				}
				CommonUtils getLongLat = common.splitLongLat(map);
				CommonUtils getAddress = common.splitAddress(address);

				double latitude = getLongLat.getLatitude();
				double longitude = getLongLat.getLongitude();

				String district = getAddress.getDistrict();
				String newAddress = getAddress.getAddress();

				Product productDAO = new Product();
				District districtDAO = new District();
				Address addressDAO = new Address();
				ProductAddress productAddressDAO = new ProductAddress();

				boolean checkExitFood = productManager.checkExitsProduct(str, newProductName);

				if (!checkExitFood) {
					productDAO.setName(newProductName);
					productDAO.setRate(userRate);
					productDAO.setSource(url);
					productDAO.setThumbPath(thumbpath);
					productDAO.setUrlRelate(str);
					productManager.insert(productDAO);

					boolean addDistrict = false;
					boolean addFoodAddress = false;
					boolean checkDistrict = districtManager.checkExitDistrict(district);
					if (!checkDistrict) {
						districtDAO.setName(district);
						districtManager.insert(districtDAO);
					}
					boolean checkAddress = addressManager.checkExitsAddress(newAddress);
					if (!checkAddress) {
						long districtID = districtManager.getDistrictIDByDistrictName(district);
						if (districtID != 0) {
							addressDAO.setDistrictId(districtID);
							addressDAO.setLatitude(latitude);
							addressDAO.setLongitude(longitude);
							addressDAO.setName(newAddress);
							addressDAO.setRestaurantName(restaurantName);
							addressManager.insert(addressDAO);
						}
					}
					long productID = productManager.getProductIDByName(newProductName);
					long addressID = addressManager.getAddressIDByAddressName(newAddress);
					if (productID != 0 && addressID != 0) {
						boolean checkFoodAdress = productAddressManager.checkExitFoodAddress(productID, addressID);
						if (!checkFoodAdress) {
							productAddressDAO.setProductId(productID);
							productAddressDAO.setAddressId(addressID);
							productAddressDAO.setAddressName(address);
							productAddressDAO.setDistrictName(district);
							productAddressDAO.setLatitude(latitude);
							productAddressDAO.setLongitude(longitude);
							productAddressDAO.setProductName(newProductName);
							productAddressDAO.setRate(userRate);
							productAddressDAO.setRestaurantName(restaurantName);
							productAddressDAO.setThumbPath(thumbpath);
							productAddressDAO.setUrlRelate(str);
							productAddressManager.insert(productAddressDAO);
						}
					}
					System.out.println("======Add To Database Success=====");
				} else {
					System.out.println("Product Exited");
				}
			}
			long estimatedTime = System.nanoTime() - startTime;
			double seconds = (double) estimatedTime / 1000000000.0;
			System.out.println("Elapsed time: " + seconds);
			session = request.getSession();
			session.setAttribute("MESSAGE", "Force parse success! New data has been inserted to storage!");
			return "success";
		}
		return "";
	}

	@RequestMapping(value = "/configData", method = RequestMethod.GET)
	public String configData(Model model, HttpServletRequest request, HttpServletResponse respone)
			throws UnsupportedEncodingException {
		// String realPath = CommonUtils.getPath();
		// get Config
		String xmlFilePath = "D:/Capstone/parserconfig.xml";
		ConfigDTOList configs = XMLUtils.unmarshall(xmlFilePath);
		HttpSession session = request.getSession();
		String str = XMLUtils.marshallConfigToString(configs);
		String quote = "'";
		char c = quote.charAt(0);
		str = str.replace('"', c);
		System.out.println(str);
		session.setAttribute("INFOCONFIG", str);

		// get Page
		xmlFilePath = "D:/Capstone/pageconfig.xml";
		PageDTOList pages = XMLUtils.unmarshallPage(xmlFilePath);

		str = XMLUtils.marshallPageToString(pages);
		str = str.replace('"', c);
		session.setAttribute("INFOPAGE", str);

		return "Configuration";
	}

	@RequestMapping(value = "/ProcessServlet", method = RequestMethod.GET)
	public String configGuration(@RequestParam String btnAction, Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if(btnAction.equals("STOP")){
			String xmlFilePath = "D:/Capstone/parserconfig.xml";
			ConfigDTOList configs = XMLUtils.unmarshall(xmlFilePath);
			HttpSession session = request.getSession();
			String str = XMLUtils.marshallConfigToString(configs);
			String quote = "'";
			char c = quote.charAt(0);
			str = str.replace('"', c);
			System.out.println(str);
			session.setAttribute("INFOCONFIG", str);

			// get Page
			xmlFilePath = "D:/Capstone/pageconfig.xml";
			PageDTOList pages = XMLUtils.unmarshallPage(xmlFilePath);

			str = XMLUtils.marshallPageToString(pages);
			str = str.replace('"', c);
			session.setAttribute("INFOPAGE", str);

			return "Configuration";
		}
		if (btnAction.equals("Set List Page")) {
			String str = request.getParameter("txtURL");
			System.out.println(str);
			URL url = new URL(str);
			// BufferedReader in;
			// InputStreamReader inputStreamReader = new
			// InputStreamReader(url.openStream(), "UTF8");
			// in = new BufferedReader(inputStreamReader);

			String inputLine;
			StringBuffer res;
			res = new StringBuffer();
			HttpSession session = request.getSession();

			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
				// van de la o in.readLine()
				while ((inputLine = in.readLine()) != null) {
					// System.out.println(in.readLine());
					res.append(CommonUtils.htmlEncode(inputLine) + "\n");
					// res.append(inputLine + "\n");
				}
				// System.out.println(res);
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("MESSAGE", "There are errors, please check your connection or input URL!");
				return "error";
			}
			// Save file
			String path = new Object() {
			}.getClass().getClassLoader().getResource("").getPath();
			String fullPath = URLDecoder.decode(path, "UTF-8");
			String pathArr[] = fullPath.split("WEB-INF/classes/");
			System.out.println("full path: " + fullPath);
			System.out.println("path: " + pathArr[0]);
			fullPath = pathArr[0] + "resources/";
			String htmlFilePath = fullPath + "tmp.html";
			System.out.println("start save");
			System.out.println(htmlFilePath);
			File file = new File(htmlFilePath);

			BufferedWriter bwr = new BufferedWriter(new FileWriter(file));

			// write contents of StringBuffer to a file
			bwr.write(res.toString());

			// flush the stream
			bwr.flush();

			// close the stream
			bwr.close();
			String result = "";
			String[] str_array = str.split("/");
			for (int i = 0; i < 3; i++) {
				result = result + str_array[i] + "/";
			}
			System.out.println(result);
			session.setAttribute("URL", result);
			session.setAttribute("LINKPAGE", str);
			return "SetListPage";
		}
		if (btnAction.equals("Set Parser Config")) {
			String str = request.getParameter("txtURL");
			System.out.println(str);
			URL url = new URL(str);
			// BufferedReader in;
			// InputStreamReader inputStreamReader = new
			// InputStreamReader(url.openStream(), "UTF8");
			// in = new BufferedReader(inputStreamReader);

			String inputLine;
			StringBuffer res;
			res = new StringBuffer();
			HttpSession session = request.getSession();

			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
				// van de la o in.readLine()
				while ((inputLine = in.readLine()) != null) {
					// System.out.println(in.readLine());
					res.append(CommonUtils.htmlEncode(inputLine) + "\n");
					// res.append(inputLine + "\n");
				}
				// System.out.println(res);
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("MESSAGE", "There are errors, please check your connection or input URL!");
				return "error";
			}
			// Save file
			String path = new Object() {
			}.getClass().getClassLoader().getResource("").getPath();
			String fullPath = URLDecoder.decode(path, "UTF-8");
			String pathArr[] = fullPath.split("WEB-INF/classes/");
			System.out.println("full path: " + fullPath);
			System.out.println("path: " + pathArr[0]);
			fullPath = pathArr[0] + "resources/";
			String htmlFilePath = fullPath + "tmp.html";
			System.out.println("start save");
			System.out.println(htmlFilePath);
			File file = new File(htmlFilePath);

			BufferedWriter bwr = new BufferedWriter(new FileWriter(file));

			// write contents of StringBuffer to a file
			bwr.write(res.toString());

			// flush the stream
			bwr.flush();

			// close the stream
			bwr.close();
			String result = "";
			String[] str_array = str.split("/");
			for (int i = 0; i < 3; i++) {
				result = result + str_array[i] + "/";
			}
			System.out.println(result);
			session.setAttribute("URL", result);
			session.setAttribute("LINKPAGE", str);
			return "SetParserConfig";
		}
		if (btnAction.equals("AddNewPageList")) {
			String xpath = request.getParameter("PAGE");
			HttpSession session = request.getSession();
			String url = (String) session.getAttribute("URL");
			String linkPage = (String) session.getAttribute("LINKPAGE");
			String foodname = request.getParameter("FOODNAME");
			String image = request.getParameter("IMAGE");
			String nextPage = request.getParameter("NEXTPAGE");

			String next = "";
			if (nextPage == "") {

				next = "N/A";
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

			String xmlFilePath = "D:/Capstone/pageconfig.xml";
			PageDTOList pages = XMLUtils.unmarshallPage(xmlFilePath);
			System.out.println(xmlFilePath);

			boolean add;
			if (pages == null) {
				pages = new PageDTOList();
				PageDTO page = new PageDTO();
				page.getPages();

			} else {
				System.out.println("Khac NULL");
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
			if (add) {
				session.setAttribute("MESSAGE", "New page list configuration has been inserted to storage!");
				return "success";
			} else {
				session.setAttribute("MESSAGE", "Some thing fails!");
				return "error";
			}
		}
		if (btnAction.equals("AddNewConfiguration")) {
			HttpSession session = request.getSession();
			String url = (String) session.getAttribute("URL");
			// session.setAttribute("URL", null);
			List<String> xpaths = new ArrayList<String>();
			xpaths.add(request.getParameter("NAME"));
			xpaths.add(request.getParameter("ADDRESS"));
			xpaths.add(request.getParameter("USER_RATE"));
			xpaths.add(request.getParameter("MAP"));

			for (String xpath : xpaths) {
				System.out.println(xpath);
			}

			ConfigDTO newConfig = new ConfigDTO(url, xpaths.get(0), xpaths.get(1), xpaths.get(2), xpaths.get(3));

			// Load file XPath có sẵn
			String xmlFilePath = "D:/Capstone/parserconfig.xml";
			ConfigDTOList configs = XMLUtils.unmarshall(xmlFilePath);

			// ...
			boolean add;
			if (configs == null) {
				configs = new ConfigDTOList();
				ConfigDTO config = new ConfigDTO();
				config.getConfigs();

			} else {
				System.out.println("Khac NULL");
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
			session.setAttribute("CONFIG", configs);
			System.out.println(add);
			if (add) {
				session.setAttribute("MESSAGE", "New configuration has been inserted to storage!");
				return "success";
			} else {
				session.setAttribute("MESSAGE", "Some thing fails!");
				return "error";
			}
		}
		return "";
	}
}

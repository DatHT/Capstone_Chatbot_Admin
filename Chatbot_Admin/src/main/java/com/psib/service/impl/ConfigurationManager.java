package com.psib.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.psib.dao.IFileServerDao;
import com.psib.dto.configuration.ConfigDTO;
import com.psib.dto.configuration.ConfigDTOList;
import com.psib.dto.configuration.PageDTO;
import com.psib.dto.configuration.PageDTOList;
import com.psib.service.IConfigurationManager;
import com.psib.service.IForceParseManager;
import com.psib.util.CommonUtils;
import com.psib.util.SpringPropertiesUtil;
import com.psib.util.XMLUtils;

@Service
public class ConfigurationManager implements IConfigurationManager {
	@Autowired
	private IForceParseManager forceParseManager;
	@Autowired
	private IFileServerDao fileServerDao;
	private String xmlFilePathFolder;
	
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
	public String loadConfig() throws IOException {
		String pageConfigXML = getPageConfigFilePath();
		System.out.println("Page config: "+pageConfigXML);
		String parserConfigXML = getParserConfigFilePath();
		System.out.println("Paser config: "+parserConfigXML);
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
		String configString = XMLUtils.marshallConfigToString(configs);
		String quote = "'";
		char c = quote.charAt(0);
		configString = configString.replace('"', c);
		System.out.println(configString);

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
		String pageString = XMLUtils.marshallPageToString(pages);
		pageString = pageString.replace('"', c);

		System.out.println(pageString);
		return configString + "," + pageString;
	}

	public static boolean linkExists(String URLName) {
		String responseText = "";
		URL u;
		try {
			u = new URL(URLName);

			HttpURLConnection huc = (HttpURLConnection) u.openConnection();
			huc.setRequestMethod("GET"); // OR huc.setRequestMethod ("HEAD");
			huc.connect();
			int code = huc.getResponseCode();
			System.out.println(code);
			if (code == 200 || code == 302) {
				return true;
			} else {
				return false;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	@Override
	public String loadHtmlContentFromUrl(String linkPage, String url, String htmlFilePath) throws IOException {
		// TODO Auto-generated method stub
		String pageSource = "";
		HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_38);
		if (linkExists(linkPage)) {
			try {
				driver.get(linkPage);
				driver.setJavascriptEnabled(true);
				String javascript = "return arguments[0].innerHTML";
				pageSource = (String) ((JavascriptExecutor) driver).executeScript(javascript,
						driver.findElement(By.tagName("html")));
				pageSource = "<html>" + pageSource + "</html>";

			} catch (StackOverflowError ex) {
				System.out.print("Cannot load this url");
				return "wrong";
			} catch (IllegalStateException ex) {
				return "wrong";
			}
		}
		else{
			return "wrong";
		}
		// catch (WebDriverException ex) {
		// System.out.print("Cannot load this url 2");
		// // return "errorPage";
		// }
		// pageSource= driver.getPageSource();
		driver.close();

		String source = CommonUtils.makeContentPage(pageSource, url);
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
		return "done";
	}

	@Override
	public String getNextPage(String nextPage, String linkPage, String xpath, String url) {
		// TODO Auto-generated method stub
		String next = "N/A";
		if (nextPage != null && !nextPage.isEmpty()) {
			WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_38);

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
			List<WebElement> nextPages = driver.findElements(By.xpath(nextPage));
			String numNextPage = "";
			if (nextPages.size() > 1) {
				numNextPage = nextPages.get(1).getAttribute("href");
			} else {
				numNextPage = nextPages.get(0).getAttribute("href");
			}
			if (numNextPage.indexOf("http") == -1) {
				numNextPage = url + numNextPage;
			}

			// get Next Page
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
			driver.close();
		}
		return next;
	}

	@Override
	public boolean addPageConfig(PageDTO pageDTO, String url) throws IOException {
		// TODO Auto-generated method stub
		String pageConfigXML = forceParseManager.getPageConfigFilePath();
		String xmlFilePath = pageConfigXML;
		PageDTOList pages = XMLUtils.unmarshallPage(xmlFilePath);

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
		// solution update
		if (exist) {
			pages.getConfig().remove(pos);
		}
		checkExist.add(pageDTO);
		add = XMLUtils.marshallToFilePage(pages, xmlFilePath);
		return add;
	}

	@Override
	public boolean addPageDetails(ConfigDTO configDTO, String url) throws IOException {
		// TODO Auto-generated method stub
		String parserConfigXML = forceParseManager.getParserConfigFilePath();
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
		checkExist.add(configDTO);
		add = XMLUtils.marshallToFile(configs, xmlFilePath);

		return add;
	}
}

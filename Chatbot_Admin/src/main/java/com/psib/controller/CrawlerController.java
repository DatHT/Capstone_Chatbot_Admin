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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.psib.dao.IFileServerDao;
import com.psib.dto.configuration.ConfigDTO;
import com.psib.dto.configuration.ConfigDTOList;
import com.psib.dto.configuration.PageDTO;
import com.psib.dto.configuration.PageDTOList;
import com.psib.service.IForceParseManager;
import com.psib.util.CommonUtils;
import com.psib.util.XMLUtils;

@Controller
public class CrawlerController extends HttpServlet {
	/**
	 * 
	 */
	@Autowired
	private IForceParseManager forceParseManager;
	
	private static final long serialVersionUID = 1L;
	

	@RequestMapping(value = "/staticParse", method = RequestMethod.POST)
	public String staticParse(Model model, HttpServletRequest request, HttpServletResponse response)
			throws InterruptedException {
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

		HttpSession session = request.getSession();
		String numPage = request.getParameter("txtPage");
		String url = request.getParameter("txtLinkPage");
		System.out.println("url =" + url);
		String noPage = request.getParameter("txtNoPage");
		String result = forceParseManager.staticParse(numPage, noPage, url);
		if (result == "done") {
			session.setAttribute("MESSAGE", "Force parse success! New data has been inserted to storage!");
			return "success";
		} else {
			session.setAttribute("MESSAGE", "STOP! Force Parse Has Been STOP!");
			return "errorPage";
		}
	}

	@RequestMapping(value = "/dynamicParse", method = RequestMethod.POST)
	public String dynamicParse(@RequestParam String btnAction, Model model, HttpServletRequest request,
			HttpServletResponse response) throws InterruptedException {

		HttpSession session = request.getSession();
		String numPage = request.getParameter("txtPage");
		int numOfPage = Integer.parseInt(numPage);
		String url = request.getParameter("txtLinkPage");
		System.out.println("url =" + url);
		String result = forceParseManager.dynamicParse(numPage, numOfPage, url);
		if (result == "done") {
			session.setAttribute("MESSAGE", "Force parse success! New data has been inserted to storage!");
			return "success";
		} else {
			session.setAttribute("MESSAGE", "STOP! Force Parse Has Been STOP!");
			return "errorPage";
		}

	}

	@RequestMapping(value = "/configuration", method = RequestMethod.GET)
	public String configData(Model model, HttpServletRequest request, HttpServletResponse respone) throws IOException {
		// String realPath = CommonUtils.getPath();
		// get Config
		String pageConfigXML = forceParseManager.getPageConfigFilePath();
		String parserConfigXML = forceParseManager.getParserConfigFilePath();
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

	@RequestMapping(value = "/setListPage", method = RequestMethod.POST)
	public String setListPage(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
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
		String pageSource = "";
		HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_38);
		try {
			driver.get(str);
			driver.setJavascriptEnabled(true);
			String javascript = "return arguments[0].innerHTML";
			pageSource = (String) ((JavascriptExecutor) driver).executeScript(javascript,
					driver.findElement(By.tagName("html")));
			pageSource = "<html>" + pageSource + "</html>";
		} catch (StackOverflowError ex) {
			System.out.print("Cannot load this url");
			session.setAttribute("MESSAGE", "Cannot load this url");
			//return "errorPage";
		}
		catch (WebDriverException ex) {
			System.out.print("Cannot load this url 2");
			session.setAttribute("MESSAGE", "Cannot load this url");
			//return "errorPage";
		}
		//pageSource= driver.getPageSource();
		driver.close();

		String source = CommonUtils.makeContentPage(pageSource, result);
		//String source = pageSource;
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

	@RequestMapping(value = "/setPageConfig", method = RequestMethod.GET)
	public String setPageConfig(@RequestParam String btnAction, Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
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
		// FileUtils.writeStringToFile(new File(htmlFilePath), source,
		// "UTF-8");
		return "setParserConfig";
	}

	@RequestMapping(value = "/addPageList", method = RequestMethod.POST)
	public String addNewPageList(@RequestParam String btnAction, Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException, Exception {

		String pageConfigXML = forceParseManager.getPageConfigFilePath();
		
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
			if (nextPage.contains("id='btnMorePlace'")) {
				nextPage = "//*[@id='btnMorePlace']";
			}
			if (nextPage.contains("@class='large-8 columns'")) {
				nextPage = "//*[@id='result']/div[5]/a[1]";
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
			System.out.println(contentPage);
			String pageSource = "";
			HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_38);
			try {
				driver.get(contentPage);
				driver.setJavascriptEnabled(true);
				String javascript = "return arguments[0].innerHTML";
				pageSource = (String) ((JavascriptExecutor) driver).executeScript(javascript,
						driver.findElement(By.tagName("html")));
				pageSource = "<html>" + pageSource + "</html>";
			} catch (StackOverflowError ex) {
				System.out.print("Cannot load this url");
				session.setAttribute("MESSAGE", "Cannot load this url");
				//return "errorPage";
			}
			catch (WebDriverException ex) {
				System.out.print("Cannot load this url 2");
				session.setAttribute("MESSAGE", "Cannot load this url");
				//return "errorPage";
			}

			//pageSource = driver.getPageSource();
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

	@RequestMapping(value = "/addPageDetails", method = RequestMethod.POST)
	public String addNewConfig(@RequestParam String btnAction, Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String parserConfigXML = forceParseManager.getParserConfigFilePath();
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
}

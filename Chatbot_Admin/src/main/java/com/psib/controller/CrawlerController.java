package com.psib.controller;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psib.constant.CodeManager;
import com.psib.dto.configuration.ConfigDTO;
import com.psib.dto.configuration.PageDTO;
import com.psib.service.IConfigurationManager;
import com.psib.service.IForceParseManager;
import com.psib.util.CommonUtils;

@Controller
public class CrawlerController extends HttpServlet {
	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(CrawlerController.class);
	@Autowired
	private IForceParseManager forceParseManager;
	@Autowired
	private IConfigurationManager configurationManager;

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
	public String dynamicParse(Model model, HttpServletRequest request, HttpServletResponse response)
			throws InterruptedException {

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
		String result = configurationManager.loadConfig();
		String[] config = result.split(",");
		HttpSession session = request.getSession();
		System.out.println(result);
		session.setAttribute("INFOCONFIG", config[0]);
		session.setAttribute("INFOPAGE", config[1]);

		return "configuration";
	}

	@RequestMapping(value = "/setListPage", method = RequestMethod.POST)
	public String setListPage(@RequestParam("txtURL") String linkPage, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		String url = CommonUtils.commonUrl(linkPage);

		HttpSession session = request.getSession();
		session.setAttribute("URL", url);
		session.setAttribute("LINKPAGE", linkPage);

		String htmlFilePath = request.getSession().getServletContext().getRealPath("/resources/") + "tmp.html";
		String rs ="";
		try {
			rs = configurationManager.loadHtmlContentFromUrl(linkPage, url, htmlFilePath);
		} catch (IOException e) {
			logger.info("cannot load this page");
		} catch(IllegalStateException ex){
			logger.info("cannot load this page");
		}
		if(rs=="wrong"){
			session.setAttribute("MESSAGE", "Your Inputted Url is not available or your connection error. Please input again or try another with Url");
			return "errorPage";
		}

		return "setListPage";
	}

	@RequestMapping(value = "/addPageList", method = RequestMethod.POST)
	public String addNewPageList(Model model, HttpServletRequest request, HttpServletResponse response) {

		String xpath = request.getParameter("PAGE");
		String productName = request.getParameter("FOODNAME");
		String image = request.getParameter("IMAGE");
		HttpSession session = request.getSession();
		String url = (String) session.getAttribute("URL");
		String linkPage = (String) session.getAttribute("LINKPAGE");
		String nextPage = request.getParameter("NEXTPAGE");
		String descriptionPage = request.getParameter("txtPageContent");

		String next = configurationManager.getNextPage(nextPage, linkPage, xpath, url);

		PageDTO newPage = new PageDTO(url, linkPage, xpath, productName, image, next);

		boolean add = false;
		try {
			add = configurationManager.addPageConfig(newPage, url);
		} catch (IOException e1) {
			logger.info("cannot add page config");
		}

		String htmlFilePath = request.getSession().getServletContext().getRealPath("/resources/") + "tmp.html";

		File file = new File(htmlFilePath);
		if (file.exists()) {
			file.delete();
			logger.info("File deleted");
		}
		if (add) {
			String result = CommonUtils.commonUrl(descriptionPage);
			session.setAttribute("URL", result);
			session.setAttribute("LINKPAGE", descriptionPage);
			session.setAttribute("PAGECONFIG", newPage);
			try {
				configurationManager.loadHtmlContentFromUrl(descriptionPage, url, htmlFilePath);
			} catch (IOException e) {
				logger.info("cannot load this page");
			}

			return "setParserConfig";
		} else {
			session.setAttribute("MESSAGE", "configuration fails!");
			return "errorPage";
		}
	}

	@RequestMapping(value = "/addPageDetails", method = RequestMethod.POST)
	public String addNewConfig(Model model, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		HttpSession session = request.getSession();
		String url = (String) session.getAttribute("URL");
		String userRate = request.getParameter("USER_RATE");
		String address = request.getParameter("ADDRESS");
		String restaurantName = request.getParameter("NAME");
		String rate = "N/A";
		if (userRate == null || userRate.isEmpty() || userRate == "") {
			userRate = rate;
		}
		ConfigDTO newConfig = new ConfigDTO(url, restaurantName, address, userRate);

		boolean add = false;
		try {
			add = configurationManager.addPageDetails(newConfig, url);
		} catch (IOException ex) {
			logger.info("Cannot add pageDetails Config");
		}
		String htmlFilePath = request.getSession().getServletContext().getRealPath("/resources/") + "tmp.html";
		File file = new File(htmlFilePath);
		if (file.exists()) {
			file.delete();
			System.out.println("File deleted");
		}
		if (add) {
			PageDTO page = (PageDTO) session.getAttribute("PAGECONFIG");
			session.setAttribute("CFS", newConfig);
			System.out.println(""+page.getSite() +": "+ page.getNextPage());
			session.setAttribute("PGS", page);
			System.out.println(""+newConfig.getSite() + ": " + newConfig.getName());
			session.setAttribute("MESSAGE", "New page configuration has been inserted to storage!");
			return "successParse";
		} else {
			session.setAttribute("MESSAGE", "Page configuration fails!");
			return "errorPage";
		}
	}

	@RequestMapping(value = "/checkUrl", method = RequestMethod.POST)
	public @ResponseBody String checkUrl(@RequestParam("url") String url, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String responseText="";
		URL u;
		try {
			u = new URL(url);

			HttpURLConnection huc = (HttpURLConnection) u.openConnection();
			huc.setRequestMethod("GET"); // OR huc.setRequestMethod ("HEAD");
			huc.connect();
			int code = huc.getResponseCode();
			System.out.println(code);
			if(code==200||code==302){
				responseText = CodeManager.DONE;
			}
			else{
				responseText = CodeManager.FAIL;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			responseText = CodeManager.FAIL;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			responseText = CodeManager.FAIL;
		}
		
		return responseText;
	}

}

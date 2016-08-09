package com.psib.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ThreadController {
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/closeThread", method = RequestMethod.POST)
	public String closeThread(Model model, HttpServletRequest request, HttpServletResponse respone) {
		HttpSession session = request.getSession();
		String threadd = System.getProperty("threadID");
		System.out.println("ThreadID: " + threadd);
		long threadID = Long.parseLong(threadd);
		Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
		// Iterate over set to find yours
		for (Thread thread : setOfThread) {
			if (thread.getId() == threadID) {
				thread.stop();
				session.setAttribute("MESSAGE", "Your Parser Has Been STOP");
			}
		}
		return "success";
	}
}

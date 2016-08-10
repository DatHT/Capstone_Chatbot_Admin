package com.psib.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.psib.util.SeleniumUtils;

@Controller
public class ThreadController {

	@RequestMapping(value = "/closeThread", method = RequestMethod.POST)
	public String closeThread(Model model, HttpServletRequest request, HttpServletResponse respone) {
		HttpSession session = request.getSession();
		
		SeleniumUtils.closeDriver();
		session.setAttribute("MESSAGE", "Your Parser Has Been STOP");
		return "success";
	}
}

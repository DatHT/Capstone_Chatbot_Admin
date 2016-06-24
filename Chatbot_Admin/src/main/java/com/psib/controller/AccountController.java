package com.psib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountController {

	@RequestMapping(value = "manageAccount")
	public String manageAccount(Model model) {
		return "manageAccount";
	}
}

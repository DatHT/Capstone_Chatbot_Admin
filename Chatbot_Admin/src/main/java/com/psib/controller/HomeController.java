package com.psib.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String hompage() {
		return "redirect:login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {

		return "login";
	}

	@RequestMapping(value = "/dashBoard", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {

		return "profile";
	}

	@RequestMapping(value = "/activateTimer", method = RequestMethod.GET)
	public String activateTimer(Locale locale, Model model) {
		// set on/off for timer
		System.setProperty("timerActive", "true");
		return "blank";
	}

	@RequestMapping(value = "/deactivateTimer", method = RequestMethod.GET)
	public String deactivateTimer(Locale locale, Model model) {
		// set on/off for timer
		System.setProperty("timerActive", "false");
		return "blank";
	}

}

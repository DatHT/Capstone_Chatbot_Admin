package com.psib.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.psib.dto.DateDto;
import com.psib.service.ILogManager;

@Controller
public class ConversationsController {

	@Autowired
	ILogManager logManager;

	@RequestMapping(value = "/conversations", method = RequestMethod.GET)
	public String loadFullConvertation(Model model) {
		List<DateDto> listDate = logManager.getListDateLog();

		model.addAttribute("DATES", listDate);
		return "conversations";
	}
}

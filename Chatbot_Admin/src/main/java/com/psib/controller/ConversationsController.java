package com.psib.controller;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@RequestMapping(value = "/fullConversation", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String getFullConversation(@RequestParam("date") String date) {
		try {
			JSONArray jsonArray = logManager.conversationCollector(date);
			return jsonArray.toString();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "failed";
	}
}

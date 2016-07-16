package com.psib.controller;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psib.common.JsonParser;
import com.psib.constant.LogStatus;
import com.psib.dto.DateDto;
import com.psib.dto.jsonmapper.TrainDto;
import com.psib.service.ILogManager;

@Controller
public class ConversationsController {
	
	private static final Logger logger = LoggerFactory.getLogger(ConversationsController.class);

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
	
	@RequestMapping(value = "/addTraining", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String addTraining(@RequestParam("logId") String logId) {
		try {
			String usersay = null; 
			
			JSONObject log = logManager.getLogByLogId(logId);
			if (log != null && log.has("userSay")) {
				usersay = log.getString("userSay");
			}
			
			List<TrainDto> pool = logManager.getTraingPool();
			boolean isExist = false;
			if (usersay != null) {
				for (TrainDto dto : pool) {
					if (dto.getTrain().equalsIgnoreCase(usersay)) {
						isExist = true;
					}
				}
				if (!isExist) {
					TrainDto trainDto = new TrainDto();
					trainDto.setTrain(usersay);
					trainDto.setDelete(false);
					
					pool.add(trainDto);
					logManager.updateTrainingLog(JsonParser.toJson(pool));
					logManager.setLogStatus(logId, LogStatus.INTRAINING);
				}
			}
			return "success";
		} catch (IOException | JSONException e) {
			logger.error("Add failed!", e);
		}
		return "failed";
	}
}

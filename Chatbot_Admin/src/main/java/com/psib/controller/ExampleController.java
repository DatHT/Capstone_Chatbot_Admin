/**
 *
 */
package com.psib.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psib.common.JsonParser;
import com.psib.common.restclient.RestfulException;
import com.psib.constant.CodeManager;
import com.psib.constant.QueryConstant;
import com.psib.constant.StatusCode;
import com.psib.dto.SuggestDto;
import com.psib.dto.jsonmapper.LexicalCategoryDto;
import com.psib.dto.jsonmapper.TrainDto;
import com.psib.dto.jsonmapper.intent.DataDto;
import com.psib.dto.jsonmapper.intent.IntentDto;
import com.psib.dto.jsonmapper.intent.IntentsDto;
import com.psib.dto.jsonmapper.intent.UserSayDto;
import com.psib.service.IIntentManager;
import com.psib.service.ILexicalCategoryManager;
import com.psib.service.ILogManager;

/**
 * @author DatHT Jun 4, 2016
 */

@Controller
public class ExampleController {

	private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

	@Autowired
	private IIntentManager manager;

	@Autowired
	private ILexicalCategoryManager lexicalManager;

	@Autowired
	private ILogManager logManager;

	public static final String INTENTS = "INTENTS";
	public static final String ERROR = "ERROR";
	public static final String LOGS = "LOGS";

	@RequestMapping(path = "/example/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String getIntent(@PathVariable("id") String id, Model model) {

		String responseText = "";
		try {
			responseText = manager.getIntentById(id);

		} catch (IOException | RestfulException e) {
			// TODO Auto-generated catch block
			model.addAttribute(ERROR, e.getMessage());

		}
		return responseText;
	}

	@RequestMapping(path = "/example", method = RequestMethod.GET)
	public String loadExample(Model model) {
		logger.info("[loadExample] - Start");
		try {
			List<IntentsDto> list = manager.getIntents();
			model.addAttribute(INTENTS, list);
			List<LexicalCategoryDto> lexicals = new ArrayList<>();
			LexicalCategoryDto dto = new LexicalCategoryDto();
			dto.setName("any");
			lexicals.add(dto);
			lexicals.addAll(lexicalManager.getApiLexicals());
			model.addAttribute(LexicalCategoryController.LEXICALS, lexicals);
			model.addAttribute("jsonLexical", JsonParser.toJson(lexicals));

			// get example from pool
			List<TrainDto> pool = logManager.getTraingPool();

			model.addAttribute(ExampleController.LOGS, pool);

			logger.info("[loadExample] - End");
			return "example";
		} catch (IOException e) {
			model.addAttribute(ERROR, e.getMessage());
			return "error";

		} catch (RestfulException e) {
			model.addAttribute(ERROR, e.getMessage());
			return "error";
		} catch (RuntimeException e) {
			model.addAttribute(ERROR, e.getMessage());
			return "error";
		}

	}

	@RequestMapping(path = "/example/add", method = RequestMethod.POST)
	public @ResponseBody String insertPattern(@RequestParam("id") String id,
			@RequestParam("trainingSentence") String trainingSentence, Model model,
			@RequestParam(name = "rawPattern", required = false) String rawPattern,
			@RequestParam(name = "rawUsersay", required = false) String rawUsersay) {
		String responseText = "";
		try {
			// set example is true-delete;
			// get example from pool
			List<TrainDto> pool = logManager.getTraingPool();
			boolean flag = false;
			if (!trainingSentence.isEmpty()) {
				for (TrainDto dto : pool) {
					if (dto.getTrain().equalsIgnoreCase(trainingSentence)) {
						dto.setDelete(true);
						flag = true;
					}
				}
				if (flag) {
					logManager.updateTrainingLog(JsonParser.toJson(pool));
				}
			}

			StatusCode status = null;
			// StatusCode status = manager.addPattern(pattern, id);
			// insert relate pattern
			if (rawPattern != null && rawUsersay != null) {
				JSONArray arrId = new JSONArray(id);
				for (int i = 0; i < arrId.length(); i++) {
					status = manager.addPatternToRelateIntent(rawPattern, rawUsersay, arrId.getString(i));
				}

			}
			switch (status) {
			case SUCCESS:
				responseText = CodeManager.SUCCESS;
				break;
			case ERROR:
				responseText = CodeManager.ERROR;
				break;
			case CONFLICT:
				responseText = CodeManager.EXISTED;
				break;
			}
		} catch (IOException e) {
			responseText = e.getMessage();
		} catch (RestfulException e) {
			responseText = e.getMessage();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			responseText = e.getMessage();
		}

		return responseText;

	}

	@RequestMapping(path = "/example/testQuery", method = RequestMethod.POST)
	public @ResponseBody String testPatternQuery(@RequestParam("trainingSentence") String trainingSentence,
			Model model) {
		String responseText = "";
		try {
			boolean result = manager.checkUserPattern(trainingSentence);
			Random random = new Random();
			int index = random.nextInt(10);
			if (result) {
				responseText = QueryConstant.ANSWER_OK_1;
				if ((index % 2) == 0) {
					responseText = QueryConstant.ANSWER_OK_1;
				} else if ((index % 3) == 0) {
					responseText = QueryConstant.ANSWER_OK_2;
				} else if ((index % 5) == 0) {
					responseText = QueryConstant.ANSWER_OK_3;
				}
			} else {
				responseText = QueryConstant.ANSWER_FAIL_1;
				if ((index % 2) == 0) {
					responseText = QueryConstant.ANSWER_FAIL_1;
				} else if ((index % 3) == 0) {
					responseText = QueryConstant.ANSWER_FAIL_2;
				}
			}

		} catch (IOException e) {
			responseText = e.getMessage();
		} catch (RestfulException e) {
			responseText = e.getMessage();
		}

		return responseText;
	}

	@RequestMapping(path = "/example/genPattern", method = RequestMethod.POST)
	@ResponseBody
	public Object suggestPattern(@RequestParam("sentence") String sentence) {
		String responseText = "";
		List<SuggestDto> listObj = new ArrayList<>();
		try {
			responseText = manager.generatePattern(sentence);
			// generate map
			String[] result = responseText.split("\\*");

			int count = 0;
			for (int i = 0; i < result.length; i++) {
				System.out.println(result[i]);
				if (!result[i].contains(",")) {
					if (!result[i].equals(" ")) {
						SuggestDto dto = new SuggestDto("any" + count, result[i].trim());
						listObj.add(dto);
						count++;
					}
				} else {
					if (!result[i].equals(" ")) {
						String[] temp = result[i].split(",");
						SuggestDto dto = new SuggestDto(temp[0].trim(), temp[1].trim());
						listObj.add(dto);
					}

				}
			}

		} catch (IOException e) {
			responseText = CodeManager.ERROR;
		} catch (RestfulException e) {
			responseText = CodeManager.ERROR;
		}
		return listObj;
	}

	@RequestMapping(path = "/viewIntent", method = RequestMethod.GET)
	public String viewIntent(Model model) {
		try {
			List<IntentsDto> list = manager.getIntents();
			model.addAttribute(INTENTS, list);
			return "viewIntent";
		} catch (IOException e) {
			model.addAttribute(ERROR, e.getMessage());
			return "error";

		} catch (RestfulException e) {
			model.addAttribute(ERROR, e.getMessage());
			return "error";
		} catch (RuntimeException e) {
			model.addAttribute(ERROR, e.getMessage());
			return "error";
		}

	}

	@RequestMapping(path = "/example/delete", method = RequestMethod.POST)
	public @ResponseBody String deletePattern(@RequestParam("pattern") String pattern, @RequestParam("id") String id,
			@RequestParam("trainingSentence") String trainingSentence, Model model) {
		String responseText = "";
		try {
			StatusCode status = manager.addPattern(pattern, id);
			switch (status) {
			case SUCCESS:
				responseText = CodeManager.SUCCESS;
				break;
			case ERROR:
				responseText = CodeManager.ERROR;
				break;
			case CONFLICT:
				responseText = CodeManager.EXISTED;
				break;
			}
		} catch (IOException e) {
			responseText = e.getMessage();
		} catch (RestfulException e) {
			responseText = e.getMessage();
		}
		return responseText;

	}

}

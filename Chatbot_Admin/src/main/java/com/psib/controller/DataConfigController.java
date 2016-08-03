/**
 * 
 */
package com.psib.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psib.common.CronExpressionException;
import com.psib.constant.DayOfWeek;
import com.psib.constant.TimeSchedule;
import com.psib.model.Scheduler;
import com.psib.service.ISchedulerManager;
import com.psib.timer.trigger.ScheduleChanger;
import com.psib.util.CronExpressionUtils;

/**
 * @author DatHT Jun 4, 2016
 */

@Controller
@RequestMapping("config")
public class DataConfigController {

	private static final Logger logger = LoggerFactory.getLogger(DataConfigController.class);

	public static final String ERROR = "ERROR";

	private static final String API = "api_sync";
	private static final String LOG = "log_sync";
	private static final String SYNONYM = "synonym_sync";
	private static final String CRAWLER = "crawler_sync";

	@Autowired
	private ISchedulerManager manager;

	@Autowired
	ScheduleChanger changer;

	@RequestMapping(method = RequestMethod.GET)
	public String loadDataConfig(Locale locale, Model model) {
		long d1 = System.currentTimeMillis();
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
		String formattedDate = df.format(d1);
		System.out.println("TIME-----------" + formattedDate);

		model.addAttribute(API, manager.getSchedularByName("api"));
		model.addAttribute(LOG, manager.getSchedularByName("log"));
		model.addAttribute(SYNONYM, manager.getSchedularByName("synonym"));
		model.addAttribute(CRAWLER, manager.getSchedularByName("crawler"));
		return "dataConfig";
	}

	@RequestMapping(value = "/sync", method = RequestMethod.POST)
	public @ResponseBody String synchronize(Model model, @RequestParam("api") String api,
			@RequestParam("log") String log, @RequestParam("synonym") String synonym,
			@RequestParam("crawler") String crawler,
			@RequestParam("day") String day, @RequestParam("hour") String hour,
			@RequestParam("minute") String minute) {

		String responseText = "";
		// sync to API
		Scheduler apiScheduler = manager.getSchedularByName("api");
		apiScheduler.setFrequency(day);
		apiScheduler.setHour(Integer.valueOf(hour));
		apiScheduler.setMinute(Integer.valueOf(minute));
		if (api.equals("yes")) {
			apiScheduler.setStatus(true);
		} else {
			apiScheduler.setStatus(false);
		}
		manager.updateShedulerStatus(apiScheduler);
		// sync to log
		Scheduler logScheduler = manager.getSchedularByName("log");
		logScheduler.setFrequency(day);
		logScheduler.setHour(Integer.valueOf(hour));
		logScheduler.setMinute(Integer.valueOf(minute));
		if (log.equals("yes")) {
			logScheduler.setStatus(true);
		} else {
			logScheduler.setStatus(false);
		}
		manager.updateShedulerStatus(logScheduler);
		//sync to synonym
		Scheduler synonymScheduler = manager.getSchedularByName("synonym");
		synonymScheduler.setFrequency(day);
		synonymScheduler.setHour(Integer.valueOf(hour));
		synonymScheduler.setMinute(Integer.valueOf(minute));
		if (synonym.equals("yes")) {
			synonymScheduler.setStatus(true);
		} else {
			synonymScheduler.setStatus(false);
		}
		manager.updateShedulerStatus(synonymScheduler);
		//sync crawler
		Scheduler crawlerScheduler = manager.getSchedularByName("crawler");
		crawlerScheduler.setFrequency(day);
		crawlerScheduler.setHour(Integer.valueOf(hour));
		crawlerScheduler.setMinute(Integer.valueOf(minute));
		if (synonym.equals("yes")) {
			crawlerScheduler.setStatus(true);
		} else {
			crawlerScheduler.setStatus(false);
		}
		manager.updateShedulerStatus(crawlerScheduler);
		logger.info("[Start Change Scheduler]");
		String cron = convertToCron(day, hour, minute);
		logger.info("[CRON_] " + cron);
		changer.change(cron);
		logger.info("[End Change Scheduler]");
		switch (day) {
		case TimeSchedule.EVERYDAY:
			responseText = "You Set Schedule everyday at " + hour + ":" + minute;
			break;
		case TimeSchedule.EVERYWEEK:
			responseText = "You Set Schedule every week at " + getDayOfWeek() + "-" + hour + ":" + minute;
			break;
		case TimeSchedule.EVERYMONTH:
			Calendar now = Calendar.getInstance();
			responseText = "You Set Schedule every month at " + now.get(Calendar.DATE) + "-" + hour + ":" + minute;
			break;
		default:
			responseText = "done";
			break;
		}
		return responseText;
	}

	private String convertToCron(String day, String hour, String minute) {
		try {

			switch (day) {
			case TimeSchedule.EVERYDAY:
				return CronExpressionUtils.daily(0, Integer.parseInt(minute), Integer.parseInt(hour));
			case TimeSchedule.EVERYWEEK:
				return CronExpressionUtils.weekly(getDayOfWeek(), 0, Integer.parseInt(minute), Integer.parseInt(hour));
			case TimeSchedule.EVERYMONTH:
				Calendar now = Calendar.getInstance();
				return CronExpressionUtils.monthly(now.get(Calendar.DATE), 0, Integer.parseInt(minute),
						Integer.parseInt(hour));
			default:
				return "";
			}
		} catch (NumberFormatException e) {
			logger.error("[ERROR]-- " + e.getMessage());
		} catch (CronExpressionException e) {
			logger.error("[ERROR]-- " + e.getMessage());
		}

		return "";
	}

	private DayOfWeek getDayOfWeek() {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);

		switch (day) {
		case Calendar.SUNDAY:
			return DayOfWeek.Sunday;
		case Calendar.MONDAY:
			return DayOfWeek.Monday;
		case Calendar.TUESDAY:
			return DayOfWeek.Tuesday;
		case Calendar.WEDNESDAY:
			return DayOfWeek.Wednesday;
		case Calendar.THURSDAY:
			return DayOfWeek.Thursday;
		case Calendar.FRIDAY:
			return DayOfWeek.Friday;
		case Calendar.SATURDAY:
			return DayOfWeek.Saturday;
		}
		return DayOfWeek.Unknown;
	}
}

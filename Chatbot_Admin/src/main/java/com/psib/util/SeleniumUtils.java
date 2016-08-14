package com.psib.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class SeleniumUtils {
	private static WebDriver driver;

	public static WebDriver createFireFoxDriver() {
		driver = new FirefoxDriver();
		return driver;
	}

	public static WebDriver createHtmlUnitDriver() {
		driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_38, false);
		return driver;
	}

	public static void closeDriver() {
		if (driver != null) {
			driver.close();
		}
	}
}

package com.psib.service;

import java.io.IOException;

import com.psib.dto.configuration.ConfigDTO;
import com.psib.dto.configuration.PageDTO;

public interface IConfigurationManager {
	public String loadConfig() throws IOException;
	public String loadHtmlContentFromUrl(String linkPage, String url, String htmlFilePath) throws IOException;
	public String getNextPage(String nextPage,String linkPage,String xpath, String url);
	public boolean addPageConfig(PageDTO pageDTO, String url) throws IOException;
	public boolean addPageDetails(ConfigDTO configDTO, String url) throws IOException;
}

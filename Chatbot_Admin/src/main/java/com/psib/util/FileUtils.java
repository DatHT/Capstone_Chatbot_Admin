package com.psib.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class FileUtils {
	public static BufferedReader readFile(String filePath) throws FileNotFoundException {
		FileReader fileReader = new FileReader(filePath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		return bufferedReader;
	}
}

package com.psib.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	public static BufferedReader readFile(String filePath) throws FileNotFoundException {
		FileReader fileReader = new FileReader(filePath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		return bufferedReader;
	}

	public static List<String> getAllFiles(String directoryPath) throws IOException {
		List<String> listFiles = new ArrayList<String>();
		Files.walk(Paths.get(directoryPath)).filter(Files::isRegularFile).forEach(filePath -> {
			listFiles.add(filePath.toString());
		});

		return listFiles;
	}
}

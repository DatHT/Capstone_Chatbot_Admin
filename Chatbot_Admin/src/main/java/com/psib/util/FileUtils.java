package com.psib.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	public static BufferedReader readFile(String filePath) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileInputStream fileInputStream = new FileInputStream(file);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));

		return bufferedReader;
	}

	public static void writeFile(String filePath, String data) throws IOException {
		File file = new File(filePath);

		FileWriter fileWriter = null;
		fileWriter = new FileWriter(file);

		fileWriter.write(data);
		fileWriter.flush();
		
		fileWriter.close();
	}

	public static List<String> getAllFiles(String directoryPath) throws IOException {
		List<String> listFiles = new ArrayList<String>();
		Files.walk(Paths.get(directoryPath)).filter(Files::isRegularFile).forEach(filePath -> {
			listFiles.add(filePath.toString());
		});

		return listFiles;
	}
}

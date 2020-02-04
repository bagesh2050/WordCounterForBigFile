package com.test.generic;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomUtility {
	public static int[] getSubStringBetween(StringBuilder fileString, String startPattern, String endPattern) {

		StringBuilder regex;
		if (endPattern != null && endPattern.length() > 0) {
			regex = new StringBuilder(startPattern).append("(.*?)").append(endPattern);
		} else {
			regex = new StringBuilder(startPattern).append(".*");
		}

		Pattern pattern = Pattern.compile(regex.toString());
		Matcher matcher = pattern.matcher(fileString);

		int[] arr = new int[2];

		while (matcher.find()) {
			arr[0] = matcher.start();
			arr[1] = matcher.end();
		}

		return arr;
	}

	public static StringBuilder fileContentsAsString(String filePath) {
		Path path = Paths.get(filePath);
		try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {

			StringBuilder currentLine = new StringBuilder();
			String temp;
			while ((temp = reader.readLine()) != null) {
				currentLine.append(temp).append(" ");
			}
			
			return currentLine;
		} catch (Throwable ex) {
		}
		return null;
	}
}

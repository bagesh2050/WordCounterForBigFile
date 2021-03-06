package com.test.utils;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomUtility {
	public static int[] getSubStringBetween(StringBuilder fileString, String startPattern, String endPattern) {
		int[] arr = new int[2];

		StringBuilder regex = null;

		if (startPattern != null && startPattern.length() > 0 && endPattern != null && endPattern.length() > 0) {
			regex = new StringBuilder(startPattern).append("(.*?)").append(endPattern);
		} else if (startPattern != null && startPattern.length() > 0) {
			regex = new StringBuilder(startPattern).append(".*");
		} else if (endPattern != null && endPattern.length() > 0) {
			regex = new StringBuilder("*.").append(endPattern);
		} else {
			arr[0] = 0;
			arr[1] = fileString.length();
		}

		if (regex != null) {
			Pattern pattern = Pattern.compile(regex.toString());
			Matcher matcher = pattern.matcher(fileString);

			while (matcher.find()) {
				arr[0] = matcher.start();
				arr[1] = matcher.end();
			}
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

	public static void printResponse(int totalWordsInFile, LocalTime startTime, LocalTime endTime) {

		System.out.println("\n****************** Word Count ************************\n");

		System.out.println("Total Word Count in file=" + totalWordsInFile);

		System.out.println("\n******************Time************************\n");

		System.out.println("Time taken In Seconds       => " + (startTime.until(endTime, ChronoUnit.SECONDS)));

		System.out.println("Time taken In Micro Seconds => " + (startTime.until(endTime, ChronoUnit.MICROS)));

		System.out.println("Time taken In Nano Seconds  => " + (startTime.until(endTime, ChronoUnit.NANOS)));
	}
}

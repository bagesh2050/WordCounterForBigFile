package com.test.forkjoin;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import com.test.utils.CustomUtility;

public class CustomFileReaderFork {

	// create parts List
	private static final List<String> partList = Arrays.asList("BOOK ONE: 1805", "BOOK TWO: 1805", "BOOK THREE: 1805",
			"BOOK FOUR: 1806", "BOOK FIVE: 1806 - 07", "BOOK SIX: 1808 - 10", "BOOK SEVEN: 1810 - 11",
			"BOOK EIGHT: 1811 - 12", "BOOK NINE: 1812", "BOOK TEN: 1812", "BOOK ELEVEN: 1812", "BOOK TWELVE: 1812",
			"BOOK THIRTEEN: 1812", "BOOK FOURTEEN: 1812", "BOOK FIFTEEN: 1812 - 13");

	public static void main(String[] args) {

		System.out.println("****************** ForkJoin Solution ***************\n");

		StringBuilder fileContent = CustomUtility.fileContentsAsString("resources\\test-book.txt");

		if (fileContent == null || fileContent.length() <= 0) {
			System.out.println("Blank file or file not found");
			return;
		}

		// Make Author information and Table Of contents part separate than actual book
		// data
		String[] data = fileContent.toString().split(partList.get(0), 3);

		StringBuilder AuthorInfo = new StringBuilder(data[0]).append(data[1]);
		StringBuilder bookParts = new StringBuilder(partList.get(0)).append(" ").append(data[2]);

		// Do time related calculation
		LocalTime startTime = LocalTime.now();

		WordForkCounter wordCounter = new WordForkCounter(0, "Part 1", bookParts, partList.get(0), partList.get(1),
				partList);

		int totalWordsInFile = new ForkJoinPool().invoke(wordCounter);

		LocalTime endTime = LocalTime.now();

		CustomUtility.printResponse(totalWordsInFile, startTime, endTime);
	}
}

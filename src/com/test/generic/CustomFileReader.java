package com.test.generic;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.test.utils.CustomUtility;

public class CustomFileReader {

	// create parts List
	private static final List<String> partList = Arrays.asList("BOOK ONE: 1805", "BOOK TWO: 1805", "BOOK THREE: 1805",
			"BOOK FOUR: 1806", "BOOK FIVE: 1806 - 07", "BOOK SIX: 1808 - 10", "BOOK SEVEN: 1810 - 11",
			"BOOK EIGHT: 1811 - 12", "BOOK NINE: 1812", "BOOK TEN: 1812", "BOOK ELEVEN: 1812", "BOOK TWELVE: 1812",
			"BOOK THIRTEEN: 1812", "BOOK FOURTEEN: 1812", "BOOK FIFTEEN: 1812 - 13");

	public static void main(String[] args) {

		System.out.println("****************** Thread with CountDown Latch Solution ***************\n");

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

		LocalTime startTime = LocalTime.now();

		CountDownLatch countDownlatch = new CountDownLatch(partList.size());

		List<WordCounter> listOfRunnableTasks = new ArrayList<WordCounter>(partList.size());

		for (int i = 0; i < partList.size(); i++) {
			String startPattern = partList.get(i);
			String endPattern = i < partList.size() - 1 ? partList.get(i + 1) : "";

			WordCounter wc = new WordCounter("Part " + (i + 1), bookParts, startPattern, endPattern, countDownlatch);
			listOfRunnableTasks.add(wc);
			new Thread(wc).start();
		}

		try {
			countDownlatch.await();
		} catch (InterruptedException e) {
			System.out.println("Some Problem Occured");
		}

		int totalWordsInFile = 0;
		for (int i = 0; i < partList.size(); i++) {
			totalWordsInFile += listOfRunnableTasks.get(i).getTotalWordsInPart();
		}

		LocalTime endTime = LocalTime.now();

		CustomUtility.printResponse(totalWordsInFile, startTime, endTime);
	}
}
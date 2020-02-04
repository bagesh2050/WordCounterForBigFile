package com.test.forkjoin;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class CustomFileReaderFork {

	// create threads
	private static final List<String> partList = Arrays.asList("BOOK ONE: 1805", "BOOK TWO: 1805", "BOOK THREE: 1805",
			"BOOK FOUR: 1806", "BOOK FIVE: 1806 - 07", "BOOK SIX: 1808 - 10", "BOOK SEVEN: 1810 - 11",
			"BOOK EIGHT: 1811 - 12", "BOOK NINE: 1812", "BOOK TEN: 1812", "BOOK ELEVEN: 1812", "BOOK TWELVE: 1812",
			"BOOK THIRTEEN: 1812", "BOOK FOURTEEN: 1812", "BOOK FIFTEEN: 1812 - 13");

	public static void main(String[] args) {

		Path path = Paths.get("resources\\test-book.txt");
		try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {

			StringBuilder currentLine = new StringBuilder();
			String temp;
			while ((temp = reader.readLine()) != null) {
				currentLine.append(temp).append(" ");
			}

			if (currentLine == null || currentLine.length() <= 0) {
				System.out.println("Blank file");
				return;
			}

			// Make Header part separate than content
			String[] data = currentLine.toString().split(partList.get(0), 3);

			StringBuilder AuthorInfo = new StringBuilder(data[0]).append(data[1]);
			StringBuilder bookParts = new StringBuilder(partList.get(0)).append(" ").append(data[2]);

			ForkJoinPool forkJoinPool = new ForkJoinPool();

			WordForkCounter wordCounter = new WordForkCounter(0, "Part 0", bookParts, partList.get(0), partList.get(1),
					partList);

			LocalTime startTime = LocalTime.now();
			int sum = forkJoinPool.invoke(wordCounter);
			LocalTime endTime = LocalTime.now();

			System.out.println("Total Word Count in file=" + sum);
			System.out.println("Time taken=" + (endTime.getNano() - startTime.getNano()));

		} catch (Throwable ex) {
			ex.printStackTrace(); // handle an exception here
		}
	}
}

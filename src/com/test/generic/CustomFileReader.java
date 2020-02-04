package com.test.generic;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CustomFileReader {

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

			// create threads
			List<String> partList = Arrays.asList("BOOK ONE: 1805", "BOOK TWO: 1805", "BOOK THREE: 1805",
					"BOOK FOUR: 1806", "BOOK FIVE: 1806 - 07", "BOOK SIX: 1808 - 10", "BOOK SEVEN: 1810 - 11",
					"BOOK EIGHT: 1811 - 12", "BOOK NINE: 1812", "BOOK TEN: 1812", "BOOK ELEVEN: 1812",
					"BOOK TWELVE: 1812", "BOOK THIRTEEN: 1812", "BOOK FOURTEEN: 1812", "BOOK FIFTEEN: 1812 - 13");

			// Make Header part separate than content
			String[] data = currentLine.toString().split(partList.get(0), 3);

			StringBuilder AuthorInfo = new StringBuilder(data[0]).append(data[1]);
			StringBuilder bookParts = new StringBuilder(partList.get(0)).append(" ").append(data[2]);

			LocalTime startTime = LocalTime.now();

			CountDownLatch countDownlatch = new CountDownLatch(partList.size());

			for (int i = 0; i < partList.size(); i++) {

				Thread thread;

				if (i + 1 < partList.size()) {
					thread = new Thread(new WordCounter("Part " + (i + 1), bookParts, partList.get(i),
							partList.get(i + 1), countDownlatch));
				} else {
					thread = new Thread(
							new WordCounter("Part " + (i + 1), bookParts, partList.get(i), "", countDownlatch));
				}

				thread.start();
			}

			countDownlatch.await();

			LocalTime endTime = LocalTime.now();

			System.out.println("Time taken=" + (endTime.getNano() - startTime.getNano()));

			System.out.println("Total Word Count in file=");

		} catch (Throwable ex) {
			ex.printStackTrace(); // handle an exception here
		}
	}
}
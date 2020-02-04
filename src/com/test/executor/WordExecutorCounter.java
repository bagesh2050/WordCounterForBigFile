package com.test.executor;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import com.test.generic.CustomUtility;

public class WordExecutorCounter implements Runnable {
	private StringBuilder fileData;
	private String startPart;
	private String endPart;
	private String partName;
	private CountDownLatch latch;

	public WordExecutorCounter(String partName, StringBuilder bookParts, String startPart, String endPart,
			CountDownLatch latch) {
		this.partName = partName;
		this.fileData = bookParts;
		this.startPart = startPart;
		this.endPart = endPart;
		this.latch = latch;
	}

	@Override
	public void run() {
		int[] arr = CustomUtility.getSubStringBetween(fileData, startPart, endPart);

		HashMap<String, Integer> firstMap = new HashMap<String, Integer>();

		int totalWords = 0;

		for (String value : fileData.substring(arr[0], arr[1]).split(" ")) {
			value = value.replaceAll("[^a-zA-Z0-9]", "");

			if (value == null || value.length() <= 0)
				continue;

			totalWords++;

			Integer oldVal = firstMap.get(value);
			if (oldVal != null && oldVal > 0) {
				firstMap.put(value, ++oldVal);
			} else {
				firstMap.put(value, 1);
			}
		}

		// System.out.println(firstMap);
		System.out.println("Total Words in " + partName + " = " + totalWords);

		latch.countDown();
	}
}

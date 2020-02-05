package com.test.forkjoin;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.RecursiveTask;

import com.test.executor.utils.CustomUtility;

public class WordForkCounter extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;
	private int size;
	private StringBuilder fileData;
	private String startPart;
	private String endPart;
	private String partName;
	private List<String> partList;

	public WordForkCounter(int size, String partName, StringBuilder bookParts, String startPart, String endPart,
			List<String> partList) {
		this.size = size;
		this.partName = partName;
		this.fileData = bookParts;
		this.startPart = startPart;
		this.endPart = endPart;
		this.partList = partList;
	}

	@Override
	protected Integer compute() {
		if (size >= partList.size() - 1) {
			return calculate();
		}

		String startPattern = partList.get(size + 1);
		String endPattern = size < partList.size() - 2 ? partList.get(size + 2) : "";

		WordForkCounter calculator = new WordForkCounter(size + 1, "Part " + (size + 1), fileData, startPattern,
				endPattern, partList);

		calculator.fork();

		return calculate() + calculator.join();
	}

	private int calculate() {
		int[] arr = CustomUtility.getSubStringBetween(fileData, startPart, endPart);

		HashMap<String, Integer> firstMap = new HashMap<String, Integer>();
		
		PriorityQueue<HashMap<String, Integer>> pQueue = new PriorityQueue<HashMap<String, Integer>>();

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

		
		System.out.println("Total Words in " + partName + " = " + totalWords);
		
		return totalWords;
	}
}
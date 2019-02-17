package com.pratik.giftcard;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GiftCardApp {
	// In-Memory Index for the price list.
	private static Map<Integer, String> priceMap = new LinkedHashMap<>();
	private static int _NO_OF_GIFTS_ = 2;

	/**
	 * Modified Binary Search to return the closest value or the value. O(log(n));
	 * 
	 * @param a
	 * @param target
	 * @return
	 */
	public static int binsearch(int[] a, int target) {

		int lo = 0;
		int hi = a.length - 1;
		Integer result = -1;

		while (lo <= hi) {

			int mid = (lo + hi) >> 1;
			result = record(a, mid, target, result);

			if (a[mid] > target) {
				hi = mid - 1;
			} else if (a[mid] < target) {

				lo = mid + 1;
			} else
				return a[mid];
		}
		return (result != -1) ? a[result] : -1;
	}

	private static int record(int[] a, int mid, int target, Integer result) {

		if (result == -1 || Math.abs(a[mid] - target) < Math.abs(a[result] - target)) {
			return mid;
		}

		return result;
	}

	private static void buildMap(List<String> list) {
		// build the in-memory index
		for (String s : list) {
			String[] arr = s.split(",");
			priceMap.put(Integer.parseInt(arr[1].trim()), arr[0]);
		}
	}

	/**
	 * If an item exists in the price list with value equal to the gift card amount,
	 * then remove it from the index. We want to buy _NO_OF_GIFTS_ for a given gift
	 * card and _NO_OF_GIFTS > 1
	 * 
	 * @param totalAmount
	 */
	private static void process(int totalAmount) {

		if (priceMap.containsKey(totalAmount))
			priceMap.remove(totalAmount);

		int[] array = convertKeysToArray();

		int searchResult = 0;
		int found = 0;
		boolean resultPossible = true;
		List<String> results = new ArrayList<>();

		while (found != _NO_OF_GIFTS_) {
			searchResult = binsearch(array, totalAmount);
			// System.out.println(searchResult);
			if (searchResult == -1 || searchResult > totalAmount) {
				resultPossible = false;
				break;
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append(priceMap.get(searchResult) + "," + searchResult);
				results.add(sb.toString());
				totalAmount -= searchResult;
				found++;
			}
		}

		System.out.println((resultPossible) ? results.toString() : "Not Possible");
	}

	private static int[] convertKeysToArray() {
		int[] array = new int[priceMap.keySet().size()];
		int index = 0;
		for (Integer element : priceMap.keySet())
			array[index++] = element.intValue();
		return array;
	}

	public static void main(String[] args) {

		try {
			// Get the file name
			String filename = args[0];
			// Get the dollar amount.
			int totalAmount = Integer.parseInt(args[1]);

			List<String> list = new ArrayList<>();
			BufferedReader br = Files.newBufferedReader(Paths.get(filename));
			list = br.lines().collect(Collectors.toList());

			buildMap(list);
			process(totalAmount);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}

	}

}

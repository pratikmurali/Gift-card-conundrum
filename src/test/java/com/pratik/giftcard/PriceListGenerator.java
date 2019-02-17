package com.pratik.giftcard;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PriceListGenerator {

	private String generateItemNames() {

		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();

		return generatedString;
	}

	private int generateRandomPriceInRange(int min, int max) {
		return (int) (Math.random() * ((max - min) + 1)) + min;
	}

	public void writeRandomFileList() {

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("huge_prices.txt"));
			for (int i = 0; i < 1000000; i++) {

				String itemName = generateItemNames();
				int price = generateRandomPriceInRange(500, 10000);
				writer.write(itemName + "," + price);
				writer.newLine();
			}

			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		PriceListGenerator plg = new PriceListGenerator();
		plg.writeRandomFileList();
		

	}

}

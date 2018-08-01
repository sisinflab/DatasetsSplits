package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Utils {
	public static Map<Integer, ArrayList<Rating>> getScores(String file, String separator) throws IOException {
		Map<Integer, ArrayList<Rating>> scores = new HashMap<>();

		BufferedReader reader = new BufferedReader(new FileReader(file));

		String line = "";
		
		while ((line = reader.readLine()) != null) {
			String[] splittedLine = line.split(separator);
			Integer userId = Integer.valueOf(splittedLine[0]);
			long itemId =  Long.valueOf(splittedLine[1]);
			float scoreValue = Float.valueOf(splittedLine[2]);
			long timestamp = Long.valueOf(splittedLine[3]);

			if(!scores.containsKey(userId)){
				scores.put(userId, new ArrayList<>());
			}
			scores.get(userId).add(new Rating(timestamp, scoreValue, itemId));

		}
		reader.close();
		return scores;
	}
	
}

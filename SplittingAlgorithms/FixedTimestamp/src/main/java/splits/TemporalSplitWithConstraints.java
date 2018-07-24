package splits;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import utils.*;

public class TemporalSplitWithConstraints {

	private int minTrain;
	private int minTest;
	Map<Integer,ArrayList<Rating>> scores;
	
	public TemporalSplitWithConstraints(int minTrain, int minTest) {
		this.minTrain = minTrain;
		this.minTest = minTest;
	}
	
	
	
	
	public void launchTemporalSplitWithConstraints(String ratingsPath, String destinationPath) throws IOException {

		
		long bestTimestamp = getBestTimestamp(ratingsPath);
		
		split(ratingsPath, destinationPath, bestTimestamp);
		
		
	}
	
	private long getBestTimestamp(String ratingsPath) throws IOException {
		
		scores = Utils.getScores(ratingsPath);
		Map<Integer,ArrayList<Long>> users = new HashMap<>();
		
		
		// For optimization purposes, for each user, first minTrain ratings and last minTest ratings are filtered out
		Iterator<Integer> it = scores.keySet().iterator();
		while(it.hasNext()) {
			Integer userId = it.next();
			ArrayList<Rating> infos = scores.get(userId);
			Collections.sort(infos);			
			for(int i = minTrain;i<infos.size() - minTest;i++) {
				
				if(!users.containsKey(userId)){
					users.put(userId, new ArrayList<Long>());
				} 
				users.get(userId).add(infos.get(i).getTimestamp());
			}
		}
		
		scores.clear();
		
		
		// Creates a set with all possible timestamps all over the platform
		Set<Long> uniqueTimestamps = new HashSet<Long>();
		Iterator<Integer> usersIt = users.keySet().iterator();
		while(usersIt.hasNext()) {
			Integer userId = usersIt.next();
			ArrayList<Long> timestamps = users.get(userId);
			for(Long timestamp:timestamps) {
				uniqueTimestamps.add(timestamp);
			}						
		}
		
//		System.out.println(uniqueTimestamps.size()); // debug 
		
		Map<Long,Integer> checkMap = new HashMap<Long,Integer>();
		
		int debug = 1;
		// For each unique timestamp, counts how many previously filtered user have that timestamp. The timestamp with the highest value of count will be chosen.
		for(Long timestamp:uniqueTimestamps) {
			
//			System.out.println(debug);
//			debug++;
			
			int count = 0;
			usersIt = users.keySet().iterator();
			while(usersIt.hasNext()) {
				Integer userId = usersIt.next();
				ArrayList<Long> usersTimestamps = users.get(userId);
				Collections.sort(usersTimestamps);
				if(timestamp >= usersTimestamps.get(0) && timestamp <= usersTimestamps.get(usersTimestamps.size()-1)) {
					count++;
				}
			}
			
			checkMap.put(timestamp, count);
		}
		// mi faccio stampare il timestamp che include il maggior numero di utenti
		Long bestTimestamp = checkMap.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
		System.out.println("BestTimestamp:" + bestTimestamp);
		return bestTimestamp;
		

//		System.out.println(checkMap.get(bestTimestamp));
		
	}
	

	private void split(String ratingsPath,String destinationDir, long referringTimestamp) throws IOException{
	
		scores = Utils.getScores(ratingsPath);

		PrintWriter out= new PrintWriter(new FileWriter(destinationDir+"/trainingset.tsv"));
		PrintWriter out2 = new PrintWriter(new FileWriter(destinationDir+"/testset.tsv"));
		PrintWriter outTimestamp = new PrintWriter(new FileWriter(destinationDir+"/TimestampUsers_timestamps.tsv"));

		
		Map<Integer,ArrayList<Rating>> train = new HashMap<>();
		Map<Integer,ArrayList<Rating>> test = new HashMap<>();
		
		
		// creates a training map where, for each user, only ratings < than the referring timestamp are inserted and a test map with only ratings > referring timestamp 
		Iterator<Integer> it = scores.keySet().iterator();
		while(it.hasNext()){
			Integer userId = it.next();
			
			ArrayList<Rating> ratings = scores.get(userId);
			Collections.sort(ratings);
			if(referringTimestamp >= ratings.get(0).getTimestamp() && referringTimestamp <= ratings.get(ratings.size()-1).getTimestamp()){
	
				
				for(Rating rating:ratings){
					if(rating.getTimestamp() <= referringTimestamp){
					
						
						if(!(train.containsKey(userId))) {
							train.put(userId, new ArrayList<Rating>());
						}
						train.get(userId).add(new Rating(rating.getTimestamp(),rating.getRating(),rating.getItemID()));
			
					}else{
						
						if(!(test.containsKey(userId))) {
							test.put(userId, new ArrayList<Rating>());
						}
						test.get(userId).add(new Rating(rating.getTimestamp(),rating.getRating(),rating.getItemID()));
					}
									
				}
				
			}
		}
		
		// dalle due mappe trovate vado a togliere tutti quegli utenti che non soddisfano i vincoli delle dimensioni minime.
		// è necessario fare questo doppio passaggio in quanto prendendo tutti gli utenti come precedentemente fatto non è detto che soddisfi i vincoli
		it = train.keySet().iterator();
		int count = 0;
		while(it.hasNext()) {
		
			
			Integer userId = it.next();
			
			ArrayList<Rating> trainRatings = train.get(userId);
			ArrayList<Rating> testRatings = test.get(userId);
			if(trainRatings != null && testRatings != null) {
				if(trainRatings.size()>=minTrain && testRatings.size() >=minTest) {
					count++;
					outTimestamp.print(userId + "\t[");
					for(Rating rating:trainRatings) {
						out.println(userId + "\t" + rating.getItemID() + "\t" + rating.getRating() + "\t" + rating.getTimestamp());
						if(rating == trainRatings.get(trainRatings.size()-1)) {
							outTimestamp.print(rating.getTimestamp() + "]");
						}else {
							outTimestamp.print(rating.getTimestamp() + ", ");
						}
					}
					for(Rating rating:testRatings) {
						out2.println(userId + "\t" + rating.getItemID() + "\t" + rating.getRating() + "\t" + rating.getTimestamp());
					}
					outTimestamp.println();
				}
			}
		}
		
		
		System.out.println("Number of users:" + count);
		out.close();
		out2.close();
		outTimestamp.close();
	}
	
	


	
	
	
	
	
}

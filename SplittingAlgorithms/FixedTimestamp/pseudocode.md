Pseudocode<br />
<br />
minTrain = 15<br />
minTest = 5<br />
U=[]<br />
Map<uniqueTimestamp, count> timestampsMap<br />
Map<user, timestampsList[]> ratingsMap<br />
<br />
for line in InputFile:<br />
	user, timestamp = readLine()<br />
	U.add(user)<br />
	timestampsMap.put(timestamp,0)<br />
	ratingsMap.put(user,ratingsMap.get(user).add(timestamp))<br />
<br />
for user in U:<br />
	timestampsList = ratingsMap.get(user)<br />
	if(timestampList.size() <= minTrain + minTest):<br />
		U.remove(user)<br />
	else:<br />
		timestampsList.sort()<br />
		timestampsList.removeFromHead(MinTrain)<br />
		timestampsList.removeFromTail(minTest)<br />
<br />
for timestamp in timestampsMap.keySet():<br />
	count = 0<br />
	for user in U:<br />
		timestampsList = ratingsMap.get(user)<br />
		timestampsList.sort()<br />
		if (timestamp >= timestampsList.first() && uniqueTimestamp <= timestampsList.last()):<br />
			count++;<br />
	timestampsMap.put(timestamp,count)<br />
bestTimestamp = argmax(timestampsMap)		

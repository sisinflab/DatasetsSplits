Pseudocode

minTrain = 15
minTest = 5
U=[]
Map<uniqueTimestamp, count> timestampsMap
Map<user, timestampsList[]> ratingsMap

for line in InputFile:
	user, timestamp = readLine()
	U.add(user)
	timestampsMap.put(timestamp,0)
	ratingsMap.put(user,ratingsMap.get(user).add(timestamp))

for user in U:
	timestampsList = ratingsMap.get(user)
	if(timestampList.size() <= minTrain + minTest):
		U.remove(user)
	else:
		timestampsList.sort()
		timestampsList.removeFromHead(MinTrain)
		timestampsList.removeFromTail(minTest)

for timestamp in timestampsMap.keySet():
	count = 0
	for user in U:
		timestampsList = ratingsMap.get(user)
		timestampsList.sort()
		if (timestamp >= timestampsList.first() && uniqueTimestamp <= timestampsList.last()):
			count++;
	timestampsMap.put(timestamp,count)
bestTimestamp = argmax(timestampsMap)		

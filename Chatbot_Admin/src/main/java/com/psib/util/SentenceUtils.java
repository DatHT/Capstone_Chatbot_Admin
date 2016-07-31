package com.psib.util;

public class SentenceUtils {
	
	public static float checkContainSentencePercent(String sentence1, String sentence2) {
		sentence1 = sentence1.toUpperCase().trim();
		sentence2 = sentence2.toUpperCase().trim();
		
		if (sentence1.equals(sentence2)) {
			return 1.0f;
		}
		String longSentence = sentence1.length() > sentence2.length() ? sentence1 : sentence2;
		String[] wordsOfLongSen = longSentence.split(" ");
		String shortSentence = sentence1.length() < sentence2.length() ? sentence1 : sentence2;
		String[] wordsOfShortSen = shortSentence.split(" ");
		
		if(longSentence.contains(shortSentence)) {
			float containPercent = (float)wordsOfShortSen.length/ (float)wordsOfLongSen.length;
			return containPercent;
		} else {
			int currIndex = 0;
			int count = 0;
			for(int i = 0; i < wordsOfShortSen.length; i++) {
				int j = currIndex;
				boolean isFound = false;
				while (j < wordsOfLongSen.length && !isFound) {
					if (wordsOfShortSen[i].equalsIgnoreCase(wordsOfLongSen[j])) {
						currIndex = j + 1;
						isFound = true;
					}
					j++;
				}
				if (isFound) {
					count++;
				}
			}
			return (float)count/(float)wordsOfLongSen.length;
		}
	}
}

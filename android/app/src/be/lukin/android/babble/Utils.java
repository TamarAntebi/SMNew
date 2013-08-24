package be.lukin.android.babble;

import java.util.HashMap;
import java.util.Map;

import be.lukin.android.babble.provider.Phrase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.text.SpannableString;
import android.text.util.Linkify;

public class Utils {

	private Utils() {}

	private static final String PUNCT = "\\p{Punct}";
	private static final String SPACE = "\\p{Space}";


	
		public static String phraseDistances(String s1) {
			s1 = s1.toLowerCase().replaceAll(SPACE, "").replaceAll(PUNCT, "");
			return computeDistances(s1);
		}

	/**
	 * Originates from: http://rosettacode.org/wiki/Levenshtein_distance#Java
	 */
	private static int computeDistance(String s1, String s2) {

		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}

	private static String computeDistances(String s1) {
		String up="up";
		String down="down";
		String left="left";
		String right="right";
		
		
		int[] costsUp = new int[up.length() + 1];
		int[] costsD = new int[down.length() + 1];
		int[] costsL = new int[left.length() + 1];
		int[] costsR = new int[right.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValueU = i;
			int lastValueD = i;
			int lastValueL = i;
			int lastValueR = i;
			for (int j = 0; j <= up.length(); j++) {
				if (i == 0)
					costsUp[j] = j;
				else {
					if (j > 0) {
						int newValue = costsUp[j - 1];
						if (s1.charAt(i - 1) != up.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValueU), costsUp[j]) + 1;
						costsUp[j - 1] = lastValueU;
						lastValueU = newValue;
					}
				}
			}
			if (i > 0){
				costsUp[up.length()] = lastValueU;}
			
			if(costsUp[up.length()]==0){
				return "up";
			}
			for (int k = 0; k <= down.length(); k++) {
				if (i == 0)
					costsD[k] = k;
				else {
					if (k > 0) {
						int newValue = costsD[k - 1];
						if (s1.charAt(i - 1) != down.charAt(k - 1))
							newValue = Math.min(Math.min(newValue, lastValueD), costsD[k]) + 1;
						costsD[k - 1] = lastValueD;
						lastValueD = newValue;
					}
				}
			}
			if (i > 0){
				costsD[down.length()] = lastValueD;}
			if(costsD[down.length()] ==0){
				return "down";
			}
			for (int g = 0; g <= left.length(); g++) {
				if (i == 0)
					costsL[g] = g;
				else {
					if (g > 0) {
						int newValue = costsL[g - 1];
						if (s1.charAt(i - 1) != left.charAt(g - 1))
							newValue = Math.min(Math.min(newValue, lastValueL), costsL[g]) + 1;
						costsL[g - 1] = lastValueL;
						lastValueL = newValue;
					}
				}
			}
			if (i > 0){
				costsL[left.length()] = lastValueL;}
			if(costsL[left.length()] ==0){
				return "left";
			}
			for (int s = 0; s <= right.length(); s++) {
				if (i == 0)
					costsR[s] = s;
				else {
					if (s > 0) {
						int newValue = costsR[s - 1];
						if (s1.charAt(i - 1) != right.charAt(s - 1))
							newValue = Math.min(Math.min(newValue, lastValueR), costsR[s]) + 1;
						costsR[s - 1] = lastValueR;
						lastValueR = newValue;
					}
				}
			}
			if (i > 0){
				costsR[right.length()] = lastValueR;}
			if(costsR[right.length()] ==0){
				return "left";
			}
		}
		return min(costsUp[up.length()],costsD[down.length()],costsL[left.length()],costsR[right.length()]);
	}
	private static String min(int updist, int downdist, int leftDist,
			int rigthDist) {
		if(updist<downdist&& updist<leftDist&& updist< rigthDist&& updist<4){
			return "up";
		}
		if(downdist<updist&& downdist<leftDist&& downdist< rigthDist&& downdist<4){
			return "down";
		}
		if(leftDist<downdist&& leftDist<updist&& leftDist< rigthDist&& leftDist<4){
			return "left";
		}
		if(rigthDist<downdist&& rigthDist<leftDist&& rigthDist< updist&& rigthDist<4){
			return "right";
		}
		
		
		return null;
	}
}



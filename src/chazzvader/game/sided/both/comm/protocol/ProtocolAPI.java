package chazzvader.game.sided.both.comm.protocol;

import java.util.Arrays;
import java.util.LinkedList;

import chazzvader.game.sided.both.comm.CommManager;

public class ProtocolAPI {
	
	public static final int CONTROL_PROTOCOLS = 16;
	public static final String CONTROL_CHARACTERS = ";:|";
	public static final String CODEC = " \\\"qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890-=!@#$%^&*()_+[]{}',.<>/?€ƒ‚„…†‡ˆ‰Š‹ŒŽ‘’“”•—˜™š›œžŸ¡¢£¤¥¦§¨©ª«Ø";
	public static final int DEFAULT_COMPRESS_BATCH_SIZE = 100;
	public static final String COMPACT_ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ[]";
	
	public static int bitsRequired(int sizeRequired) {
		if(sizeRequired <= 0) throw new IllegalArgumentException("Size required cannot be negative");
		int currentMax = 2;
		int index = 1;
		while(true) {
			if(currentMax >= sizeRequired) return index;
			currentMax *= 2;
			index++;
		}
	}
	
	public static LinkedList<String> receive(CommManager commManager){
		LinkedList<String> items = new LinkedList<String>();
		while(commManager.hasItems()) {
			String msg = commManager.receiveln();
			items.add(msg);
		}
		LinkedList<String> toReturn = ProtocolAPI.deCompress(items);
		return toReturn;
	}

	public static void send(CommManager commManager, LinkedList<String> message) {
		message = ProtocolAPI.compress(message);
		while (!message.isEmpty()) {
			String toSend = message.pollFirst();
			commManager.println(toSend);
		}
	}

	public static void send(CommManager commManager, String message) {
		LinkedList<String> wrappedMessage = new LinkedList<String>();
		wrappedMessage.add(message);
		send(commManager, wrappedMessage);
	}
	
	/**
	 * Converts bits into a integers
	 * @param bits Most significant to least
	 * @return The int
	 */
	public static int bitsToInt(boolean[] b) {
		int r = 0;
		int j = 0;
		for(int i = b.length-1;i >= 0;i --) {
			if(b[i]) r += Math.pow(2, j);
			j++;
		}
		
		return r;
	}
	
	public static LinkedList<String> compress(LinkedList<String> input, int compressionFactor) {
		int inputSize = input.size();
		LinkedList<String> toReturn = new LinkedList<String>();
		for(int i = 0;i < inputSize;i += compressionFactor) {
			StringBuilder string = new StringBuilder();
			for(int j = 0;j < compressionFactor;j ++) {
				if(input.isEmpty()) break;
				string.append(input.pollFirst());
				if(j < compressionFactor-1) {
					string.append("|");
				}
			}
			toReturn.addLast(string.toString());
		}
		return toReturn;
	}
	
	public static LinkedList<String> compress(LinkedList<String> input) {
		return compress(input, DEFAULT_COMPRESS_BATCH_SIZE);
	}

	public static LinkedList<String> deCompress(LinkedList<String> input) {
		int inputSize = input.size();
		LinkedList<String> toReturn = new LinkedList<String>();
		for(int i = 0;i < inputSize;i ++) {
			String string = input.pollFirst();
			String[] stringArray = string.split("\\|");
			for(int j = 0;j < stringArray.length;j ++) {
				toReturn.addFirst(stringArray[j]);
			}
		}
		return toReturn;
	}
	
	public static boolean[] intToBits(int b, boolean force, int tForce) {
		float l = 0;
		if(!force) l = (float) (Math.log10(b+1)/Math.log10(2));
		boolean[] a = new boolean[force ? tForce : (int) Math.ceil(l)];
		int j = 0;
		for(int i = a.length-1;i >= 0;i --) {
			if(b >= Math.pow(2, i)) {
				b -= Math.pow(2, i);
				a[j] = true;
			} else {
				a[j] = false;
			}
			j++;
		}

		return a;
	}
	
	public static boolean[] stringToBits(String s) {
		boolean[] r = new boolean[s.length()*7];
		char[] sa = s.toCharArray();
		for(int i = 0;i < s.length();i ++) {
			boolean[] v = charToBits(sa[i]);
			for(int j = 0;j < 7;j ++) {
				r[i*7+j] = v[j];
			}
		}
		return r;
	}
	
	/**
	 * Converts
	 */
	public static String bitsToString(boolean[] b) {
		char[] ca = new char[Math.floorDiv(b.length, 7)];
		for(int i = 0;i < ca.length;i ++) {
			boolean[] tc = Arrays.copyOfRange(b, i*7, (i+1)*7);
			ca[i] = bitsToChar(tc);
		}
		return new String(ca);
	}
	
	public static char bitsToChar(boolean[] b) {
		if(b.length != 7) throw new IllegalArgumentException();
		return CODEC.toCharArray()[bitsToInt(b)];
	}
	
	public static boolean[] charToBits(char c) {
		char[] ccodec = CODEC.toCharArray();
		int j = 0;
		for(int i = 0;i < ccodec.length;i ++) {
			if(ccodec[i] == c) {
				j = i;
				break;
			}
		}
		return intToBits(j, true, 7);
	}

	public static boolean[] combine(boolean[][] baa) {
		int length = 0;
		for(int i = 0;i < baa.length;i ++) {
			length += baa[i].length;
		}
		boolean[] ret = new boolean[length];
		int k = 0;
		for(int i = 0;i < baa.length;i ++) {
			for(int j = 0;j < baa[i].length;j ++, k ++) {
				ret[k] = baa[i][j];
			}
		}
		return ret;
	}

}
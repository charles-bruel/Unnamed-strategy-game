package chazzvader.game.sided.both.comm;

import java.util.LinkedList;

import chazzvader.game.other.Console;

public final class InternalComms {

	private InternalComms() {}
	
	private static CQueue cs = new CQueue();
	private static CQueue sc = new CQueue();
	
	public static CQueue clientServer() {
		return cs;
	}
	
	public static CQueue serverClient() {
		return sc;
	}

	public static class CQueue {
		private LinkedList<String> q = new LinkedList<>();
		
		private CQueue() {}
		
		public void add(String p) {
			if(p == null) return;
			q.addLast(p);
		}
		
		public String get() {
			if(q.peek() == null) {
				Console.print("(Communication) Queue is empty, returning a blank string.", 2);
				q.poll();
				return "";
			}
			return q.poll();
		}
		
		public boolean hasItems() {
			return !q.isEmpty() && q.peek() != null;
		}
	}
}

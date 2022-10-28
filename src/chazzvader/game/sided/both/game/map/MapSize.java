package chazzvader.game.sided.both.game.map;

public enum MapSize {
	
	TEST(15,10,2),DUEL(82, 55, 2),TINY(116, 77, 4),SMALL(142, 95, 6),STANDARD(164, 109, 8),LARGE(183, 122, 10),HUGE(200, 134, 12);
	
	private int width, height, players;

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the players
	 */
	public int getPlayers() {
		return players;
	}

	/**
	 * @param width
	 * @param height
	 * @param players
	 */
	private MapSize(int width, int height, int players) {
		this.width = width;
		this.height = height;
		this.players = players;
	}

}

package chazzvader.game.other;

import chazzvader.game.other.SubTileCoordinate.SubTile;

public final class HexagonData {

	private HexagonData() {}
	
	public static final int HEXAGON_WIDTH = 524;
	public static final int HEXAGON_HEIGHT = 606;
	
	public static final int HEXAGON_RADIUS = HEXAGON_HEIGHT/2;
	
	public static final int SIXTY_DEGREE_X;
	public static final int SIXTY_DEGREE_Y;

	public static final int THIRTY_DEGREE_X;
	public static final int THIRTY_DEGREE_Y;
	
	public static final float HEXAGON_RATIO = (float) HEXAGON_WIDTH/(float) HEXAGON_HEIGHT;
	
	static {
		int los_num = HEXAGON_RADIUS/1;//Sin 90 is 1 here just as a demo of how the value was gotten
		SIXTY_DEGREE_X = (int) (los_num*Math.sin(Math.toRadians(30)));
		SIXTY_DEGREE_Y = (int) (los_num*Math.sin(Math.toRadians(60)));
		THIRTY_DEGREE_X = (int) (los_num*Math.sin(Math.toRadians(60)));
		THIRTY_DEGREE_Y = (int) (los_num*Math.sin(Math.toRadians(30)));
	}
	
	public static final int HEXAGON_SUBTILE_C_X = 0;
	public static final int HEXAGON_SUBTILE_C_Y = 0;
	
	public static final int HEXAGON_SUBTILE_NW_X = (int) (-SIXTY_DEGREE_X*0.29f);
	public static final int HEXAGON_SUBTILE_NW_Y = (int) (-SIXTY_DEGREE_Y*0.29f);
	
	public static final int HEXAGON_SUBTILE_NE_X = (int) (+SIXTY_DEGREE_X*0.29f);
	public static final int HEXAGON_SUBTILE_NE_Y = (int) (-SIXTY_DEGREE_Y*0.29f);
	
	public static final int HEXAGON_SUBTILE_SE_X = (int) (+SIXTY_DEGREE_X*0.29f);
	public static final int HEXAGON_SUBTILE_SE_Y = (int) (+SIXTY_DEGREE_Y*0.29f);
	
	public static final int HEXAGON_SUBTILE_SW_X = (int) (-SIXTY_DEGREE_X*0.29f);
	public static final int HEXAGON_SUBTILE_SW_Y = (int) (+SIXTY_DEGREE_Y*0.29f);
	
	public static final int HEXAGON_SUBTILE_W_X = (int) (-HEXAGON_RADIUS*0.29f);
	public static final int HEXAGON_SUBTILE_W_Y = 0;
	
	public static final int HEXAGON_SUBTILE_E_X = (int) (+HEXAGON_RADIUS*0.29f);
	public static final int HEXAGON_SUBTILE_E_Y = 0;
	
	public static final int HEXAGON_SUBTILE_FN_X = 0;
	public static final int HEXAGON_SUBTILE_FN_Y = -HEXAGON_RADIUS/2;
	
	public static final int HEXAGON_SUBTILE_FS_X = 0;
	public static final int HEXAGON_SUBTILE_FS_Y = +HEXAGON_RADIUS/2;
	
	public static final int HEXAGON_SUBTILE_FNW_X = -THIRTY_DEGREE_X/2;
	public static final int HEXAGON_SUBTILE_FNW_Y = -THIRTY_DEGREE_Y/2;
	
	public static final int HEXAGON_SUBTILE_FNE_X = +THIRTY_DEGREE_X/2;
	public static final int HEXAGON_SUBTILE_FNE_Y = -THIRTY_DEGREE_Y/2;
	
	public static final int HEXAGON_SUBTILE_FSE_X = +THIRTY_DEGREE_X/2;
	public static final int HEXAGON_SUBTILE_FSE_Y = +THIRTY_DEGREE_Y/2;
	
	public static final int HEXAGON_SUBTILE_FSW_X = -THIRTY_DEGREE_X/2;
	public static final int HEXAGON_SUBTILE_FSW_Y = +THIRTY_DEGREE_Y/2;
	
	public static final int getXBySubTile(SubTile subtile) {
		switch(subtile){
		case CENTER:
			return HEXAGON_SUBTILE_C_X;
		case EAST:
			return HEXAGON_SUBTILE_E_X;
		case FLOATING_NORTH:
			return HEXAGON_SUBTILE_FN_X;
		case FLOATING_SOUTH:
			return HEXAGON_SUBTILE_FS_X;
		case NORTH_WEST:
			return HEXAGON_SUBTILE_NW_X;
		case NORTH_EAST:
			return HEXAGON_SUBTILE_NE_X;
		case SOUTH_EAST:
			return HEXAGON_SUBTILE_SE_X;
		case SOUTH_WEST:
			return HEXAGON_SUBTILE_SW_X;
		case WEST:
			return HEXAGON_SUBTILE_W_X;
		}
		return 0;
	}
	
	public static final int getYBySubTile(SubTile subtile) {
		switch(subtile){
		case CENTER:
			return HEXAGON_SUBTILE_C_X;
		case EAST:
			return HEXAGON_SUBTILE_E_Y;
		case FLOATING_NORTH:
			return HEXAGON_SUBTILE_FN_Y;
		case FLOATING_SOUTH:
			return HEXAGON_SUBTILE_FS_Y;
		case NORTH_WEST:
			return HEXAGON_SUBTILE_NW_Y;
		case NORTH_EAST:
			return HEXAGON_SUBTILE_NE_Y;
		case SOUTH_EAST:
			return HEXAGON_SUBTILE_SE_Y;
		case SOUTH_WEST:
			return HEXAGON_SUBTILE_SW_Y;
		case WEST:
			return HEXAGON_SUBTILE_W_Y;
		}
		return 0;
	}
	
	
}

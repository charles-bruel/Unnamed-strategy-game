package chazzvader.game.other;

/**
 * Version of Coordinate that includes sub-tile data
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public class SubTileCoordinate extends Coordinate {

	@Override
	public String toString() {
		return x+", "+y+", "+subTile.toString();
		//return "SubTileCoordinate [subTile=" + subTile + ", x=" + x + ", y=" + y + ", hashCode()=" + hashCode() + "]";
	}

	private SubTile subTile;
	
	public SubTileCoordinate(int x, int y,  SubTile subTile) {
		super(x, y);
		this.subTile = subTile;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((subTile == null) ? 0 : subTile.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubTileCoordinate other = (SubTileCoordinate) obj;
		if (subTile != other.subTile)
			return false;
		return true;
	}

	public SubTileCoordinate(Coordinate c,  SubTile subTile) {
		super(c.getX(), c.getY());
		this.subTile = subTile;
	}

	public SubTile getSubTile() {
		return subTile;
	}

	public void setSubTile(SubTile subTile) {
		this.subTile = subTile;
	}
	
	@Override
	public boolean sameAs(Coordinate c) {
		if(c instanceof SubTileCoordinate) {
			SubTileCoordinate stc = (SubTileCoordinate) c;
			return super.sameAs(c) && stc.getSubTile() == subTile;
		} else {
			return super.sameAs(c);
		}
	}

	public enum SubTile {
		CENTER,NORTH_WEST,NORTH_EAST,EAST,SOUTH_EAST,SOUTH_WEST,WEST,FLOATING_NORTH,FLOATING_SOUTH;
		
		public static SubTile fromID(int id) {
			switch (id) {
			case 0:
				return CENTER;
			case 1:
				return NORTH_WEST;
			case 2:
				return NORTH_EAST;
			case 3:
				return EAST;
			case 4:
				return SOUTH_EAST;
			case 5:
				return SOUTH_WEST;
			case 6:
				return WEST;
			case 7:
				return FLOATING_NORTH;
			case 8:
				return FLOATING_SOUTH;
			default:
				return CENTER;
			}
		}
		
		public static int toID(SubTile type) {
			switch (type) {
			case CENTER:
				return 0;
			case NORTH_WEST:
				return 1;
			case NORTH_EAST:
				return 2;
			case EAST:
				return 3;
			case SOUTH_EAST:
				return 4;
			case SOUTH_WEST:
				return 5;
			case WEST:
				return 6;
			case FLOATING_NORTH:
				return 7;
			case FLOATING_SOUTH:
				return 8;
			default:
				return 0;
			}
		}
		
		public int toID() {
			switch (this) {
			case CENTER:
				return 0;
			case NORTH_WEST:
				return 1;
			case NORTH_EAST:
				return 2;
			case EAST:
				return 3;
			case SOUTH_EAST:
				return 4;
			case SOUTH_WEST:
				return 5;
			case WEST:
				return 6;
			case FLOATING_NORTH:
				return 7;
			case FLOATING_SOUTH:
				return 8;
			default:
				return 0;
			}
		}
	}
	
}
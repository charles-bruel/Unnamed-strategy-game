package chazzvader.game.content.units;

import chazzvader.game.content.units.military.UnitAxeman;
import chazzvader.game.other.SubTileCoordinate.SubTile;
import chazzvader.game.sided.both.game.Entity;

public class Units {
	
	public static UnitSelector fromIndex(int index) {
		switch(index) {
		case 0:
			return MILITARY;
		}
		return null;
	}
	
	public static int toUpperIndex(Unit u) {
		int i = -1;
		
		int r = MILITARY.toIndex(u);
		if(r != -1) i = r;
		
		return i;
	}
	
	public static final UnitSelectorMilitary MILITARY = new UnitSelectorMilitary();
	
	
	public static class UnitSelectorMilitary extends UnitSelector {
		public Unit axeman(Entity owner, int x, int y, SubTile subTile, int uid) { return new UnitAxeman(owner, x, y, subTile, uid); }
		
		@Override
		public int toIndex(Unit unit) {
			int i = -1;
			
			if(unit instanceof UnitAxeman) i = 0;
			
			return i;
		}
		
		@Override
		public Unit fromIndex(int index, Entity owner, int x, int y, SubTile subTile, int uid) {
			switch(index) {
			case 0:
				return axeman(owner, x, y, subTile, uid);
			}
			return null;
		}
	}
}

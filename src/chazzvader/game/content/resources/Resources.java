package chazzvader.game.content.resources;

public class Resources {
	
	public static Resource diamond() {return new ResourceDiamond();}
	public static Resource uranium() {return new ResourceUranium();}
	public static Resource iron() {return new ResourceIron();}
	public static Resource deer() {return new ResourceDeer();}
	public static Resource oil() {return new ResourceOil();}
	
	public static Resource fromIndex(int i) {
		switch(i) {
		case 1: return diamond();
		case 2: return uranium();
		case 3: return iron();
		case 4: return deer();
		case 5: return oil();
		}
		return null;
	}
	
	public static int resourceCount() {
		return 5;
	}
	
	public static int toIndex(Resource r) {
		if(r == null) return 0;
		switch(r.getInName()) {
		case "ResourceDiamond": return 1;
		case "ResourceUranium": return 2;
		case "ResourceIron": return 3;
		case "ResourceDeer": return 4;
		case "ResourceOil": return 5;
		}
		return 0;
	}

}

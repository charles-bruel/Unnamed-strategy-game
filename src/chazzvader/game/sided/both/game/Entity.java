package chazzvader.game.sided.both.game;

import java.awt.Color;

/**
 * Represents a political entity.
 * @author csbru
 * @version 1.0
 * @since 1.0
 */
public class Entity {

	private String name;
	private Color color;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * @param name
	 * @param color
	 */
	public Entity(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	public enum EntityType {
		PLAYER(0);
		
		EntityType(int id){
			
		}
	}
	
}
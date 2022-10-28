package chazzvader.game.engine.text;

import chazzvader.game.engine.render.TextureManager.Texture;

public class TextCharacter {

	public static enum TextCharacterType {
		CHARACTER,ICON;
	}
	
	public final char replaceChar;
	public final Texture linkTex;
	public final String name;
	public final float width;
	public final boolean number;
	public final TextCharacterType textCharacterType;

	public TextCharacter(char replaceChar, Texture linkTex, String name, float width, TextCharacterType textCharacterType, boolean number) {
		this.replaceChar = replaceChar;
		this.linkTex = linkTex;
		this.name = name;
		this.width = width;
		this.textCharacterType = textCharacterType;
		this.number = number;
		TextCharacters.registerTextCharacter(this);
	}
	
	public TextCharacter(char replaceChar, Texture linkTex, String name, float width, TextCharacterType textCharacterType) {
		this(replaceChar, linkTex, name, width, textCharacterType, false);
	}
	
	public TextCharacter(char replaceChar, Texture linkTex, float width, TextCharacterType textCharacterType, boolean number) {
		this(replaceChar, linkTex, "char " + replaceChar, width, textCharacterType, number);
	}
	
	public TextCharacter(char replaceChar, Texture linkTex, float width, TextCharacterType textCharacterType) {
		this(replaceChar, linkTex, "char " + replaceChar, width, textCharacterType);
	}
	
}

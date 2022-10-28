package chazzvader.game.engine.text;

import java.util.ArrayList;

import chazzvader.game.engine.render.TextureManager;

public class TextCharacters {
	
	private TextCharacters() {}
	
	private static ArrayList<TextCharacter> textCharacters = new ArrayList<>();
	
	public static final TextCharacter 
		CHAR_q = new TextCharacter('q', TextureManager.ICON_q, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_Q = new TextCharacter('Q', TextureManager.ICON_Q, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_w = new TextCharacter('w', TextureManager.ICON_w, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_W = new TextCharacter('W', TextureManager.ICON_W, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_e = new TextCharacter('e', TextureManager.ICON_e, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_E = new TextCharacter('E', TextureManager.ICON_E, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_r = new TextCharacter('r', TextureManager.ICON_r, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_R = new TextCharacter('R', TextureManager.ICON_R, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_t = new TextCharacter('t', TextureManager.ICON_t, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_T = new TextCharacter('T', TextureManager.ICON_T, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_y = new TextCharacter('y', TextureManager.ICON_y, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_Y = new TextCharacter('Y', TextureManager.ICON_Y, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_u = new TextCharacter('u', TextureManager.ICON_u, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_U = new TextCharacter('U', TextureManager.ICON_U, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_i = new TextCharacter('i', TextureManager.ICON_i, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_I = new TextCharacter('I', TextureManager.ICON_I, 0.5f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_o = new TextCharacter('o', TextureManager.ICON_o, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_O = new TextCharacter('O', TextureManager.ICON_O, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_p = new TextCharacter('p', TextureManager.ICON_p, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_P = new TextCharacter('P', TextureManager.ICON_P, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_a = new TextCharacter('a', TextureManager.ICON_a, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_A = new TextCharacter('A', TextureManager.ICON_A, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_s = new TextCharacter('s', TextureManager.ICON_s, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_S = new TextCharacter('S', TextureManager.ICON_S, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_d = new TextCharacter('d', TextureManager.ICON_d, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_D = new TextCharacter('D', TextureManager.ICON_D, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_f = new TextCharacter('f', TextureManager.ICON_f, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_F = new TextCharacter('F', TextureManager.ICON_F, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_g = new TextCharacter('g', TextureManager.ICON_g, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_G = new TextCharacter('G', TextureManager.ICON_G, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_h = new TextCharacter('h', TextureManager.ICON_h, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_H = new TextCharacter('H', TextureManager.ICON_H, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_j = new TextCharacter('j', TextureManager.ICON_j, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_J = new TextCharacter('J', TextureManager.ICON_J, 0.5f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_k = new TextCharacter('k', TextureManager.ICON_k, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_K = new TextCharacter('K', TextureManager.ICON_K, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_l = new TextCharacter('l', TextureManager.ICON_l, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_L = new TextCharacter('L', TextureManager.ICON_L, 0.5f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_z = new TextCharacter('z', TextureManager.ICON_z, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_Z = new TextCharacter('Z', TextureManager.ICON_Z, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_x = new TextCharacter('x', TextureManager.ICON_x, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_X = new TextCharacter('X', TextureManager.ICON_X, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_c = new TextCharacter('c', TextureManager.ICON_c, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_C = new TextCharacter('C', TextureManager.ICON_C, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_v = new TextCharacter('v', TextureManager.ICON_v, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_V = new TextCharacter('V', TextureManager.ICON_V, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_b = new TextCharacter('b', TextureManager.ICON_b, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_B = new TextCharacter('B', TextureManager.ICON_B, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_n = new TextCharacter('n', TextureManager.ICON_n, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_N = new TextCharacter('N', TextureManager.ICON_N, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_m = new TextCharacter('m', TextureManager.ICON_m, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_M = new TextCharacter('M', TextureManager.ICON_M, 0.6f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_0 = new TextCharacter('0', TextureManager.ICON_0, 0.7f, TextCharacter.TextCharacterType.CHARACTER, true),
		CHAR_1 = new TextCharacter('1', TextureManager.ICON_1, 0.4f, TextCharacter.TextCharacterType.CHARACTER, true),
		CHAR_2 = new TextCharacter('2', TextureManager.ICON_2, 0.6f, TextCharacter.TextCharacterType.CHARACTER, true),
		CHAR_3 = new TextCharacter('3', TextureManager.ICON_3, 0.7f, TextCharacter.TextCharacterType.CHARACTER, true),
		CHAR_4 = new TextCharacter('4', TextureManager.ICON_4, 0.7f, TextCharacter.TextCharacterType.CHARACTER, true),
		CHAR_5 = new TextCharacter('5', TextureManager.ICON_5, 0.7f, TextCharacter.TextCharacterType.CHARACTER, true),
		CHAR_6 = new TextCharacter('6', TextureManager.ICON_6, 0.7f, TextCharacter.TextCharacterType.CHARACTER, true),
		CHAR_7 = new TextCharacter('7', TextureManager.ICON_7, 0.7f, TextCharacter.TextCharacterType.CHARACTER, true),
		CHAR_8 = new TextCharacter('8', TextureManager.ICON_8, 0.7f, TextCharacter.TextCharacterType.CHARACTER, true),
		CHAR_9 = new TextCharacter('9', TextureManager.ICON_9, 0.7f, TextCharacter.TextCharacterType.CHARACTER, true),
		CHAR_AND = new TextCharacter('&', TextureManager.ICON_AND, 0.8f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_ASTERISK = new TextCharacter('*', TextureManager.ICON_ASTERISK, 0.8f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_AT = new TextCharacter('@', TextureManager.ICON_AT, 0.8f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_BACKSLASH = new TextCharacter('\\', TextureManager.ICON_BACKSLASH, 0.8f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_CARROT = new TextCharacter('^', TextureManager.ICON_CARROT, 0.8f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_COLON = new TextCharacter(':', TextureManager.ICON_COLON, 0.2f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_COMMA = new TextCharacter(',', TextureManager.ICON_COMMA, 0.2f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_DASH = new TextCharacter('-', TextureManager.ICON_DASH, 0.8f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_DOLLAR_SIGN = new TextCharacter('$', TextureManager.ICON_DOLLAR_SIGN, 0.8f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_DOUBLE_QUOTES = new TextCharacter('"', TextureManager.ICON_DOUBLE_QUOTES, 0.8f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_EQUALS = new TextCharacter('=', TextureManager.ICON_EQUALS, 0.8f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_EXCLAMATION_MARK = new TextCharacter('!', TextureManager.ICON_EXCLAMATION_MARK, 0.2f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_HASHTAG = new TextCharacter('#', TextureManager.ICON_HASHTAG, 0.8f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_LEFT_ANGLE_BRACKET = new TextCharacter('<', TextureManager.ICON_LEFT_ANGLE_BRACKET, 0.3f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_LEFT_CURLY_BRACKET = new TextCharacter('{', TextureManager.ICON_LEFT_CURLY_BRACKET, 0.3f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_LEFT_PARENTHESES = new TextCharacter('(', TextureManager.ICON_LEFT_PARENTHESES, 0.3f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_LEFT_SQUARE_BRACKET = new TextCharacter('[', TextureManager.ICON_LEFT_SQUARE_BRACKET, 0.3f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_PERCENT = new TextCharacter('%', TextureManager.ICON_PERCENT, 0.8f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_PEROID = new TextCharacter('.', TextureManager.ICON_PEROID, 0.2f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_PIPE = new TextCharacter('|', TextureManager.ICON_PIPE, 0.2f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_PLUS = new TextCharacter('+', TextureManager.ICON_PLUS, 0.8f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_QUESTION_MARK = new TextCharacter('?', TextureManager.ICON_QUESTION_MARK, 0.8f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_QUOTE = new TextCharacter('\'', TextureManager.ICON_QUOTE, 0.3f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_RIGHT_ANGLE_BRACKET = new TextCharacter('>', TextureManager.ICON_RIGHT_ANGLE_BRACKET, 0.8f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_RIGHT_CURLY_BRACKET = new TextCharacter('}', TextureManager.ICON_RIGHT_CURLY_BRACKET, 0.3f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_RIGHT_PARENTHESES = new TextCharacter(')', TextureManager.ICON_RIGHT_PARENTHESES, 0.3f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_RIGHT_SQUARE_BRACKET = new TextCharacter(']', TextureManager.ICON_RIGHT_SQUARE_BRACKET, 0.3f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_SEMICOLON = new TextCharacter(';', TextureManager.ICON_SEMICOLON, 0.2f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_SLASH = new TextCharacter('/', TextureManager.ICON_SLASH, 0.3f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_UNDERSCORE = new TextCharacter('_', TextureManager.ICON_UNDERSCORE, 0.8f, TextCharacter.TextCharacterType.CHARACTER),
		CHAR_SPACE = new TextCharacter(' ', TextureManager.ICON_BLANK, 1, TextCharacter.TextCharacterType.CHARACTER);
	
	private static final TextCharacter CHAR_FALLBACK = CHAR_QUESTION_MARK;
	
	public static void registerTextCharacter(TextCharacter textCharacter) {
		if(!textCharacters.contains(textCharacter)) textCharacters.add(textCharacter);
	}

	public static TextCharacter getByChar(char c) {
		for(int i = 0;i < textCharacters.size();i ++) {
			if(textCharacters.get(i).replaceChar == c) {
				return textCharacters.get(i);
			}
		}
		return CHAR_FALLBACK;
	}
	

}
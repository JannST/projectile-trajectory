package de.janst.trajectory.util;

public enum RGBColor {

	WHITE("white", "White", 255, 255, 255, (byte)0),
	ORANGE("orange", "Orange", 228, 119, 11, (byte)1),
	MAGENTA("magenta", "Magenta", 171, 66, 180, (byte)2),
	BLUE("blue", "Blue", 101, 133, 199, (byte)3),
	YELLOW("yellow", "Yellow", 255, 255, 1, (byte)4),
	LIME("lime", "Lime", 1, 255, 1, (byte)5),
	PINK("pink", "Pink", 216, 152, 169, (byte)6),
	DARK_GRAY("darkgray", "Dark gray", 61, 61, 61, (byte)7),
	GRAY("gray", "Gray", 42, 101, 126, (byte)8),
	CYAN("cyan", "Cyan", 1, 255, 255, (byte)9),
	PURPLE("purple", "Purple", 104, 49, 151, (byte)10),
	DARK_BLUE("darkblue", "Dark blue", 43, 53, 150, (byte)11),
	BROWN("brown", "Brown", 78, 49, 30, (byte)12),
	DARK_GREEN("green", "Green", 57, 76, 30, (byte)13),
	DARK_RED("red", "Red", 255, 1, 1, (byte)14),
	BLACK("black", "Black", 1, 1, 1, (byte)15);
	
	private int r;
	private int g;
	private int b;
	private String name;
	private String displayName;
	private byte data;

	RGBColor(String name, String displayName ,int r, int g, int b, byte data) {
		this.name = name;
		this.displayName = displayName;
		this.r = r;
		this.g = g;
		this.b = b;
		this.data = data;
	}
	
	public static RGBColor fromName(String name) {
		for(RGBColor color : values()) {
			if(color.getName().equals(name)) {
				return color;
			}
		}
		return null;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public int getRed() {
		return this.r;
	}
	
	public int getGreen() {
		return this.g;
	}
	
	public int getBlue() {
		return this.b;
	}
	
	public byte getData() {
		return this.data;
	}
	
}

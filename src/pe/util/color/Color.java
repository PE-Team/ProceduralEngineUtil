package pe.util.color;

import java.nio.FloatBuffer;

import pe.util.math.Maths;
import pe.util.math.Vec3f;
import pe.util.math.Vec4f;

/**
 * Represents a Color in the sRGB color space. Each color has four values: Red,
 * Green, Blue and Alpha as r, g, b, and a respectively. By default, not
 * specifying an alpha value in the constructor will set the alpha to be 1.
 * <p>
 * Constructing a Color using integers will always set <code>colorMode</code> to
 * be <code>Color.INT</code>, which means that the values of r, g, and b should
 * be regarded as integers between 0 and 255 (inclusive). Using floats will
 * instead set the <code>colorMode</code> to <code>Color.FLOAT</code>, which
 * means the values of r, g, and b should be regarded as decimals between 0 and
 * 1 (inclusive). It should be noted that the bounds are not enforced, so a
 * float color may have a value above 1 for Red, Green, or Blue.
 * 
 * @author Ethan Penn
 * 
 * @see #r
 * @see #g
 * @see #b
 * @see #a
 * @see #colorMode
 * @see #Color(int, int, int)
 * @see #Color(float, float, float)
 *
 * @since 1.0
 */
public class Color {

	/**
	 * The value for <code>colorType</code> to designate that the values for rgb
	 * directly correlate to the Red, Green, and Blue of the color as well as
	 * that the Alpha (a) of the color defaulted to 1.
	 * 
	 * @since 1.0
	 * 
	 * @see #colorType
	 */
	public static final int RGB = 0;

	/**
	 * The value for <code>colorType</code> to designate that the values for
	 * rgba directly correlate to the Red, Green, Blue, and Alpha of the color.
	 * 
	 * @see #colorType
	 * 
	 * @since 1.0
	 */
	public static final int RGBA = 2;

	/**
	 * The value for <code>colorType</code> to designate that the values for rgb
	 * correlate to Cyan, Magenta, and Yellow of the color as well as that the
	 * Alpha (a) of the color defaulted to 1.
	 * 
	 * @see #colorType
	 * 
	 * @since 1.0
	 */
	public static final int CMY = 4;

	/**
	 * The value for <code>colorType</code> to designate that the values for
	 * rgba correlate to the Cyan, Magenta, Yellow, and Alpha of the color.
	 * 
	 * @see #colorType
	 * 
	 * @since 1.0
	 */
	public static final int CMYA = 6;

	/**
	 * The value for <code>colorMode</code> to designate that the values of rgb
	 * should be integers between 0 and 255 (inclusive).
	 * 
	 * @see #colorMode
	 * 
	 * @since 1.0
	 */
	public static final int INT = 1;

	/**
	 * The value for <code>colorMode</code> to designate that the values of rgb
	 * should be floats between 0 and 1 (inclusive).
	 * 
	 * @see #colorMode
	 * 
	 * @since 1.0
	 */
	public static final int FLOAT = 3;

	/**
	 * A completely clear and transparent color. Should be used as defaults for
	 * <code>Color</code> fields.
	 * <p>
	 * Values:
	 * <ul>
	 * <li>r: 0
	 * <li>g: 0
	 * <li>b: 0
	 * <li>a: 0
	 * </ul>
	 * 
	 * @since 1.0
	 */
	public static final Color CLEAR = new Color(0, 0, 0, 0);

	/**
	 * The color Black as a <code>Color</code> object.
	 * <p>
	 * Values:
	 * <ul>
	 * <li>r: 0
	 * <li>g: 0
	 * <li>b: 0
	 * <li>a: 1
	 * </ul>
	 * 
	 * @since 1.0
	 */
	public static final Color BLACK = new Color(0, 0, 0);

	/**
	 * The color Dark Gray as a <code>Color</code> object.
	 * <p>
	 * Values:
	 * <ul>
	 * <li>r: 64
	 * <li>g: 64
	 * <li>b: 64
	 * <li>a: 1
	 * </ul>
	 * 
	 * @since 1.0
	 */
	public static final Color DARK_GRAY = new Color(64, 64, 64);

	/**
	 * The color Gray as a <code>Color</code> object.
	 * <p>
	 * Values:
	 * <ul>
	 * <li>r: 0
	 * <li>g: 0
	 * <li>b: 0
	 * <li>a: 1
	 * </ul>
	 * 
	 * @since 1.0
	 */
	public static final Color GRAY = new Color(128, 128, 128);

	/**
	 * The color Light Gray as a <code>Color</code> object.
	 * <p>
	 * Values:
	 * <ul>
	 * <li>r: 192
	 * <li>g: 192
	 * <li>b: 192
	 * <li>a: 1
	 * </ul>
	 * 
	 * @since 1.0
	 */
	public static final Color LIGHT_GRAY = new Color(192, 192, 192);

	/**
	 * The color White as a <code>Color</code> object.
	 * <p>
	 * Values:
	 * <ul>
	 * <li>r: 255
	 * <li>g: 255
	 * <li>b: 255
	 * <li>a: 1
	 * </ul>
	 * 
	 * @since 1.0
	 */
	public static final Color WHITE = new Color(255, 255, 255);

	/**
	 * The color Red as a <code>Color</code> object.
	 * <p>
	 * Values:
	 * <ul>
	 * <li>r: 256
	 * <li>g: 0
	 * <li>b: 0
	 * <li>a: 1
	 * </ul>
	 * 
	 * @since 1.0
	 */
	public static final Color RED = new Color(255, 0, 0);

	/**
	 * The color Orange as a <code>Color</code> object.
	 * <p>
	 * Values:
	 * <ul>
	 * <li>r: 255
	 * <li>g: 165
	 * <li>b: 0
	 * <li>a: 1
	 * </ul>
	 * 
	 * @since 1.0
	 */
	public static final Color ORANGE = new Color(255, 165, 0);

	/**
	 * The color Blue as a <code>Color</code> object.
	 * <p>
	 * Values:
	 * <ul>
	 * <li>r: 0
	 * <li>g: 0
	 * <li>b: 255
	 * <li>a: 1
	 * </ul>
	 * 
	 * @since 1.0
	 */
	public static final Color BLUE = new Color(0, 0, 255);

	/**
	 * The color Green as a <code>Color</code> object.
	 * <p>
	 * Values:
	 * <ul>
	 * <li>r: 0
	 * <li>g: 255
	 * <li>b: 0
	 * <li>a: 1
	 * </ul>
	 * 
	 * @since 1.0
	 */
	public static final Color GREEN = new Color(0, 255, 0);

	/**
	 * The value for the Red of the color.
	 * 
	 * @since 1.0
	 */
	public float r;

	/**
	 * The value for the Green of the color.
	 * 
	 * @since 1.0
	 */
	public float g;

	/**
	 * The value for the Blue of the color.
	 * 
	 * @since 1.0
	 */
	public float b;

	/**
	 * The value for the Alpha (transparency) of the color. This value should
	 * always be between 0 and 1, with 1 being a solid color and 0 meaning the
	 * color is completely invisible.
	 * 
	 * @since 1.0
	 */
	public float a;

	/**
	 * Represents the way the <code>Color</code> object uses its rgba values.
	 * Can be set to values such as <code>Color.RGB</code>,
	 * <code>Color.RGBA</code>, <code>Color.CMY</code>, and more.
	 * 
	 * @see #RGB
	 * @see #RGBA
	 * @see #CMY
	 * @see #CMYA
	 * 
	 * @since 1.0
	 */
	private int colorType;

	/**
	 * Represents whether the rgb values should be values between 0 and 255
	 * (inclusive) as integers or between 0 and 1 (inclusive) as floats.
	 * 
	 * @see #INT
	 * @see #FLOAT
	 * 
	 * @since 1.0
	 */
	private int colorMode;

	/**
	 * Constructs a <code>Color</code> object with the given rgb values for the
	 * color. The Alpha (a) is defaulted to 1, the <code>colorType</code>
	 * defaults to <code>Color.RGB</code>, and the <code>colorMode</code>
	 * defaults to <code>Color.FLOAT</code>.
	 * 
	 * @param r
	 *            The value for Red between 0 and 1 (inclusive)
	 * @param g
	 *            The value for Green between 0 and 1 (inclusive)
	 * @param b
	 *            The value for Blue between 0 and 1 (inclusive)
	 * 
	 * @see #r
	 * @see #g
	 * @see #b
	 * @see #a
	 * @see #colorType
	 * @see #RGB
	 * @see #colorMode
	 * @see #FLOAT
	 * 
	 * @since 1.0
	 */
	public Color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1;
		this.colorType = RGB;
		this.colorMode = FLOAT;
	}

	/**
	 * Constructs a <code>Color</code> object with the given rgba values for the
	 * color. The <code>colorType</code> defaults to <code>Color.RGBA</code>,
	 * and the <code>colorMode</code> defaults to <code>Color.FLOAT</code>.
	 * 
	 * @param r
	 *            The value for Red between 0 and 1 (inclusive)
	 * @param g
	 *            The value for Green between 0 and 1 (inclusive)
	 * @param b
	 *            The value for Blue between 0 and 1 (inclusive)
	 * @param a
	 *            The value for Alpha between 0 and 1 (inclusive)
	 * 
	 * @see #r
	 * @see #g
	 * @see #b
	 * @see #a
	 * @see #colorType
	 * @see #RGBA
	 * @see #colorMode
	 * @see #FLOAT
	 * 
	 * @since 1.0
	 */
	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		this.colorType = RGBA;
		this.colorMode = FLOAT;
	}

	/**
	 * Constructs a <code>Color</code> object from the given integer encoding of
	 * the color in either the form 0bRRRRRRRRGGGGGGGGBBBBBBBBAAAAAAAA or the
	 * form 0bRRRRRRRRGGGGGGGGBBBBBBBB where for each color, for example Red,
	 * its string of letters (RRRRRRRR) is the binary representation of the
	 * integer value for that primary color. If the alpha is not specified as
	 * the second integer, then the value of <code>alpha</code> should be false.
	 * and the Alpha value will default to 1. In either case, the
	 * <code>colorType</code> defaults to <code>Color.RGB</code> or
	 * <code>Color.RGBA</code> depending on <code>alpha</code>, and the
	 * <code>colorMode</code> defaulting to <code>Color.INT</code>.
	 * <p>
	 * As an example, 0b 11111111 00000000 00000000 would be Red with an alpha
	 * value equal to 1, whereas 0b 11111111 00000000 00000000 01111111 would be
	 * a half-translucent Red.
	 * 
	 * @param color
	 *            The integer value for a color
	 * @param alpha
	 *            Whether the integer color value encodes the Alpha.
	 * 
	 * @see #r
	 * @see #g
	 * @see #b
	 * @see #a
	 * @see #colorType
	 * @see #RGB
	 * @see #RGBA
	 * @see #colorMode
	 * @see #Int
	 * @see #toInt()
	 * 
	 * @since 1.0
	 */
	public Color(int color, boolean alpha) {
		if (alpha) {
			this.r = (color) >> 24;
			this.g = (color - ((int) r << 24)) >> 16;
			this.b = (color - ((int) r << 24) - ((int) g << 16)) >> 8;
			this.a = (color - ((int) r << 24) - ((int) g << 16) - ((int) b << 8));
			this.colorType = RGBA;
		} else {
			this.r = (color) >> 16;
			this.g = (color - ((int) r << 16)) >> 8;
			this.b = (color - ((int) r << 16) - ((int) g << 8));
			this.a = 1;
			this.colorType = RGB;
		}

		this.colorMode = INT;
	}

	/**
	 * Constructs a <code>Color</code> object with the given rgb values for the
	 * color. The Alpha (a) is defaulted to 1, the <code>colorType</code>
	 * defaults to <code>Color.RGB</code>, and the <code>colorMode</code>
	 * defaults to <code>Color.INT</code>.
	 * 
	 * @param r
	 *            The value for Red between 0 and 255 (inclusive)
	 * @param g
	 *            The value for Green between 0 and 255 (inclusive)
	 * @param b
	 *            The value for Blue between 0 and 255 (inclusive)
	 * 
	 * @see #r
	 * @see #g
	 * @see #b
	 * @see #a
	 * @see #colorType
	 * @see #RGB
	 * @see #colorMode
	 * @see #Int
	 * 
	 * @since 1.0
	 */
	public Color(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1;
		this.colorType = RGB;
		this.colorMode = INT;
	}

	/**
	 * Constructs a <code>Color</code> object with the given rgba values for the
	 * color. The <code>colorType</code> defaults to <code>Color.RGBA</code>,
	 * and the <code>colorMode</code> defaults to <code>Color.INT</code>.
	 * 
	 * @param r
	 *            The value for Red between 0 and 255 (inclusive)
	 * @param g
	 *            The value for Green between 0 and 255 (inclusive)
	 * @param b
	 *            The value for Blue between 0 and 255 (inclusive)
	 * @param a
	 *            The value for Alpha between 0 and 255 (inclusive)
	 * 
	 * @see #r
	 * @see #g
	 * @see #b
	 * @see #a
	 * @see #colorType
	 * @see #RGBA
	 * @see #colorMode
	 * @see #INT
	 * 
	 * @since 1.0
	 */
	public Color(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		this.colorType = RGBA;
		this.colorMode = INT;
	}

	/**
	 * Constructs a <code>Color</code> object using a {@link java.awt.Color}
	 * object. The rgba values are set to <code>color</code>'s Red, Green, Blue,
	 * and Alpha values. <code>colorType</code> defaults to
	 * <code>Color.RGBA</code> and <code>colorMode</code> defaults to
	 * <code>Color.INT</code>.
	 * 
	 * @param color
	 *            A <code>java.awt.Color</code> object from which to extract its
	 *            rgba values
	 * 
	 * @see #r
	 * @see #g
	 * @see #b
	 * @see #a
	 * @see #colorType
	 * @see #RGBA
	 * @see #colorMode
	 * @see #INT
	 * @see java.awt.Color
	 * 
	 * @since 1.0
	 */
	public Color(java.awt.Color color) {
		this.r = color.getRed();
		this.g = color.getGreen();
		this.b = color.getBlue();
		this.a = color.getAlpha();
		this.colorType = RGBA;
		this.colorMode = INT;
	}

	/**
	 * Constructs a <code>Color</code> object using a {@link Vec3f} object. The
	 * rgb values are set to <code>vec</code>'s x, y, and z values. The Alpha
	 * (a) defaults to 1, the <code>colorType</code> defaults to
	 * <code>Color.RGB</code> and <code>colorMode</code> defaults to
	 * <code>Color.FLOAT</code>.
	 * 
	 * @param vec
	 *            A <code>java.awt.Color</code> object from which to a color's
	 *            rgb values from the vector's xyz values. The vector's
	 *            components should be between 0 and 1 (inclusive).
	 * 
	 * @see #r
	 * @see #g
	 * @see #b
	 * @see #a
	 * @see #colorType
	 * @see #RGB
	 * @see #colorMode
	 * @see #FLOAT
	 * @see Vec3f
	 * 
	 * @since 1.0
	 */
	public Color(Vec3f vec) {
		this.r = vec.x;
		this.g = vec.y;
		this.b = vec.z;
		this.a = 1;
		this.colorType = RGB;
		this.colorMode = FLOAT;
	}

	/**
	 * Constructs a <code>Color</code> object using a {@link Vec3f} object and
	 * the parameter <code>alpha</code> as the value for the color's Alpha (a).
	 * The rgb values are set to <code>vec</code>'s x, y, and z values. The
	 * <code>colorType</code> defaults to <code>Color.RGBA</code> and
	 * <code>colorMode</code> defaults to <code>Color.FLOAT</code>.
	 * 
	 * @param vec
	 *            A <code>java.awt.Color</code> object from which to a color's
	 *            rgb values from the vector's xyz values. The vector's
	 *            components should be between 0 and 1 (inclusive).
	 * 
	 * @param a
	 *            The value of the Alpha for the color between 0 and 1
	 *            (inclusive)
	 * 
	 * @see #r
	 * @see #g
	 * @see #b
	 * @see #a
	 * @see #colorType
	 * @see #RGBA
	 * @see #colorMode
	 * @see #FLOAT
	 * @see Vec3f
	 * 
	 * @since 1.0
	 */
	public Color(Vec3f vec, float alpha) {
		this.r = vec.x;
		this.g = vec.y;
		this.b = vec.z;
		this.a = alpha;
		this.colorType = RGBA;
		this.colorMode = FLOAT;
	}

	public Color(Vec4f vec) {
		this.r = vec.x;
		this.g = vec.y;
		this.b = vec.z;
		this.a = vec.w;
		this.colorType = RGBA;
		this.colorMode = FLOAT;
	}

	public int getColorChannels() {
		switch (colorType) {
		case RGB:
		case CMY:
			return 3;
		case RGBA:
		case CMYA:
			return 4;
		}
		throw new IllegalStateException("This color has a unknown 'colorType' value of: " + colorType);
	}

	public int getColorMode() {
		return colorMode;
	}

	public int getColorType() {
		return colorType;
	}

	/**
	 * Gives the {@link Vec3f} representation of the Color. The vector will be
	 * equal to {r, g, b}.
	 * 
	 * @return The vector representation of this color.
	 * 
	 * @see #r
	 * @see #g
	 * @see #b
	 * @see Vec3f
	 * 
	 * @since 1.0
	 */
	public Vec3f getVec3f() {
		return new Vec3f(r, g, b);
	}

	/**
	 * Gives the {@link Vec4f} representation of the Color. The vector will be
	 * equal to {r, g, b, a}.
	 * 
	 * @return The vector representation of this color.
	 * 
	 * @see #r
	 * @see #g
	 * @see #b
	 * @see #a
	 * @see Vec4f
	 * 
	 * @since 1.0
	 */
	public Vec4f getVec4f() {
		this.toDecimal();
		return new Vec4f(r, g, b, a);
	}

	public String getHex() {
		return Maths.intToHex(this.getInt());
	}

	public int getInt() {
		return ((int) r) << 24 + ((int) g) << 16 + ((int) b) << 8 + ((int) a);
	}

	/**
	 * Takes the <code>Color</code> object represented by this class and returns
	 * a <code>Color</code> object from <code>java.awt.Color</code>. It does
	 * this by first converting the color to its decimal form, and then
	 * inputting that into the <code>java.awt.Color</code> constructor for
	 * floats. The alpha for this color is included in the constructor.
	 * 
	 * @return A <code>java.awt.Color</code> object representing this object.
	 * 
	 * @since 1.0
	 */
	public java.awt.Color getJColor() {
		this.toDecimal();
		return new java.awt.Color(this.r, this.g, this.b, this.a);
	}

	public FloatBuffer putInBuffer(FloatBuffer buffer) {
		switch (colorType) {
		case RGB:
		case CMY:
			return putInBuffer3(buffer);
		case RGBA:
		case CMYA:
			return putInBuffer4(buffer);
		}
		throw new IllegalArgumentException("This color has a unknown 'colorType' value of: " + colorType);
	}

	public FloatBuffer putInBuffer3(FloatBuffer buffer) {
		return buffer.put(r).put(g).put(b);
	}

	public FloatBuffer putInBuffer4(FloatBuffer buffer) {
		this.toDecimal();
		return buffer.put(r).put(g).put(b).put(a);
	}

	public Color toCMY() {
		if (colorType == CMY || colorType == CMYA)
			return this;

		switch (colorMode) {
		case INT:
			this.r = 255 - r;
			this.g = 255 - g;
			this.b = 255 - b;
		case FLOAT:
			this.r = 1 - r;
			this.g = 1 - g;
			this.b = 1 - b;
			break;
		}

		switch (colorType) {
		case RGB:
			this.colorMode = CMY;
		case RGBA:
			this.colorMode = CMYA;
		}

		return this;
	}

	public Color toDecimal() {
		if (colorMode == FLOAT)
			return this;

		this.r /= 255;
		this.g /= 255;
		this.b /= 255;
		this.colorMode = FLOAT;
		return this;
	}

	public Color toInt() {
		if (colorMode == INT)
			return this;

		this.r *= 255;
		this.g *= 255;
		this.b *= 255;
		this.colorMode = INT;
		return this;
	}

	public Color toRGB() {
		if (colorType == RGB || colorType == RGBA)
			return this;

		switch (colorMode) {
		case INT:
			this.r = 255 - r;
			this.g = 255 - g;
			this.b = 255 - b;
		case FLOAT:
			this.r = 1 - r;
			this.g = 1 - g;
			this.b = 1 - b;
			break;
		}

		switch (colorType) {
		case CMY:
			this.colorMode = RGB;
		case CMYA:
			this.colorMode = RGBA;
		}

		return this;
	}
}

package pe.util.math;

import java.nio.FloatBuffer;

public class Vec2f {

	public float x, y;

	public Vec2f() {
		this.x = 0;
		this.y = 0;
	}

	public Vec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vec2f mul(float scale) {
		this.x *= scale;
		this.y *= scale;
		return this;
	}

	/**
	 * Returns true if both the <code>x</code> and the <code>y</code> value for
	 * the input vector are the same for this vector.
	 * 
	 * @param vec The vector to compare to.
	 * @return Whether the input vector is equal to this vector.
	 * 
	 * @since 1.0
	 */
	@Override
	public boolean equals(Object vector) {
		Vec2f vec = (Vec2f) vector;
		return x == vec.x && y == vec.y;
	}

	public static Vec2f add(Vec2f vec1, Vec2f vec2) {
		return new Vec2f(vec2.x + vec1.x, vec2.y + vec1.y);
	}

	public static Vec2f subtract(Vec2f vec1, Vec2f vec2) {
		return new Vec2f(vec2.x - vec1.x, vec2.y - vec1.y);
	}

	public static float dot(Vec2f vec1, Vec2f vec2) {
		return vec1.x * vec2.x + vec1.y * vec2.y;
	}

	public static Vec3f cross(Vec2f vec1, Vec2f vec2) {
		return new Vec3f(0, 0, vec1.x * vec2.y - vec1.y * vec2.x);
	}

	public static float angleBetween(Vec2f vec1, Vec2f vec2) {
		return (float) Math.toDegrees(Math.acos((dot(vec1, vec2)) / (vec1.length() * vec2.length())));
	}

	public Vec2f unit() {
		float length = this.length();
		this.x /= length;
		this.y /= length;
		return this;
	}

	/**
	 * Loads the <code>Vec2f</code> object into a Float vector. The same as
	 * <code>floatBuffer.put(vector.x).put(vector.y)</code>. The same buffer
	 * object that is a parameter is used, so the buffer object will
	 * automatically be updated with the vector's information, however, in the
	 * case of a one-line use, the function also returns the buffer. Note that
	 * the buffer will still need to be flipped afterwards.
	 * 
	 * @param buffer
	 *            the <code>FloatBuffer</code> to put the vector in.
	 * 
	 * @see #x
	 * @see #y
	 * @see #FloatBuffer.put()
	 * @see FloatBuffer
	 * 
	 * @since 1.0
	 */
	public FloatBuffer putInBuffer(FloatBuffer buffer) {
		buffer.put(x).put(y);
		return buffer;
	}

	public float length() {
		return (float) Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public String toString() {
		return "{" + this.x + "," + this.y + "}";
	}
}

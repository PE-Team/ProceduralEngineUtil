package pe.util.math;

import java.nio.FloatBuffer;

public class Vec3f {

	public float x, y, z;

	public Vec3f() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Vec3f(Vec2f vec, float z) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = z;
	}

	public Vec3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3f mul(float scale) {
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
		return this;
	}

	public static Vec3f add(Vec3f vec1, Vec3f vec2) {
		return new Vec3f(vec2.x + vec1.x, vec2.y + vec1.y, vec2.z + vec1.z);
	}

	public static Vec3f subtract(Vec3f vec1, Vec3f vec2) {
		return new Vec3f(vec2.x - vec1.x, vec2.y - vec1.y, vec2.z - vec1.z);
	}

	public static float dot(Vec3f vec1, Vec3f vec2) {
		return vec1.x * vec2.x + vec1.y * vec2.y + vec1.z * vec2.z;
	}

	public static Vec3f cross(Vec3f vec1, Vec3f vec2) {
		return new Vec3f(vec1.y * vec2.z - vec1.z * vec2.y, vec1.z * vec2.x - vec1.x * vec2.z,
				vec1.x * vec2.y - vec1.y * vec2.x);
	}

	public static float angleBetween(Vec3f vec1, Vec3f vec2) {
		return (float) Math.toDegrees(Math.acos((dot(vec1, vec2)) / (vec1.length() * vec2.length())));
	}

	public Vec3f unit() {
		float length = this.length();
		this.x /= length;
		this.y /= length;
		this.z /= length;
		return this;
	}

	public float length() {
		return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}

	/**
	 * Loads the <code>Vec3f</code> object into a Float vector. The same as
	 * <code>floatBuffer.put(vector.x).put(vector.y).put(vector.z)</code>.
	 * The same buffer object that is a parameter is used, so the buffer object
	 * will automatically be updated with the vector's information, however, in
	 * the case of a one-line use, the function also returns the buffer. Note
	 * that the buffer will still need to be flipped afterwards.
	 * 
	 * 
	 * @param buffer
	 *            the <code>FloatBuffer</code> to put the vector in.
	 * 
	 * @return The float buffer the vector was put in.
	 * 
	 * @see #x
	 * @see #y
	 * @see #z
	 * @see #FloatBuffer.put()
	 * @see FloatBuffer
	 * 
	 * @since 1.0
	 */
	public FloatBuffer putInBuffer(FloatBuffer buffer) {
		buffer.put(x).put(y).put(z);
		return buffer;
	}
	
	/**
	 * Loads the <code>Vec3f</code> object into a Float vector. The same as
	 * <code>floatBuffer.clear(); floatBuffer.put(vector.x).put(vector.y).put(vector.z)</code>.
	 * The same buffer object that is a parameter is used, so the buffer object
	 * will automatically be updated with the vector's information, however, in
	 * the case of a one-line use, the function also returns the buffer. Note
	 * that the buffer is automatically flipped.
	 * 
	 * 
	 * @param buffer
	 *            the <code>FloatBuffer</code> to put the vector in.
	 * 
	 * @return The float buffer the vector was put in.
	 * 
	 * @see #x
	 * @see #y
	 * @see #z
	 * @see #FloatBuffer.put()
	 * @see FloatBuffer
	 * 
	 * @since 1.0
	 */
	public FloatBuffer putInBufferC(FloatBuffer buffer) {
		buffer.clear();
		buffer.put(x).put(y).put(z);
		buffer.flip();
		return buffer;
	}

	public String toString() {
		return "{" + this.x + "," + this.y + "," + this.z + "}";
	}
}

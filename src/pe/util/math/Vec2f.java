package pe.util.math;

import java.nio.FloatBuffer;

public class Vec2f {

	public static Vec2f zero(){
		return new Vec2f(0f, 0f);
	}

	public float x, y;

	public Vec2f() {
		this.x = 0;
		this.y = 0;
	}
	
	public Vec2f(Vec2f vec){
		this.x = vec.x;
		this.y = vec.y;
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
	 * @param vec
	 *            The vector to compare to.
	 * @return Whether the input vector is equal to this vector.
	 * 
	 * @since 1.0
	 */
	@Override
	public boolean equals(Object vector) {
		Vec2f vec = (Vec2f) vector;
		return x == vec.x && y == vec.y;
	}
	
	public static Vec2f mul(Vec2f vec, float scale){
		return new Vec2f(vec.x * scale, vec.y * scale);
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
	
	public static float crossArea(Vec2f vec1, Vec2f vec2){
		return vec1.x * vec2.y - vec1.y * vec2.x;
	}

	public static float angleBetween(Vec2f vec1, Vec2f vec2) {
		return (float) Math.toDegrees(Math.acos((dot(vec1, vec2)) / (vec1.length() * vec2.length())));
	}
	
	public static double radiansBetween(Vec2f vec1, Vec2f vec2) {
		return Math.acos((dot(vec1, vec2)) / (vec1.length() * vec2.length()));
	}

	public Vec2f unit() {
		float length = this.length();
		this.x /= length;
		this.y /= length;
		return this;
	}
	
	public static Vec2f projectOnto(Vec2f direction, Vec2f vec){
		Vec2f unitDir = (new Vec2f(direction.x, direction.y)).unit();
		float dot = Vec2f.dot(vec, unitDir);
		
		return unitDir.mul(dot);
	}

	/**
	 * Inverts a vector so that it points in the opposite direction. More
	 * specifically, multiplies each component of the vector by <code>-1</code>.
	 * This is effectively the same as <code>vector.mul(-1)</code>.
	 * 
	 * @return The vector pointed in the opposite direction.
	 * 
	 * @see #mul(float)
	 * 
	 * @since 1.0
	 */
	public Vec2f invert() {
		this.x = -x;
		this.y = -y;
		return this;
	}

	/**
	 * Returns a non-unit normal vector to this vector. Specifically the one
	 * which lies to the "left" of the vector. The new vector will have the
	 * components <code>{-y, x</code>.
	 * 
	 * @return A normal vector to the vector provided.
	 * 
	 * @since 1.0
	 */
	public Vec2f normal() {
		return new Vec2f(-y, x);
	}

	/**
	 * Loads the <code>Vec2f</code> object into a Float vector. The same as
	 * <code>floatBuffer.put(vector.x).put(vector.y</code>. The same buffer
	 * object that is a parameter is used, so the buffer object will
	 * automatically be updated with the vector's information, however, in the
	 * case of a one-line use, the function also returns the buffer. Note that
	 * the buffer will still need to be flipped afterwards.
	 * 
	 * 
	 * @param buffer
	 *            the <code>FloatBuffer</code> to put the vector in.
	 * 
	 * @return The float buffer the vector was put in.
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

	/**
	 * Loads the <code>Vec2f</code> object into a Float vector. The same as
	 * <code>floatBuffer.clear(); floatBuffer.put(vector.x).put(vector.y)</code>.
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
	 * @see #FloatBuffer.put()
	 * @see FloatBuffer
	 * 
	 * @since 1.0
	 */
	public FloatBuffer putInBufferC(FloatBuffer buffer) {
		buffer.clear();
		buffer.put(x).put(y);
		buffer.flip();
		return buffer;
	}

	public float length() {
		return (float) Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public String toString() {
		return "{" + this.x + "," + this.y + "}";
	}
}

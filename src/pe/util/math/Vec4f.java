package pe.util.math;

import java.nio.FloatBuffer;

public class Vec4f {

	public float x, y, z, w;
	
	public Vec4f(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 0;
	}
	
	public Vec4f(Vec2f vec, float z, float w){
		this.x = vec.x;
		this.y = vec.y;
		this.z = z;
		this.w = w;
	}
	
	public Vec4f(Vec3f vec, float w){
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
		this.w = w;
	}
	
	public Vec4f(float x, float y, float z, float w){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vec4f mul(float scale){
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
		this.w *= scale;
		return this;
	}
	
	public static Vec4f add(Vec4f vec1, Vec4f vec2){
		return new Vec4f(vec2.x+vec1.x, vec2.y+vec1.y, vec2.z+vec1.z, vec2.w+vec1.w);
	}
	
	public static Vec4f subtract(Vec4f vec1, Vec4f vec2){
		return new Vec4f(vec2.x-vec1.x, vec2.y-vec1.y, vec2.z-vec1.z, vec2.w-vec1.w);
	}
	
	public static float dot(Vec4f vec1, Vec4f vec2){
		return vec1.x*vec2.x + vec1.y*vec2.y + vec1.z*vec2.z + vec1.w*vec2.w;
	}
	
	public static float angleBetween(Vec4f vec1, Vec4f vec2){
		return (float) Math.toDegrees(Math.acos((dot(vec1,vec2))/(vec1.length()*vec2.length())));
	}
	
	/**
	 * Loads the <code>Vec3f</code> object into a Float vector. The same as
	 * <code>floatBuffer.put(vector.x).put(vector.y)</code>. The same buffer
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
	 * @see #z
	 * @see #w
	 * @see #FloatBuffer.put()
	 * @see FloatBuffer
	 * 
	 * @since 1.0
	 */
	public FloatBuffer putInBuffer(FloatBuffer buffer) {
		buffer.put(x).put(y).put(z).put(w);
		return buffer;
	}
	
	public Vec4f unit(){
		float length = this.length();
		this.x /= length;
		this.y /= length;
		this.z /= length;
		this.w /= length;
		return this;
	}
	
	public float length(){
		return (float) Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z + this.w*this.w);
	}
	
	public String toString(){
		return "{" + this.x + "," + this.y + "," + this.z + "," + this.w + "}";
	}
}

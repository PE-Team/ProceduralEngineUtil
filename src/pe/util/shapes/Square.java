package pe.util.shapes;

import pe.util.math.Vec2f;

public class Square extends Rectangle {
	
	/**
	 * Empty constructor for the purposes of allowing sub-classes to set
	 * <code>vertices</code> without super call. Should never be called itself.
	 * 
	 * @see #vertices
	 * 
	 * @since 1.0
	 */
	protected Square(){
		
	}

	public Square(Vec2f c1, int width) {
		this.vertices = new Vec2f[] { c1, new Vec2f(c1.x + width, c1.y), new Vec2f(c1.x + width, c1.y + width),
				new Vec2f(c1.x, c1.y + width) };
	}
}

package pe.util.shapes;

import pe.util.math.Vec2f;

public class Rectangle extends Quadrilateral {
	
	/**
	 * Empty constructor for the purposes of allowing sub-classes to set
	 * <code>vertices</code> without super call. Should never be called itself.
	 * 
	 * @see #vertices
	 * 
	 * @since 1.0
	 */
	protected Rectangle(){
		
	}
	
	public Rectangle(int width, int height){
		super(new Vec2f(0, 0), new Vec2f(width, 0), new Vec2f(width, height), new Vec2f(0, height));
	}

	public Rectangle(Vec2f c1, Vec2f c2) {
		super(c1, new Vec2f(c2.x, c1.y), c2, new Vec2f(c1.x, c2.y));
	}
	
	public Rectangle(Vec2f c1, Vec2f c2, int width){
		Vec2f edgeDirection = Vec2f.subtract(c1, c2);
		Vec2f widthVector = (new Vec2f(-edgeDirection.y, edgeDirection.x)).unit().mul(width);
		
		this.vertices = new Vec2f[]{
				c1, c2, Vec2f.add(c1, widthVector), Vec2f.add(c2, widthVector)
		};
		
		Vec2f boundingBox = getBoundingBox(c1, c2, Vec2f.add(c1, widthVector), Vec2f.add(c2, widthVector));
		
		this.width = boundingBox.x;
		this.height = boundingBox.y;
	}

	public Rectangle(Vec2f c1, Vec2f c2, Vec2f c3) {
		Vec2f diagonalHalf = Vec2f.subtract(c1, c3).mul(0.5f);
		Vec2f normalToC3 = Vec2f.subtract(diagonalHalf, c3);
		Vec2f c4 = Vec2f.add(diagonalHalf.mul(-1), normalToC3);
		
		this.vertices = new Vec2f[]{
				c1, c2, c3, c4
		};
		
		Vec2f boundingBox = getBoundingBox(c1, c2, c3, c4);
		
		this.width = boundingBox.x;
		this.height = boundingBox.y;
	}
}

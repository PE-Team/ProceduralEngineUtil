package pe.util.shapes;

import pe.util.math.Vec2f;

public class Polygon {

	protected Vec2f[] verteces;
	
	public Polygon(Vec2f... verteces){
		this.verteces = verteces;
	}
	
	public Vec2f[] getVerteces(){
		return verteces;
	}
	
	public int[] getIndeces(){
		return null;
	}
}

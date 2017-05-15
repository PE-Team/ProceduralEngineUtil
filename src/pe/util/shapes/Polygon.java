package pe.util.shapes;

import pe.util.Util;
import pe.util.math.Vec2f;

public class Polygon {
	
	private Vec2f[] verteces;
	
	public Polygon(Vec2f... verteces){
		this.verteces = verteces;
	}
	
	public int[] getIndeces(){
		return Util.getPolygonIndeces(verteces);
	}
}

package pe.util.shapes;

import pe.util.math.Vec2f;

public class Line {
	
	private Vec2f point;
	private Vec2f direction;
	
	public Line(Vec2f p1, Vec2f p2){
		point = p1;
		direction = Vec2f.subtract(p1, p2);
	}
	
	public boolean crosses(Line line){
		return Vec2f.dot(direction, line.getDirection()) != 0;
	}
	
	public Vec2f getDirection(){
		return direction;
	}
}

package pe.util.shapes;

import pe.util.Util;
import pe.util.math.Vec2f;

/**
 * A Polygon representing a 2D shape. Used to render and hold arbitrary shapes.
 * 
 * @author Ethan Penn
 * 
 * @since 1.0
 *
 */
public class Polygon {

	/**
	 * <p>
	 * The vertices of the polygon as 2-Dimensional vectors.
	 * </p>
	 * 
	 * @see Vec2f
	 * 
	 * @since 1.0
	 */
	protected Vec2f[] vertices;

	/**
	 * <p>
	 * The bounding width of the polygon. More descriptively, the maximum
	 * horizontal distance between the <code>x</code> values for any two
	 * vertices.
	 * </p>
	 * 
	 * @since 1.0
	 */
	protected float width;

	/**
	 * <p>
	 * The bounding height of the polygon. More descriptively, the maximum
	 * vertical distance between the <code>y</code> values for any two vertices.
	 * </p>
	 * 
	 * @since 1.0
	 */
	protected float height;

	/**
	 * <p>
	 * Empty constructor for the purposes of allowing sub-classes to set
	 * <code>vertices</code> without super call. Should never be called itself.
	 * </p>
	 * 
	 * @see #vertices
	 * 
	 * @since 1.0
	 */
	protected Polygon() {

	}

	/**
	 * <p>
	 * Creates a new polygon with the given vertices. It should be a simple
	 * polygon. That is, none of its edges should intersect another.
	 * </p>
	 * 
	 * @param polygon
	 *            The vertices of the polygon.
	 * 
	 * @see #vertices
	 * 
	 * @since 1.0
	 */
	public Polygon(Vec2f... polygon) {
		this.vertices = polygon;

		Vec2f boundingBox = getBoundingBox(polygon);

		this.width = boundingBox.x;
		this.height = boundingBox.y;
	}

	/**
	 * <p>
	 * Returns the height of the bounding box for this polygon.
	 * </p>
	 * 
	 * @return The height of the bounding box.
	 * 
	 * @see #height
	 * 
	 * @since 1.0
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Returns the indices of the polygon. That is, an array of integers coupled
	 * in threes. Each set of three integers represents which vertices in
	 * <code>vertices</code> make up the triangles of the polygon. Uses the
	 * triangulation method in {@link Util} to triangluate the polygon
	 * automatically.
	 * 
	 * @return The indices of the triangles that make up the polygon.
	 * 
	 * @see pe.util.Util#generatePolygonIndices(Vec2f[])
	 * 
	 * @since 1.0
	 */
	public int[] getIndices() {
		return Util.generatePolygonIndices(vertices);
	}

	/**
	 * <p>
	 * Returns the vertices that make up the polygon
	 * </p>
	 * 
	 * @return The vertices of the polygon.
	 * 
	 * @see #vertices
	 * 
	 * @since 1.0
	 */
	public Vec2f[] getVertices() {
		return vertices;
	}

	/**
	 * <p>
	 * Returns the width of the bounding box for this polygon.
	 * </p>
	 * 
	 * @return The width of the bounding box.
	 * 
	 * @see #width
	 * 
	 * @since 1.0
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Returns the width and height of the bounding box for the polygon inputed
	 * in the <code>x</code> and <code>y</code> values of the vector,
	 * respectively.
	 * 
	 * @param polygon
	 *            The polygon to get the bounding box for.
	 * @return The bounding box dimensions.
	 */
	protected static Vec2f getBoundingBox(Vec2f... polygon) {
		Vec2f least = new Vec2f(Float.MAX_VALUE, Float.MAX_VALUE);
		Vec2f most = new Vec2f(-Float.MAX_VALUE, -Float.MAX_VALUE);
		for (Vec2f vertex : polygon) {
			if (vertex.x > most.x)
				most.x = vertex.x;
			if (vertex.y > most.y)
				most.y = vertex.y;
			if (vertex.x < least.x)
				least.x = vertex.x;
			if (vertex.y < least.y)
				least.y = vertex.y;
		}

		return new Vec2f(most.x - least.x, most.y - least.y);
	}
}

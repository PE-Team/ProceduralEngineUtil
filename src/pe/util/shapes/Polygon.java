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
}

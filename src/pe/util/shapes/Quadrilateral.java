package pe.util.shapes;

import pe.util.math.Vec2f;

/**
 * A 2D quadrilateral (four-sided shape) with four arbitrary vertices. Sub-classes {@link Polygon}.
 * 
 * @author Ethan Penn
 * 
 * @since 1.0
 */
public class Quadrilateral extends Polygon {

	/**
	 * Empty constructor for the purposes of allowing sub-classes to set
	 * <code>vertices</code> without super call. Should never be called itself.
	 * 
	 * @see #vertices
	 * 
	 * @since 1.0
	 */
	protected Quadrilateral() {

	}

	/**
	 * Creates a new quadrilaterial with each corner as a vertex. The
	 * quadrilaterial should be a simple polygon, that is, none of its edges
	 * should intersect another.
	 * 
	 * @param c1
	 * @param c2
	 * @param c3
	 * @param c4
	 */
	public Quadrilateral(Vec2f c1, Vec2f c2, Vec2f c3, Vec2f c4) {
		super(c1, c2, c3, c4);
	}

	/**
	 * Returns the indices of the quadrilaterial. That is, an array of integers
	 * coupled in threes. Each set of three integers represents which vertices
	 * in <code>vertices</code> make up the triangles of the quadrilaterial. The
	 * indices are equal to the <code>int</code> array:
	 * <code>{0, 1, 3, 1, 2, 3}</code>.
	 * 
	 * @return The indices of the triangles that make up the quadrilaterial.
	 * 
	 * @see pe.util.Util#generatePolygonIndices(Vec2f[])
	 * 
	 * @since 1.0
	 */
	@Override
	public int[] getIndices() {
		return new int[] { 0, 1, 3, 1, 2, 3 };
	}
}

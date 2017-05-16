package pe.util.shapes;

import pe.util.math.Vec2f;

/**
 * A 2D triangle having three arbitrary corners. Sub-classes {@link Polygon}.
 * 
 * @author Ethan Penn
 *
 * @since 1.0
 */
public class Triangle extends Polygon {

	/**
	 * Empty constructor for the purposes of allowing sub-classes to set
	 * <code>vertices</code> without super call. Should never be called itself.
	 * 
	 * @see #vertices
	 * 
	 * @since 1.0
	 */
	protected Triangle() {

	}

	/**
	 * Creates a triangle with the provided corners, <code>c1</code>,
	 * <code>c2</code>, <code>c3</code> as vertices of the triangle. Vertices do
	 * not need to be provided in any particular order.
	 * 
	 * @param c1
	 *            The first corner of the triangle.
	 * @param c2
	 *            The second corner of the triangle.
	 * @param c3
	 *            the third corner of the triangle.
	 * 
	 * @since 1.0
	 */
	public Triangle(Vec2f c1, Vec2f c2, Vec2f c3) {
		super(c1, c2, c3);
	}

	/**
	 * Returns the indices of the triangle. That is, an array of integers
	 * coupled in threes. Each set of three integers represents which vertices
	 * in <code>vertices</code> make up the triangle. The indices is equal to
	 * the <code>int</code> array: <code>{0, 1, 2}</code>.
	 * 
	 * @return The indices of the triangle.
	 * 
	 * @since 1.0
	 */
	@Override
	public int[] getIndices() {
		return new int[] { 0, 1, 2 };
	}
}

package pe.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pe.util.math.Vec2f;
import pe.util.math.Vec3f;

public class PolygonTriangulationAlgorithm {

	public static void main(String... args) {

		Vec2f[] square = { new Vec2f(0, 0), new Vec2f(0, 1), new Vec2f(1, 1), new Vec2f(1, 0) };

		Vec2f[] plus = { new Vec2f(1, 1), new Vec2f(0, 1), new Vec2f(0, 2), new Vec2f(1, 2), new Vec2f(1, 3),
				new Vec2f(2, 3), new Vec2f(2, 2), new Vec2f(3, 2), new Vec2f(3, 1), new Vec2f(2, 1), new Vec2f(2, 0),
				new Vec2f(1, 0) };

		int[] indices = generatePolygonIndeces(plus);
		System.out.println(Util.arrayToString(indices));

		Vec3f[] barycentric = Util.giveBarycentricCoords(indices);
		System.out.println(Util.arrayToString(barycentric));
	}

	/**
	 * Will generate a set of indices which indicate which vertices of a
	 * polygon, when connected with two others, will triangulate the polygon
	 * (turn the polygon into a bunch of triangles). The indices will be in a
	 * set of 3 integers so that the number of triangles that the indices would
	 * create is equal to indices.length / 3.
	 * 
	 * @param polygon
	 *            The polygon to generate the indices of.
	 * @return The set of indices needed to triangulate this polygon.
	 * 
	 * @since 1.0
	 */
	public static int[] generatePolygonIndeces(Vec2f[] polygon) {
		if (polygon.length < 3)
			throw new IllegalArgumentException("A polygon must have at least 3 sides.");

		/* Step 0: Create resultant variables */
		Set<int[]> indecesSet = new HashSet<int[]>();
		Map<Vec2f, Integer> vertexIndeces = new HashMap<Vec2f, Integer>();
		Map<Vec2f, Vec2f> vertexOrderings = new HashMap<Vec2f, Vec2f>();

		for (int i = 0; i < polygon.length; i++) {
			vertexIndeces.put(polygon[i], i);

			vertexOrderings.put(polygon[i], Util.getl(i + 1, polygon));
		}

		/*
		 * Step 1: Generate polygon edge normal multiplier to determine polygon
		 * direction
		 */
		int normalMult = clockwisePolygon(polygon) > 0 ? -1 : 1;

		/*
		 * Step 2: Go around polygon connecting triangles by using the edge
		 * direction to detect if triangle is in the polygon or not
		 */
		Vec2f vertex1 = polygon[0];
		while (vertexOrderings.size() > 3) {
			Vec2f vertex2 = vertexOrderings.get(vertex1);
			Vec2f vertex3 = vertexOrderings.get(vertex2);
			Vec2f vertex4 = vertexOrderings.get(vertex3);

			int edge13NormalMult = clockwisePolygon(new Vec2f[] { vertex1, vertex2, vertex3 }) < 0 ? -1 : 1;
			Vec2f edge13Normal = getEdgeNormal(vertex1, vertex3, edge13NormalMult);

			Vec2f polygon12Normal = getEdgeNormal(vertex1, vertex2, normalMult);
			Vec2f polygon23Normal = getEdgeNormal(vertex2, vertex3, normalMult);
			Vec2f polygon123NormalAvg = Vec2f.add(polygon12Normal, polygon23Normal).unit();

			if (Util.doSegmentsIntersect(vertex1, vertex2, vertex3, vertex4)
					|| Util.doSegmentsIntersect(vertex1, vertex4, vertex2, vertex3)) {
				if (areColinear(vertex1, vertex2, vertex3) || areColinear(vertex2, vertex3, vertex4)) {
					/*
					 * Case 1:: The vertices are colinear so another triangle
					 * should be found
					 */
					System.out.println("Case 1");

					vertex1 = vertex2;
				} else if (Vec2f.dot(polygon123NormalAvg, edge13Normal) < 0) {
					/* Case 2: The triangle 1 -> 2 -> 3 is in the polygon */
					System.out.println("Case 2");

					indecesSet.add(new int[] { vertexIndeces.get(vertex1), vertexIndeces.get(vertex2),
							vertexIndeces.get(vertex3) });

					vertexOrderings.remove(vertex2);
					vertexOrderings.replace(vertex1, vertex3);
				} else {
					/* Case 3: The triangle 2 -> 3 -> 4 is in the polygon */
					System.out.println("Case 3");

					indecesSet.add(new int[] { vertexIndeces.get(vertex2), vertexIndeces.get(vertex3),
							vertexIndeces.get(vertex4) });

					vertexOrderings.remove(vertex3);
					vertexOrderings.replace(vertex2, vertex4);
				}
			} else {
				if (Vec2f.dot(polygon123NormalAvg, edge13Normal) < 0) {
					/*
					 * Case 4: The quadrilateral 1 -> 2 -> 3 -> 4 is in the
					 * polygon
					 */
					System.out.println("Case 4");

					indecesSet.add(new int[] { vertexIndeces.get(vertex1), vertexIndeces.get(vertex2),
							vertexIndeces.get(vertex3) });
					indecesSet.add(new int[] { vertexIndeces.get(vertex1), vertexIndeces.get(vertex3),
							vertexIndeces.get(vertex4) });

					vertexOrderings.remove(vertex2);
					vertexOrderings.remove(vertex3);
					vertexOrderings.replace(vertex1, vertex4);
				} else {
					/*
					 * Case 5: No combination of these vertices are in the final
					 * triangluation
					 */
					System.out.println("Case 5");

					vertex1 = vertex2;
				}
			}
		}

		/* Step 3: Resolve the final triangle (or skip if it is a line) */
		if (vertexOrderings.size() == 3) {
			Vec2f vertex2 = vertexOrderings.get(vertex1);
			Vec2f vertex3 = vertexOrderings.get(vertex2);

			indecesSet.add(
					new int[] { vertexIndeces.get(vertex1), vertexIndeces.get(vertex2), vertexIndeces.get(vertex3) });
		}

		/* Step 4: Turn the set of sets of indeces into a single array */
		int[] finalIndeces = new int[indecesSet.size() * 3];
		int index = 0;
		for (int[] indexSet : indecesSet) {
			finalIndeces[index] = indexSet[0];
			finalIndeces[index + 1] = indexSet[1];
			finalIndeces[index + 2] = indexSet[2];

			index += 3;
		}

		return finalIndeces;
	}

	/**
	 * <p>
	 * Returns true if all of the vectors supplied to colinear to every other
	 * one, false otherwise. To be colinear means that the given points all lie
	 * on the same line.
	 * </p>
	 * 
	 * @param vectors
	 *            The points to test.
	 * @return Whether all of the vectors given are colinear or not.
	 * 
	 * @since 1.0
	 */
	public static boolean areColinear(Vec2f... vectors) {
		if (vectors.length < 1)
			return true;

		Vec2f direction = Vec2f.subtract(vectors[0], vectors[1]);
		for (int i = 1; i < vectors.length - 1; i++) {
			if (!direction.equals(Vec2f.subtract(vectors[i], vectors[i + 1])))
				return false;
		}
		return true;
	}

	/**
	 * <p>
	 * Returns a normal vector to the edge made from <code>v1</code> and
	 * <code>v2</code>.
	 * </p>
	 * 
	 * @param v1
	 *            The first vertex of the edge.
	 * @param v2
	 *            The second vertex of the edge.
	 * @param normalMult
	 *            Either 1 or -1. Will change the direction of the normal.
	 *            Related to determining the normal for a polygon edge.
	 * @return A vector normal to the edge.
	 * 
	 * @see #clockwisePolygon(Vec2f[])
	 * 
	 * @since 1.0
	 */
	public static Vec2f getEdgeNormal(Vec2f v1, Vec2f v2, int normalMult) {
		Vec2f edgeDir = Vec2f.subtract(v1, v2);
		Vec2f edgeNormal = new Vec2f(-edgeDir.y, edgeDir.x).unit();

		return edgeNormal.mul(normalMult);
	}

	/**
	 * <p>
	 * Returns a value based on how "clockwise" a polygon is.
	 * </p>
	 * <ul>
	 * <li>> 1: The polygon is generally clockwise.
	 * <li>= 1: The polygon is neither clockwise nor counter-clockwise.
	 * <li>< 1: The polygon is generally counter-clockwise.
	 * </ul>
	 * <p>
	 * If the edges of a polygon generally turn "right" to form the next edge,
	 * the the polygon is considered clockwise.
	 * </p>
	 * 
	 * @param polygon
	 *            The polygon to determine how "clockwise" it is.
	 * @return A value determining how "clockwise" the polygon is.
	 * 
	 * @since 1.0
	 */
	public static float clockwisePolygon(Vec2f[] polygon) {
		float sum = 0;
		for (int i = 0; i < polygon.length; i++) {
			Vec2f nextVec = Util.getl(i + 1, polygon);
			float add = (nextVec.x - polygon[i].x) * (nextVec.y + polygon[i].y);
			sum += add;
		}
		return sum;
	}
}

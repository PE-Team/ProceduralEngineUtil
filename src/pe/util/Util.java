package pe.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import pe.util.math.Vec2f;

public class Util {

	public static String[] defaultClassFileLocations = new String[] { "./bin", "C:/Program Files/Java/jdk1.8.0_101/" };

	public static final float NANO = 1000000000.0f;
	public static final int FLOAT_BYTE_SIZE = 4;

	public static final char[] CHARSET_NUMBER_CASTING_CHARACTERS = { 'd', 'f', 'l', 'i', 's', 'b', 'D', 'F', 'L', 'I',
			'S', 'B' };

	public static final char[] CHARSET_LETTERS_LOWERCASE = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	public static final char[] CHARSET_LETTERS_UPPERCASE = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	public static final char[] CHARSET_LETTERS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	public static final char[] CHARSET_NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	public static final char[] CHARSET_HEX_LOWERCASE = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
			'c', 'd', 'e', 'f' };

	public static final char[] CHARSET_HEX_UPPERCASE = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B',
			'C', 'D', 'E', 'F' };

	public static final char[] CHARSET_HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static final char[] CHARSET_NUMBERS_LETTERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
			'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	public static int[] add(int[] array, int addend) {
		for (int i = 0; i < array.length; i++) {
			array[i] += addend;
		}
		return array;
	}

	public static int addAll(int[] array) {
		int count = 0;
		for (int i = 0; i < array.length; i++) {
			count += array[i];
		}
		return count;
	}

	public static int[] getPolygonIndeces(Vec2f[] polygon) {
		int[] indeces = new int[0];
		int[] vertIndeces = createAutoIncrementArray(0, polygon.length);

		int point = 0;
		while (vertIndeces.length > 3) {
			int index0 = (point) % vertIndeces.length;
			int index1 = (point + 1) % vertIndeces.length;
			int index2 = (point + 2) % vertIndeces.length;
			int index3 = (point + 3) % vertIndeces.length;
			Vec2f p1 = polygon[vertIndeces[index0]];
			Vec2f p2 = polygon[vertIndeces[index1]];
			Vec2f p3 = polygon[vertIndeces[index2]];
			Vec2f p4 = polygon[vertIndeces[index3]];
			if (doSegmentsIntersect(p1, p2, p3, p4) || doSegmentsIntersect(p1, p4, p2, p3)) {
				if (pointWindingNumber(getMidpoint(p1, p3), polygon) == 0) {
					indeces = append(indeces, vertIndeces[index0]);
					indeces = append(indeces, vertIndeces[index2]);
					indeces = append(indeces, vertIndeces[index3]);
					vertIndeces = remove(index2, vertIndeces, new int[vertIndeces.length - 1]);
					point = index3;
				} else {
					indeces = append(indeces, vertIndeces[index0]);
					indeces = append(indeces, vertIndeces[index1]);
					indeces = append(indeces, vertIndeces[index2]);
					vertIndeces = remove(index1, vertIndeces, new int[vertIndeces.length - 1]);
					point = index2;
				}
			} else {
				if (pointWindingNumber(getMidpoint(p1, p3), polygon) == 0) {
					point = index1;
				} else {
					indeces = append(indeces, vertIndeces[index0]);
					indeces = append(indeces, vertIndeces[index1]);
					indeces = append(indeces, vertIndeces[index2]);

					indeces = append(indeces, vertIndeces[index0]);
					indeces = append(indeces, vertIndeces[index2]);
					indeces = append(indeces, vertIndeces[index3]);

					vertIndeces = remove(index2, vertIndeces, new int[vertIndeces.length - 1]);
					vertIndeces = remove(index1, vertIndeces, new int[vertIndeces.length - 1]);

					point = index3;
				}
			}
		}

		if (vertIndeces.length == 3) {
			indeces = append(indeces, vertIndeces[0]);
			indeces = append(indeces, vertIndeces[1]);
			indeces = append(indeces, vertIndeces[2]);
		}

		return indeces;
	}

	/**
	 * <p>
	 * Creates an array of consecutive increasing integers with the first
	 * integer having a value of <code>start</code>.
	 * </p>
	 * 
	 * @param start
	 *            The value of the first integer.
	 * @param length
	 *            The length of the array.
	 * @return An array of consecutive increasing integers starting at
	 *         <code>start</code>.
	 * 
	 * @since 1.0
	 */
	public static int[] createAutoIncrementArray(int start, int length) {
		int[] array = new int[length];
		for (int i = 0; i < length; i++) {
			array[i] = start + i;
		}
		return array;
	}

	/**
	 * <p>
	 * Returns the two-dimensional midpoint between two points.
	 * </p>
	 * 
	 * @param p1
	 *            The first point of the line segment.
	 * @param p2
	 *            The second point of the line segment.
	 * @return The midpoint of the line segment.
	 * 
	 * @see Vec2f
	 * 
	 * @since 1.0
	 */
	public static Vec2f getMidpoint(Vec2f p1, Vec2f p2) {
		return Vec2f.add(p1, Vec2f.subtract(p1, p2).mul(0.5f));
	}

	/**
	 * <p>
	 * The Winding test algorithm for a point and a polygon. Explains whether a
	 * point is in a polygon. If the output is 0, than the point is not in the
	 * polygon, otherwise it is.
	 * </p>
	 * 
	 * @param point
	 *            The point to be tested.
	 * @param polygon
	 *            The polygon to test with.
	 * @return A value explaining if the point is in the polygon. 0 if it is
	 *         not, anything else if it is.
	 * 
	 * @see #getLineSide(Vec2f, Vec2f, Vec2f)
	 * @see Vec2f
	 * 
	 * @since 1.0
	 */
	public static int pointWindingNumber(Vec2f point, Vec2f[] polygon) {
		int windingNumber = 0;

		for (int i = 0; i < polygon.length; i++) {
			if (polygon[i].y <= point.y) {
				if (getl(i + 1, polygon).y > point.y)
					if (getLineSide(point, polygon[i], getl(i + 1, polygon)) > 0)
						windingNumber++;
			} else {
				if (getl(i + 1, polygon).y <= point.y)
					if (getLineSide(point, polygon[i], getl(i + 1, polygon)) < 0)
						windingNumber--;
			}
		}

		return windingNumber;
	}

	/**
	 * <p>
	 * Returns a float designating on which side of an infinite line a point
	 * lies.
	 * </p>
	 * 
	 * @param point
	 *            The point to be tested.
	 * @param lp1
	 *            The first point for the line
	 * @param lp2
	 *            The second point for the line
	 * @return > 0 if the point is 'left' of the line. = 0 if the point is on
	 *         the line. < 0 if the point is 'right' of the line.
	 * 
	 * @see Vec2f
	 * 
	 * @since 1.0
	 */
	public static float getLineSide(Vec2f point, Vec2f lp1, Vec2f lp2) {
		return ((lp2.x - lp1.x) * (point.y - lp1.y) - (point.x - lp1.x) * (lp2.y - lp1.y));
	}

	public static String alignStrings(String leftAlign, String centerAlign, String rightAlign, int stringLength) {
		int spaces = stringLength - (leftAlign.length() + centerAlign.length() + rightAlign.length() + 2);
		spaces = spaces < 1 ? spaces = 1 : spaces;
		String space1 = repeatCharFor(' ', spaces / 2);
		String space2 = repeatCharFor(' ', spaces / 2 + spaces % 2);
		return String.format("%s%s%s%s%s", leftAlign, space1, centerAlign, space2, rightAlign);
	}

	public static boolean allCharsMatch(char charRight, char charLeft, String statementStr) {
		int rightCharNumb = 0;
		int leftCharNumb = 0;
		for (int c = 0; c < statementStr.length(); c++) {
			if (statementStr.charAt(c) == charRight)
				rightCharNumb++;
			if (statementStr.charAt(c) == charLeft)
				leftCharNumb++;
			if (leftCharNumb > rightCharNumb)
				return false;
		}
		if (rightCharNumb != leftCharNumb)
			return false;
		return true;
	}

	public static int[] append(int[] array, int numb) {
		int[] result = new int[array.length + 1];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i];
		}
		result[result.length - 1] = numb;
		return result;
	}

	public static int[] append(int[] array1, int[] array2) {
		int[] result = new int[array1.length + array2.length];
		for (int i = 0; i < array1.length; i++) {
			result[i] = array1[i];
		}
		for (int i = 0; i < array2.length; i++) {
			result[i + array1.length] = array2[i];
		}
		return result;
	}

	public static String[] append(String[] array, String str) {
		String[] result = new String[array.length + 1];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i];
		}
		result[result.length - 1] = str;
		return result;
	}

	public static <T> T[] append(T[] array, T element) {
		List<T> result = new ArrayList<T>();
		for (int i = 0; i < array.length; i++) {
			result.add(array[i]);
		}
		result.add(element);
		return result.toArray(array);
	}

	public static <T> List<T> arrayToList(T[] array) {
		List<T> result = new ArrayList<>();
		if (array == null)
			return result;
		for (int i = 0; i < array.length; i++) {
			result.add(array[i]);
		}
		return result;
	}

	public static <T> String arrayToString(T[] array) {
		String result = "";
		for (int i = 0; i < array.length; i++) {
			result += "," + array[i].toString();
		}
		if (result.length() > 0)
			result = result.substring(1, result.length());
		return "[" + result + "]";
	}

	public static String centerAlignString(String msg, int stringLength) {
		int extraChars = (int) (stringLength - msg.length());
		return repeatCharFor(' ', extraChars / 2) + msg + repeatCharFor(' ', (extraChars / 2) + (extraChars % 2));
	}

	public static <T> List<T> cloneList(List<T> list1, List<T> list2) {
		list1.clear();
		for (int i = 0; i < list2.size(); i++) {
			list1.add(list2.get(i));
		}
		return list1;
	}

	public static int[] copy(int[] array, int start, int end) {
		if (array.length == 0)
			return array;
		int[] result = new int[end - start + 1];
		for (int i = 0; i < result.length; i++) {
			result[i] = array[i + start];
		}
		return result;
	}

	public static int count(String strPart, String str) {
		int result = 0;

		for (int c = 0; c < str.length() - strPart.length() + 1; c++) {
			if (str.charAt(c) == strPart.charAt(0)) {
				boolean fullString = true;
				for (int i = 1; i < strPart.length(); i++) {
					if (str.charAt(c + i) != strPart.charAt(i)) {
						fullString = false;
						break;
					}
				}
				if (fullString)
					result++;
			}
		}

		return result;
	}

	public static int countPrimitiveNumberClassesIn(List<Class<?>> list) {
		int count = 0;
		for (int c = 0; c < list.size(); c++) {
			if (list.get(c).equals(Double.class) || list.get(c).equals(double.class) || list.get(c).equals(Long.class)
					|| list.get(c).equals(long.class) || list.get(c).equals(Integer.class)
					|| list.get(c).equals(int.class) || list.get(c).equals(Float.class)
					|| list.get(c).equals(float.class) || list.get(c).equals(Short.class)
					|| list.get(c).equals(short.class) || list.get(c).equals(Byte.class)
					|| list.get(c).equals(byte.class))
				count++;
		}
		return count;
	}

	/**
	 * Returns true if the line segment consisting of the first parameter and
	 * the second parameter intersects the line segment consisting of the third
	 * parameter and the fourth parameter.
	 * 
	 * @param l1p1
	 *            The first point for the first line segment.
	 * @param l1p2
	 *            The second point for the first line segment.
	 * @param l2p1
	 *            The first point for the second line segment.
	 * @param l2p2
	 *            The second point for the second line segment.
	 * 
	 * @return Whether the two line segments consisting of parameters one and
	 *         two, and, three and four respectively, intersect.
	 * 
	 * @see Vec2f
	 * 
	 * @since 1.0
	 */
	public static boolean doSegmentsIntersect(Vec2f l1p1, Vec2f l1p2, Vec2f l2p1, Vec2f l2p2) {
		Vec2f lineDir1 = Vec2f.subtract(l1p1, l1p2);
		Vec2f lineDir2 = Vec2f.subtract(l2p1, l2p2);

		float dirCrossMag = Vec2f.cross(lineDir1, lineDir2).length();

		float lineDirCrossMag1 = Vec2f.cross(Vec2f.subtract(l1p1, l2p1), lineDir2).length();
		float lineDirCrossMag2 = Vec2f.cross(Vec2f.subtract(l1p1, l2p1), lineDir1).length();

		if (dirCrossMag == 0) {
			if (lineDirCrossMag2 == 0) {
				return true; // Colinear
			} else {
				return false; // Parallel
			}
		}

		float c1 = (lineDirCrossMag1) / dirCrossMag;
		float c2 = (lineDirCrossMag2) / dirCrossMag;

		return 0 <= c1 && c1 <= 1 && 0 <= c2 && c2 <= 1;
	}

	public static boolean existsChar(String string, char character) {
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == character)
				return true;
		}
		return false;
	}

	/**
	 * Returns the element of type <code>T</code> in an array at the given
	 * index. If the index is negative, then the function returns the
	 * [<code>index</code>]th element from the end. For example,
	 * <code>get(-1, {1, 2, 3})</code> will return 2 and
	 * <code>get(-1, {1, 2, 3})</code> will return 3. Uses 0-indexing.
	 * 
	 * @param index
	 *            The index of the element, may be negative.
	 * @param array
	 *            The array to get the element from.
	 * @return The element at the given index either from the beginning or from
	 *         the end depending on whether index is negative or not.
	 * 
	 * @since 1.0
	 */
	public static <T> T get(int index, T[] array) {
		if (index < 0) {
			return array[array.length - index];
		} else {
			return array[index];
		}
	}

	/**
	 * Returns the element of type <code>T</code> in an array at the given
	 * index. If the index is negative, then the function returns the
	 * [<code>index</code>]th element from the end. For example,
	 * <code>get(-1, {1, 2, 3})</code> will return 2 and
	 * <code>get(-1, {1, 2, 3})</code> will return 3. Uses 0-indexing.
	 * Additionally, if the index is outside of the array, it will loop back to
	 * be in the array.
	 * 
	 * @param index
	 *            The index of the element, may be negative or outside the
	 *            array.
	 * @param array
	 *            The array to get the element from.
	 * @return The element at the given index either from the beginning or from
	 *         the end depending on whether index is negative or not.
	 * 
	 * @since 1.0
	 */
	public static <T> T getl(int index, T[] array) {
		return array[index % array.length];
	}

	public static Class<?> getClassByName(String className, String[] classFileLocations) {
		boolean identifiedSpecificClass = false;
		int index = 0;

		int colonIndex = Util.getFirstIndexOf(":", className);
		if (colonIndex != -1) {
			index = Integer.parseInt(className.substring(0, colonIndex));
			className = className.substring(colonIndex + 1);
			identifiedSpecificClass = true;
		}

		List<Class<?>> listClasses = getClassesByName(className, classFileLocations);

		if ((listClasses.size() > 1 && !identifiedSpecificClass) || index >= listClasses.size()) {
			String errMsg = "There is more than one possible class with the name " + listClasses + "\n";
			for (int pc = 0; pc < listClasses.size(); pc++) {
				errMsg += "\t" + pc + ": " + listClasses.get(pc).getName() + "\n";
			}
			errMsg += "\nNext time, call the correct class by using 'number_from_above:Class_Name'\n\tEx: '0:Scanner' will return java.util.Scanner.class";
			throw new IllegalArgumentException(errMsg);
		}

		return listClasses.get(index);
	}

	public static List<Class<?>> getClassesByName(String className, String[] classFileLocations) {

		List<Class<?>> possibleClasses = new ArrayList<Class<?>>();

		File baseDir;
		File[] baseDirFiles;
		List<File> dirList;
		List<String> possibleClassPaths = new ArrayList<String>();

		for (String fileLocation : classFileLocations) {
			baseDir = new File(fileLocation);
			baseDirFiles = baseDir.listFiles();
			dirList = arrayToList(baseDirFiles);

			while (dirList.size() > 0) {
				int iterations = dirList.size();
				for (int f = 0; f < iterations; f++) {
					File file = dirList.get(0);

					if (file.isDirectory()) {
						File[] currentDir = file.listFiles();
						dirList.addAll(arrayToList(currentDir));
					} else if (file.getName().length() > 4 && file.getName()
							.substring(file.getName().length() - 4, file.getName().length()).equals(".zip")) {

						try {
							ZipFile zipFile = new ZipFile(file.getAbsolutePath());
							Enumeration<? extends ZipEntry> entries = zipFile.entries();
							while (entries.hasMoreElements()) {
								ZipEntry entry = entries.nextElement();
								int lastDirIndex = Util.getLastIndexOf("/", entry.getName());
								String output = lastDirIndex != -1 ? entry.getName().substring(lastDirIndex + 1)
										: entry.getName();
								if (output.equals(className + ".class") || output.equals(className + ".java")) {

									String[] classExt = new String[] { ".class", ".java" };
									String possibleClassPath = entry.getName();

									possibleClassPath = possibleClassPath.replace(classExt[0], "");
									possibleClassPath = possibleClassPath.replace(classExt[1], "");
									possibleClassPath = possibleClassPath.replace("\\", ".");
									possibleClassPath = possibleClassPath.replace("/", ".");

									possibleClassPaths.add(possibleClassPath);
								}
							}
							zipFile.close();
						} catch (IOException e) {
							System.out.println("Error opening Zip" + e);
						}

					} else if (file.isFile() && (file.getName().equals(className + ".class")
							|| file.getName().equals(className + ".java"))) {

						String srcDir = fileLocation.replace("./", "").replace(".\\", "").replace("\\", "").replace("/",
								"");
						String[] classExt = new String[] { ".class", ".java" };
						String possibleClassPath = file.getPath();

						int indexSrcDir = getFirstIndexOf(srcDir, possibleClassPath);

						if (indexSrcDir >= 0)
							possibleClassPath = possibleClassPath.substring(indexSrcDir + srcDir.length() + 1);

						possibleClassPath = possibleClassPath.replace(classExt[0], "");
						possibleClassPath = possibleClassPath.replace(classExt[1], "");
						possibleClassPath = possibleClassPath.replace("\\", ".");
						possibleClassPath = possibleClassPath.replace("/", ".");

						possibleClassPaths.add(possibleClassPath);
					}
					dirList.remove(0);
				}
			}
		}

		for (String classPackage : possibleClassPaths) {
			try {
				possibleClasses.add(Class.forName(classPackage));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return possibleClasses;
	}

	public static Constructor<?> getConstructor(Class<?> className, List<Class<?>> initClasses) {
		try {
			return className.getConstructor(initClasses.toArray(new Class<?>[0]));
		} catch (Exception e) {
			String constructor = className.getName() + "(";
			for (int c = 0; c < initClasses.size(); c++) {
				constructor += initClasses.get(c).getSimpleName() + ", ";
			}
			constructor = Util.removeLast(", ", constructor);
			constructor += ")";
			throw new NullPointerException("No such constructor: " + constructor);
		}
	}

	public static List<Constructor<?>> getConstructors(Class<?> className, List<Class<?>> initClasses) {
		List<Constructor<?>> possibleConstructors = new ArrayList<Constructor<?>>();
		List<Integer> possibleConstructorProximity = new ArrayList<Integer>();
		List<Class<?>> initClassesCopy = new ArrayList<Class<?>>();
		initClassesCopy.addAll(initClasses);
		// 6

		int[] primitiveTryIndex = new int[countPrimitiveNumberClassesIn(initClasses)];
		int[] primitiveClassIndeces = getPrimitiveNumberClassIndeces(initClasses);
		int[] proximities = new int[primitiveTryIndex.length];

		for (int i = 0; i < primitiveClassIndeces.length; i++) {
			Class<?> primClass = initClasses.get(i);

			if (primClass.equals(Double.class) || primClass.equals(double.class))
				proximities[i] = 0;
			if (primClass.equals(Long.class) || primClass.equals(long.class))
				proximities[i] = 1;
			if (primClass.equals(Integer.class) || primClass.equals(int.class))
				proximities[i] = 2;
			if (primClass.equals(Float.class) || primClass.equals(float.class))
				proximities[i] = 3;
			if (primClass.equals(Short.class) || primClass.equals(short.class))
				proximities[i] = 4;
			if (primClass.equals(Byte.class) || primClass.equals(byte.class))
				proximities[i] = 5;

		}

		while (Util.addAll(primitiveTryIndex) < primitiveTryIndex.length * 5) {
			for (int i = 0; i < primitiveTryIndex.length; i++) {
				Class<?> primitiveClass = null;
				switch (primitiveTryIndex[i]) {
				case 0:
					primitiveClass = double.class;
					break;
				case 1:
					primitiveClass = float.class;
					break;
				case 2:
					primitiveClass = long.class;
					break;
				case 3:
					primitiveClass = int.class;
					break;
				case 4:
					primitiveClass = short.class;
					break;
				case 5:
					primitiveClass = byte.class;
					break;
				}

				initClassesCopy.set(primitiveClassIndeces[i], primitiveClass);
			}

			try {
				possibleConstructors.add(getConstructor(className, initClassesCopy));
				possibleConstructorProximity.add(Math.abs(Util.addAll(Util.subtract(proximities, primitiveTryIndex))));
			} catch (Exception e) {
			}

			for (int i = 0; i < primitiveTryIndex.length; i++) {
				primitiveTryIndex[i]++;
				if (primitiveTryIndex[i] < 6)
					break;
				primitiveTryIndex[i] = 0;
			}
		}

		if (possibleConstructors.isEmpty()) {
			String constructor = className.getName() + "(";
			for (int c = 0; c < initClasses.size(); c++) {
				constructor += initClasses.get(c).getSimpleName() + ",";
			}
			constructor = Util.removeLast(",", constructor);
			constructor += ")";
			throw new NullPointerException("No constructor like: " + constructor + " exists");
		}

		List<Constructor<?>> finalConstructors = new ArrayList<Constructor<?>>();

		while (!possibleConstructors.isEmpty()) {
			int smallestNumb = 6;
			int smallestNumbIndex = -1;
			for (int i = 0; i < possibleConstructors.size(); i++) {
				if (possibleConstructorProximity.get(i) < smallestNumb) {
					smallestNumb = possibleConstructorProximity.get(i);
					smallestNumbIndex = i;
				}
			}

			finalConstructors.add(possibleConstructors.get(smallestNumbIndex));
			possibleConstructors.remove(smallestNumbIndex);
			possibleConstructorProximity.remove(smallestNumbIndex);
		}

		return finalConstructors;
	}

	public static int getFirstIndexAfter(int index, String strPart, String str) {
		for (int i = index + 1; i < str.length() - strPart.length() + 1; i++) {
			if (str.charAt(i) == strPart.charAt(0)) {
				boolean fullString = true;
				for (int c = 1; c < strPart.length(); c++) {
					if (str.charAt(i + c) != strPart.charAt(c)) {
						fullString = false;
						break;
					}
				}
				if (fullString)
					return i;
			}
		}
		return -1;
	}

	public static int getFirstIndexOf(String strPart, String str) {
		for (int i = 1; i < str.length() - strPart.length() + 1; i++) {
			if (str.charAt(i) == strPart.charAt(0)) {
				boolean fullString = true;
				for (int c = 1; c < strPart.length(); c++) {
					if (str.charAt(i + c) != strPart.charAt(c)) {
						fullString = false;
						break;
					}
				}
				if (fullString)
					return i;
			}
		}
		return -1;
	}

	public static int getLastIndexBefore(int index, String strPart, String str) {
		for (int i = index - strPart.length(); i >= 0; i--) {
			if (str.charAt(i) == strPart.charAt(0)) {
				boolean fullString = true;
				for (int c = 1; c < strPart.length(); c++) {
					if (str.charAt(i + c) != strPart.charAt(c)) {
						fullString = false;
						break;
					}
				}
				if (fullString)
					return i;
			}
		}
		return -1;
	}

	public static int getLastIndexOf(String strPart, String str) {
		return getLastIndexBefore(str.length(), strPart, str);
	}

	public static Method getMethod(Class<?> className, String name, List<Class<?>> paramClasses) {
		try {
			return className.getDeclaredMethod(name, paramClasses.toArray(new Class<?>[0]));
		} catch (Exception e) {
			System.err.println("NO SUCH METHOD FOUND");
			e.printStackTrace();
		}
		return null;
	}

	public static int[] getNewLineIndeces(String string) {
		int[] result = new int[0];
		int[] old = result;
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == '\n') {
				old = result;
				result = new int[old.length + 1];
				for (int j = 0; j < old.length; j++) {
					result[j] = old[j];
				}
				result[old.length] = i;
			}
		}
		return result;
	}

	public static Class<?> getPrimitiveClassOf(Object obj) {
		Class<?> objClass = obj.getClass();
		if (objClass.equals(Double.class))
			return double.class;
		if (objClass.equals(Long.class))
			return long.class;
		if (objClass.equals(Integer.class))
			return int.class;
		if (objClass.equals(Float.class))
			return float.class;
		if (objClass.equals(Character.class))
			return char.class;
		if (objClass.equals(Short.class))
			return short.class;
		if (objClass.equals(Byte.class))
			return byte.class;

		return objClass;
	}

	public static int[] getPrimitiveNumberClassIndeces(List<Class<?>> list) {
		int[] indeces = new int[0];
		for (int c = 0; c < list.size(); c++) {
			Class<?> primClass = list.get(c);
			if (primClass.equals(Double.class) || primClass.equals(double.class) || primClass.equals(Long.class)
					|| primClass.equals(long.class) || primClass.equals(Integer.class) || primClass.equals(int.class)
					|| primClass.equals(Float.class) || primClass.equals(float.class) || primClass.equals(Short.class)
					|| primClass.equals(short.class) || primClass.equals(Byte.class) || primClass.equals(byte.class))
				indeces = append(indeces, c);
		}
		return indeces;
	}

	public static int getStringParameterIndex(String paramStr) {
		if (!isStringParameter(paramStr))
			throw new IllegalArgumentException("'" + paramStr + "' does not follow the format: @p[number].");
		return Integer.parseInt(paramStr.substring(2));
	}

	public static <T> List<T> insertList(List<T> listInsert, List<T> base, int index) {
		List<T> result = new ArrayList<>();
		for (int i = 0; i < base.size(); i++) {
			if (i != index) {
				result.add(base.get(i));
			} else {
				for (int j = 0; j < listInsert.size(); j++) {
					result.add(listInsert.get(j));
				}
			}
		}
		return result;
	}

	public static String invertString(String str) {
		StringBuilder result = new StringBuilder();
		for (int c = str.length() - 1; c >= 0; c--) {
			result.append(str.charAt(c));
		}
		return result.toString();
	}

	public static boolean isBoolean(String bool) {
		return bool.equals("true") || bool.equals("false");
	}

	public static boolean isConstructor(String constrStr) {
		constrStr = constrStr.replace("new ", "");
		constrStr = constrStr.replace(" ", "");

		int begParenthIndex = getFirstIndexOf("(", constrStr);

		return (begParenthIndex > 0) && allCharsMatch('(', ')', constrStr)
				&& isValidByCharset(constrStr.substring(0, begParenthIndex), CHARSET_NUMBERS_LETTERS);
	}

	public static boolean isDecimal(String number) {
		boolean decimal = false;
		for (int c = 0; c < number.length(); c++) {
			if (number.charAt(c) == '.') {
				if (decimal)
					return false;
				decimal = true;
			}
		}
		return isNumber(number);
	}

	public static boolean isField(String field) {
		String[] splitField = field.split("\\.");
		return splitField.length > 1 && splitField[splitField.length - 1].length() > 0;
	}

	public static boolean isInStringArray(String str, String[] array) {
		for (int i = 0; i < array.length; i++) {
			if (str.equals(array[i]))
				return true;
		}
		return false;
	}

	public static boolean isMethod(String methodStr) {

		int periodIndex = getFirstIndexOf(".", methodStr);
		int begParenthIndex = getFirstIndexOf("(", methodStr);

		return (periodIndex > 0) && (begParenthIndex > 0) && allCharsMatch('(', ')', methodStr)
				&& isValidByCharset(methodStr.substring(0, periodIndex), CHARSET_NUMBERS_LETTERS)
				&& isValidByCharset(methodStr.substring(periodIndex + 1, begParenthIndex), CHARSET_NUMBERS_LETTERS);
	}

	public static boolean isNumber(char number) {
		for (int i = 0; i < CHARSET_NUMBERS.length; i++) {
			if (CHARSET_NUMBERS[i] == number) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNumber(String number) {
		boolean decimal = false;
		for (int i = 1; i < number.length() - 1; i++) {
			char numb = number.charAt(i);
			if ((!Util.isNumber(numb)) && (numb != '.' || decimal)) {
				return false;
			} else if (number.charAt(i) == '.') {
				decimal = true;
			}
		}
		char posneg = number.charAt(0);
		if (!isNumber(posneg) && ((posneg != '+' && posneg != '-') || number.length() <= 1))
			return false;

		char numbCast = number.charAt(number.length() - 1);
		if (!isNumber(numbCast) && !Util.isValidByCharset(numbCast, CHARSET_NUMBER_CASTING_CHARACTERS))
			return false;
		return true;
	}

	public static boolean isParsableConstructor(String constrStr) {
		int colonIndex = getFirstIndexOf(":", constrStr);
		if (colonIndex == -1) {
			return isConstructor(constrStr);
		} else if (isNumber(constrStr.substring(0, colonIndex))) {
			return isConstructor(constrStr.substring(colonIndex + 1));
		} else {
			return false;
		}
	}

	public static boolean isParsableMethod(String methodStr) {
		int colonIndex = getFirstIndexOf(":", methodStr);
		int periodIndex = getFirstIndexOf(".", methodStr);
		if (periodIndex == -1)
			return false;
		if (colonIndex == -1) {
			if (isStringParameter(methodStr.substring(0, periodIndex))) {
				return isConstructor(methodStr.substring(periodIndex + 1));
			} else {
				return isMethod(methodStr);
			}
		} else if (isNumber(methodStr.substring(0, colonIndex))) {
			return isMethod(methodStr.substring(colonIndex + 1));
		} else {
			return false;
		}
	}

	public static boolean isString(String str) {
		return (count("\"", str) == 2) && (str.charAt(0) == '\"') && (str.charAt(str.length() - 1) == '\"');
	}

	public static boolean isStringParameter(String paramStr) {
		return paramStr.length() > 2 && paramStr.substring(0, 2).equals("@p") && isNumber(paramStr.substring(2));
	}

	public static boolean isStringParameterList(String paramListStr) {
		String[] paramList = paramListStr.split(",");
		for (int i = 0; i < paramList.length; i++) {
			if (!isStringParameter(paramList[i]))
				return false;
		}
		return true;
	}

	public static boolean isValidByCharset(char character, char[] charset) {
		for (int i = 0; i < charset.length; i++) {
			if (character == charset[i])
				return true;
		}
		return false;
	}

	public static boolean isValidByCharset(String str, char[] charset) {
		for (int c = 0; c < str.length(); c++) {
			if (!isValidByCharset(str.charAt(c), charset))
				return false;
		}
		return true;
	}

	public static String leftAlignString(String msg, int stringLength) {
		return msg + repeatCharFor(' ', stringLength - msg.length());
	}

	public static Object[] listToArray(List<Object> list) {
		Object[] objArray = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {
			objArray[i] = list.get(i);
		}
		return objArray;
	}

	public static String listToString(List<String> list) {
		String result = "";
		for (int i = 0; i < list.size(); i++) {
			result += list.get(i);
		}
		return result;
	}

	public static Object parseBoolean(String bool, List<Object> paramValues) {
		if (!isBoolean(bool))
			throw new IllegalArgumentException(bool + "is not a valid boolean");

		if (bool.equals("true")) {
			paramValues.add(true);
			return true;
		} else {
			paramValues.add(false);
			return false;
		}
	}

	public static Object parseConstructor(String constrStr, List<Object> paramValues, String[] classFileLocations) {
		if (!isParsableConstructor(constrStr))
			throw new IllegalArgumentException(constrStr + "is not a valid Constructor");

		constrStr = constrStr.replace("new", "").replace(" ", "");

		int rightStartParenth = Util.getFirstIndexOf("(", constrStr);
		int leftEndParenth = Util.getLastIndexOf(")", constrStr);

		String constructorName = constrStr.substring(0, rightStartParenth);

		Class<?> constrClass = getClassByName(constructorName, classFileLocations);

		List<Class<?>> paramClasses = new ArrayList<Class<?>>();
		List<Object> paramObjects = new ArrayList<Object>();
		String[] innerArgs = constrStr.substring(rightStartParenth + 1, leftEndParenth).split(",");
		for (int i = 0; i < innerArgs.length; i++) {
			int argIndex = getStringParameterIndex(innerArgs[i]);
			paramClasses.add(getPrimitiveClassOf(paramValues.get(argIndex)));
			paramObjects.add(paramValues.get(argIndex));
		}

		Constructor<?> constr = Util.getConstructor(constrClass, paramClasses);

		Object instancedObject = null;
		try {
			instancedObject = constr.newInstance(listToArray(paramObjects));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		paramValues.add(instancedObject);

		return paramValues.get(paramValues.size() - 1);
	}

	public static Object parseField(String fieldStr, List<Object> paramValues, String[] classFileLocations) {
		if (!isField(fieldStr))
			throw new IllegalArgumentException(fieldStr + "is not a valid field");

		int paramNumb = paramValues.size();
		String[] fields = fieldStr.split("\\.");
		String className = fields[0];
		Class<?> varClass = Util.getClassByName(className, classFileLocations);

		for (int f = 1; f < fields.length; f++) {

			className = fields[f - 1];
			String varName = fields[f];

			if (isStringParameter(className)) {
				int paramIndex = getStringParameterIndex(className);
				Object paramObj = paramValues.get(paramIndex);
				try {
					paramValues.add(paramObj.getClass().getField(varName).get(paramObj));
					fields[f] = "@p" + paramNumb;
					paramNumb++;
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException
						| IllegalAccessException e) {
					boolean enumFound = false;
					Object[] enums = ((Class<?>) paramObj).getEnumConstants();
					for (Object enm : enums) {
						if (enm.toString().equals(fields[f])) {
							paramValues.add(enm);
							fields[f] = "@p" + paramNumb;
							paramNumb++;
							enumFound = true;
							break;
						}
					}

					if (!enumFound) {
						e.printStackTrace();
						System.exit(2);
					}
				}
			} else {
				try {
					paramValues.add(varClass.getField(varName).get(varClass));
					fields[f] = "@p" + paramNumb;
					paramNumb++;
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException
						| IllegalAccessException e) {
					boolean enumClassFound = false;
					Class<?>[] declClasses = varClass.getDeclaredClasses();
					for (Class<?> declClass : declClasses) {
						if (declClass.getSimpleName().equals(fields[f])) {
							paramValues.add(declClass);
							fields[f] = "@p" + paramNumb;
							paramNumb++;
							enumClassFound = true;
							break;
						}
					}

					if (!enumClassFound) {
						e.printStackTrace();
						System.exit(0);
					}
				}
			}
		}

		return paramValues.get(paramValues.size() - 1);
	}

	public static Object parseIntoObject(String objStr, List<Object> paramValues, String[] classFileLocations) {

		objStr = objStr.replace("new ", "").replace(" ", "");

		int paramNumb = 0;

		while (true) {
			int rightParenthIndex = -1;
			int leftParenthIndex = -1;
			for (int c = 0; c < objStr.length(); c++) {
				if (objStr.charAt(c) == '(')
					rightParenthIndex = c;
				if (objStr.charAt(c) == ')')
					leftParenthIndex = c;

				if (leftParenthIndex > rightParenthIndex && rightParenthIndex > -1) {
					String inParenthStr = objStr.substring(rightParenthIndex + 1, leftParenthIndex);
					if (!isStringParameterList(inParenthStr)) {
						String[] inParenthArgs = inParenthStr.split(",");
						String finalInsert = "";
						for (int arg = 0; arg < inParenthArgs.length; arg++) {
							String currentArg = inParenthArgs[arg];

							if (isStringParameter(currentArg)) {
								finalInsert += "," + currentArg;
								continue;
							} else if (isBoolean(currentArg)) {
								parseBoolean(currentArg, paramValues);
								finalInsert += ",@p" + paramNumb;
								paramNumb++;
								continue;
							} else if (isNumber(currentArg)) {
								parseNumber(currentArg, paramValues);
								finalInsert += ",@p" + paramNumb;
								paramNumb++;
								continue;
							} else if (isString(currentArg)) {
								parseString(currentArg, paramValues);
								finalInsert += ",@p" + paramNumb;
								paramNumb++;
								continue;
							} else if (isField(currentArg)) {
								parseField(currentArg, paramValues, classFileLocations);
								finalInsert += ",@p" + paramNumb;
								paramNumb++;
								continue;
							}

							throw new IllegalArgumentException("'" + currentArg + "' is not an accepted argument.");
						}

						objStr = objStr.substring(0, rightParenthIndex + 1) + finalInsert.substring(1)
								+ objStr.substring(leftParenthIndex);
						break;
					} else {
						int rightOuterParenthIndex = getLastIndexBefore(rightParenthIndex, "(", objStr);
						int rightOuterCommaIndex = getLastIndexBefore(rightParenthIndex, ",", objStr);
						int rightOuterIndex = rightOuterParenthIndex > rightOuterCommaIndex ? rightOuterParenthIndex
								: rightOuterCommaIndex;
						String functionStr = objStr.substring(rightOuterIndex + 1, leftParenthIndex + 1);

						if (isParsableConstructor(functionStr)) {
							parseConstructor(functionStr, paramValues, classFileLocations);
							objStr = objStr.substring(0, rightOuterIndex + 1) + "@p" + paramNumb
									+ objStr.substring(leftParenthIndex + 1);
							paramNumb++;
							break;
						} else if (isParsableMethod(functionStr)) {
							parseMethod(functionStr, paramValues, classFileLocations);
							objStr = objStr.substring(0, rightOuterIndex + 1) + "@p" + paramNumb
									+ objStr.substring(leftParenthIndex + 1);
							paramNumb++;
							break;
						}

						throw new IllegalArgumentException(
								"'" + functionStr + "' is not an accepted constructor or method.");
					}
				}
			}

			if (isStringParameter(objStr))
				break;
		}

		return paramValues.get(paramValues.size() - 1);
	}

	public static Object parseMethod(String methodStr, List<Object> paramValues, String[] classFileLocations) {
		if (!isParsableMethod(methodStr))
			throw new IllegalArgumentException(methodStr + "is not a valid Method");

		methodStr = methodStr.replace(" ", "");

		int rightStartParenth = Util.getFirstIndexOf("(", methodStr);
		int leftEndParenth = Util.getLastIndexOf(")", methodStr);
		int rightStartPeriod = Util.getFirstIndexOf(".", methodStr);

		String methodObject = methodStr.substring(0, rightStartPeriod);
		boolean invokeOnObject = isStringParameter(methodObject);
		String methodName = methodStr.substring(rightStartPeriod + 1, rightStartParenth);

		Class<?> methodClass = invokeOnObject ? paramValues.get(getStringParameterIndex(methodObject)).getClass()
				: getClassByName(methodObject, classFileLocations);

		List<Class<?>> paramClasses = new ArrayList<Class<?>>();
		List<Object> paramObjects = new ArrayList<Object>();
		String[] innerArgs = methodStr.substring(rightStartParenth + 1, leftEndParenth).split(",");
		for (int i = 0; i < innerArgs.length; i++) {
			int argIndex = getStringParameterIndex(innerArgs[i]);
			paramClasses.add(getPrimitiveClassOf(paramValues.get(argIndex)));
			paramObjects.add(paramValues.get(argIndex));
		}

		Method method = Util.getMethod(methodClass, methodName, paramClasses);

		Object methodResult = null;
		try {
			methodResult = invokeOnObject
					? method.invoke(paramValues.get(getStringParameterIndex(methodObject)), listToArray(paramObjects))
					: method.invoke(methodClass, listToArray(paramObjects));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		paramValues.add(methodResult);

		return paramValues.get(paramValues.size() - 1);
	}

	public static Object parseNumber(String numb, List<Object> paramValues) {
		if (!isNumber(numb))
			throw new IllegalArgumentException(numb + "is not a valid number");

		switch (numb.charAt(numb.length() - 1)) {
		case 'f':
		case 'F':
			paramValues.add(Float.parseFloat(numb.substring(0, numb.length() - 1)));
			break;
		case 'l':
		case 'L':
			paramValues.add(Long.parseLong(numb.substring(0, numb.length() - 1).split("\\.")[0]));
			break;
		case 'i':
		case 'I':
			paramValues.add(Integer.parseInt(numb.substring(0, numb.length() - 1).split("\\.")[0]));
			break;
		case 'd':
		case 'D':
			paramValues.add(Double.parseDouble(numb.substring(0, numb.length() - 1)));
			break;
		case 's':
		case 'S':
			paramValues.add(Short.parseShort(numb.substring(0, numb.length() - 1).split("\\.")[0]));
			break;
		case 'b':
		case 'B':
			paramValues.add(Byte.parseByte(numb.substring(0, numb.length() - 1).split("\\.")[0]));
			break;
		default:
			if (isDecimal(numb)) {
				paramValues.add(Double.parseDouble(numb));
				break;
			} else {
				paramValues.add(Long.parseLong(numb));
				break;
			}
		}

		return paramValues.get(paramValues.size() - 1);
	}

	public static Object parseString(String str, List<Object> paramValues) {
		if (!isString(str))
			throw new IllegalArgumentException("'" + str + "' is not a valid String");
		String result = new String(str.substring(1, str.length() - 1));
		paramValues.add(result);
		return result;
	}

	/**
	 * <p>
	 * Removes the specified element at <code>index</code> in <code>array</code>
	 * and puts the new array into <code>result</code>.
	 * </p>
	 * 
	 * @param index
	 *            The index at which to remove an element.
	 * @param array
	 *            The array the element is being removed from.
	 * @param result
	 *            The array where the change will be stored.
	 * @return The resulting array.
	 * 
	 * @throws IllegalArgumentException
	 *             If <codeindex</code> is not a valid index for
	 *             <code>result</code> or if <code>result</code>'s length is not
	 *             one less than <code>array</code>.
	 * 
	 * @since 1.0
	 */
	public static int[] remove(int index, int[] array, int[] result) {
		if (index > result.length - 1)
			throw new IllegalArgumentException("The specified index cannot be an index outside of 'result'.");

		if (result.length + 1 != array.length)
			throw new IllegalArgumentException(
					"The 'result' array must be an array with a length one less than 'array'.");

		for (int i = 0; i < index; i++) {
			result[i] = array[i];
		}

		for (int i = index + 1; i < result.length; i++) {
			result[i] = array[i];
		}

		return result;
	}

	/**
	 * <p>
	 * Removes the specified element at <code>index</code> in <code>array</code>
	 * and puts the new array into <code>result</code>.
	 * </p>
	 * 
	 * @param index
	 *            The index at which to remove an element.
	 * @param array
	 *            The array the element is being removed from.
	 * @param result
	 *            The array where the change will be stored.
	 * @return The resulting array.
	 * 
	 * @throws IllegalArgumentException
	 *             If <codeindex</code> is not a valid index for
	 *             <code>result</code> or if <code>result</code>'s length is not
	 *             one less than <code>array</code>.
	 * 
	 * @since 1.0
	 */
	public static <T> T[] remove(int index, T[] array, T[] result) {
		if (index > result.length - 1)
			throw new IllegalArgumentException("The specified index cannot be an index outside of 'result'.");

		if (result.length + 1 != array.length)
			throw new IllegalArgumentException(
					"The 'result' array must be an array with a length one less than 'array'.");

		for (int i = 0; i < index; i++) {
			result[i] = array[i];
		}

		for (int i = index + 1; i < result.length; i++) {
			result[i] = array[i];
		}

		return result;
	}

	public static String[] remove(String[] array, int start, int length) {
		String[] temp = new String[array.length - length];
		int index = 0;
		for (int i = 0; i < start; i++) {
			temp[index] = array[i];
			index++;
		}
		for (int i = start + length; i < array.length; i++) {
			temp[index] = array[i];
			index++;
		}
		return temp;
	}

	public static String removeFirst(String strPart, String str) {
		int index = getFirstIndexOf(strPart, str);
		return index == -1 ? str : str.substring(0, index) + str.substring(index + strPart.length());
	}

	public static String removeLast(String strPart, String str) {
		int index = getLastIndexOf(strPart, str);
		return index == -1 ? str : str.substring(0, index) + str.substring(index + strPart.length());
	}

	public static String repeatCharFor(char character, int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(character);
		}
		return sb.toString();
	}

	public static String repeatStringFor(String str, int numbTimes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numbTimes; i++) {
			sb.append(str);
		}
		return sb.toString();
	}

	public static String replaceChar(String str, int index, char character) {
		char[] charArr = str.toCharArray();
		charArr[index] = character;
		return new String(charArr);
	}

	public static String rightAlignString(String msg, int stringLength) {
		return repeatCharFor(' ', stringLength - msg.length()) + msg;
	}

	public static String arrayToString(int[] array) {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (int numb : array) {
			sb.append(numb);
			sb.append(", ");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.deleteCharAt(sb.length() - 1);
		sb.append(']');
		return sb.toString();
	}

	public static List<?> shrinkList(List<?> list, int min, int max) {
		int size = list.size();
		for (int i = 0; i < size - max - 1; i++) {
			list.remove(max + 1);
		}
		for (int i = 0; i < min; i++) {
			list.remove(0);
		}
		return list;
	}

	public static String[] splitBy(String str, String... parts) {
		String[] result = new String[0];
		int startIndex = 0;

		for (int c = 0; c < str.length(); c++) {
			for (int i = 0; i < parts.length; i++) {
				if (c + parts[i].length() >= str.length())
					break;

				if (str.charAt(c) == parts[i].charAt(0)) {
					boolean fullString = true;
					for (int j = 1; j < parts[i].length(); j++) {
						if (str.charAt(c + j) != parts[i].charAt(j))
							fullString = false;
					}
					if (fullString) {
						String appendPart = str.substring(startIndex, c);

						result = Util.append(result, appendPart);
						c += parts[i].length();
						startIndex = c;
						break;
					}
				}
			}
		}

		return result;
	}

	public static int[] subtract(int[] array1, int[] array2) {
		int greaterLength = array1.length >= array2.length ? array1.length : array2.length;
		int lesserLength = array1.length <= array2.length ? array1.length : array2.length;
		int[] result = new int[greaterLength];

		for (int i = 0; i < lesserLength; i++) {
			result[i] = array2[i] - array1[i];
		}

		for (int i = lesserLength; i < array1.length; i++) {
			result[i] = array1[i];
		}
		for (int i = lesserLength; i < array2.length; i++) {
			result[i] = array2[i];
		}

		return result;
	}
}
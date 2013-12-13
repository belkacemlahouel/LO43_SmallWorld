
/*
 * Implementation of static methods used as tools during the project
 * For example, the generation of a random number between two others
*/

public class Tools {
	public static int rand (int higher, int lower) {
		return (int) ((Math.random() * (higher - lower)) + lower);
	}
}

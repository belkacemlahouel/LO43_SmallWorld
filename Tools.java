
public class Tools {
	public static int rand (int higher, int lower) {
		// return (int) ((Math.random() * (higher - lower)) + lower);
		Random r = new Random ();
		return r.nextInt(higher - lower + 1) + lower;
	}
}

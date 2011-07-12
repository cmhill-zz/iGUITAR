package edu.umd.cs.guitar.guitestrunner;

/**
 * Utilities useful to pretty much any type of test case replayer.
 * @author Scott McMaster
 *
 */
public class Utils {

	/**
	 * Sleep and eat the useless checked exception.
	 * @param ms
	 */
	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}
}

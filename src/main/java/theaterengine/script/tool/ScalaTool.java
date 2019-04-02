package theaterengine.script.tool;

import com.google.common.util.concurrent.RateLimiter;

/**
 *
 * @author hundun
 * Created on 2019/04/02
 */
public class ScalaTool {
	
	private static int rate = 100;
	
	public static int meterToPixel(double n) {
		return (int) (n * rate);
	}
	
	public static double pixelToMeter(int n) {
		return n * 1.0 / rate;
	}

}

package theaterengine.script.tool;

import com.google.common.util.concurrent.RateLimiter;

import theaterengine.MovementAction;
import theaterengine.script.statement.Keyword;

/**
 *
 * @author hundun
 * Created on 2019/04/02
 */
public class ScalaTool {
	
	private static int rate = 50;
	
	public static double meterToPixel(double n) {
		return n * rate;
	}
	
	public static double pixelToMeter(int n) {
		return n * 1.0 / rate;
	}

	public static double getSignedDistanceInPixel(String argDirection, String argDistance) {
		return Keyword.get(argDirection).getIntValue() * ScalaTool.meterToPixel(Double.parseDouble(argDistance));
	}
}

package theaterengine.script.tool;



import theaterengine.script.statement.TokenType;

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

//	public static double getSignedDistanceInPixel(String argDirection, String argDistance) {
//		return TokenType.get(argDirection).getIntValue() * ScalaTool.meterToPixel(Double.parseDouble(argDistance));
//	}
}

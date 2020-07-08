package theaterengine;

import theaterengine.gui.MainFrame;
import theaterengine.script.tool.ScalaTool;

/**
 * @author hundun
 * Created on 2020/07/08
 */
public class Application {
    
    public static int positionZeroX = (int)ScalaTool.meterToPixel(9);
    public static int positionZeroY = (int)ScalaTool.meterToPixel(4);
    
    public static int stagePanelWidth = (int)ScalaTool.meterToPixel(18);
    public static int stagePanelHeight = (int)ScalaTool.meterToPixel(8);
    
    final static String scriptFilename = "play/test.txt";
    
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame(stagePanelWidth, stagePanelHeight, positionZeroX, positionZeroY);
        mainFrame.loadScript(scriptFilename);
    }

}

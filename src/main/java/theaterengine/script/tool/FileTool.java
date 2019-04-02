package theaterengine.script.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author hundun
 * Created on 2019/04/02
 */
public class FileTool {
	
	public static void saveTxtFile(String pathName, String text) throws IOException {
		 
        File f = new File(pathName);
        FileOutputStream fop = new FileOutputStream(f);

        OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");

        writer.append(text);

        writer.close();
        fop.close();
    }

}

package org.openmrs.module.custombranding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jakub on 25.11.14.
 */
public class CustomizeCssUtils {

	//metoda do podmianki plików
	//
	public static void overrideDefaultCssFilesWithCustom() throws IOException {
		Log log = LogFactory.getLog(CustomizeCssUtils.class);

		CssFileService service = Context.getService(CssFileService.class);
		List<CssFile> allDbFiles = (List<CssFile>)service.getAllCssFiles();

		for(CssFile cssf : allDbFiles) {

			File f = new File(cssf.getPath());
			if(!f.isDirectory()) {
				FileWriter writer = new FileWriter(f, false); //override file
				// false to overwrite.
				writer.write(cssf.getContent());
				writer.close();
			} else {
				log.warn("path recieve from database file points to directory instead of css file: " + cssf.getPath());
			}
		}
	}
}

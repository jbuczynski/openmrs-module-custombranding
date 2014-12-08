package org.openmrs.module.custombranding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CustomizeCssUtils {

	public static void overrideDefaultCssFilesWithCustom() throws IOException {
		Log log = LogFactory.getLog(CustomizeCssUtils.class);

		CssFileService service = Context.getService(CssFileService.class);

		List<CssFile> allDbFiles = (List<CssFile>) service.getAllCssFiles();

		for(CssFile cssf : allDbFiles) {

			File f = new File(cssf.getNameAndPath());
			if(!f.isDirectory()) {
				FileWriter writer = new FileWriter(f, false); //override file
				writer.write(cssf.getContent());
				writer.close();
			} else {
				log.warn("path recieved from database file points to directory instead of css file: " + cssf.getNameAndPath());
			}
		}
	}

	public static String getAppPath(HttpSession session) {
		return session.getServletContext().getRealPath("/");
	}
}

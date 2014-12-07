package org.openmrs.module.custombranding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.util.OpenmrsConstants;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


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

    public static void setServerLocation() {

//        String realPath = Context.getServletContext().getRealPath("/");
//        GlobalProperty gp = new GlobalProperty();
//        gp.setPropertyValue(xml);
//        Context.getAdministrationService().saveGlobalProperty(gp);
    }
}

package org.openmrs.module.custombranding.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The controller for previewing a HtmlForm by loading the xml file that defines that HtmlForm from
 * disk.
 * <p/>
 */
@Controller
public class CustomizeCssFormController {

	 private List<String> cssFileNames;
	 private HashMap<String,String> cssFileMap;



	@RequestMapping(value="/module/custombranding/customizeCssEdit.form", method=RequestMethod.GET)
	public void handleCssEditing( HttpServletRequest request, ModelMap model ) {

		String realPath = request.getSession().getServletContext().getRealPath("");
		File dir = new File(realPath);
		getCsFiles(dir);

		model.addAttribute("cssFileNames", cssFileNames);
		model.addAttribute("cssFileMap", cssFileMap);

	}



	@RequestMapping(value="/module/custombranding/customizeCssReplaceFiles.form", method=RequestMethod.GET)
	public void handleCssReplacing( HttpServletRequest request, ModelMap model  ){
		String realPath = request.getSession().getServletContext().getRealPath("");
		File dir = new File(realPath);
		getCsFiles(dir);

		model.addAttribute("cssFileNames", cssFileNames);
		model.addAttribute("cssFileMap", cssFileMap);
	}

	private void getCsFiles(File dir) {
		cssFileMap = new HashMap<String, String>();
		cssFileNames = new LinkedList<String>();
		FileFilter urlFilter = new FileFilter() {
			@Override
			public boolean accept(File file) {
				//if (file.isDirectory()) {
				//	return true; // to recursive
				//}
				return file.getName().endsWith(".css"); // return .url files
			}
		};
		File[] tmp = dir.listFiles(urlFilter);
		if(tmp != null) {

			for (final File fileEntry : tmp) { // listFiles using filter
				String fileName = fileEntry.getName();
				// match file names w/o extension
				cssFileMap.put(fileName, dir.getAbsolutePath());
				cssFileNames.add(fileName);
			}
		}
	}

}

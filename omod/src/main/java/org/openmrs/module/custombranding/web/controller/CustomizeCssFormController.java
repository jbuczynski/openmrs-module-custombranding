package org.openmrs.module.custombranding.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.custombranding.CssFile;
import org.openmrs.module.custombranding.CssFileService;
import org.openmrs.module.custombranding.CustomizeCssUtils;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * The controller for previewing a HtmlForm by loading the xml file that defines that HtmlForm from
 * disk.
 * <p/>
 */
@Controller
@RequestMapping(value="/module/custombranding")
public class CustomizeCssFormController {

	protected final Log log = LogFactory.getLog(getClass());
	private List<String> cssFileNames;
	private HashMap<String,String> cssFileMap;
	private String realPath;
	private CssFile currentFile = new CssFile();
	private Boolean lastRecursionToogle = false;


	@RequestMapping(value="/customizeCssEdit.form", method=RequestMethod.GET)
	public void handleCssEditing( HttpServletRequest request, ModelMap model ) {

		realPath = request.getSession().getServletContext().getRealPath("");
		File dir = new File(realPath);
		getCsFiles(dir, lastRecursionToogle);

		model.addAttribute("curreCssFile", currentFile);
		model.addAttribute("cssFileNames", cssFileNames);
		model.addAttribute("cssFileMap", cssFileMap);

	}



	@RequestMapping(value="/customizeCssReplaceFiles.form", method=RequestMethod.GET)
	public void handleCssReplacing(HttpServletRequest request, ModelMap model  ){

		realPath = request.getSession().getServletContext().getRealPath("");
		File dir = new File(realPath);
		getCsFiles(dir, lastRecursionToogle);

		model.addAttribute("curreCssFile", currentFile);
		model.addAttribute("cssFileNames", cssFileNames);
		model.addAttribute("cssFileMap", cssFileMap);
	}


	@RequestMapping(value="/CssContent", method=RequestMethod.GET)
	public @ResponseBody String getCssFileContent(@RequestParam(value="name", defaultValue="none") String name, HttpServletRequest request) throws IOException {

		if(!name.equals("none") && cssFileMap.containsKey(name)) {

			BufferedReader br = new BufferedReader(new FileReader(cssFileMap.get(name) + "/" + name));
			try {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
				String content = sb.toString();

				currentFile.setName(name);
				currentFile.setContent(content);
				currentFile.setPath(cssFileMap.get(name));

				return content;
			} finally {
				br.close();
			}
		}
		else {
			return "No such file";
		}
	}

	@RequestMapping(value="/SearchCssFiles", method=RequestMethod.GET)
	public @ResponseBody Map<String, String> SearchCssFiles( HttpServletRequest request, ModelMap model)  {

		realPath = request.getSession().getServletContext().getRealPath("");
		File dir = new File(realPath);
		lastRecursionToogle = !lastRecursionToogle;
		getCsFiles(dir, lastRecursionToogle);

		model.addAttribute("curreCssFile", currentFile);
		model.addAttribute("cssFileNames", cssFileNames);
		model.addAttribute("cssFileMap", cssFileMap);

		return cssFileMap;
	}

    @RequestMapping(value = "/dbRequest", method = RequestMethod.POST)
	public String submitDepartment( ModelMap model,HttpServletRequest request,
								   @RequestParam(required = true, value = "action") String action) {

		MessageSourceService mss = Context.getMessageSourceService();
		String redirect = "redirect:/module/custombranding/customizeCssEdit.form";

			if (!Context.isAuthenticated()) {
				//errors.reject("custombranding.auth.required");
			} else if (mss.getMessage("custombranding.db.action.updateCssFile").equals(action)) {
				redirect = updateCssFile(request, model);
			} else if (mss.getMessage("custombranding.db.action.deleteCssFile").equals(action)) {
				redirect = deleteCssFile(request, model);
			}

		try {
			CustomizeCssUtils.overrideDefaultCssFilesWithCustom();
		}  catch (IOException e) {
			log.error("Error while manipulating css files", e);
		}
		return redirect;
	}

	private String updateCssFile( HttpServletRequest request, ModelMap model ) {

		MessageSourceService mss = Context.getMessageSourceService();
		CssFileService fileService = Context.getService(CssFileService.class);


		try {
			CssFile tmp = fileService.getCssFileByName(currentFile.getName());
			currentFile.setId(tmp.getId());
			CssFile cssFile = fileService.saveCssFile(currentFile);
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "custombranding.db.save.success");
		} catch (org.hibernate.NonUniqueResultException e) {
			log.error("In database exist multiple css files with same name!", e);
		}
		catch (Exception ex) {
			request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "custombranding.db.save.failure");
			log.error("Failed to delete css file", ex);

		}

		return "redirect:/module/custombranding/customizeCssEdit.form";
	}


	private String deleteCssFile( HttpServletRequest request, ModelMap model ) {



		MessageSourceService mss = Context.getMessageSourceService();
		CssFileService fileService = Context.getService(CssFileService.class);

		try {

			fileService.purgeCssFile(currentFile);
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "custombranding.db.delete.success");
		}
		catch (Exception ex) {
			request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "custombranding.db.delete.failure");
			log.error("Failed to delete department", ex);
		}

		return "redirect:/module/custombranding/customizeCssReplaceFiles.form";

	}

	private void getCsFiles(File dir, Boolean recursive) {

		cssFileMap = new HashMap<String, String>();
		cssFileNames = new LinkedList<String>();

		FileFilter urlFilter = new FileFilter() {
			@Override
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return true;
				}
				return file.getName().endsWith(".css"); // return .url files
			}
		};

		List<File> allFiles = new ArrayList<File>();
		Queue<File> dirs = new LinkedList<File>();
		dirs.add(dir);
		while (!dirs.isEmpty()) {
			for (File f : dirs.poll().listFiles(urlFilter)) {
				if (f.isDirectory() && recursive) {
					dirs.add(f);
				} else if (f.isFile()) {
					allFiles.add(f);
					cssFileMap.put(f.getName(), dir.getAbsolutePath());
					cssFileNames.add(f.getName());
				}
			}
		}
	}

}

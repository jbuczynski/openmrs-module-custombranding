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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
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
	private HashMap<String,String> cssFileMap;
	private String realPath;
	private CssFile currentFile = new CssFile();
	private Boolean lastRecursionToogle = false;


    @RequestMapping(value="/customizeCssEdit.form", method=RequestMethod.GET)
	public void handleCssEditing( HttpServletRequest request, ModelMap model ) {
		handleListModel(request, model);
	}

	@RequestMapping(value="/customizeCssReplaceFiles.form", method=RequestMethod.GET)
	public void handleCssReplacing(HttpServletRequest request, ModelMap model  ){
		handleListModel(request, model);
	}

	@RequestMapping(value="/CssContent", method=RequestMethod.GET)
	public @ResponseBody String getCssFileContent(@RequestParam(value="path", defaultValue="none") String path,
												  HttpServletRequest request) throws IOException {

		if(!path.equals("none") && cssFileMap.containsKey(path)) {
				return prepareContent(path);
			} else {
			MessageSourceService mss = Context.getMessageSourceService();
			mss.getMessage("No such file");
			return "No such file";
		}
	}

	@RequestMapping(value="/SearchCssFiles", method=RequestMethod.GET)
	public @ResponseBody Map<String, String> SearchCssFiles( HttpServletRequest request)  {

		realPath = request.getSession().getServletContext().getRealPath("/");
//        String path2;
//        String path3;
//        try{
//          // path3 = request.getSession().getServletContext().getResource("").getPath();
//            path2 = CustomizeCssUtils.getPath(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        File dir = new File(realPath);
		lastRecursionToogle = !lastRecursionToogle;
		getCsFiles(dir, lastRecursionToogle);

		return cssFileMap;
	}

    @RequestMapping(value = "/dbRequest", method = RequestMethod.POST)
	public @ResponseBody String dbRequest( HttpServletRequest request,
								   @RequestParam(required = true, value = "action") String action,
								   @RequestParam(required = false, value = "content") String content ) {

		MessageSourceService mss = Context.getMessageSourceService();
		String redirect = "/module/custombranding/customizeCssEdit.form";
		if ( content == null) {
			content = "";
			log.warn("css file content never should be null");
		}
		currentFile.setContent(content);

			if (!Context.isAuthenticated()) {
				//errors.reject("custombranding.auth.required");
			} else if (mss.getMessage("custombranding.db.action.updateCssFile").equals(action)) {
				redirect = updateCssFile(request);
			} else if (mss.getMessage("custombranding.db.action.deleteCssFile").equals(action)) {
				redirect = deleteCssFile(request);
			} else if(mss.getMessage("custombranding.db.action.replaceCssFile").equals(action)) {
				redirect = updateCssFile(request);
			}

		try {
			CustomizeCssUtils.overrideDefaultCssFilesWithCustom();
		}  catch (IOException e) {
			log.error("Error while manipulating css files", e);
		}
		return redirect;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private String updateCssFile( HttpServletRequest request) {

		CssFileService fileService = Context.getService(CssFileService.class);

		try {
			CssFile tmp = fileService.getCssFileByNameAndPath(currentFile.getNameAndPath());
            if(tmp != null) {
			    currentFile.setId(tmp.getId());
            }
			validateCF();
			CssFile cssFile = fileService.mergeCssFile(currentFile);
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "custombranding.db.save.success");
		} catch (org.hibernate.NonUniqueResultException e) {
			request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "custombranding.db.save.failure");
			log.error("In database exist multiple css files with same name!", e);
		}
		catch (Exception ex) {
			request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "custombranding.db.save.failure");
			log.error("Failed to update css file", ex);

		}

		return "/module/custombranding/customizeCssEdit.form";
	}


	private String deleteCssFile( HttpServletRequest request) {

		CssFileService fileService = Context.getService(CssFileService.class);

		try {
			validateCF();
			fileService.purgeCssFile(currentFile);
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "custombranding.db.delete.success");
		}
		catch (Exception ex) {
			request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "custombranding.db.delete.failure");
			log.error("Failed to delete department", ex);
		}

		return "/module/custombranding/customizeCssReplaceFiles.form";

	}



	private String prepareContent(String path) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(path));

		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}
		String content = sb.toString();

		currentFile.setName(cssFileMap.get(path));
		currentFile.setContent(content);
		currentFile.setPath(  path.substring(0, path.length() - cssFileMap.get(path).length()));
		currentFile.setNameAndPath(path);
		br.close();

		return content;
	}

	private void getCsFiles(File dir, Boolean recursive) {

		cssFileMap = new HashMap<String, String>();

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
					cssFileMap.put(f.getAbsolutePath(), f.getName());
				}
			}
		}
	}

	private void handleListModel(HttpServletRequest request, ModelMap model ) {

		realPath = request.getSession().getServletContext().getRealPath("/");

		File dir = new File(realPath);
		getCsFiles(dir, lastRecursionToogle);

		model.addAttribute("currentCssFile", currentFile);
		model.addAttribute("cssFileMap", cssFileMap);
	}

	private void validateCF() throws Exception{

		if(currentFile.getName() == null || currentFile.getPath() == null || currentFile.getContent() == null ||
				currentFile.getNameAndPath() == null) {
			throw new Exception("css file validation exception, any field cannot be null");
		}
	}

}

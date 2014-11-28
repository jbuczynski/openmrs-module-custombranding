package org.openmrs.module.custombranding.web.controller;

import org.openmrs.module.custombranding.CssFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
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
@RequestMapping(value="/module/custombranding", method=RequestMethod.GET)
public class CustomizeCssFormController {

	private List<String> cssFileNames;
	private HashMap<String,String> cssFileMap;
	private String realPath;
	private CssFile currentFile = new CssFile();


	@RequestMapping(value="/customizeCssEdit.form", method=RequestMethod.GET)
	public void handleCssEditing( HttpServletRequest request, ModelMap model ) {

		realPath = request.getSession().getServletContext().getRealPath("");
		File dir = new File(realPath);
		getCsFiles(dir);

		model.addAttribute("curreCssFile", currentFile);
		model.addAttribute("cssFileNames", cssFileNames);
		model.addAttribute("cssFileMap", cssFileMap);

	}



	@RequestMapping(value="/customizeCssReplaceFiles.form", method=RequestMethod.GET)
	public void handleCssReplacing( HttpServletRequest request, ModelMap model  ){
		realPath = request.getSession().getServletContext().getRealPath("");
		File dir = new File(realPath);
		getCsFiles(dir);

		model.addAttribute("curreCssFile", currentFile);
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

	@RequestMapping(value="/replaceCssFile", method=RequestMethod.POST)
	public void replaceCssFile( @RequestParam(value="name", defaultValue="none") String name, HttpServletRequest request) {

		realPath = request.getSession().getServletContext().getRealPath("");
		File dir = new File(realPath);
		//getCsFiles(dir);

		//model.addAttribute("cssFileNames", cssFileNames);
		//model.addAttribute("cssFileMap", cssFileMap);

	}

	@RequestMapping(value="/updateCssFile", method=RequestMethod.POST)
	public void updateCssFile( @RequestParam(value="name", defaultValue="none") String name, HttpServletRequest request) {

		String realPath = request.getSession().getServletContext().getRealPath("");
		File dir = new File(realPath);
		//getCsFiles(dir);

		//model.addAttribute("cssFileNames", cssFileNames);
		//model.addAttribute("cssFileMap", cssFileMap);

	}

//	@RequestMapping(value = "/dbRequest", method = RequestMethod.POST)
//	public String submitDepartment(WebRequest request, HttpSession httpSession, ModelMap model,
//								   @RequestParam(required = false, value = "action") String action,
//								   @ModelAttribute("cssFile") CssFile department, BindingResult errors) {
//
//		MessageSourceService mss = Context.getMessageSourceService();
//		DepartmentService departmentService = Context.getService(DepartmentService.class);
//		if (!Context.isAuthenticated()) {
//			errors.reject("department.auth.required");
//		} else if (mss.getMessage("department.purgeDepartment").equals(action)) {
//			try {
//				departmentService.purgeDepartment(department);
//				httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "department.delete.success");
//				return "redirect:departmentList.list";
//			}
//			catch (Exception ex) {
//				httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "department.delete.failure");
//				log.error("Failed to delete department", ex);
//				return "redirect:departmentForm.form?departmentId=" + request.getParameter("departmentId");
//			}
//		} else {
//			departmentService.saveDepartment(department);
//			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "department.saved");
//		}
//		return "redirect:departmentList.list";
//	}

}

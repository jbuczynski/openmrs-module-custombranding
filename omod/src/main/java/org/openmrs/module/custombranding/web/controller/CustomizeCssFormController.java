package org.openmrs.module.custombranding.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The controller for previewing a HtmlForm by loading the xml file that defines that HtmlForm from
 * disk.
 * <p/>
 */
@Controller
public class CustomizeCssFormController {

	private List<String> cssFilesNames;

	@ModelAttribute
	public Map<String,String> getCssFiles() {
		return new HashMap<String, String>();
	}

	@RequestMapping(value="/module/custombranding/customizeCss.form", method=RequestMethod.GET)
	public void showForm(){

	}

	@RequestMapping(value="/module/custombranding/customizeCssReplaceFiles", method=RequestMethod.GET)
	public void showForm1(){

	}

}

package org.openmrs.module.custombranding.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/module/custombranding")
public class CustombrandingAdminPageController {

    @RequestMapping(value="/custombrandingAdminPaget.form", method= RequestMethod.GET)
    public void handleCustombrandingAdminPage( HttpServletRequest request, ModelMap model ) {

    }
}

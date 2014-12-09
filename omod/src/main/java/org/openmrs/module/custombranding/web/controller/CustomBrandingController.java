/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.custombranding.web.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.openmrs.module.custombranding.CustomBrandingUtils;
import org.openmrs.web.WebConstants;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

public class CustomBrandingController extends SimpleFormController {

    String largeLogo, smallLogo, textLogo, orgUrl, favicon, icon;

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        String fileSysLocation = request.getRealPath("/");
        String messageFileLocation = fileSysLocation + File.separator + "WEB-INF" + File.separator + "messages.properties";
        HttpSession session = request.getSession();

        if (request.getParameter("action") != null) {
            if (request.getParameter("id") != null) {
                if (request.getParameter("id").equals("largeLogo")) {
                    if (new File(fileSysLocation + largeLogo + ".orig").exists()) {
                        FileUtils.copyFile(new File(fileSysLocation + largeLogo + ".orig"), new File(fileSysLocation + largeLogo));
                        session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Back to default large logo");
                    }
                } else if (request.getParameter("id").equals("smallLogo")) {
                    if (new File(fileSysLocation + smallLogo + ".orig").exists()) {
                        FileUtils.copyFile(new File(fileSysLocation + smallLogo + ".orig"), new File(fileSysLocation + smallLogo));
                        session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Back to default small logo");
                    }
                } else if (request.getParameter("id").equals("textLogo")) {
                    if (new File(fileSysLocation + textLogo + ".orig").exists()) {
                        FileUtils.copyFile(new File(fileSysLocation + textLogo + ".orig"), new File(fileSysLocation + textLogo));
                        session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Back to default text logo");
                    }
                } else if (request.getParameter("id").equals("messageFile")) {
                    if (new File(messageFileLocation + ".orig").exists()) {
                        FileUtils.copyFile(new File(messageFileLocation + ".orig"), new File(messageFileLocation));
                        session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Back to default messages");
                    }
                } else if (request.getParameter("id").equals("faviconFile")) {
                    if (new File(fileSysLocation + favicon + ".orig").exists()) {
                        FileUtils.copyFile(new File(fileSysLocation + favicon + ".orig"), new File(fileSysLocation + favicon));
                        session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Back to default favicon");
                    }
                } else if (request.getParameter("id").equals("iconFile")) {
                    if (new File(fileSysLocation + icon + ".orig").exists()) {
                        FileUtils.copyFile(new File(fileSysLocation + icon + ".orig"), new File(fileSysLocation + icon));
                        session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Back to default icon");
                    }
                }
            }
        } else {
            try {
                FileUploadBean bean = (FileUploadBean) command;
                MultipartFile largeLogoFile = bean.getLargeLogoFile();
                MultipartFile smallLogoFile = bean.getSmallLogoFile();
                MultipartFile textLogoFile = bean.getTextLogoFile();
                MultipartFile messageFile = bean.getMessageFile();
                MultipartFile faviconFile = bean.getFaviconFile();
                MultipartFile iconFile = bean.getIconFile();
                
                String orgUrlStr = bean.getOrgUrl();

                if (largeLogoFile != null) {
                    FileUtils.copyFile(new File(fileSysLocation + largeLogo), new File(fileSysLocation + largeLogo + ".orig"));
                    largeLogoFile.transferTo(new File(fileSysLocation + largeLogo));
                    session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Successfully replaced large logo");
                } else if (smallLogoFile != null) {
                    FileUtils.copyFile(new File(fileSysLocation + smallLogo), new File(fileSysLocation + smallLogo + ".orig"));
                    smallLogoFile.transferTo(new File(fileSysLocation + smallLogo));
                    session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Successfully replaced small logo");
                } else if (textLogoFile != null) {
                    FileUtils.copyFile(new File(fileSysLocation + textLogo), new File(fileSysLocation + textLogo + ".orig"));
                    textLogoFile.transferTo(new File(fileSysLocation + textLogo));
                    session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Successfully replaced text logo");
                } else if (messageFile != null) {
                    FileUtils.copyFile(new File(messageFileLocation), new File(messageFileLocation + ".orig"));
                    messageFile.transferTo(new File(messageFileLocation));
                    session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Successfully replaced messages file");
                } else if (faviconFile != null) {
                    FileUtils.copyFile(new File(fileSysLocation + favicon), new File(fileSysLocation + favicon + ".orig"));
                    faviconFile.transferTo(new File(fileSysLocation + favicon));
                    session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Successfully replaced favicon file");
                } else if (iconFile != null) {
                    FileUtils.copyFile(new File(fileSysLocation + icon), new File(fileSysLocation + icon + ".orig"));
                    iconFile.transferTo(new File(fileSysLocation + icon));
                    session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Successfully replaced favicon file");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView(new RedirectView(request.getContextPath() + getSuccessView()));
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return new FileUploadBean();
    }

    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        String fileSysLocation = request.getRealPath("/");
        String messageFileLocation = fileSysLocation  + "WEB-INF" + File.separator + "messages.properties";
        String messageNewLocation = fileSysLocation + "images" + File.separator + "messages.properties";
        FileUtils.copyFile(new File(messageFileLocation), new File(messageNewLocation));

        Properties themeProps = CustomBrandingUtils.getThemeProperties(request);
        largeLogo = themeProps.getProperty("image.logo.large");
        smallLogo = themeProps.getProperty("image.logo.small");
        textLogo = themeProps.getProperty("image.logo.text.small");
        orgUrl = themeProps.getProperty("url.organization");
        favicon = themeProps.getProperty("favicon");
        icon =  themeProps.getProperty("favicon.png");
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("largeLogo", largeLogo);
        map.put("smallLogo", smallLogo);
        map.put("textLogo", textLogo);
        map.put("orgUrl", orgUrl);
        map.put("favicon", favicon);
        map.put("icon", icon);

        return map;
    }

    public class FileUploadBean {

        private MultipartFile largeLogoFile;
        private MultipartFile smallLogoFile;
        private MultipartFile textLogoFile;
        private MultipartFile messageFile;
        private MultipartFile faviconFile;
        private MultipartFile iconFile;
        private String orgUrl;

        public MultipartFile getLargeLogoFile() {
            return largeLogoFile;
        }

        public void setLargeLogoFile(MultipartFile largeLogoFile) {
            this.largeLogoFile = largeLogoFile;
        }

        public String getOrgUrl() {
            return orgUrl;
        }

        public void setOrgUrl(String orgUrl) {
            this.orgUrl = orgUrl;
        }

        public MultipartFile getSmallLogoFile() {
            return smallLogoFile;
        }

        public void setSmallLogoFile(MultipartFile smallLogoFile) {
            this.smallLogoFile = smallLogoFile;
        }

        public MultipartFile getTextLogoFile() {
            return textLogoFile;
        }

        public void setTextLogoFile(MultipartFile textLogoFile) {
            this.textLogoFile = textLogoFile;
        }

        public MultipartFile getMessageFile() {
            return messageFile;
        }

        public void setMessageFile(MultipartFile messageFile) {
            this.messageFile = messageFile;
        }

		public MultipartFile getFaviconFile() {
			return faviconFile;
		}

		public void setFaviconFile(MultipartFile faviconFile) {
			this.faviconFile = faviconFile;
		}

		public MultipartFile getIconFile() {
			return iconFile;
		}

		public void setIconFile(MultipartFile iconFile) {
			this.iconFile = iconFile;
		}
    }
}

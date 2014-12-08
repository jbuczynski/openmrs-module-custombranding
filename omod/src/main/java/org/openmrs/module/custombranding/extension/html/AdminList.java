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
package org.openmrs.module.custombranding.extension.html;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openmrs.module.Extension;
import org.openmrs.module.web.extension.AdministrationSectionExt;
import org.openmrs.util.OpenmrsClassLoader;

/**
 * This class defines the links that will appear on the administration page under the
 * "custombranding.title" heading. 
 */
public class AdminList extends AdministrationSectionExt {
	
	private static String requiredPrivileges = "View Custom Branding";

	/**
	 * @see AdministrationSectionExt#getMediaType()
	 */
	@Override
	public Extension.MEDIA_TYPE getMediaType() {
		return Extension.MEDIA_TYPE.html;
	}
	
	/**
	 * @see AdministrationSectionExt#getTitle()
	 */
	@Override
	public String getTitle() {
		return "custombranding.title";
	}
	
	/**
	 * @see AdministrationSectionExt#getRequiredPrivilege()
	 */
	@Override
	public String getRequiredPrivilege() {
		if (requiredPrivileges == null) {
			StringBuilder builder = new StringBuilder();
			requiredPrivileges = builder.toString();
		}
		
		return requiredPrivileges;
	}

	/**
	 * @see AdministrationSectionExt#getLinks()
	 */
	@Override
    public Map<String, String> getLinks() {
        HashMap<String, String> links = new LinkedHashMap<String, String>();
        links.put("/module/custombranding/custombranding.form", "custombranding.link.name");
		links.put("/module/custombranding/customizeCssEdit.form", "custombranding.link.css");
        return links;
    }
	
}

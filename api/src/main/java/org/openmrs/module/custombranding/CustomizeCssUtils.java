package org.openmrs.module.custombranding;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jakub on 25.11.14.
 */
public class CustomizeCssUtils {

	//pobieranie sciezek plików css z servera
	//pobieranie nazw plikow css

	public static String getUrl(HttpServletRequest req) {
		String reqUrl = req.getRequestURL().toString();
		String queryString = req.getQueryString();
		if (queryString != null) {
			reqUrl += "?" + queryString;
		}
		return reqUrl;
	}


}

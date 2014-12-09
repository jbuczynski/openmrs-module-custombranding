package org.openmrs.module.custombranding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.custombranding.web.controller.CustomizeCssFormController;
import org.openmrs.test.Verifies;
import org.openmrs.web.controller.encounter.LocationFormController;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

public class CustombrandingControllerTest extends BaseModuleWebContextSensitiveTest {

	protected final Log log = LogFactory.getLog(getClass());

	private CssFileService cssFileService;

	protected static final String INITIAL_FIELDS_XML = "org/openmrs/module/include/TestDataset.xml";
	/**
	 * Run this before each unit test in this class.
	 *
	 * @throws Exception
	 */
	@Before
	public void runBeforeTest() throws Exception {
		cssFileService = Context.getService(CssFileService.class);
		executeDataSet(INITIAL_FIELDS_XML);
	}

    @Ignore
	@Test
	@Verifies(value = "should fill cssFilesMap", method = "handleCssEditing( HttpServletRequest request, ModelMap model )")
	public void onSubmit_shouldNotRetireLocationIfReasonIsEmpty() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "");


		HttpServletResponse response = new MockHttpServletResponse();

		CustomizeCssFormController controller = (CustomizeCssFormController)applicationContext.getBean("locationForm");

		//ModelAndView modelAndView = controller.handleRequest(request, response);

//		// make sure an error is returned because of the empty retire reason
//		BeanPropertyBindingResult bindingResult = (BeanPropertyBindingResult) modelAndView.getModel().get(
//				"org.springframework.validation.BindingResult.location");
//		Assert.assertTrue(bindingResult.hasFieldErrors("retireReason"));
	}

}

package org.openmrs.module.custombranding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class CustombrandingServiceTest extends BaseModuleContextSensitiveTest {

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

	@Test
	public void shouldPassSavingCssFileWithNullFields() throws Exception {

		CssFile testFile = new CssFile();
		testFile.setId(1);
		testFile.setContent(null);
		testFile.setName(null);
		testFile.setNameAndPath(null);

		cssFileService.mergeCssFile(testFile);

		Assert.assertNotNull(cssFileService.getCssFile(testFile.getId()));

	}

	@Test(expected = org.hibernate.exception.ConstraintViolationException.class)
	public void shouldFailIfTwoDfferentFilesCantHaveSameNameAndPath() throws Exception {

		CssFile existingCssFile = cssFileService.getCssFile(3);

		CssFile testFile = new CssFile();
		testFile.setContent(null);
		testFile.setName(null);
		testFile.setNameAndPath(existingCssFile.getNameAndPath());

		cssFileService.mergeCssFile(testFile);
	}

	@Test
	public void shouldPassMergingTwoDifferentFilesWthSameProperties() throws Exception {

		CssFile file1 = cssFileService.getCssFile(3);

		CssFile file2 = new CssFile();

		fillCssFile(file1, file2);

		cssFileService.mergeCssFile(file1);

		cssFileService.mergeCssFile(file2);
	}

	@Test(expected = org.hibernate.NonUniqueObjectException.class)
	public void shouldFailSavingAndUpdatingMethodWhenDifferentFilesHaveSameProperties() throws Exception {

		CssFile file1 = cssFileService.getCssFile(3);

		CssFile file2 = new CssFile();

		fillCssFile(file1, file2);

		cssFileService.saveCssFile(file1);

		cssFileService.saveCssFile(file2);
	}

	private void fillCssFile(CssFile file1, CssFile file2) {

		file2.setId(file1.getId());
		file2.setName(file1.getName());
		file2.setNameAndPath(file1.getNameAndPath());
		file2.setContent(file1.getContent());
		file2.setUuid(file1.getUuid());
	}



}

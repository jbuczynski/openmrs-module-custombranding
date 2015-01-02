package org.openmrs.module.custombranding;

import org.openmrs.OpenmrsMetadata;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.OpenmrsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Defines the services provided by the CssFile Entry module
 *
 */

public interface CssFileService extends OpenmrsService {

	/**
	 * Retrieves the CssFile with the specified id
	 *
	 * @param id
	 * @return the CssFile with the specified id
	 */
	public CssFile getCssFile(Integer id);

	/**
	 * Retrieves the CssFile with the specified uuid
	 *
	 * @param uuid
	 * @return the CssFile with the given uuid
	 *
	 */
	public CssFile getCssFileByUuid(String uuid);

	/**
	 * Retrieves the CssFile with the specified name
	 *
	 * @param uuid
	 * @return the CssFile with the given name
	 *
	 */
	public CssFile getCssFileByName(String name);

	/**
	 * Retrieves the CssFile with the specified nameAndPath
	 *
	 * @param uuid
	 * @return the CssFile with the given nameAndPath
	 *
	 */
	public CssFile getCssFileByNameAndPath(String nameAndPath);

	/**
	 * Retrieves all CssFiles in the system

	 * @return a list of all CssFiles in the system
	 * @should return all CssFiles
	 */
	public List<CssFile> getAllCssFiles();

	/**
	 * Saves the specified Css file to the database
	 *
	 * @param CssFile the CssFile to save
	 * @return the CssFile saved
	 */
	public CssFile saveCssFile(CssFile file);

	/**
	 * Saves the specified Css file to the database
	 *
	 * @param CssFile the CssFile to save
	 * @return the CssFile saved
	 */
	public CssFile mergeCssFile(CssFile file);

	/**
	 * Purges the specified CssFile from the database
	 *
	 * @param CssFile the CssFile to purge
	 */
	public void purgeCssFile(CssFile CssFile);


}

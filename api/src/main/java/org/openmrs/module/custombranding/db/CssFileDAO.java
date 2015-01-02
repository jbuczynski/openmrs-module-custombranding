package org.openmrs.module.custombranding.db;

import org.openmrs.OpenmrsMetadata;
import org.openmrs.OpenmrsObject;
import org.openmrs.module.custombranding.CssFile;

import java.util.List;

/**
 * Defines the methods that must be implemented by the Data Access Object
 */
public interface CssFileDAO {

	/**
	 * Retrieves the CssFile referenced by id
	 * 
	 * @param id
	 * @return CssFile
	 */
    public CssFile getCssFile(Integer id);
    
	/**
	 * Retrieves the CssFile referenced by uuid
	 * 
	 * @param uuid
	 * @return CssFile
	 */
	public CssFile getCssFileByUuid(String uuid) ;


	/**
	 * Retrieves the CssFile referenced by name
	 *
	 * @param name
	 * @return CssFile
	 */
	public CssFile getCssFileByName(String name) ;


    /**
     * Retrieves the CssFile referenced by nameAndPath
     *
     * @param nameAndPath
     * @return CssFile
     */
    public CssFile getCssFileByNameAndPath(String nameAndPath) ;


    /**
     * Saves an CssFile to the database
     * 
     * @param CssFile
     * @return
     */
    public CssFile saveCssFile(CssFile CssFile);

	public CssFile mergeCssFile(CssFile CssFile);

	/**
     * Deletes an CssFile from the database
     * 
     * @param CssFile
     */
    public void deleteCssFile(CssFile CssFile);

    /**
     * Returns all CssFiles in the database
     * @return
     */
    public List<CssFile> getAllCssFiles();

}

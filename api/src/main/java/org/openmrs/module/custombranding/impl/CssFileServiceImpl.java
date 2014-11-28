package org.openmrs.module.custombranding.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.OpenmrsMetadata;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.custombranding.CssFile;
import org.openmrs.module.custombranding.CssFileService;
import org.openmrs.module.custombranding.db.CssFileDAO;

import java.util.List;

/**
 * Standard implementation of the CssFileService
 */
public class CssFileServiceImpl extends BaseOpenmrsService implements CssFileService {

	protected final Log log = LogFactory.getLog(getClass());

	private CssFileDAO dao;

	/*
	 * Optimization to minimize database hits for the needs-name-and-description-migration check.
	 * Once all forms have been migrated, we no longer need to hit the database on further checks
	 * because there is no way to add more un-migrated forms. (In theory someone could add some 
	 * directly to the database, so we use an instance variable here that will be reset whenever
	 * the system is restarted or the module is reloaded.
	 */
	private boolean nameAndDescriptionMigrationDone = false;

	/**
	 * Sets the DAO
	 *
	 * @param dao
	 */
	public void setDao(CssFileDAO dao) {
		this.dao = dao;
	}

	@Override
	public CssFile getCssFile(Integer id) {
		return dao.getCssFile(id);
	}

	@Override
	public CssFile getCssFileByUuid(String uuid)  {
		return dao.getCssFileByUuid(uuid);
	}

	@Override
	public CssFile saveCssFile(CssFile CssFile) {
		return dao.saveCssFile(CssFile);
	}

	@Override
	public void purgeCssFile(CssFile CssFile) {
		dao.deleteCssFile(CssFile);
	}

	@Override
	public List<CssFile> getAllCssFiles() {
		return dao.getAllCssFiles();
	}

	@Override
	public OpenmrsObject getItemByUuid(Class<? extends OpenmrsObject> type, String uuid) {
		return dao.getItemByUuid(type, uuid);
	}

	@Override
	public OpenmrsObject getItemById(Class<? extends OpenmrsObject> type, Integer id) {
		return dao.getItemById(type, id);
	}

	@Override
	public OpenmrsObject getItemByName(Class<? extends OpenmrsMetadata> type, String name) {
		return dao.getItemByName(type, name);
	}

}

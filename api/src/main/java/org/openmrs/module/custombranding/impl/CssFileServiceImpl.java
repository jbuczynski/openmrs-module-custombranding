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

	private boolean nameAndDescriptionMigrationDone = false;

	/**
	 * Sets the DAO
	 *
	 * @param dao
	 */
	public void setDao(CssFileDAO dao) {
		this.dao = dao;
	}

	public CssFileDAO getDao() {
		return dao;
	}

	@Override
	public CssFile getCssFile(Integer id) {
		return dao.getCssFile(id);
	}

	@Override
	public CssFile getCssFileByName(String name) {
		return dao.getCssFileByName(name);
	}

    @Override
    public CssFile getCssFileByNameAndPath(String nameAndPath) {
        return dao.getCssFileByNameAndPath(nameAndPath);
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
	public CssFile mergeCssFile(CssFile CssFile) {
		return dao.mergeCssFile(CssFile);
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

package org.openmrs.module.custombranding.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.custombranding.CssFile;
import org.openmrs.module.custombranding.CssFileService;
import org.openmrs.module.custombranding.db.CssFileDAO;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(readOnly = true)
	public CssFile getCssFile(Integer id) {
		return dao.getCssFile(id);
	}

	@Override
	@Transactional(readOnly = true)
	public CssFile getCssFileByName(String name) {
		return dao.getCssFileByName(name);
	}

    @Override
	@Transactional(readOnly = true)
    public CssFile getCssFileByNameAndPath(String nameAndPath) {
        return dao.getCssFileByNameAndPath(nameAndPath);
    }

	@Override
	@Transactional(readOnly = true)
	public CssFile getCssFileByUuid(String uuid)  {
		return dao.getCssFileByUuid(uuid);
	}

	@Override
	@Transactional
	public CssFile saveCssFile(CssFile CssFile) {
		return dao.saveCssFile(CssFile);
	}

	@Override
	@Transactional
	public CssFile mergeCssFile(CssFile CssFile) {
		return dao.mergeCssFile(CssFile);
	}

	@Override
	@Transactional
	public void purgeCssFile(CssFile CssFile) {
		dao.deleteCssFile(CssFile);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CssFile> getAllCssFiles() {
		return dao.getAllCssFiles();
	}

}

package org.openmrs.module.custombranding.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.openmrs.module.custombranding.CssFile;
import org.openmrs.module.custombranding.db.CssFileDAO;

import java.util.List;


/**
 * Hibernate implementation of the Data Access Object
 */
public class HibernateCssFileDAO implements CssFileDAO {

	private static Log log = LogFactory.getLog(HibernateCssFileDAO.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public CssFile getCssFile(Integer id) {
		return (CssFile) sessionFactory.getCurrentSession().get(CssFile.class, id);
	}

	@Override
	public CssFile getCssFileByUuid(String uuid)  {
		Query q = sessionFactory.getCurrentSession().createQuery("from CssFile f where f.uuid = :uuid");
		return (CssFile) q.setString("uuid", uuid).uniqueResult();
	}

	@Override
	public CssFile getCssFileByName(String name)  {
		Query q = sessionFactory.getCurrentSession().createQuery("from CssFile f where f.name = :name");
		return (CssFile) q.setString("name", name).uniqueResult();
	}

    @Override
    public CssFile getCssFileByNameAndPath(String nameAndPath)  {
        Query q = sessionFactory.getCurrentSession().createQuery("from CssFile f where f.nameAndPath = :nameAndPath");
        return (CssFile) q.setString("nameAndPath", nameAndPath).uniqueResult();
    }

	@Override
	public CssFile saveCssFile(CssFile CssFile) {
		sessionFactory.getCurrentSession().saveOrUpdate(CssFile);
		return CssFile;
	}

	@Override
	public CssFile mergeCssFile(CssFile CssFile) {
		sessionFactory.getCurrentSession().merge(CssFile); //merging two different objects with same id in session
		return CssFile;
	}

	@Override
	public void deleteCssFile(CssFile CssFile) {
		sessionFactory.getCurrentSession().delete(CssFile);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CssFile> getAllCssFiles() {
		return sessionFactory.getCurrentSession().createCriteria(CssFile.class).list();
	}

}

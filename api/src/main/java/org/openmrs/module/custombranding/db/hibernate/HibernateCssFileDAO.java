package org.openmrs.module.custombranding.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.openmrs.OpenmrsMetadata;
import org.openmrs.OpenmrsObject;
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
	public CssFile saveCssFile(CssFile CssFile) {
		sessionFactory.getCurrentSession().saveOrUpdate(CssFile);
		return CssFile;
	}

	@Override
	public void deleteCssFile(CssFile CssFile) {
		sessionFactory.getCurrentSession().delete(CssFile);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CssFile> getAllCssFiles() {
		Query query = sessionFactory.getCurrentSession().createQuery("from CssFile order by form.name asc");
		return (List<CssFile>) query.list();
	}

	@Override
	public OpenmrsObject getItemByUuid(Class<? extends OpenmrsObject> type, String uuid) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(type);
			criteria.add(Expression.eq("uuid", uuid));
			OpenmrsObject result = (OpenmrsObject) criteria.uniqueResult();
			return result;
		}
		catch(Exception e) {
			log.error("Error fetching item by uuid:" + e);
			return null;
		}
	}


	@Override
	public OpenmrsObject getItemById(Class<? extends OpenmrsObject> type, Integer id) {
		try {
			String idProperty = sessionFactory.getClassMetadata(type).getIdentifierPropertyName();
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(type);
			criteria.add(Expression.eq(idProperty, id));
			OpenmrsObject result = (OpenmrsObject) criteria.uniqueResult();
			return result;
		}
		catch(Exception e) {
			log.error("Error fetching item by id:" + e);
			return null;
		}
	}

	@Override
	public OpenmrsObject getItemByName(Class<? extends OpenmrsMetadata> type, String name) {
		// we use a try/catch here to handle oddities like "Role" which don't have a directly-referenceable name property
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(type);
			criteria.add(Expression.eq("name", name));
			OpenmrsObject result = (OpenmrsObject) criteria.uniqueResult();
			return result;
		}
		catch(Exception e) {
			log.error("Error fetching item by name:" + e);
			return null;
		}
	}
}

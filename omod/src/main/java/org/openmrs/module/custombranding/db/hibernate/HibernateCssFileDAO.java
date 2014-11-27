package org.openmrs.module.custombranding.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.custombranding.db.CssFileDAO;

/**
 * Hibernate implementation of the Data Access Object
 */
public class HibernateCssFileDAO implements CssFileDAO {

	private static Log log = LogFactory.getLog(HibernateCssFileDAO.class);
	
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    

}

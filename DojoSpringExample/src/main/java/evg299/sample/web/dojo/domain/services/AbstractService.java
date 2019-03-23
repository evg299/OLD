package evg299.sample.web.dojo.domain.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Evgeny on 06.01.2015.
 */
public abstract class AbstractService
{
    @Autowired
    protected SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public void save(Object entity) {
        Session session = getCurrentSession();
        session.save(entity);
    }

    @Transactional
    public void update(Object entity) {
        Session session = getCurrentSession();
        session.update(entity);
    }
    @Transactional
    public void saveOrUpdate(Object entity) {
        Session session = getCurrentSession();
        session.saveOrUpdate(entity);
    }

    @Transactional
    public void delete(Object entity) {
        Session session = getCurrentSession();
        session.delete(entity);
    }
}

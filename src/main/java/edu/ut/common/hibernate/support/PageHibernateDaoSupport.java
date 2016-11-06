package edu.ut.common.hibernate.support;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public abstract class PageHibernateDaoSupport<T extends Serializable> extends HibernateDaoSupport implements CrudDao<T> {

    @Autowired
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    private Class<T> clazz;

    protected final void setClass(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public final T get(String id) {
        return getHibernateTemplate().get(clazz, id);
    }

    public String save(T entity) {
        return (String)getHibernateTemplate().save(entity);
    }

    public void update(T entity) {
        getHibernateTemplate().update(entity);
    }

    public void delete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    public void delete(String id) {
        delete(get(id));
    }

    public List<T> findAll() {
        return (List<T>) getHibernateTemplate().find("from " + clazz.getName());
    }

    public List<T> findAll(String orderby, boolean desc) {
        String hql = "from " + clazz.getName() + " order by " + orderby;
        if(desc) {
            hql += " desc";
        }
        return (List<T>) getHibernateTemplate().find(hql);
    }

}
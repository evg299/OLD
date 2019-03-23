package evg299.sample.web.dojo.domain.services;

import evg299.sample.web.dojo.domain.entities.User;
import evg299.sample.web.dojo.util.RestUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Evgeny on 06.01.2015.
 */
@Service
public class UserService extends AbstractService
{
    @Transactional(readOnly = true)
    public List<User> getAll()
    {
        return getCurrentSession()
                .createCriteria(User.class)
                .list();
    }

    @Transactional(readOnly = true)
    public Long countAll()
    {
        return (Long) getCurrentSession().createCriteria(User.class)
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<User> get(RestUtil.Range range)
    {
        return getCurrentSession().createCriteria(User.class)
                .setFirstResult(range.getFirst())
                .setMaxResults(range.getLast() - range.getFirst())
                .list();
    }

    @Transactional(readOnly = true)
    public List<User> get(List<RestUtil.FilterContainer> filter, List<RestUtil.SortContainer> sort, RestUtil.Range range)
    {
        Criteria c = getCurrentSession().createCriteria(User.class);
        for (RestUtil.FilterContainer filterContainer : filter)
        {
            c.add(Restrictions.eq(filterContainer.getField(), filterContainer.getValue()));
        }

        for (RestUtil.SortContainer sortContainer : sort)
        {
            if (sortContainer.isAsc())
                c.addOrder(Order.asc(sortContainer.getField()));
            else
                c.addOrder(Order.desc(sortContainer.getField()));
        }

        if (null != range)
            c.setFirstResult(range.getFirst())
                    .setMaxResults(range.getLast() - range.getFirst());

        return c.list();
    }

    @Transactional(readOnly = true)
    public Long count(List<RestUtil.FilterContainer> filter, List<RestUtil.SortContainer> sort)
    {
        Criteria c = getCurrentSession().createCriteria(User.class);
        for (RestUtil.FilterContainer filterContainer : filter)
        {
            c.add(Restrictions.eq(filterContainer.getField(), filterContainer.getValue()));
        }

        for (RestUtil.SortContainer sortContainer : sort)
        {
            if (sortContainer.isAsc())
                c.addOrder(Order.asc(sortContainer.getField()));
            else
                c.addOrder(Order.desc(sortContainer.getField()));
        }

        return (Long) c.setProjection(Projections.rowCount()).uniqueResult();
    }

    @Transactional(readOnly = true)
    public User getById(long id)
    {
        return (User) getCurrentSession().get(User.class, id);
    }
}

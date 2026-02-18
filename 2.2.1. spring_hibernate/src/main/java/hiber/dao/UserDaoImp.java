package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserByCarModelAndSeries(String model, int series) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("SELECT u FROM User u JOIN u.car c WHERE c.model=:model AND c.series=:series");
        query.setParameter("model", model);
        query.setParameter("series", series);
        List<User> userList = query.getResultList();
        if (!userList.isEmpty()) {
            return userList.get(0);
        } else {
            throw new RuntimeException("По данным параметрам нет пользователей");
        }
    }
}


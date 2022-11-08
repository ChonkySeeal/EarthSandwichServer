package com.EarthSandwich.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.EarthSandwich.entity.User;

@Repository
public class UserDAOImp implements UserDAO {

	private EntityManager entityManager;

	@Autowired
	public UserDAOImp(EntityManager entitymanager) {
		this.entityManager = entitymanager;
	}

	@Override
	public List<User> findAll() {

		Session current = entityManager.unwrap(Session.class);

		Query<User> query = current.createQuery("from User", User.class);

		List<User> users = query.getResultList();

		return users;
	}

	@Override
	public User findById(int id) {
		Session current = entityManager.unwrap(Session.class);

		User user = current.get(User.class, id);

		return user;
	}

	@Override
	public void save(User user) {

		Session current = entityManager.unwrap(Session.class);

		current.saveOrUpdate(user);

	}

	@Override
	public void deleteById(int id) {
		Session current = entityManager.unwrap(Session.class);

		Query query = current.createQuery("delete from User where id=:userId");
		query.setParameter("userId", id);
		query.executeUpdate();

	}

}

package com.EarthSandwich.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
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
	public User findById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		User user = currentSession.get(User.class, id);

		return user;
	}

	@Override
	public void save(User user) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(user);

	}

	@Override
	public void deleteById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query query = currentSession.createQuery("delete from User where id=:userId");
		query.setParameter("userId", id);
		query.executeUpdate();

	}

	@Override
	public User findByName(String name) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query query = currentSession.createQuery("from User where username=:username");
		query.setParameter("username", name);
		User result = (User) query.getResultStream().findFirst().orElse(null);

		return result;
	}

	@Override
	public User findByEmail(String email) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query query = currentSession.createQuery("from User where email=:email", User.class);
		query.setParameter("email", email);
		User result = (User) query.getResultStream().findFirst().orElse(null);

		return result;
	}

	@Override
	public boolean findExistUser(String email, String pass) {
		Session currentSession = entityManager.unwrap(Session.class);

		boolean exists = currentSession.createQuery("select 1 from User Where email=:email AND password=:password")
				.setParameter("email", email).setParameter("password", pass).uniqueResult() != null;

		return exists;
	}

}

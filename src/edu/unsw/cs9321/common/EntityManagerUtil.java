package edu.unsw.cs9321.common;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
	private static final EntityManagerFactory entityManagerFactory;
	static {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("assign2");
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
}

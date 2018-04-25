package com.bfwg.entities;


import java.util.List;

public interface UserEntityPersister {

	void save(UserEntity UserEntityWithId);

	UserEntity findByEmail(String email) ;

	boolean delete(UserEntity UserEntity);

	// should be able to provide the id, and get the object from the store.
	UserEntity fromPersistenceStore(String id);

	UserEntity findByUsername(String username);

	List<UserEntity> findAll();
}

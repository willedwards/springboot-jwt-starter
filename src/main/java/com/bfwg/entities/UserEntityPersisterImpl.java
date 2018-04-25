package com.bfwg.entities;

import com.googlecode.objectify.ObjectifyService;

import java.util.Collection;
import java.util.List;

public class UserEntityPersisterImpl extends ObjectifyDao implements UserEntityPersister {

	public UserEntityPersisterImpl(){
		ObjectifyService.register(UserEntity.class);
	}

	@Override
	public void save(UserEntity UserEntityWithId){
		ofyCreate(UserEntityWithId);
	}


	@Override
	public UserEntity findByEmail(String email)
	{
		List<UserEntity> list = ofyRetrieveAllByFilter(UserEntity.class,"email",email,"-timestamp");
		if(list == null || list.size() == 0)
			return null;
		return list.get(0);
	}


	@Override
	public boolean delete(UserEntity userEntity) {
		ofyDelete(UserEntity.class, userEntity.id);
		return true;
	}

	@Override
	public UserEntity fromPersistenceStore(String id)  {
		return ofyRetrieve(UserEntity.class, id);
	}

	@Override
	public UserEntity findByUsername(String username) {
		return findByEmail(username);
	}

	@Override
	public List<UserEntity> findAll() {
		return 	ofyRetrieveAll(UserEntity.class, null);

	}


}

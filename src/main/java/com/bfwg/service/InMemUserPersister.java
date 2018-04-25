package com.bfwg.service;

import com.bfwg.entities.UserEntity;
import com.bfwg.entities.UserEntityPersister;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemUserPersister implements UserEntityPersister {

    Map<String,UserEntity> map = new ConcurrentHashMap<>();

    @Override
    public void save(UserEntity userEntity) {
        map.put(userEntity.id,userEntity);
    }

    @Override
    public UserEntity findByEmail(String email) {
        for(UserEntity e : map.values()){
            if(e.email.equals(email)){
                return e;
            }
        }
        return null;
    }

    @Override
    public boolean delete(UserEntity UserEntity) {
        return true;
    }

    @Override
    public UserEntity fromPersistenceStore(String id) {
        return map.get(id);
    }

    @Override
    public UserEntity findByUsername(String username) {
        for(UserEntity e : map.values()){
            if(e.username.equals(username)){
                return e;
            }
        }
        return null;
    }

    @Override
    public List<UserEntity> findAll() {
        List v = new ArrayList();

        for(UserEntity u : map.values()){
            v.add(u);
        }
        return v;
    }
}

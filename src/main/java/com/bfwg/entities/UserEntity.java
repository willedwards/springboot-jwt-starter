package com.bfwg.entities;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Cache
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class UserEntity
{
	@Id
	public String id;
	@Index
	public String email;

	public String name;

	public String password;



	boolean enabled;

	@Index
	public String username;

	long lastPasswordResetDate;

	public String role;

	@Index
	public long timestamp;
	public UserEntity()
    {
        //required for objectify
	}
}

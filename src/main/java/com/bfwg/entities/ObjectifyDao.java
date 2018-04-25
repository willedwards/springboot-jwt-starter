package com.bfwg.entities;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.List;
import java.util.Map;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ObjectifyDao {

	public <T> Key<T> ofyCreate(T t) {
		return ObjectifyService.ofy().save().entity(t).now();
	}

	public <T> void ofyDelete(Class<T> clazz, String id)  {
		ofy().delete().key(Key.create(clazz, id)).now();
	}

	public <T> T ofyRetrieve(Class<T> clazz, String id)  {
		return ofy().load().key(Key.create(clazz, id)).now();
	}

	public <T> Key<T> ofyRetrieveKey(Class<T> clazz, String id) {
		return ofy().load().type(clazz).filterKey(id).keys().first().now();
	}

	public <T> List<Key<T>> ofyRetrieveKeysByFilter(Class<T> clazz,
	        String property, Object value) {
		return ofy().load().type(clazz).filter(property, value).keys().list();
	}

	public <T> List<T> ofyRetrieveAll(Class<T> clazz, String order) {
		Query<T> query = ofy().load().type(clazz);
		if (order != null) {
			query = query.order(order);
		}
		return query.list();
	}

	public <T> List<T> ofyRetrieveAllByFilter(Class<T> clazz, String property,
	        Object value, String order) {
		Query<T> query = ofy().load().type(clazz).filter(property, value);
		if (order != null) {
			query = query.order(order);
		}
		return query.list();
	}

	public <T> List<T> ofyRetrieveAllBy2Properties(Class<T> clazz,
                                                   String property1,Object value1,
                                                   String property2, Object value2,
                                                   String order) {
		Query<T> query = ofy().load().type(clazz).filter(property1, value1).filter(property2,value2);
		if (order != null) {
			query = query.order(order);
		}
		return query.list();
	}

	public <T> List<T> ofyRetrieveAllBy3Properties(Class<T> clazz,
                                                   String property1,Object value1,
                                                   String property2, Object value2,
                                                   String property3, Object value3,
                                                   String order) {
		Query<T> query = ofy().load().type(clazz).filter(property1, value1).filter(property2,value2).filter(property3,value3);
		if (order != null) {
			query = query.order(order);
		}
		return query.list();
	}

	public <T> List<T> ofyRetrieveAllByFilter(Class<T> clazz, Map<String, Object> filters, String order) {
		Query<T> query = ofy().load().type(clazz);

		for (String prop : filters.keySet()) {
			query = query.filter(prop, filters.get(prop));
		}
		if (order != null) {
			query = query.order(order);
		}
		return query.list();
	}

	public <T> T ofyRetrieveMostRecent(Class<T> clazz, String property, Object value, String order)
 	{
		Query<T> query = ofy().load().type(clazz).filter(property, value).order(order).limit(1);
		List<T> list = query.list();
		if(list.isEmpty())
			return null;

		return list.get(0);
	}
}

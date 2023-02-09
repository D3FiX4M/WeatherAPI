package com.practice.Weather.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Location.class)
public abstract class Location_ {

	public static volatile SingularAttribute<Location, String> country;
	public static volatile SingularAttribute<Location, Double> latitude;
	public static volatile SingularAttribute<Location, String> name;
	public static volatile SingularAttribute<Location, Long> id;
	public static volatile SingularAttribute<Location, User> user;
	public static volatile SingularAttribute<Location, Double> longitude;

	public static final String COUNTRY = "country";
	public static final String LATITUDE = "latitude";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String USER = "user";
	public static final String LONGITUDE = "longitude";

}


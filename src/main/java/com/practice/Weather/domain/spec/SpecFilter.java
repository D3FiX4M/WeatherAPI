package com.practice.Weather.domain.spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class SpecFilter<E, D> {

    public  Specification<E> createSpec(String key, Object value) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(key), value);
    }

    public  Specification<E> getSpecFromFilter(D request) throws IllegalAccessException {

        Specification<E> spec = Specification.where(null);

        for (Field field : request.getClass().getDeclaredFields())
        {
            field.setAccessible(true);

            if (field.get(request)!=null){
                spec = spec.and(createSpec(field.getName(), field.get(request)));
            }

        }
        return spec;
    }



}

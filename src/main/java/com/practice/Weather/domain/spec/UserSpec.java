package com.practice.Weather.domain.spec;


import com.practice.Weather.domain.entity.User;
import com.practice.Weather.domain.entity.User_;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@UtilityClass
public class UserSpec {

    public Specification<User> usernameEq(String username){

        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get(User_.username), username);
    }

}

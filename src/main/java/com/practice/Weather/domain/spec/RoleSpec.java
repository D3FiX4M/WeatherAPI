package com.practice.Weather.domain.spec;

import com.practice.Weather.domain.entity.Role;
import com.practice.Weather.domain.entity.Role_;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class RoleSpec {

    public Specification<Role> nameEq(String name){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Role_.name), name));
    }
}

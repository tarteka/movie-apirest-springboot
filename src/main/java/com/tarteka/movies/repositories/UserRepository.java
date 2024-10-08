package com.tarteka.movies.repositories;

import com.tarteka.movies.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{

    Optional<UserEntity> findUserEntitiesByUsername(String username);
};

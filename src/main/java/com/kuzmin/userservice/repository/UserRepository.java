package com.kuzmin.userservice.repository;

import com.kuzmin.userservice.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, ObjectId> {
    User findByUsername(String username);
}

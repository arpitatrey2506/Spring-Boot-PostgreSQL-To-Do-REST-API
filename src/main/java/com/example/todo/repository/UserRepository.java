package com.example.todo.repository;

import com.example.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    @Query("SELECT u FROM User u WHERE " +
            "CAST(u.id AS string) = :identifier OR " +
            "LOWER(u.name) = LOWER(:identifier) OR " +
            "LOWER(u.address) = LOWER(:identifier) OR " +
            "LOWER(u.email) = LOWER(:identifier)")
    Optional<User> findByUserDetail(@Param("identifier") String identifier);
}
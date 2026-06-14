package com.example.todo.repository;

import com.example.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users (id, name, address, email) VALUES (:id, :name, :address, :email)", nativeQuery = true)
    void insertUser(@Param("id") Integer id, 
                    @Param("name") String name, 
                    @Param("address") String address, 
                    @Param("email") String email);
}
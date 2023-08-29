package com.shivsundar.repositories;

import com.shivsundar.models.db.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

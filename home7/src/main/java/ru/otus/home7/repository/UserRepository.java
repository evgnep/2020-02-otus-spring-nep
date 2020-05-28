package ru.otus.home7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.home7.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String name);
}

package ru.aicontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.aicontest.domain.BaseUserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<BaseUserEntity, Long> {

    @Query(value = "SELECT * FROM base_user WHERE login=?1 AND password=?2", nativeQuery = true)
    BaseUserEntity findByLoginAndPassword(String login, String password);

    @Modifying
    @Transactional
    @Query(value = "SELECT 1 FROM create_base_user(?1, ?2, ?3)", nativeQuery = true)
    List<Integer> createBaseUser(String login, String password, String email);

    int countByLogin(String login);

    List<BaseUserEntity> findAllByOrderByLogin();

    Optional<BaseUserEntity> findByLogin(String login);


}

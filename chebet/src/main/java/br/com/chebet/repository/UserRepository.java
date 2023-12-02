package br.com.chebet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.chebet.model.User;
import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String email);

    public User findByCpf(String cpf);

    public User findByPhoneNumber(String phoneNumber);

    public List<User> findAll();

    @Modifying
    @Transactional
    @Query(value = "CALL SoftDeleteUser(:id)", nativeQuery = true)
    void softDeleteUser(@Param("id") int id);

}

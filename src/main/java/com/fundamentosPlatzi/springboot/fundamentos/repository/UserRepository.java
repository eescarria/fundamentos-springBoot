package com.fundamentosPlatzi.springboot.fundamentos.repository;

import com.fundamentosPlatzi.springboot.fundamentos.dto.UserDto;
import com.fundamentosPlatzi.springboot.fundamentos.entity.User;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM User WHERE email = ?1", nativeQuery = true)
    Optional<String> findByUserEmail(String email);

    @Query(value = "select u from User u where u.name like ?1%")
    List<User> findAndSort(String name, Sort sort);

    List<User> findByName(String name);

    Optional<User> findByEmailAndName(String email, String name);

    List<User> findByNameLike(String name);

    List<User> findByNameOrEmail(String name, String email);

    List<User> findByBirthDateBetween(LocalDate begin, LocalDate end);

    List<User> findByNameLikeOrderByIdDesc(String name);
    List<User> findByNameContainingOrderByIdDesc(String name);

    @Query("SELECT NEW com.fundamentosPlatzi.springboot.fundamentos.dto.UserDto(u.id, u.name, u.birthDate)" +
    "FROM User u " +
    "WHERE u.birthDate=:parametroFecha " +
    "AND u.email=:parametroEmail")
    Optional<UserDto> getAllByBirthDateAndEmail(@Param("parametroFecha") LocalDate date,
                                                @Param("parametroEmail") String email);

    List<User> findAll();
}

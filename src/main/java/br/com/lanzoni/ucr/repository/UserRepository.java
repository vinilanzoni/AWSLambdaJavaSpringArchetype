package br.com.lanzoni.ucr.repository;

import br.com.lanzoni.ucr.domain.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface UserRepository extends CrudRepository <User, String> {
    Optional<User> findByEmail(String email);
}

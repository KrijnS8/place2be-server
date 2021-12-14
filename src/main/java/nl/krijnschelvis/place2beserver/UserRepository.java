package nl.krijnschelvis.place2beserver;

import org.springframework.data.repository.CrudRepository;

import nl.krijnschelvis.place2beserver.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    boolean existsByEmailAndPassword(String email, String password);
    User findUserByEmail(String email);
    boolean existsByEmail(String email);
}

package tz.or.orci.orcidutyroster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tz.or.orci.orcidutyroster.model.entities.Token;
import tz.or.orci.orcidutyroster.model.entities.User;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    List<Token> findByUserAndRevokedFalse(User user);

    boolean existsByTokenAndRevokedTrue(String token);
}
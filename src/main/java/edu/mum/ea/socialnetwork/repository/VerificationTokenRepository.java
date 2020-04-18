package edu.mum.ea.socialnetwork.repository;

import edu.mum.ea.socialnetwork.domain.User;
import edu.mum.ea.socialnetwork.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository  extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

}

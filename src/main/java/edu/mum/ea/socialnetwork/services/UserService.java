package edu.mum.ea.socialnetwork.services;

import edu.mum.ea.socialnetwork.domain.User;
import edu.mum.ea.socialnetwork.domain.VerificationToken;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User save(User user);

    User register(User user);

    User confirmRegister(User user);

    User findUserById(Long id);

    User findUserByName(String name);

    Optional<User> findByUsername(String username);

    //used in Following Controller to do the follow task
    User rawSave(User user);


    User update(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    User forgetPassword(User user);

    void resetPassword(User user);

    VerificationToken generateNewVerificationToken(String existingToken);
}

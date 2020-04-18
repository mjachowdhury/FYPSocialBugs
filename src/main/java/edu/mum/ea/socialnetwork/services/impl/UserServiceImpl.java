package edu.mum.ea.socialnetwork.services.impl;

import edu.mum.ea.socialnetwork.domain.Profile;
import edu.mum.ea.socialnetwork.domain.User;
import edu.mum.ea.socialnetwork.domain.VerificationToken;
import edu.mum.ea.socialnetwork.repository.ProfileRepository;
import edu.mum.ea.socialnetwork.repository.UserRepository;
import edu.mum.ea.socialnetwork.repository.VerificationTokenRepository;
import edu.mum.ea.socialnetwork.services.MailService;
import edu.mum.ea.socialnetwork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private Environment env;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username:" + username + " not found!"));


        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
//        String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
        String[] roles = new String[1];
        roles[0] = user.getRole().toString();
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);
        return authorities;
    }

    public User save(User user) {
        String pass = user.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(pass));
        user.getProfile().setJoinDate(LocalDate.now());
        return userRepository.save(user);
    }


    public User register(User user) {
        user.setEnabled(false);
        user = save(user);

        String token = UUID.randomUUID().toString();
        createVerificationToken(user, token);

        sendConfirmationMail(user, token);

        return user;
    }

    private void sendConfirmationMail(User user, String token) {
        String subject = messageSource.getMessage("register.email.subject", null, LocaleContextHolder.getLocale());
        String confirmationUrl = env.getProperty("app.base.url") + "/register/confirm?token=" + token;
        System.out.println("Registration confirmation link: " + confirmationUrl);
        String message = messageSource.getMessage("register.email.body", new Object[] {confirmationUrl}, LocaleContextHolder.getLocale());

        mailService.sendEmail(user, subject, message);
    }

    public User confirmRegister(User user) {
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User findUserByName(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username:" + username + " not found!"));
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt;
    }

    @Override
    public User rawSave(User user){
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }


    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public User forgetPassword(User user) {
        String token = UUID.randomUUID().toString();
        createVerificationToken(user, token);

        String subject = messageSource.getMessage("forget.password.email.subject", null, LocaleContextHolder.getLocale());
        String passwordResetUrl = env.getProperty("app.base.url") + "/forgetPassword/reset?token=" + token;
        System.out.println("Password rest link: " + passwordResetUrl);
        String message = messageSource.getMessage("forget.password.email.body", new Object[] {passwordResetUrl}, LocaleContextHolder.getLocale());

        mailService.sendEmail(user, subject, message);

        return user;
    }

    @Override
    public void resetPassword(User user) {
        String pass = user.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(pass));

        userRepository.save(user);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingToken) {
        VerificationToken tokenDb = tokenRepository.findByToken(existingToken);

        if(tokenDb != null) {
            User user = tokenDb.getUser();
            String newToken = UUID.randomUUID().toString();
            tokenDb.setToken(newToken);
            tokenDb.setExpiryDate(tokenDb.calculateExpiryDate(tokenDb.EXPIRATION));
            tokenRepository.save(tokenDb);

            sendConfirmationMail(user, tokenDb.getToken());

            return tokenDb;
        }

        return null;
    }
}
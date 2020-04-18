package edu.mum.ea.socialnetwork.controller;

import edu.mum.ea.socialnetwork.config.CaptchaSettings;
import edu.mum.ea.socialnetwork.domain.*;
import edu.mum.ea.socialnetwork.services.CaptchaService;
import edu.mum.ea.socialnetwork.services.MailService;
import edu.mum.ea.socialnetwork.services.ProfileService;
import edu.mum.ea.socialnetwork.services.UserService;
import edu.mum.ea.socialnetwork.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ProfileService profileService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MailService mailService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private CaptchaSettings captchaSettings;

    @GetMapping("/login")
    public String login(@ModelAttribute("newUser") User user, Model model) {
        model.addAttribute("siteKey", captchaSettings.getSite());
        return "login";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User user,
                           BindingResult bindingResult,
                           Model model,
                           HttpServletRequest request,
                           @RequestParam(name="g-recaptcha-response") String recaptchaResponse) {

        String ip = request.getRemoteAddr();
        String captchaVerifyMessage = captchaService.verifyRecaptcha(ip, recaptchaResponse);

        if (!StringUtils.isEmpty(captchaVerifyMessage)) {
            model.addAttribute("title", "Invalid Captcha!");
            model.addAttribute("message", "Invalid Captcha");
            return "message";
        }

        Profile existingProfile = profileService.findByEmail(user.getProfile().getEmail());
        if (existingProfile != null) {
            bindingResult.rejectValue("profile.email", "profile.email.exist");
        }

        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            bindingResult.rejectValue("username", "username.exist");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("siteKey", captchaSettings.getSite());
            return "login";
        }

        user.setRole(Role.ROLE_USER);
        userService.register(user);

        String message = messageSource.getMessage("register.sen.email.message", new Object[] {user.getProfile().getEmail()}, LocaleContextHolder.getLocale());
        model.addAttribute("title", "Registration Successful!");
        model.addAttribute("message", message);

        return "message";
    }

    // this method redirect request to the login page with get method which happen after changing the language
    @GetMapping("/register")
    public String redirect(@Valid @ModelAttribute("newUser") User user, BindingResult bindingResult) {
        return "login";
    }

    @GetMapping("/register/confirm")
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messageSource.getMessage("register.message.invalidToken", null, LocaleContextHolder.getLocale());
            model.addAttribute("title", "Error!");
            model.addAttribute("message", message);
            return "message";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = messageSource.getMessage("register.message.expired", null, LocaleContextHolder.getLocale());
            model.addAttribute("title", "Token Expired!");
            model.addAttribute("message", messageValue);
            model.addAttribute("token", token);

            return "registrationFailed";
        }

        userService.confirmRegister(user);

        return "redirect:/login";
    }

    @GetMapping("/register/resendRegistrationToken")
    public String resendRegistrationToken (Model model, @RequestParam("token") String existingToken) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);

        if(newToken != null) {
            User user = newToken.getUser();

            model.addAttribute("title", "Email Sent!");
            String message = messageSource.getMessage("register.sen.email.message", new Object[] {user.getProfile().getEmail()}, LocaleContextHolder.getLocale());
            model.addAttribute("message", message);
        } else {
            String message = messageSource.getMessage("register.message.invalidToken", null, LocaleContextHolder.getLocale());
            model.addAttribute("title", "Error!");
            model.addAttribute("message", message);
        }

        return "message";
    }

    @GetMapping("/forgetPassword")
    public String forgetPassword() {
        return "forgetPassword";
    }

    @PostMapping("/forgetPassword")
    public String forgetPassword(Model model,@ModelAttribute("profile") Profile profile) {
        Profile dbProfile = profileService.findByEmail(profile.getEmail());
        if(dbProfile != null) {
            userService.forgetPassword(dbProfile.getUser());
            String messageValue = messageSource.getMessage("forget.password.message", null, LocaleContextHolder.getLocale());
            model.addAttribute("title", "Success!");
            model.addAttribute("message", messageValue);
        } else {
            model.addAttribute("title", "Error!");
            model.addAttribute("message", "Email not found on the system");
        }

        return "message";
    }

    @GetMapping("/forgetPassword/reset")
    public String resetPasswordView(WebRequest request, Model model, @RequestParam("token") String token) {

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messageSource.getMessage("reset.password.message.invalidToken", null, LocaleContextHolder.getLocale());
            model.addAttribute("title", "Error!");
            model.addAttribute("message", message);
            return "message";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = messageSource.getMessage("reset.password.message.expired", null, LocaleContextHolder.getLocale());
            model.addAttribute("title", "Error!");
            model.addAttribute("message", messageValue);
            return "message";
        }

        model.addAttribute("id", user.getId());
        return "resetPassword";
    }

    @PostMapping("/forgetPassword/reset")
    public String resetPassword(@ModelAttribute("user") User user) {
        User loadedUser = userService.findUserById(user.getId());
        loadedUser.setPassword(user.getPassword());
        userService.resetPassword(loadedUser);
        return "redirect:/login";
    }

/************************************************************************************/

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userService
                .loadUserByUsername(user.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}

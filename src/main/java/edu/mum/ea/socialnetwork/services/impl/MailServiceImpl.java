package edu.mum.ea.socialnetwork.services.impl;

import edu.mum.ea.socialnetwork.domain.User;
import edu.mum.ea.socialnetwork.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    private JavaMailSender javaMailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(User user, String subject, String body) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getProfile().getEmail());
        email.setFrom("socialbugs.network@gmail.com");
        email.setSubject(subject);
        email.setText(body);
        try {
            javaMailSender.send(email);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

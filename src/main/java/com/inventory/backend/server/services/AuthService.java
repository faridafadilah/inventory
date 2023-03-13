package com.inventory.backend.server.services;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.backend.server.base.ResponAPI;
import com.inventory.backend.server.dto.response.DtoResResetPassword;
import com.inventory.backend.server.model.User;
import com.inventory.backend.server.repository.UserRepository;

@Service
public class AuthService {

  @Autowired
  private UserRepository repository;

  @Autowired
  private PasswordEncoder encoder;
  private ModelMapper mapper = new ModelMapper();

  @Autowired
    private JavaMailSender mailSender;

  @Transactional
  public boolean resetPassword(ResponAPI<String> responseMessage, String token, String newPassword) {
    Optional<User> userOp = repository.findUsersByResetToken(token);
    if (!userOp.isPresent()) {
      responseMessage.setErrorMessage("Link Anda Salah");
      return false;
    }
    User users = userOp.get();
    try {
      users.setPassword(encoder.encode(newPassword));
      repository.save(users);
      responseMessage.setErrorMessage("Password Updated.");
    } catch (Exception e) {
      responseMessage.setErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

  @Transactional
  public boolean forgetPassword(ResponAPI<DtoResResetPassword> responseMessage, String email,
      String siteUrl) {
    Optional<User> userOp = repository.findByEmail(email);
    if (!userOp.isPresent()) {
      responseMessage.setErrorMessage("User dengan email tersebut tidak ditemukan!");
      return false;
    }

    String token = UUID.randomUUID().toString();
    User users = userOp.get();
    users.setResetToken(token);
    users.setResetTokenDate(new Date());
    String verifyUrl = siteUrl + "reset-password?token=" + users.getResetToken();
    try {
      repository.save(users);
      sendPasswordResetEmail(users.getEmail(), verifyUrl);
      DtoResResetPassword res = mapper.map(users, DtoResResetPassword.class);
      responseMessage.setData(res);
      responseMessage.setErrorMessage("Email Sent.");
    } catch (Exception e) {
      responseMessage.setErrorMessage(e.getMessage());
    }
    return true;
  }

  public void sendPasswordResetEmail(String email, String verifyUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("faridafadilah42807@gmail.com");
        message.setTo(email);
        message.setSubject("Reset Password");
        message.setText("To reset your password, please click the link below:\n\n" + verifyUrl);
        mailSender.send(message);
    }

}

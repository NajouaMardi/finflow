package com.finflow.backend.Authentication;


import com.finflow.backend.Model.Token;
import com.finflow.backend.Model.User;
import com.finflow.backend.Repository.RoleRepository;
import com.finflow.backend.Repository.TokenRepository;
import com.finflow.backend.Repository.UserRepository;
import com.finflow.backend.Security.JwtService;
import com.finflow.backend.Service.EmailService;
import com.finflow.backend.Service.EmailTemplateName;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class AuthenticationService {


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    @Autowired
    private JwtService jwtService;


    @Autowired
    private AuthenticationManager authenticationManager;

    public void register(RegistrationRequest request) throws MessagingException{

        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Role USER was not initialized"));

//        var user = User.builder()
//                .firstname(request.getFirstname())
//                .lastname(request.getLastname())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .accountLocked(false)
//                .enabled(false)
//                .roles(List.of(userRole))
//                .build();

        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAccountLocked(false);
        user.setEnabled(false);
        user.setRoles(List.of(userRole));


        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        //send Email
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account Activation"
        );

    }

    private String generateAndSaveActivationToken(User user) {

        //generate token
        String generatedToken = generateActivationCode(6);
//        var token = Token.builder()
//                .token(generatedToken)
//                .createdAt(LocalDateTime.now())
//                .expiresAt(LocalDateTime.now().plusMinutes(15))
//                .user(user)
//                .build();


        Token token = new Token();
        token.setToken(generatedToken);
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        token.setUser(user);
        tokenRepository.save(token);
        return generatedToken;

    }

    private String generateActivationCode(int length) {

        String characters = "0123456789"; // what composes my token
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom =  new SecureRandom();
        for (int i=0; i<length; i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return  codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        //takes care of authentication request if email and password are correct
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullname",user.fullName());
        var jwtToken = jwtService.generateToken(claims, user);

        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setToken(jwtToken);
        return authResponse;
    }



    //@Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())){ //if token already expired
            sendValidationEmail(savedToken.getUser());
            throw
                    new RuntimeException("Activation token has expired. A new token has been sent to the same email address");

        }
        //if token is not expired
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);

        
        
    }


}

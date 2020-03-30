package br.com.lanzoni.ucr.controller;

import br.com.lanzoni.ucr.domain.User;
import br.com.lanzoni.ucr.service.AuthService;
import br.com.lanzoni.ucr.service.JwtService;
import br.com.lanzoni.ucr.service.UserService;
import br.com.lanzoni.ucr.util.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @JsonView(JacksonView.Auth.class)
    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> body) {
        User user = authService.login(body);
        String jwt = JwtService.generateAuthentication(user);
        user.setJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @JsonView(JacksonView.Auth.class)
    @PostMapping("signin")
    public ResponseEntity<User> signin(@RequestBody Map<String, String> body) {
        User user = userService.save(body);
        String jwt = JwtService.generateAuthentication(user);
        user.setJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}

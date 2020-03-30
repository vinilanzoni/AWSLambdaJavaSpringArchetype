package br.com.lanzoni.ucr.service;

import br.com.lanzoni.ucr.domain.User;
import br.com.lanzoni.ucr.exception.BadRequestException;
import br.com.lanzoni.ucr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EncryptService encryptService;

    public User login(Map<String, String> body) {
        validateFields(body);
        Optional<User> user = userRepository.findByEmail(body.get("email"));
        if(user.isEmpty()) {
            throw new BadRequestException("Email e/ou senha inválida");
        }
        boolean success = encryptService.check(body.get("password"), user.get().getPassword());
        if(!success) {
            throw new BadRequestException("Email e/ou senha inválida");
        }
        return user.get();
    }

    private void validateFields(Map<String, String> body) {
        if(!body.containsKey("email")) {
            throw new BadRequestException("Missing email.");
        }
        if(!body.containsKey("password")) {
            throw new BadRequestException("Missing password.");
        }
    }
}

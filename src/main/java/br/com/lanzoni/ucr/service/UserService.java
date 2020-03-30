package br.com.lanzoni.ucr.service;

import br.com.lanzoni.ucr.domain.User;
import br.com.lanzoni.ucr.exception.BadRequestException;
import br.com.lanzoni.ucr.repository.UserRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    EncryptService encryptService;

    @Autowired
    UserRepository userRepository;

    public List<User> list() {
        return IterableUtils.toList(userRepository.findAll());
    }

    public User find(String id)  {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new BadRequestException("Usuário não encontrado.");
        }
    }

    public User save(Map<String, String> body) {
        validateBody(body);
        User user = new User();
        user.setName(body.get("name"));
        user.setEmail(body.get("email"));
        user.setPassword(encryptService.encrypt(body.get("password")));
        return userRepository.save(user);
    }

    public User update(String id, Map<String, String> body) {
        User user = find(id);
        if(body.containsKey("name")) {
            user.setName(body.get("name"));
        }
        if(body.containsKey("email")) {
            user.setEmail(body.get("email"));
        }
        if(body.containsKey("password")) {
            user.setPassword(body.get("password"));
        }
        return userRepository.save(user);
    }

    private void validateBody(Map<String, String> body) {
        if(!body.containsKey("name")) {
            throw new BadRequestException("Missing name.");
        }
        if(!body.containsKey("email")) {
            throw new BadRequestException("Missing email.");
        }
        if(!body.containsKey("password")) {
            throw new BadRequestException("Missing password.");
        }
    }
}

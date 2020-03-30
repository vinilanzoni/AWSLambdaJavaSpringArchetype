package br.com.lanzoni.ucr.controller;

import br.com.lanzoni.ucr.domain.User;
import br.com.lanzoni.ucr.service.UserService;
import br.com.lanzoni.ucr.util.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @JsonView(JacksonView.Public.class)
    @GetMapping()
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userService.list(), HttpStatus.OK);
    }

    @JsonView(JacksonView.Public.class)
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(userService.find(id), HttpStatus.OK);
    }

    @JsonView(JacksonView.Public.class)
    @PostMapping()
    public ResponseEntity<User> save(@RequestBody Map<String, String> body) {
        return new ResponseEntity<>(userService.save(body), HttpStatus.CREATED);
    }

    @JsonView(JacksonView.Public.class)
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") String id, @RequestBody Map<String, String> body) {
        return new ResponseEntity<>(userService.update(id, body), HttpStatus.OK);
    }

}

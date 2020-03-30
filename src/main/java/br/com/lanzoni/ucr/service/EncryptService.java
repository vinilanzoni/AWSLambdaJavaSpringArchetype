package br.com.lanzoni.ucr.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class EncryptService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String encrypt(String str) {
        return bCryptPasswordEncoder.encode(str);
    }

    public boolean check(String str, String hash) {
        return bCryptPasswordEncoder.matches(str, hash);
    }

}

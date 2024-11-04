package com.example.tokens.security.jwt.util;

import com.example.tokens.model.Usuario;
import com.example.tokens.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("adminpassword"));
            admin.setRole("ADMIN");
            usuarioRepository.save(admin);

            Usuario user = new Usuario();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("userpassword"));
            user.setRole("USER");
            usuarioRepository.save(user);
        }
    }
}

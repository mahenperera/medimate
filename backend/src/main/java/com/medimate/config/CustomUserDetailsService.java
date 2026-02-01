package com.medimate.config;

import com.medimate.model.AppUser;
import com.medimate.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private AuthRepository authRepository;

    @Autowired
    public CustomUserDetailsService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AppUser appUser = authRepository.findByEmail(email);

        if (appUser == null) {
            System.out.println("AppUser not found");
            throw new UsernameNotFoundException("AppUser not found");
        }

        return new UserPrincipal(appUser);
    }
}

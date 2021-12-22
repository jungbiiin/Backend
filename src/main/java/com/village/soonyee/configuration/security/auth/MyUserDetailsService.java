package com.village.soonyee.configuration.security.auth;

import com.village.soonyee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final MemberRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return adminRepository.findByEmail(email).get(0);
    }
}

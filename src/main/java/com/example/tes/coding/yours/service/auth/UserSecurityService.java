package com.example.tes.coding.yours.service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserSecurityService {

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}

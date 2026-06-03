package com.ankit.civicconnectbackend.services;

import com.ankit.civicconnectbackend.entities.Admin;
import com.ankit.civicconnectbackend.entities.Staff;
import com.ankit.civicconnectbackend.entities.User;
import com.ankit.civicconnectbackend.repos.AdminRepo;
import com.ankit.civicconnectbackend.repos.StaffRepo;
import com.ankit.civicconnectbackend.repos.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepo userRepo;
    private final AdminRepo adminRepo;
    private final StaffRepo staffRepo;

    public CustomUserDetailsService(
            UserRepo userRepo,
            AdminRepo adminRepo,
            StaffRepo staffRepo
    ) {

        this.userRepo = userRepo;
        this.adminRepo = adminRepo;
        this.staffRepo = staffRepo;
    }

    @Override
    public UserDetails loadUserByUsername(
            String email
    ) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email);

        if (user != null) {

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    List.of(
                            new SimpleGrantedAuthority("ROLE_USER")
                    )
            );
        }

        Admin admin = adminRepo.findByAdminEmail(email);

        if (admin != null) {

            return new org.springframework.security.core.userdetails.User(
                    admin.getAdminEmail(),
                    admin.getAdminPassword(),
                    List.of(
                            new SimpleGrantedAuthority("ROLE_ADMIN")
                    )
            );
        }

        Staff staff = staffRepo.findByEmail(email);

        if (staff != null) {

            return new org.springframework.security.core.userdetails.User(
                    staff.getEmail(),
                    staff.getPassword(),
                    List.of(
                            new SimpleGrantedAuthority("ROLE_STAFF")
                    )
            );
        }

        throw new UsernameNotFoundException(
                "User not found"
        );
    }
}
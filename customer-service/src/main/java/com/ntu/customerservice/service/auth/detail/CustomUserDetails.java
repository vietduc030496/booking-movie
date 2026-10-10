package com.ntu.customerservice.service.auth.detail;

import com.ntu.moviecore.domain.authentication.entity.Gender;
import com.ntu.moviecore.domain.authentication.entity.Role;
import com.ntu.moviecore.domain.authentication.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {
    private long id;
    private String username;
    private String fullName;
    private String city;
    private String district;
    private String address;
    private Gender gender;
    private String avatarUrl;
    private String password;
    private Role role;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getEmail();
        this.fullName = user.getProfile().getFullName();
        this.city = user.getProfile().getCity();
        this.district = user.getProfile().getDistrict();
        this.address = user.getProfile().getAddress();
        this.gender = user.getProfile().getGender();
        this.avatarUrl = user.getProfile().getAvatarUrl();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {
            return List.of();
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    // isAccountNonExpired/isAccountNonLocked/isCredentialsNonExpired/isEnabled are not overridden:
    // the UserDetails defaults (all true) already give the desired behavior.
}

package com.ntu.customerservice.service.auth.detail;

import com.ntu.moviecore.domain.authentication.entity.Gender;
import com.ntu.moviecore.domain.authentication.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
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

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getEmail();
        this.fullName = user.getProfile().getFullName();
        this.city = user.getProfile().getCity();
        this.district = user.getProfile().getDistrict();
        this.address = user.getProfile().getAddress();
        this.gender = user.getProfile().getGender();
        this.avatarUrl = user.getProfile().getAvatarUrl();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

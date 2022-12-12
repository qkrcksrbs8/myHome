package com.example.myhome.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

public class UserDetails extends User {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private Object userVO;

    public Object getUserVO() {
        return userVO;
    }

    public void setUserVO(Object userVO) {
        this.userVO = userVO;
    }

    public UserDetails(String userid, String username, String password, boolean enabled,
                       boolean accountNonExpired, boolean credentialsNonExpired,
                       boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
                       Object userVO) throws IllegalArgumentException {

        super(userid, username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

        this.userVO = userVO;
    }

    public UserDetails(String userid, String username, String password, boolean enabled, Object userVO) throws IllegalArgumentException {

        this(userid, username, password, enabled, true, true, true,
                Arrays.asList(new GrantedAuthority[]{new SimpleGrantedAuthority("ROLE_GUEST")}), userVO);
    }

}

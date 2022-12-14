package com.example.myhome.security;

import com.example.myhome.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class UserDetailsHelper {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsHelper.class);

    public static Object getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (ObjectUtil.isNull(authentication)) {
            logger.error("## authentication object is null!!");
            return null;
        }

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails details = (UserDetails) authentication.getPrincipal();

            logger.debug("## UserDetailsHelper.getAuthenticatedUser : AuthenticatedUser is {}", details.getUserid());

            return details.getUserVO();
        } else {
            return authentication.getPrincipal();
        }
    }

    public static List<String> getAuthorities() {
        List<String> listAuth = new ArrayList<String>();

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (ObjectUtil.isNull(authentication)) {
            logger.error("## authentication object is null!!");
            return null;
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        Iterator<? extends GrantedAuthority> iter = authorities.iterator();

        while (iter.hasNext()) {
            GrantedAuthority auth = iter.next();
            listAuth.add(auth.getAuthority());

            logger.debug("## UserDetailsHelper.getAuthorities : Authority is {}", auth.getAuthority());

        }

        return listAuth;
    }

    public static Boolean isAuthenticated() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (ObjectUtil.isNull(authentication)) {
            logger.error("## authentication object is null!!");
            return Boolean.FALSE;
        }

        String username = authentication.getName();
        if (username.equals("anonymousUser")) {
            logger.debug("## username is {}", username);
            return Boolean.FALSE;
        }

        Object principal = authentication.getPrincipal();

        return (Boolean.valueOf(!ObjectUtil.isNull(principal)));
    }

    public static Boolean isAuthenticated(String authority) {
        Boolean result = false;
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (ObjectUtil.isNull(authentication)) {
            logger.error("## authentication object is null!!");
            return Boolean.FALSE;
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        Iterator<? extends GrantedAuthority> iter = authorities.iterator();

        while (iter.hasNext()) {
            GrantedAuthority auth = iter.next();
            logger.debug("## UserDetailsHelper.getAuthorities : Authority is {}", auth.getAuthority());
            if (authority.equals(auth.getAuthority())) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static void setAuthenticated(boolean auth) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        authentication.setAuthenticated(auth);
    }

    public static int userIdx() {
        return ((UserVO) getAuthenticatedUser()).getIdx();
    }

}

package com.ae.apis.security;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.ae.apis.common.Constants.TOKEN_PREFIX;
import static java.util.Optional.ofNullable;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {

        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(header) || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.substring(TOKEN_PREFIX.length());
        if (!tokenProvider.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context
        String username = tokenProvider.getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                ofNullable(userDetails)
                        .map(UserDetails::getAuthorities)
                        .orElse(new ArrayList<>())
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}

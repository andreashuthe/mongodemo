package de.demo.app.sec;

import de.demo.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

public class DemoAuthenticationProvider implements AuthenticationProvider {

    private final SecurityService securityService;

    public DemoAuthenticationProvider(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (name != null && password != null && securityService.checkAccess(name, password)) {
            return new UsernamePasswordAuthenticationToken(name, password, AuthorityUtils.createAuthorityList("ROLE_USER"));
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
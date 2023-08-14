package de.demo.service.impl;

import de.demo.service.SecurityService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public boolean checkAccess(String userName, String password) {
        return "admin".equals(userName) && "admin".equals(password);
    }
}

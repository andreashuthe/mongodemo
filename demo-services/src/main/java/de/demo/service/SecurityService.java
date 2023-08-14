package de.demo.service;

public interface SecurityService {
    boolean checkAccess(String userName, String password);
}

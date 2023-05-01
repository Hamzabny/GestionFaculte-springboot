package com.example.projetetudiant.security.services;

import com.example.projetetudiant.security.entities.appRole;
import com.example.projetetudiant.security.entities.appUser;

public interface iservice {
    appUser addUser(String username, String password, String rePassword);
    appRole addRole(String rolename,String description);
    void addRoleToUser(String username,String rolename);
    void removeRoleFromUser(String username, String rolename);
    void removeUser(String username);
    void editUser(String username, String password, String rePassword);
    void deleteUserAndRoles(String username);
    appUser loadUserByUsername(String username);

    Object getAllRoles();
}

package com.example.projetetudiant.security.services;

import com.example.projetetudiant.entities.classe;
import com.example.projetetudiant.entities.etudiant;
import com.example.projetetudiant.security.entities.appRole;
import com.example.projetetudiant.security.entities.appUser;
import com.example.projetetudiant.security.repository.appRoleRep;
import com.example.projetetudiant.repositories.classeRepository;
import com.example.projetetudiant.repositories.etudiantRepository;
import com.example.projetetudiant.security.repository.appUserRep;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ImplServices implements iservice {
    private appRoleRep appRoleRep;
    private appUserRep appUserRep;
    private classeRepository classeRepository;
    private etudiantRepository etudiantRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public appUser addUser(String username,String password,String rePassword) {
            appUser appUser1= appUserRep.findByUsername(username);
            if (appUser1!=null) throw new RuntimeException("this user is already exist");
            if(!password.equals(rePassword)) throw new RuntimeException("les mots de passe ne correspondent pas");
                appUser1=new appUser();
                appUser1.setUsername(username);
              //  appUser1.setId(1L);
                appUser1.setPassword(passwordEncoder.encode(password));
            return appUserRep.save(appUser1);
    }

    @Override
    public appRole addRole(String rolename,String description) {
        appRole appRole1=appRoleRep.findByRolename(rolename);
        if(appRole1!=null)throw new RuntimeException("ce role exist déjà");
        appRole1=new appRole();
        appRole1.setRolename(rolename);
        appRole1.setDescription(description);
        return appRoleRep.save(appRole1);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
           appUser appUser=appUserRep.findByUsername(username);
           if(appUser==null) throw new RuntimeException("user not found");
           appRole appRole=appRoleRep.findByRolename(rolename);
           if (appRole==null) throw new RuntimeException("role not found");
           appUser.getListRoles().add(appRole);
    }

    @Override
    public void removeRoleFromUser(String username, String rolename) {
        appUser appUser=appUserRep.findByUsername(username);
        if(appUser==null) throw new RuntimeException("user not found");
        appRole appRole=appRoleRep.findByRolename(rolename);
        if (appRole==null) throw new RuntimeException("role not found");
        appUser.getListRoles().remove(appRole);
    }
    @Override
    public void removeUser(String username) {
        appUser appUser = appUserRep.findByUsername(username);
        if (appUser == null) throw new RuntimeException("user not found");
        appUserRep.delete(appUser);
    }
    @Override
    public appUser editUser(String oldUsername, String newUsername, String password, String rePassword) {
        appUser appUser = appUserRep.findByUsername(oldUsername);
        if (appUser == null) {
            throw new RuntimeException("user not found");
        }
        if (!password.equals(rePassword)) {
            throw new RuntimeException("les mots de passe ne correspondent pas");
        }
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setUsername(newUsername);
        return appUserRep.save(appUser);
    }

    @Override
    public void deleteUserAndRoles(String username) {
        appUser appUser=appUserRep.findByUsername(username);
        if(appUser==null) throw new RuntimeException("User not found");
        // Remove user's roles
        appUser.getListRoles().clear();
        // Delete user
        appUserRep.delete(appUser);
    }

    @Override
    public appUser loadUserByUsername(String username) {
        appUser appUser=appUserRep.findByUsername(username);
        if(appUser==null) throw new RuntimeException("User not found");
        return appUser;
    }

    @Override
    public Object getAllRoles() {
        return null;
    }

    @Override
    public void updateRoleForUser(String oldUsername, String newUsername, String rolename) {
        appUser appUser = appUserRep.findByUsername(oldUsername);
        if (appUser == null) {
            throw new RuntimeException("user not found");
        }
        appUser.setUsername(newUsername);
        appRole appRole = appRoleRep.findByRolename(rolename);
        if (appRole == null) {
            throw new RuntimeException("role not found");
        }
        appUser.getListRoles().clear();
        appUser.getListRoles().add(appRole);
    }



}

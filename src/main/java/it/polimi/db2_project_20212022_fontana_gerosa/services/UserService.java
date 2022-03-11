package it.polimi.db2_project_20212022_fontana_gerosa.services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import javax.security.auth.login.CredentialException;
import java.util.List;

@Stateless
public class UserService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

    public User checkCredentials(String email, String password) throws CredentialException {
        List<User> matchingUsers;
        try {
            matchingUsers = em.createNamedQuery("User.checkCredentials", User.class).
                    setParameter(1, email).setParameter(2, password).getResultList();
        } catch (PersistenceException e){
            throw new CredentialException("Could not verify credentials");
        }
        if(matchingUsers.isEmpty()){
            return null;
        }
        return matchingUsers.get(0);
    }

    public User findUserById(int userId){
        List<User> matchingUsers = null;
        try {
            matchingUsers = em.createNamedQuery("User.findById", User.class).setParameter(1, userId).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve user");
        }
        if(matchingUsers.isEmpty()){
            return null;
        }
        return matchingUsers.get(0);
    }

    public User findUserByEmail(String email) {
        List<User> matchingUsers = null;
        try {
            matchingUsers = em.createNamedQuery("User.findByEmail", User.class).
                    setParameter(1, email).getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Couldn't retrieve user");
        }
        if(matchingUsers.isEmpty()){
            return null;
        }
        return matchingUsers.get(0);    }

    public User findUserByUsername(String username) {
        List<User> matchingUsers = null;
        try {
            matchingUsers = em.createNamedQuery("User.findByUsername", User.class).
                    setParameter(1, username).getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Couldn't retrieve user");
        }
        if(matchingUsers.isEmpty()){
            return null;
        }
        return matchingUsers.get(0);
    }

    public User registerUser(String email, String username, String password){
        User userToRegister = new User(email, username, password);
        em.persist(userToRegister);
        em.flush();
        return userToRegister;
    }

}

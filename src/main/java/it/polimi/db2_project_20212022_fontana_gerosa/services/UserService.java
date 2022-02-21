package it.polimi.db2_project_20212022_fontana_gerosa.services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.List;

@Stateless
public class UserService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

    public User checkCredentials(String email, String password) throws PersistenceException {
        List<User> matchingUsers = em.createNamedQuery("User.checkCredentials", User.class).
                setParameter(1, email).setParameter(2, password).getResultList();
        return matchingUsers.get(0);
    }

    public User findUserByEmail(String email){
        List<User> matchingUsers = em.createNamedQuery("User.findByEmail", User.class).
                setParameter(1, email).getResultList();
        return matchingUsers.get(0);
    }

    public User findUserByUsername(String username){
        List<User> matchingUsers = em.createNamedQuery("User.findByUsername", User.class).
                setParameter(1, username).getResultList();
        return matchingUsers.get(0);
    }

    public User registerUser(String email, String username, String password){
        User userToRegister = new User(email, username, password);
        em.persist(userToRegister);
        em.flush();
        return userToRegister;
    }

}

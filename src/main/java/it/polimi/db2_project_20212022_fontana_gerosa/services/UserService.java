package it.polimi.db2_project_20212022_fontana_gerosa.services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.User;
import jakarta.persistence.EntityManager;

import java.util.List;

public class UserService {
    private EntityManager em;

    public User checkCredentials(String username, String email, String password){
        List<User> matchingUsers = em.createNamedQuery("User.checkCredentials", User.class).
                setParameter(1, username).setParameter(2, email).setParameter(3, password).getResultList();
        return matchingUsers.get(0);
    }
}

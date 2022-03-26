package it.polimi.db2_project_20212022_fontana_gerosa.ejbs;

import it.polimi.db2_project_20212022_fontana_gerosa.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import javax.security.auth.login.CredentialException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Stateless
public class UserService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

    /**
     * Checks if the User trying to log in is present into the DB
     * @param email to identify the User
     * @param password to authenticate the User
     * @return the User if found in the DB, null otherwise
     * @throws CredentialException if the credentials couldn't be verified
     */
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

    /**
     * Gives a User given their id
     * @param userId id of the User to be searched
     * @return the User found
     */
    public User findUserById(int userId) {
        User matchingUser = null;
        try {
            matchingUser = em.find(User.class, userId);
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve user");
        }
        return matchingUser;
    }

    /**
     * Gives a User given their email
     * @param email email address of the User to be searched
     * @return the User found
     */
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

    /**
     * Gives a user given their username
     * @param username username of the User to be searched
     * @return the User found
     */
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

    /**
     * Create a new User in the DB
     * @param email mail of the User to be added
     * @param username username of the User to be added
     * @param password password of the User to be added
     * @return the User just added
     */
    public User registerUser(String email, String username, String password){
        User userToRegister = new User(email, username, password);
        em.persist(userToRegister);
        em.flush();
        return userToRegister;
    }

    /**
     * Gives a collection of Strings containing the description of all insolvent Users in the DB
     * @return a collection of String containing the textual descriptions
     */
    public Collection<String> getAllInsolventUsersDescriptions(){
        Collection<User> users = null;
        Collection<String> descriptions = new ArrayList<>();
        try {
            users = em.createNamedQuery("User.getAllInsolventUsers", User.class).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve MVTotalPurchasesPerOp results");
        }
        if(users != null){
            users.forEach(user -> descriptions.add(user.getDescription()));
        }
        return descriptions;
    }

    /**
     * Gives a User given the id of an Order made by them
     * @param orderId id of the Order made by the User to be searched
     * @return the User found
     */
    public User getUserByOrderId(int orderId){
        List<User> users = null;
        try{
            users = em.createNamedQuery("User.getUserByOrderId", User.class).
                    setParameter(1, orderId).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve matching user");
        }
        if(users.isEmpty()){
            return null;
        }
        return users.get(0);
    }

}

package it.polimi.db2_project_20212022_fontana_gerosa.utils;

import org.apache.commons.validator.routines.EmailValidator;

public class DataValidator {

    /**
     * Method to check email validity
     * @param email email to check
     * @return true if it's valid, false otherwise
     */
    public static boolean isEmailValid(String email) {
        return email != null && EmailValidator.getInstance().isValid(email);
    }

    /**
     * Method to check username validity
     * @param username username to check
     * @return true if it's valid, false otherwise
     */
    public static boolean isUsernameValid(String username) {
        return username != null && username.length()<32 && username.length() > 3;
    }

    /**
     * Method to check username validity
     * @param password password to check
     * @return true if it's valid, false otherwise
     */
    public static boolean isPasswordValid(String password){
        return password != null && password.length()<32 && password.length() > 3;
    }
}

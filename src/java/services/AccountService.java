package services;

import models.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private Map<String, UserProfile> loginToProfile;
    private Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public UserProfile getUserProfileByLogin(String login) {
        return loginToProfile.get(login);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public UserProfile getUserProfileBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
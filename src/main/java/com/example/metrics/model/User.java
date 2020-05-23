package com.example.metrics.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private long id;

    private String username;

    private String password;

    private boolean enabled;

    private String role;

    private List<Group> groups;

    private List<Group> ownedGroups;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "USER";
        this.enabled = true;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Group> getOwnedGroups() {
        return ownedGroups;
    }

    public void setOwnedGroups(List<Group> ownedGroups) {
        this.ownedGroups = ownedGroups;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGroupOwner(long id) {
        for (Group g : this.ownedGroups) {
            if (g.getId() == id) {
                return true;
            }
        }

        return false;
    }

    public boolean isGroupMember(long id) {
        for (Group g : this.groups) {
            if (g.getId() == id) {
                return true;
            }
        }

        return false;
    }


    public User() {
        super();
    }

}
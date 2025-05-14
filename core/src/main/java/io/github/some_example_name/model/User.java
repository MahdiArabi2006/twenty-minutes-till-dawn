package io.github.some_example_name.model;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.awt.*;

public class User {
    private String username;
    private String password;
    private final String answerSecurityQuestion;
    private final Image avatar;

    public User(String username, String password, String answerSecurityQuestion,Image avatar) {
        this.username = username;
        this.password = password;
        this.answerSecurityQuestion = answerSecurityQuestion;
        this.avatar = avatar;
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

    public String getAnswerSecurityQuestion() {
        return answerSecurityQuestion;
    }

    public Image getAvatar() {
        return avatar;
    }
}

package io.github.some_example_name.model;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class User {
    private String username;
    private String password;
    private final String answerSecurityQuestion;
    private final GameKey gameKey = new GameKey();
    private boolean autoReloadingEnable;
    private Image avatar;
    private int score;
    private Game lastGame;

    public User(String username, String password, String answerSecurityQuestion, Image avatar) {
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public Game getLastGame() {
        return lastGame;
    }

    public void setLastGame(Game lastGame) {
        this.lastGame = lastGame;
    }

    public GameKey getGameKey() {
        return gameKey;
    }

    public boolean isAutoReloadingEnable() {
        return autoReloadingEnable;
    }

    public void setAutoReloadingEnable(boolean autoReloadingEnable) {
        this.autoReloadingEnable = autoReloadingEnable;
    }
}

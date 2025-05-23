package io.github.some_example_name.model;

import java.util.Locale;

public class App {
    private static User loggedInUser = null;
    private static boolean enableSFX = true;
    private static boolean enableMusic = false;
    private static boolean blackWhiteMode = false;
    private static Locale locale = Locale.ENGLISH;
    private static float musicVolume;
    private static Music playedMusic;

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        App.loggedInUser = loggedInUser;
    }

    public static boolean isEnableSFX() {
        return enableSFX;
    }

    public static void setEnableSFX(boolean enableSFX) {
        App.enableSFX = enableSFX;
    }

    public static Music getPlayedMusic() {
        return playedMusic;
    }

    public static void setPlayedMusic(Music playedMusic) {
        App.playedMusic = playedMusic;
    }

    public static boolean isEnableMusic() {
        return enableMusic;
    }

    public static void setEnableMusic(boolean enableMusic) {
        App.enableMusic = enableMusic;
    }

    public static float getMusicVolume() {
        return musicVolume;
    }

    public static void setMusicVolume(float musicVolume) {
        App.musicVolume = musicVolume;
    }

    public static boolean isBlackWhiteMode() {
        return blackWhiteMode;
    }

    public static void setBlackWhiteMode(boolean blackWhiteMode) {
        App.blackWhiteMode = blackWhiteMode;
    }

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale locale) {
        App.locale = locale;
    }
}

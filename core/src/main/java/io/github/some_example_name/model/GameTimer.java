package io.github.some_example_name.model;

public class GameTimer {
    private float remainingTime;
    private boolean isRunning;
    private final float countdownDuration;

    public GameTimer(float duration) {
        this.countdownDuration = duration;
        this.remainingTime = duration;
    }

    public void start() {
        isRunning = true;
    }

    public void pause() {
        isRunning = false;
    }

    public void reset() {
        remainingTime = countdownDuration;
        isRunning = false;
    }

    public String getFormattedTime() {
        int minutes = (int) (remainingTime / 60);
        int seconds = (int) (remainingTime % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

    public float getRemainingTime() {
        return remainingTime;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRemainingTime(float remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public float getCountdownDuration() {
        return countdownDuration;
    }
}

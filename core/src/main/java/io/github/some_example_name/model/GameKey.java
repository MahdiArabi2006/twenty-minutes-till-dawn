package io.github.some_example_name.model;

import com.badlogic.gdx.Input;

public class GameKey {
    private int MOVE_UP = Input.Keys.W;
    private int MOVE_DOWN = Input.Keys.S;
    private int MOVE_RIGHT = Input.Keys.D;
    private int MOVE_LEFT = Input.Keys.A;
    private int RELOAD = Input.Keys.R;
    private int PAUSE = Input.Keys.P;

    public int getMOVE_UP() {
        return MOVE_UP;
    }

    public void setMOVE_UP(int MOVE_UP) {
        this.MOVE_UP = MOVE_UP;
    }

    public int getMOVE_DOWN() {
        return MOVE_DOWN;
    }

    public void setMOVE_DOWN(int MOVE_DOWN) {
        this.MOVE_DOWN = MOVE_DOWN;
    }

    public int getMOVE_RIGHT() {
        return MOVE_RIGHT;
    }

    public void setMOVE_RIGHT(int MOVE_RIGHT) {
        this.MOVE_RIGHT = MOVE_RIGHT;
    }

    public int getMOVE_LEFT() {
        return MOVE_LEFT;
    }

    public void setMOVE_LEFT(int MOVE_LEFT) {
        this.MOVE_LEFT = MOVE_LEFT;
    }

    public int getRELOAD() {
        return RELOAD;
    }

    public void setRELOAD(int RELOAD) {
        this.RELOAD = RELOAD;
    }

    public int getCHEAT_REDUCE_TIME() {
        return Input.Keys.T;
    }

    public int getCHEAT_INCREASE_LEVEL() {
        return Input.Keys.L;
    }

    public int getCHEAT_INCREASE_HEALTH() {
        return Input.Keys.H;
    }

    public int getPAUSE() {
        return PAUSE;
    }

    public void setPAUSE(int PAUSE) {
        this.PAUSE = PAUSE;
    }
}

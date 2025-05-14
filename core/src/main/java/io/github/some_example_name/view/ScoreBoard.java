package io.github.some_example_name.view;

public class ScoreBoard extends Menu {
    private static ScoreBoard instance;

    private ScoreBoard() {
    }

    public static ScoreBoard getInstance() {
        if (instance==null) instance = new ScoreBoard();
        return instance;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

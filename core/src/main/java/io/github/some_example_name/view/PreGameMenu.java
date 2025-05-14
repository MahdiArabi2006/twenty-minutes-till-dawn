package io.github.some_example_name.view;

public class PreGameMenu extends Menu{
    private static PreGameMenu instance;

    private PreGameMenu(){}

    public static PreGameMenu getInstance(){
        if (instance==null) instance = new PreGameMenu();
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

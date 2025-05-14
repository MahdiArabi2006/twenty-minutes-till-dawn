package io.github.some_example_name.view;

public class ProfileMenu extends Menu{
    private static ProfileMenu instance;

    private ProfileMenu(){}

    public static ProfileMenu getInstance(){
        if (instance==null) instance = new ProfileMenu();
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

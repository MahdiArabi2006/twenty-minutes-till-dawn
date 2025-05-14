package io.github.some_example_name.view;

public class SettingMenu extends Menu {
    private static SettingMenu instance;

    private SettingMenu() {
    }

    public static SettingMenu getInstance() {
        if (instance==null) instance = new SettingMenu();
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

package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.GameAsset;
import io.github.some_example_name.view.FirstMenu;
import io.github.some_example_name.view.LoginMenu;
import io.github.some_example_name.view.Menu;
import io.github.some_example_name.view.RegisterMenu;

public class FirstMenuController {
    private final FirstMenu view;


    public FirstMenuController(FirstMenu menu) {
        this.view = menu;
    }

    public void handleInput() {
        if (view.getTextButtonList().get(0).isPressed()) {
            if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
            Main.getInstance().setScreen(new LoginMenu());
        } else if (view.getTextButtonList().get(1).isPressed()) {
            if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
            Main.getInstance().setScreen(new RegisterMenu());
        } else if (view.getTextButtonList().get(2).isPressed()) {
            if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
            Gdx.app.exit();
        }
    }

    public Menu getView() {
        return view;
    }
}

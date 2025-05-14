package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAsset {
    private static GameAsset instance;
    private static final Skin menuSkin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

    private GameAsset(){}
    public static GameAsset getInstance(){
        if (instance==null) instance = new GameAsset();
        return instance;
    }

    public static Skin getMenuSkin() {
        return menuSkin;
    }
}

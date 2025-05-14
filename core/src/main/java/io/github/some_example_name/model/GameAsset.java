package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.List;

public class GameAsset {
    private static GameAsset instance;
    private static final Skin menuSkin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
    private static final List<Image> avatars = createAvatars();

    private GameAsset() {
    }

    public static GameAsset getInstance() {
        if (instance==null) instance = new GameAsset();
        return instance;
    }

    public static Skin getMenuSkin() {
        return menuSkin;
    }

    public static List<Image> getAvatars() {
        return avatars;
    }

    private static List<Image> createAvatars() {
        List<Image> avatars = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Texture texture = new Texture(Gdx.files.internal("avatars/" + i + ".png"));
            Image image = new Image(texture);
            image.setSize(270, 313);
            avatars.add(image);
        }
        return avatars;
    }
}

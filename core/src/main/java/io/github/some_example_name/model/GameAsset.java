package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.List;

public class GameAsset {
    public static final Skin menuSkin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
    public static final List<Image> avatars = createAvatars();
    public static final Animation<Texture> scarletIdle = new Animation<>(0.1f, new Texture("Idle/scarlet/0.png"),
        new Texture("Idle/scarlet/1.png"), new Texture("Idle/scarlet/2.png"),
        new Texture("Idle/scarlet/3.png"), new Texture("Idle/scarlet/4.png"),
        new Texture("Idle/scarlet/5.png"));
    public static final Animation<Texture> lilithIdle = new Animation<>(0.1f, new Texture("Idle/lilith/0.png"),
        new Texture("Idle/lilith/1.png"), new Texture("Idle/lilith/2.png"),
        new Texture("Idle/lilith/3.png"), new Texture("Idle/lilith/4.png"),
        new Texture("Idle/lilith/5.png"));
    public static final Animation<Texture> diamondIdle = new Animation<>(0.1f, new Texture("Idle/diamond/0.png"),
        new Texture("Idle/diamond/1.png"), new Texture("Idle/diamond/2.png"),
        new Texture("Idle/diamond/3.png"), new Texture("Idle/diamond/4.png"),
        new Texture("Idle/diamond/5.png"));
    public static final Animation<Texture> dasherIdle = new Animation<>(0.1f, new Texture("Idle/dasher/0.png"),
        new Texture("Idle/dasher/1.png"), new Texture("Idle/dasher/2.png"),
        new Texture("Idle/dasher/3.png"), new Texture("Idle/dasher/4.png"),
        new Texture("Idle/dasher/5.png"));
    public static final Animation<Texture> shanaIdle = new Animation<>(0.1f, new Texture("Idle/shana/0.png"),
        new Texture("Idle/shana/1.png"), new Texture("Idle/shana/2.png"),
        new Texture("Idle/shana/3.png"), new Texture("Idle/shana/4.png"),
        new Texture("Idle/shana/5.png"));
    public static final Animation<Texture> scarletWalk = new Animation<>(0.1f, new Texture("Walk/scarlet/0.png"),
        new Texture("Walk/scarlet/1.png"), new Texture("Walk/scarlet/2.png"),
        new Texture("Walk/scarlet/3.png"), new Texture("Walk/scarlet/4.png"),
        new Texture("Walk/scarlet/5.png"), new Texture("Walk/scarlet/6.png"));
    public static final Animation<Texture> lilithWalk = new Animation<>(0.1f, new Texture("Walk/lilith/0.png"),
        new Texture("Walk/lilith/1.png"), new Texture("Walk/lilith/2.png"),
        new Texture("Walk/lilith/3.png"), new Texture("Walk/lilith/4.png"),
        new Texture("Walk/lilith/5.png"), new Texture("Walk/lilith/6.png"));
    public static final Animation<Texture> diamondWalk = new Animation<>(0.1f, new Texture("Walk/diamond/0.png"),
        new Texture("Walk/diamond/1.png"), new Texture("Walk/diamond/2.png"),
        new Texture("Walk/diamond/3.png"), new Texture("Walk/diamond/4.png"),
        new Texture("Walk/diamond/5.png"), new Texture("Walk/diamond/6.png"));
    public static final Animation<Texture> dasherWalk = new Animation<>(0.1f, new Texture("Walk/dasher/0.png"),
        new Texture("Walk/dasher/1.png"), new Texture("Walk/dasher/2.png"),
        new Texture("Walk/dasher/3.png"), new Texture("Walk/dasher/4.png"),
        new Texture("Walk/dasher/5.png"), new Texture("Walk/dasher/6.png"));
    public static final Animation<Texture> shanaWalk = new Animation<>(0.1f, new Texture("Walk/shana/0.png"),
        new Texture("Walk/shana/1.png"), new Texture("Walk/shana/2.png"),
        new Texture("Walk/shana/3.png"), new Texture("Walk/shana/4.png"),
        new Texture("Walk/shana/5.png"), new Texture("Walk/shana/6.png"));
    public static final Animation<Texture> scarletRun = new Animation<>(0.1f, new Texture("Run/scarlet/0.png"),
        new Texture("Run/scarlet/1.png"), new Texture("Run/scarlet/2.png"),
        new Texture("Run/scarlet/3.png"));
    public static final Animation<Texture> lilithRun = new Animation<>(0.1f, new Texture("Run/lilith/0.png"),
        new Texture("Run/lilith/1.png"), new Texture("Run/lilith/2.png"),
        new Texture("Run/lilith/3.png"));
    public static final Animation<Texture> diamondRun = new Animation<>(0.1f, new Texture("Run/diamond/0.png"),
        new Texture("Run/diamond/1.png"), new Texture("Run/diamond/2.png"),
        new Texture("Run/diamond/3.png"));
    public static final Animation<Texture> dasherRun = new Animation<>(0.1f, new Texture("Run/dasher/0.png"),
        new Texture("Run/dasher/1.png"), new Texture("Run/dasher/2.png"),
        new Texture("Run/dasher/3.png"));
    public static final Animation<Texture> shanaRun = new Animation<>(0.1f, new Texture("Run/shana/0.png"),
        new Texture("Run/shana/1.png"), new Texture("Run/shana/2.png"),
        new Texture("Run/shana/3.png"));
    public static final Texture SMGsTexture = new Texture("weapon/SMGs/SMGStill.png");
    public static final Texture shotgunTexture = new Texture("weapon/shotgun/T_DualShotgun_Gun.png");
    public static final Texture revolverTexture = new Texture("weapon/revolver/RevolverStill.png");
    public static final Texture bulletTexture = new Texture("weapon/bullet.png");

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

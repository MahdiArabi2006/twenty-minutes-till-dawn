package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameAsset {
    private static BitmapFont customFont;
    public static Skin menuSkin;

    static {
        loadResources();
    }

    private static void loadResources() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
            Gdx.files.internal("LiberationSans.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param =
            new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 30;
        param.color = Color.WHITE;
        param.borderWidth = 1;
        param.borderColor = Color.BLACK;
        customFont = generator.generateFont(param);
        generator.dispose();
        menuSkin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
        menuSkin.add("default-font", customFont);
        setFontForAllStyles();
    }

    private static void setFontForAllStyles() {
        menuSkin.get(Label.LabelStyle.class).font = customFont;
        menuSkin.get(TextButton.TextButtonStyle.class).font = customFont;
        menuSkin.get(TextField.TextFieldStyle.class).font = customFont;
        menuSkin.get(SelectBox.SelectBoxStyle.class).font = customFont;
    }

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
    public static final Texture treeTexture = new Texture("enemy/tree/T_TreeMonster_2.png");
    public static final Animation<Texture> tentacleWalk = new Animation<>(0.1f, new Texture("enemy/tentacle/walk/T_TentacleEnemy_0.png"),
        new Texture("enemy/tentacle/walk/T_TentacleEnemy_1.png"), new Texture("enemy/tentacle/walk/T_TentacleEnemy_2.png"),
        new Texture("enemy/tentacle/walk/T_TentacleEnemy_3.png"));
    public static final Animation<Texture> eyeBatWalk = new Animation<>(0.1f, new Texture("enemy/eyebat/walk/T_EyeBat_0.png"),
        new Texture("enemy/eyebat/walk/T_EyeBat_1.png"), new Texture("enemy/eyebat/walk/T_EyeBat_2.png"));
    public static final Animation<Texture> elderWalk = new Animation<>(0.1f, new Texture("enemy/elder/walk/BrainMonster_0.png"),
        new Texture("enemy/elder/walk/BrainMonster_1.png"), new Texture("enemy/elder/walk/BrainMonster_2.png")
        , new Texture("enemy/elder/walk/BrainMonster_3.png"));
    public static final Sound win = Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/You Win (2).wav"));
    public static final Sound lose = Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/You Lose (4).wav"));
    public static final Sound UIClick = Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/UI Click 36.wav"));
    public static final Sound singleShoot = Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/single_shot.wav"));
    public static final Sound monsterSpawn = Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/Monster_2_RecieveAttack_HighIntensity_01.wav"));
    public static final Sound monsterDied = Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/Explosion_Blood_01.wav"));
    public static final Sound levelUp = Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/Buff_Intelligence.wav"));
    public static final Sound batDied = Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/Bat_Death_02.wav"));
    public static final Sound reloadGun = Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/gun-reloading-fx_45bpm_C_minor.wav"));
    public static final Music requiemForADream = Gdx.audio.newMusic(Gdx.files.internal("Requiem for a dream - Lux Aeterna.mp3"));
    public static final Music heathens = Gdx.audio.newMusic(Gdx.files.internal("Heathens - Twenty One Pilots .mp3"));
    public static final Music betweenTheBars = Gdx.audio.newMusic(Gdx.files.internal("Elliot Smith – Between The Bars.mp3"));
    public static final Map<String, Integer> keyMap = new HashMap<>();

    static {
        keyMap.put("A", Input.Keys.A);
        keyMap.put("B", Input.Keys.B);
        keyMap.put("C", Input.Keys.C);
        keyMap.put("D", Input.Keys.D);
        keyMap.put("E", Input.Keys.E);
        keyMap.put("F", Input.Keys.F);
        keyMap.put("G", Input.Keys.G);
        keyMap.put("H", Input.Keys.H);
        keyMap.put("I", Input.Keys.I);
        keyMap.put("J", Input.Keys.J);
        keyMap.put("K", Input.Keys.K);
        keyMap.put("L", Input.Keys.L);
        keyMap.put("M", Input.Keys.M);
        keyMap.put("N", Input.Keys.N);
        keyMap.put("O", Input.Keys.O);
        keyMap.put("P", Input.Keys.P);
        keyMap.put("Q", Input.Keys.Q);
        keyMap.put("R", Input.Keys.R);
        keyMap.put("S", Input.Keys.S);
        keyMap.put("T", Input.Keys.T);
        keyMap.put("U", Input.Keys.U);
        keyMap.put("V", Input.Keys.V);
        keyMap.put("W", Input.Keys.W);
        keyMap.put("X", Input.Keys.X);
        keyMap.put("Y", Input.Keys.Y);
        keyMap.put("Z", Input.Keys.Z);
        keyMap.put("Up", Input.Keys.UP);
        keyMap.put("Down", Input.Keys.DOWN);
        keyMap.put("Left", Input.Keys.LEFT);
        keyMap.put("Right", Input.Keys.RIGHT);
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

    public static void setCustomCursor() {
        Pixmap pixmap = new Pixmap(Gdx.files.internal("T_Cursor.png"));
        int xHotspot = pixmap.getWidth() / 2;
        int yHotspot = pixmap.getHeight() / 2;
        Cursor cursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot);
        Gdx.graphics.setCursor(cursor);
        pixmap.dispose();
    }

    public static void resetToDefault() {
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
    }
}

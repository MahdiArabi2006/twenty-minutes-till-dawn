package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.GameAsset;
import io.github.some_example_name.model.Music;

public class SettingMenu extends Menu {
    private final Stage stage;
    private final Table table;
    private final CheckBox musicToggle, sfxToggle, autoReloadToggle, bwToggle;
    private final Slider musicVolumeSlider;
    private final SelectBox<String> musicSelector;
    private final SelectBox<String> upKeySelector, downKeySelector,
        leftKeySelector, rightKeySelector, reloadKeySelector, pauseKeySelector;
    private final TextButton backButton;
    private final Image background;

    public SettingMenu() {
        this.stage = new Stage(Main.getInstance().getViewport(), Main.getInstance().getBatch());
        this.table = new Table();
        Texture backgroundTexture = new Texture(Gdx.files.internal("background2.jpg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.background = new Image(backgroundTexture);
        this.musicToggle = new CheckBox("Music On/Off", GameAsset.getMenuSkin());
        this.sfxToggle = new CheckBox("SFX On/Off", GameAsset.getMenuSkin());
        this.autoReloadToggle = new CheckBox("Auto Reload On/Off", GameAsset.getMenuSkin());
        this.bwToggle = new CheckBox("Black & White Mode On/Off", GameAsset.getMenuSkin());
        this.musicVolumeSlider = new Slider(0f, 1f, 0.01f, false, GameAsset.getMenuSkin());
        this.musicSelector = new SelectBox<>(GameAsset.getMenuSkin());
        this.upKeySelector = new SelectBox<>(GameAsset.getMenuSkin());
        this.downKeySelector = new SelectBox<>(GameAsset.getMenuSkin());
        this.leftKeySelector = new SelectBox<>(GameAsset.getMenuSkin());
        this.rightKeySelector = new SelectBox<>(GameAsset.getMenuSkin());
        this.reloadKeySelector = new SelectBox<>(GameAsset.getMenuSkin());
        this.pauseKeySelector = new SelectBox<>(GameAsset.getMenuSkin());
        this.backButton = new TextButton("Back", GameAsset.getMenuSkin());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.defaults().pad(10).width(400).height(60);
        table.align(Align.top);

        musicToggle.setChecked(App.isEnableMusic());
        musicToggle.addListener(e -> {
            App.setEnableMusic(musicToggle.isChecked());
            for (io.github.some_example_name.model.Music m : io.github.some_example_name.model.Music.values())
                m.pause();
            if (App.isEnableMusic()) {
                if (App.getPlayedMusic()!=null) App.getPlayedMusic().play();
            }
            return false;
        });

        musicVolumeSlider.setValue(App.getMusicVolume());
        musicVolumeSlider.addListener(e -> {
            for (io.github.some_example_name.model.Music m : io.github.some_example_name.model.Music.values())
                m.changeVolume(App.getMusicVolume());
            return false;
        });

        musicSelector.setItems(io.github.some_example_name.model.Music.BETWEEN_THE_BARS.getName(),
            io.github.some_example_name.model.Music.REQUIEM_FOR_A_DREAM.getName(),
            io.github.some_example_name.model.Music.HEATHENS.getName());
        musicSelector.setSelected(Music.REQUIEM_FOR_A_DREAM.getName());
        musicSelector.addListener(e -> {
            for (io.github.some_example_name.model.Music m : io.github.some_example_name.model.Music.values())
                m.pause();
            int index = musicSelector.getSelectedIndex();
            io.github.some_example_name.model.Music.values()[index].changeVolume(App.getMusicVolume());
            App.setPlayedMusic(Music.values()[index]);
            if (App.isEnableMusic()) App.getPlayedMusic().play();
            return false;
        });

        sfxToggle.setChecked(App.isEnableSFX());
        sfxToggle.addListener(e -> {
            App.setEnableSFX(sfxToggle.isChecked());
            return false;
        });

        autoReloadToggle.setChecked(App.getLoggedInUser().isAutoReloadingEnable());
        autoReloadToggle.addListener(e -> {
            App.getLoggedInUser().setAutoReloadingEnable(autoReloadToggle.isChecked());
            return false;
        });

        bwToggle.setChecked(App.isBlackWhiteMode());
        bwToggle.addListener(e -> {
            App.setBlackWhiteMode(bwToggle.isChecked());
            return false;
        });

        upKeySelector.setItems(GameAsset.keyMap.keySet().toArray(new String[0]));
        upKeySelector.setSelected("W");
        downKeySelector.setItems(GameAsset.keyMap.keySet().toArray(new String[0]));
        downKeySelector.setSelected("S");
        rightKeySelector.setItems(GameAsset.keyMap.keySet().toArray(new String[0]));
        rightKeySelector.setSelected("D");
        leftKeySelector.setItems(GameAsset.keyMap.keySet().toArray(new String[0]));
        leftKeySelector.setSelected("A");
        pauseKeySelector.setItems(GameAsset.keyMap.keySet().toArray(new String[0]));
        pauseKeySelector.setSelected("P");
        reloadKeySelector.setItems(GameAsset.keyMap.keySet().toArray(new String[0]));
        reloadKeySelector.setSelected("R");

        upKeySelector.addListener(e -> {
            App.getLoggedInUser().getGameKey().setMOVE_UP(GameAsset.keyMap.get(upKeySelector.getSelected()));
            return false;
        });
        downKeySelector.addListener(e -> {
            App.getLoggedInUser().getGameKey().setMOVE_DOWN(GameAsset.keyMap.get(downKeySelector.getSelected()));
            return false;
        });
        rightKeySelector.addListener(e -> {
            App.getLoggedInUser().getGameKey().setMOVE_RIGHT(GameAsset.keyMap.get(rightKeySelector.getSelected()));
            return false;
        });
        leftKeySelector.addListener(e -> {
            App.getLoggedInUser().getGameKey().setMOVE_LEFT(GameAsset.keyMap.get(leftKeySelector.getSelected()));
            return false;
        });
        reloadKeySelector.addListener(e -> {
            App.getLoggedInUser().getGameKey().setRELOAD(GameAsset.keyMap.get(reloadKeySelector.getSelected()));
            return false;
        });
        pauseKeySelector.addListener(e -> {
            App.getLoggedInUser().getGameKey().setPAUSE(GameAsset.keyMap.get(pauseKeySelector.getSelected()));
            return false;
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play();
                Main.getInstance().setScreen(new MainMenu());
            }
        });

        table.add(new Label("Setting Menu",GameAsset.getMenuSkin(),"title")).padRight(150).row();
        table.add(musicToggle).row();
        table.add(new Label("Music Volume", GameAsset.getMenuSkin())).row();
        table.add(musicVolumeSlider).row();
        table.add(new Label("Choose Music", GameAsset.getMenuSkin())).row();
        table.add(musicSelector).row();
        table.add(sfxToggle).row();
        table.add(autoReloadToggle).row();
        table.add(bwToggle).row();
        Table keyTable = new Table(GameAsset.getMenuSkin());
        keyTable.defaults().pad(10).width(300).height(50);
        keyTable.add(new Label("Move Up", GameAsset.getMenuSkin()));
        keyTable.add(new Label("Move Down", GameAsset.getMenuSkin()));
        keyTable.add(new Label("Move Right", GameAsset.getMenuSkin()));
        keyTable.add(new Label("Move Left", GameAsset.getMenuSkin()));
        keyTable.add(new Label("Reload", GameAsset.getMenuSkin()));
        keyTable.add(new Label("Pause", GameAsset.getMenuSkin())).colspan(3);
        keyTable.row();
        keyTable.add(upKeySelector);
        keyTable.add(downKeySelector);
        keyTable.add(rightKeySelector);
        keyTable.add(leftKeySelector);
        keyTable.add(reloadKeySelector);
        keyTable.add(pauseKeySelector).colspan(3);
        keyTable.row();
        table.add(keyTable).colspan(1).bottom().row();
        table.add(backButton).padTop(30);

        background.setFillParent(true);
        stage.addActor(background);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        if (stage!=null) stage.dispose();
    }
}

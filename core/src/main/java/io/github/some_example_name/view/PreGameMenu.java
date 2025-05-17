package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.PreGameController;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.Character;
import io.github.some_example_name.model.GameAsset;
import io.github.some_example_name.model.WeaponType;

public class PreGameMenu extends Menu {
    private final PreGameController controller = new PreGameController(this);
    private final Stage stage;
    private final TextButton heroSelectionButton, weaponSelectionButton, startGameButton, backButton;
    private final SelectBox selectBoxTime;
    private final Dialog heroSelectionDialog, weaponSelectionDialog;
    private boolean playAsGuest;
    private Label errorLabel;
    private final Table table;
    private final Image background;
    private Character character;
    private WeaponType weaponType;
    private Integer time;

    public PreGameMenu(boolean playAsGuest) {
        this.playAsGuest = playAsGuest;
        this.stage = new Stage(Main.getInstance().getViewport(), Main.getInstance().getBatch());
        this.table = new Table();
        Texture backgroundTexture = new Texture(Gdx.files.internal("background5.png"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.background = new Image(backgroundTexture);
        this.heroSelectionButton = new TextButton("hero selection", GameAsset.getMenuSkin());
        this.weaponSelectionButton = new TextButton("weapon selection", GameAsset.getMenuSkin());
        this.startGameButton = new TextButton("start game", GameAsset.getMenuSkin());
        this.backButton = new TextButton("back", GameAsset.getMenuSkin());
        this.heroSelectionDialog = new Dialog("hero selection", GameAsset.getMenuSkin());
        this.weaponSelectionDialog = new Dialog("weapon selection", GameAsset.getMenuSkin());
        errorLabel = new Label("", GameAsset.getMenuSkin());
        errorLabel.setColor(Color.RED);
        this.selectBoxTime = new SelectBox<>(GameAsset.getMenuSkin());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        setupWeaponSelectionDialog();
        setupSelectionHeroDialog();
        setupUI();
    }

    private void setupWeaponSelectionDialog() {
        weaponSelectionDialog.getContentTable().clear();
        weaponSelectionDialog.getButtonTable().clear();
        Table avatarTable = new Table();
        avatarTable.defaults().pad(10);
        for (WeaponType value : WeaponType.values()) {
            Texture avatarTexture = value.getWeaponTexture();
            Image avatar = new Image(avatarTexture);
            avatar.setSize(100, 100);
            avatar.setScaling(Scaling.fit);
            avatar.setTouchable(Touchable.enabled);
            avatar.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                    weaponType = value;
                    weaponSelectionDialog.hide();
                }
            });
            Label label = new Label(value.getName(), GameAsset.getMenuSkin());
            label.setAlignment(Align.center);
            Table itemTable = new Table();
            itemTable.add(avatar).size(100, 100).row();
            itemTable.add(label).padTop(5).width(100).center();
            avatarTable.add(itemTable).pad(10);
        }
        TextButton cancelButton = new TextButton("Cancel", GameAsset.getMenuSkin());
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                weaponSelectionDialog.hide();
            }
        });
        weaponSelectionDialog.getContentTable().add(avatarTable).pad(20);
        weaponSelectionDialog.getButtonTable().defaults().pad(10);
        weaponSelectionDialog.button(cancelButton);
    }

    private void setupSelectionHeroDialog() {
        heroSelectionDialog.getContentTable().clear();
        heroSelectionDialog.getButtonTable().clear();
        Table avatarTable = new Table();
        avatarTable.defaults().pad(10);
        for (Character value : Character.values()) {
            Image avatar = value.getAvatar();
            avatar.setSize(100, 100);
            avatar.setScaling(Scaling.fit);
            avatar.setTouchable(Touchable.enabled);
            avatar.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                    character = value;
                    heroSelectionDialog.hide();
                }
            });
            Label label = new Label(value.getName(), GameAsset.getMenuSkin());
            label.setAlignment(Align.center);
            Table itemTable = new Table();
            itemTable.add(avatar).size(100, 100).row();
            itemTable.add(label).padTop(5).width(100).center();
            avatarTable.add(itemTable).pad(10);
        }
        TextButton cancelButton = new TextButton("Cancel", GameAsset.getMenuSkin());
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                heroSelectionDialog.hide();
            }
        });
        heroSelectionDialog.getContentTable().add(avatarTable).pad(20);
        heroSelectionDialog.getButtonTable().defaults().pad(10);
        heroSelectionDialog.button(cancelButton);
    }

    private void setupUI() {
        table.clear();
        table.setFillParent(true);
        table.defaults().pad(10).width(300).height(40);
        table.align(Align.bottom);

        table.add(errorLabel).colspan(2).height(20).row();
        table.add(heroSelectionButton).align(Align.left).width(450).height(100);
        heroSelectionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                heroSelectionDialog.show(stage);
            }
        });
        table.add(weaponSelectionButton).align(Align.center).width(450).height(100);
        weaponSelectionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                weaponSelectionDialog.show(stage);
            }
        });
        Array<String> timeItems = new Array<>();
        timeItems.add("time");
        timeItems.add("20");
        timeItems.add("10");
        timeItems.add("5");
        timeItems.add("2");
        selectBoxTime.setItems(timeItems);
        table.add(selectBoxTime).align(Align.right).width(250).height(100);
        selectBoxTime.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!selectBoxTime.getSelected().equals("time")){
                    if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                    time = Integer.parseInt((String) selectBoxTime.getSelected());
                }
            }
        });
        table.row();
        table.add(startGameButton).align(Align.left).width(300).height(100);
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                if (!playAsGuest){
                    controller.startGame();
                }
                else {
                    controller.startGameAsGuest();
                }
            }
        });
        table.add(backButton).align(Align.right).width(250).height(100);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                if (!playAsGuest){
                    Main.getInstance().setScreen(new MainMenu());
                }
                else {
                    Main.getInstance().setScreen(new FirstMenu());
                }
            }
        });

        background.setFillParent(true);
        stage.addActor(background);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
        stage.clear();
    }

    @Override
    public void dispose() {
        if (stage!=null) stage.dispose();
    }

    public void showError(String message) {
        errorLabel.setText(message);
        errorLabel.addAction(Actions.sequence(
            Actions.alpha(0),
            Actions.fadeIn(0.3f),
            Actions.delay(3),
            Actions.fadeOut(1f)
        ));
    }

    public Character getCharacter() {
        return character;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public Integer getTime() {
        return time;
    }
}

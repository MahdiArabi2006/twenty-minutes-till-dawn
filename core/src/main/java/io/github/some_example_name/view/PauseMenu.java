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
import io.github.some_example_name.controller.PauseMenuController;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.GameAsset;
import io.github.some_example_name.model.LanguageManager;
import io.github.some_example_name.model.TextKey;

public class PauseMenu extends Menu {
    private final PauseMenuController controller = new PauseMenuController(this);
    private final Stage stage;
    private final TextButton resumeButton;
    private final TextButton giveUpButton;
    private final TextButton saveAndQuitButton;
    private final CheckBox bwToggle;
    private final Table table;
    private final Table table1;
    private final Table table2;
    private final Image background;

    public PauseMenu() {
        this.stage = new Stage(Main.getInstance().getViewport(), Main.getInstance().getBatch());
        this.table = new Table();
        this.table1 = new Table();
        this.table2 = new Table();
        this.bwToggle = new CheckBox(LanguageManager.get(TextKey.MENU_BW_TOGGLE), GameAsset.getMenuSkin());
        Texture backgroundTexture = new Texture(Gdx.files.internal("background2.jpg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.background = new Image(backgroundTexture);
        this.resumeButton = new TextButton(LanguageManager.get(TextKey.MENU_RESUME_BUTTON), GameAsset.getMenuSkin());
        this.giveUpButton = new TextButton(LanguageManager.get(TextKey.MENU_GIVE_UP_BUTTON), GameAsset.getMenuSkin());
        this.saveAndQuitButton = new TextButton(LanguageManager.get(TextKey.MENU_SAVE_AND_QUIT_BUTTON), GameAsset.getMenuSkin());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        setupUI();
    }

    private void setupUI() {
        table.clear();
        table.setFillParent(true);
        table.defaults().pad(10).width(300).height(40);
        table.align(Align.topLeft);
        Label title = new Label(LanguageManager.get(TextKey.MENU_PAUSE_MENU_BUTTON), GameAsset.getMenuSkin(), "title");
        title.setAlignment(Align.center);
        table.add(title).colspan(2).right().padTop(40).padLeft(300).padBottom(50).row();

        table.add(new Label(LanguageManager.get(TextKey.MENU_CHEAT_CODES_LABEL) + ": ", GameAsset.getMenuSkin())).row();
        table.add(new Label(LanguageManager.get(TextKey.MENU_CHEAT1) + " :     p", GameAsset.getMenuSkin())).row();
        table.add(new Label(LanguageManager.get(TextKey.MENU_CHEAT2) + " :    l", GameAsset.getMenuSkin())).row();
        table.add(new Label(LanguageManager.get(TextKey.MENU_CHEAT3) + " :   o", GameAsset.getMenuSkin())).row();
        table.add(new Label(LanguageManager.get(TextKey.MENU_CHEAT4) + " :    b", GameAsset.getMenuSkin())).row();
        table.add(new Label(LanguageManager.get(TextKey.MENU_CHEAT5) + " :   k", GameAsset.getMenuSkin())).row();


        table1.clear();
        table1.setFillParent(true);
        table1.defaults().pad(10).width(300).height(40);
        table1.align(Align.topRight);
        table1.add(new Label(LanguageManager.get(TextKey.MENU_PLAYER_ABILITY_LABEL) + ": ", GameAsset.getMenuSkin())).row();
        if (App.getLoggedInUser().getLastGame().getPlayer().getAbility()!=null) {
            table1.add(new Image(App.getLoggedInUser().getLastGame().getPlayer().getAbility().getTexture())).size(270, 313).padBottom(20).row();
            table1.add(new Label(App.getLoggedInUser().getLastGame().getPlayer().getAbility().getName(), GameAsset.getMenuSkin()));
        }

        table2.clear();
        table2.setFillParent(true);
        table2.defaults().pad(10).width(300).height(40);
        table2.align(Align.bottom);
        bwToggle.setChecked(App.isBlackWhiteMode());
        bwToggle.addListener(e -> {
            App.setBlackWhiteMode(bwToggle.isChecked());
            return false;
        });
        table2.add(bwToggle).align(Align.center).row();
        table2.add(resumeButton).align(Align.left).width(350).height(100).padBottom(150);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                Main.getInstance().setScreen(new GameView());
            }
        });
        table2.add(giveUpButton).align(Align.center).width(350).height(100).padBottom(150);
        giveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                controller.giveUp();
            }
        });
        table2.add(saveAndQuitButton).align(Align.right).width(350).height(100).padBottom(150);
        saveAndQuitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!App.getLoggedInUser().getLastGame().isPlayAsGuest()) {
                    if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                    controller.saveAndQuit();
                }
            }
        });

        background.setFillParent(true);
        stage.addActor(background);
        stage.addActor(table);
        stage.addActor(table1);
        stage.addActor(table2);
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
}

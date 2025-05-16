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
import io.github.some_example_name.controller.MainMenuController;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.GameAsset;

public class MainMenu extends Menu {
    private final MainMenuController controller = new MainMenuController(this);
    private final Stage stage;
    private final TextButton settingButton, profileButton, preGameButton,
        scoreBoardButton, talentButton, continueLastGameButton, logoutButton;
    private final Table table1;
    private final Table table2;
    private final Image background;

    public MainMenu() {
        this.stage = new Stage(Main.getInstance().getViewport(), Main.getInstance().getBatch());
        this.table1 = new Table();
        this.table2 = new Table();
        Texture backgroundTexture = new Texture(Gdx.files.internal("background2.jpg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.background = new Image(backgroundTexture);
        this.settingButton = new TextButton("setting menu", GameAsset.getMenuSkin());
        this.preGameButton = new TextButton("pre-game menu", GameAsset.getMenuSkin());
        this.scoreBoardButton = new TextButton("score board menu", GameAsset.getMenuSkin());
        this.talentButton = new TextButton("talent menu", GameAsset.getMenuSkin());
        this.profileButton = new TextButton("profile menu", GameAsset.getMenuSkin());
        this.continueLastGameButton = new TextButton("continue last game", GameAsset.getMenuSkin());
        this.logoutButton = new TextButton("logout", GameAsset.getMenuSkin());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        setupUI();
    }

    private void setupUI() {
        table1.clear();
        table2.clear();
        table1.setFillParent(true);
        table1.defaults().pad(10).width(600).height(40);
        table1.align(Align.topRight);

        table1.add(settingButton).align(Align.left).width(450).height(100);
        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new SettingMenu());
            }
        });
        table1.add(profileButton).align(Align.right).width(450).height(100);
        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new ProfileMenu());
            }
        });
        table1.row();
        table1.add(preGameButton).align(Align.left).width(450).height(100);
        preGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new PreGameMenu());
            }
        });
        table1.add(talentButton).align(Align.right).width(450).height(100);
        talentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new TalentMenu());
            }
        });
        table1.row();
        table1.add(scoreBoardButton).align(Align.left).width(450).height(100);
        scoreBoardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new ScoreBoard());
            }
        });
        table1.add(continueLastGameButton).align(Align.right).width(450).height(100);
        continueLastGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        table1.row();
        table1.add(logoutButton).align(Align.center).width(450).height(100);
        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.logout();
            }
        });

        table2.setFillParent(true);
        table2.defaults().pad(10).width(300).height(40);
        table2.align(Align.topLeft);
        table2.add(App.getLoggedInUser().getAvatar()).size(270, 313).padBottom(20).row();
        table2.add(new Label("username: " + App.getLoggedInUser().getUsername(), GameAsset.getMenuSkin())).colspan(2).row();
        table2.add(new Label("score: " + App.getLoggedInUser().getScore(), GameAsset.getMenuSkin()));

        background.setFillParent(true);
        stage.addActor(background);
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

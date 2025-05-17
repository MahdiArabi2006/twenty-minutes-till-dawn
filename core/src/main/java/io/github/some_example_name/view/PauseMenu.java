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

public class PauseMenu extends Menu {
    private final PauseMenuController controller = new PauseMenuController(this);
    private final Stage stage;
    private final TextButton resumeButton;
    private final TextButton giveUpButton;
    private final TextButton saveAndQuitButton;
    private final TextButton makeScreenWhiteAndBlackButton;
    private final Table table;
    private final Table table1;
    private final Table table2;
    private final Image background;

    public PauseMenu() {
        this.stage = new Stage(Main.getInstance().getViewport(), Main.getInstance().getBatch());
        this.table = new Table();
        this.table1 = new Table();
        this.table2 = new Table();
        Texture backgroundTexture = new Texture(Gdx.files.internal("background2.jpg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.background = new Image(backgroundTexture);
        this.resumeButton = new TextButton("resume", GameAsset.getMenuSkin());
        this.giveUpButton = new TextButton("give up", GameAsset.getMenuSkin());
        this.saveAndQuitButton = new TextButton("save and quit", GameAsset.getMenuSkin());
        this.makeScreenWhiteAndBlackButton = new TextButton("white and black screen", GameAsset.getMenuSkin());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        setupUI();
    }

    private void setupUI(){
        table.clear();
        table.setFillParent(true);
        table.defaults().pad(10).width(300).height(40);
        table.align(Align.topLeft);
        table.add(new Label("cheat codes: ",GameAsset.getMenuSkin())).row();
        table.add(new Label("reduce time a minute :     p",GameAsset.getMenuSkin())).row();
        table.add(new Label("increase player level :    l",GameAsset.getMenuSkin())).row();
        table.add(new Label("make player health full:   o",GameAsset.getMenuSkin())).row();

        table1.clear();
        table1.setFillParent(true);
        table1.defaults().pad(10).width(300).height(40);
        table1.align(Align.topRight);
        table1.add(new Label("player ability: ",GameAsset.getMenuSkin())).row();
        if (App.getLoggedInUser().getLastGame().getPlayer().getAbility()!=null){
            table1.add(new Image(App.getLoggedInUser().getLastGame().getPlayer().getAbility().getTexture())).size(270,313).padBottom(20).row();
            table1.add(new Label(App.getLoggedInUser().getLastGame().getPlayer().getAbility().getName(),GameAsset.getMenuSkin()));
        }


        table2.clear();
        table2.setFillParent(true);
        table2.defaults().pad(10).width(300).height(40);
        table2.align(Align.bottom);
        table2.add(resumeButton).align(Align.left).width(350).height(100);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new GameView());
            }
        });
        table2.add(giveUpButton).align(Align.center).width(350).height(100);
        giveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.giveUp();
            }
        });
        table2.add(makeScreenWhiteAndBlackButton).align(Align.right).width(550).height(100);
        makeScreenWhiteAndBlackButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        table2.row();
        table2.add(saveAndQuitButton).align(Align.center).width(350).height(100);
        saveAndQuitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!App.getLoggedInUser().getLastGame().isPlayAsGuest()){

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

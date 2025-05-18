package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.Ability;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.GameAsset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelUpView implements Screen {
    private final Stage stage;
    private final Dialog abilityDialog;
    private final TextButton chooseAbility;
    private final TextButton continueTheGame;
    private final Table table;
    private final Image background;

    public LevelUpView() {
        this.stage = new Stage(Main.getInstance().getViewport(), Main.getInstance().getBatch());
        this.table = new Table();
        Texture backgroundTexture = new Texture(Gdx.files.internal("background2.jpg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.background = new Image(backgroundTexture);
        this.continueTheGame = new TextButton("Continue", GameAsset.getMenuSkin());
        this.chooseAbility = new TextButton("Choose Ability", GameAsset.getMenuSkin());
        this.abilityDialog = new Dialog("forget password", GameAsset.getMenuSkin());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        setupDialog();
        setupUI();
    }

    private void setupDialog() {
        abilityDialog.getContentTable().clear();
        abilityDialog.getButtonTable().clear();
        abilityDialog.setModal(true);
        Table abilityTable = new Table();
        abilityTable.defaults().pad(10);
        List<Ability> abilityList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Random random = new Random();
            abilityList.add(Ability.values()[random.nextInt(0, 4)]);
        }
        for (Ability ability : abilityList) {
            Texture texture = ability.getTexture();
            Image abilityAvatar = new Image(texture);
            abilityAvatar.setSize(100, 100);
            abilityAvatar.setScaling(Scaling.fit);
            abilityAvatar.setTouchable(Touchable.enabled);
            abilityAvatar.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                    App.getLoggedInUser().getLastGame().getPlayer().changeAbility(ability);
                    abilityDialog.hide();
                }
            });
            Label label = new Label(ability.getName(), GameAsset.getMenuSkin());
            label.setAlignment(Align.center);
            Table itemTable = new Table();
            itemTable.add(abilityAvatar).size(100, 100).pad(60).row();
            itemTable.add(label).padTop(5).width(100).pad(100).center();
            abilityTable.add(itemTable).pad(10);
        }
        abilityDialog.getContentTable().add(abilityTable).pad(20);
        abilityDialog.getButtonTable().defaults().pad(10);

    }

    private void setupUI() {
        table.clear();
        table.setFillParent(true);
        table.defaults().pad(10).width(300).height(40);
        table.align(Align.center);
        table.add(new Label("Level " + App.getLoggedInUser().getLastGame().getPlayer().getLevel(), GameAsset.getMenuSkin(), "title")).align(Align.center).padBottom(100).row();

        chooseAbility.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                abilityDialog.show(stage);
            }
        });
        table.add(chooseAbility).align(Align.center).row();
        continueTheGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new GameView());
            }
        });
        table.add(continueTheGame).align(Align.center).row();

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
}

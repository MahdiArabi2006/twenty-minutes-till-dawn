package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.GameAsset;

public class TalentMenu extends Menu {
    private final Stage stage;
    private final Table table;
    private final TextButton backButton;
    private final Image background;

    public TalentMenu() {
        this.stage = new Stage(Main.getInstance().getViewport(), Main.getInstance().getBatch());
        this.table = new Table();
        Texture backgroundTexture = new Texture(Gdx.files.internal("background2.jpg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.background = new Image(backgroundTexture);
        this.backButton = new TextButton("Back", GameAsset.getMenuSkin());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.defaults().pad(10);
        table.align(Align.top);

        Label title = new Label("Talent Menu", GameAsset.getMenuSkin(), "title");
        title.setAlignment(Align.center);
        table.add(title).padTop(20).row();

        Table charTable = new Table(GameAsset.getMenuSkin());
        charTable.defaults().pad(5);
        charTable.add("Character").left();
        charTable.add("HP").center();
        charTable.add("Speed").center().row();
        charTable.add("Shana").left(); charTable.add("4").center(); charTable.add("4").center().row();
        charTable.add("Diamond").left(); charTable.add("7").center(); charTable.add("1").center().row();
        charTable.add("Scarlet").left(); charTable.add("3").center(); charTable.add("5").center().row();
        charTable.add("Lilith").left(); charTable.add("5").center(); charTable.add("3").center().row();
        charTable.add("Dasher").left(); charTable.add("2").center(); charTable.add("10").center().row();

        Table cheatTable = new Table(GameAsset.getMenuSkin());
        cheatTable.defaults().pad(5).left();
        cheatTable.add(new Label("Cheat Codes :", GameAsset.getMenuSkin())).left().row();
        cheatTable.add(new Label("Reduce remaining time by 1 minute", GameAsset.getMenuSkin())).left().row();
        cheatTable.add(new Label("Increase player level", GameAsset.getMenuSkin())).left().row();
        cheatTable.add(new Label("Add HP only if player is not at max HP", GameAsset.getMenuSkin())).left().row();
        cheatTable.add(new Label("Boss pass", GameAsset.getMenuSkin())).left().row();
        cheatTable.add(new Label("Implement one custom cheat", GameAsset.getMenuSkin())).left().row();

        Table rowTable = new Table();
        rowTable.defaults().pad(20);
        rowTable.add(charTable).top().left().expandX().fillX().padRight(30);
        rowTable.add(cheatTable).top().left().expandX().fillX();

        table.add(rowTable).expandX().fillX().padTop(30).row();
        Table abilityTable = new Table(GameAsset.getMenuSkin());
        abilityTable.defaults().pad(5);
        abilityTable.add("Ability").left();
        abilityTable.add("Description").left().row();
        abilityTable.add("VITALITY").left(); abilityTable.add("Increase max HP by 1").left().row();
        abilityTable.add("DAMAGER").left(); abilityTable.add("+25% weapon damage for 10s").left().row();
        abilityTable.add("PROCREASE").left(); abilityTable.add("+1 weapon projectile").left().row();
        abilityTable.add("AMOCREASE").left(); abilityTable.add("+1 weapon max ammo").left().row();
        abilityTable.add("SPEEDY").left(); abilityTable.add("2x player speed for 10s").left().row();
        table.add(abilityTable).expandX().fillX().center().row();

        table.add(backButton).padTop(30);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play();
                Main.getInstance().setScreen(new MainMenu());
            }
        });

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
        stage.clear();
    }

    @Override
    public void dispose() {
        if (stage!=null) stage.dispose();
    }
}

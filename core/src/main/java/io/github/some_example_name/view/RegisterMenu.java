package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.controller.RegisterMenuController;
import io.github.some_example_name.model.GameAsset;

public class RegisterMenu extends Menu{
    private static RegisterMenu instance;
    private final RegisterMenuController controller = new RegisterMenuController(this);
    private Stage stage;
    private TextButton textButton;
    private Label label;
    private TextField textField;
    private Table table;
    private Image background;


    private RegisterMenu(){
        this.stage = new Stage(new ScreenViewport());
        this.table = new Table();
        Texture backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.background = new Image(backgroundTexture);
        this.textButton = new TextButton("textButton", GameAsset.getMenuSkin());
        this.label = new Label("label", GameAsset.getMenuSkin());
        this.textField = new TextField("textField", GameAsset.getMenuSkin());
    }
    public static RegisterMenu getInstance(){
        if (instance==null) instance = new RegisterMenu();
        return instance;
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

        table.clear();
        table.setFillParent(true);
        table.left();
        table.add(label);
        table.row().pad(10, 0, 10, 0);
        table.add(textField).width(600);
        table.row().pad(10, 0, 10, 0);
        table.add(textButton);

        stage.clear();
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

    }

    @Override
    public void dispose() {
        if (stage!=null) stage.dispose();
    }
}

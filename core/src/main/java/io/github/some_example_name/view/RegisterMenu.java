package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.RegisterMenuController;
import io.github.some_example_name.model.GameAsset;

public class RegisterMenu extends Menu {
    private static RegisterMenu instance;
    private final RegisterMenuController controller = new RegisterMenuController(this);
    private final Stage stage;
    private final TextButton registerButton;
    private final TextButton guestButton;
    private final TextButton backButton;
    private final TextField username, password, answerSecurityQuestion;
    private Label errorLabel;
    private final Table table;
    private final Image background;


    private RegisterMenu() {
        this.stage = new Stage(Main.getInstance().getViewport(), Main.getInstance().getBatch());
        this.table = new Table();
        Texture backgroundTexture = new Texture(Gdx.files.internal("background2.jpg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.background = new Image(backgroundTexture);
        this.registerButton = new TextButton("Sign Up", GameAsset.getMenuSkin());
        this.guestButton = new TextButton("Guest", GameAsset.getMenuSkin());
        this.backButton = new TextButton("back", GameAsset.getMenuSkin());
        username = createTextField("username");
        answerSecurityQuestion = createPasswordField("answer");
        password = createPasswordField("password");
        errorLabel = new Label("", GameAsset.getMenuSkin());
        errorLabel.setColor(Color.RED);
    }

    public static RegisterMenu getInstance() {
        if (instance==null) instance = new RegisterMenu();
        return instance;
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

        table.add(new Label("Sign Up", GameAsset.getMenuSkin())).colspan(2).row();
        table.add(new Label("username: ", GameAsset.getMenuSkin())).right();
        table.add(username).width(400).height(70).pad(5).row();
        table.add(new Label("password: ", GameAsset.getMenuSkin())).right();
        table.add(password).width(400).height(70).pad(5).row();
        table.add(new Label("Your favorite movie: ", GameAsset.getMenuSkin()));
        table.add(answerSecurityQuestion).width(400).height(70).pad(5).row();
        table.add(errorLabel).colspan(2).height(20).row();
        table.add(registerButton).align(Align.left).width(250).height(100);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.register(username.getText(), password.getText(), answerSecurityQuestion.getText());
            }
        });
        table.add(guestButton).align(Align.center).width(250).height(100);
        guestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        table.add(backButton).align(Align.right).width(250).height(100);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(FirstMenu.getInstance());
            }
        });

        background.setFillParent(true);
        stage.addActor(background);
        stage.addActor(table);
    }

    private TextField createTextField(String hint) {
        TextField textField = new TextField("", GameAsset.getMenuSkin());
        textField.setMessageText(hint);
        return textField;
    }

    private TextField createPasswordField(String hint) {
        TextField field = new TextField("", GameAsset.getMenuSkin());
        field.setMessageText(hint);
        field.setPasswordMode(true);
        field.setPasswordCharacter('•');
        return field;
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

    public void showSuccessMessage(String message) {
        Label successLabel = new Label(message, GameAsset.getMenuSkin());
        successLabel.setColor(Color.GREEN);

        successLabel.addAction(Actions.sequence(
            Actions.alpha(0),
            Actions.fadeIn(1f),
            Actions.delay(2f),
            Actions.fadeOut(1f)
        ));

        stage.addActor(successLabel);
    }

    public void displayAvatar(Runnable onComplete, Image avatarImage) {
        avatarImage.setSize(0, 0);
        avatarImage.setPosition(stage.getWidth() / 2, stage.getHeight() / 2, Align.center);

        avatarImage.addAction(Actions.sequence(
            Actions.parallel(
                Actions.scaleTo(1, 1, 0.5f, Interpolation.bounceOut),
                Actions.rotateBy(360, 0.5f)
            ),
            Actions.delay(1.5f),
            Actions.run(onComplete)
        ));

        stage.addActor(avatarImage);
    }

    public TextButton getRegisterButton() {
        return registerButton;
    }

    public TextField getUsername() {
        return username;
    }

    public TextField getPassword() {
        return password;
    }


    public TextField getAnswerSecurityQuestion() {
        return answerSecurityQuestion;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public void setErrorLabel(Label errorLabel) {
        this.errorLabel = errorLabel;
    }

    public Table getTable() {
        return table;
    }

    public TextButton getGuestButton() {
        return guestButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }
}

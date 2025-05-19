package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.LoginMenuController;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.GameAsset;
import io.github.some_example_name.model.LanguageManager;
import io.github.some_example_name.model.TextKey;

public class LoginMenu extends Menu {
    private final LoginMenuController controller = new LoginMenuController(this);
    private final Stage stage;
    private final TextButton loginButton;
    private final TextButton backButton;
    private final TextButton forgetPasswordButton;
    private final TextField username, password;
    private TextField answer;
    private TextField newPassword;
    private final Dialog forgetPasswordDialog;
    private Label errorLabel;
    private final Table table;
    private final Image background;

    public LoginMenu() {
        this.stage = new Stage(Main.getInstance().getViewport(), Main.getInstance().getBatch());
        this.table = new Table();
        Texture backgroundTexture = new Texture(Gdx.files.internal("background2.jpg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.background = new Image(backgroundTexture);
        this.loginButton = new TextButton(LanguageManager.get(TextKey.MENU_LOGIN_TEXT_BUTTON), GameAsset.getMenuSkin());
        this.backButton = new TextButton(LanguageManager.get(TextKey.MENU_BACK_TEXT_BUTTON), GameAsset.getMenuSkin());
        this.forgetPasswordButton = new TextButton(LanguageManager.get(TextKey.MENU_FORGET_PASSWORD_DIALOG), GameAsset.getMenuSkin());
        this.forgetPasswordDialog = new Dialog(LanguageManager.get(TextKey.MENU_FORGET_PASSWORD_DIALOG), GameAsset.getMenuSkin());
        username = createTextField(LanguageManager.get(TextKey.MENU_USERNAME_LABEL));
        password = createPasswordField(LanguageManager.get(TextKey.MENU_PASSWORD_TEXT_FILED));
        errorLabel = new Label("", GameAsset.getMenuSkin());
        errorLabel.setColor(Color.RED);
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
    public void show() {
        Gdx.input.setInputProcessor(stage);
        setupForgetPasswordDialog();
        setupUI();
    }

    private void setupForgetPasswordDialog() {
        answer = new TextField(LanguageManager.get(TextKey.MENU_SECURITY_LABEL), GameAsset.getMenuSkin());
        newPassword = new TextField(LanguageManager.get(TextKey.MENU_NEW_PASSWORD_LABEL), GameAsset.getMenuSkin());
        TextButton submitButton = new TextButton(LanguageManager.get(TextKey.MENU_CONFIRM_TEXT_BUTTON), GameAsset.getMenuSkin());
        TextButton cancelButton = new TextButton(LanguageManager.get(TextKey.MENU_CANCEL_TEXT_BUTTON), GameAsset.getMenuSkin());

        Table contentTable = forgetPasswordDialog.getContentTable();
        contentTable.add(new Label(LanguageManager.get(TextKey.MENU_SECURITY_QUESTION_LABEL) + ": ", GameAsset.getMenuSkin())).padRight(10);
        contentTable.add(answer).width(300).row();
        contentTable.add(new Label(LanguageManager.get(TextKey.MENU_NEW_PASSWORD_LABEL), GameAsset.getMenuSkin())).padRight(10);
        contentTable.add(newPassword).width(300).row();
        forgetPasswordDialog.getButtonTable().defaults().pad(10);
        forgetPasswordDialog.button(submitButton, true);
        forgetPasswordDialog.button(cancelButton, false);

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                controller.forgetPassword();
            }
        });
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                forgetPasswordDialog.hide();
            }
        });
    }

    public void setupUI() {
        table.clear();
        table.setFillParent(true);
        table.defaults().pad(10).width(300).height(40);
        table.align(Align.topLeft);

        table.add(new Label("Login Menu", GameAsset.getMenuSkin(), "title")).colspan(2).padBottom(30).padTop(30).row();
        table.add(new Label(LanguageManager.get(TextKey.MENU_USERNAME_LABEL) + ": ", GameAsset.getMenuSkin())).right();
        table.add(username).width(400).height(70).pad(5).row();
        table.add(new Label(LanguageManager.get(TextKey.MENU_PASSWORD_TEXT_FILED) + " : ", GameAsset.getMenuSkin())).right();
        table.add(password).width(400).height(70).pad(5).row();
        table.add(errorLabel).colspan(2).height(20).row();
        table.add(loginButton).align(Align.left).width(250).height(100);
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                controller.login(username.getText(), password.getText());
            }
        });
        table.add(forgetPasswordButton).align(Align.center).width(450).height(100);
        forgetPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                forgetPasswordDialog.show(stage);
            }
        });
        table.add(backButton).align(Align.right).width(250).height(100);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                Main.getInstance().setScreen(new FirstMenu());
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

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public TextField getUsername() {
        return username;
    }

    public TextField getPassword() {
        return password;
    }

    public TextField getAnswer() {
        return answer;
    }

    public TextField getNewPassword() {
        return newPassword;
    }

    public Dialog getForgetPasswordDialog() {
        return forgetPasswordDialog;
    }
}

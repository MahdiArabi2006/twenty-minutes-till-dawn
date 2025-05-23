package io.github.some_example_name.view;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.ProfileMenuController;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.GameAsset;
import io.github.some_example_name.model.LanguageManager;
import io.github.some_example_name.model.TextKey;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProfileMenu extends Menu {
    private final ProfileMenuController controller = new ProfileMenuController(this);
    private final Stage stage;
    private final TextButton changeUsernameButton, changePasswordButton,
        deleteAccountButton, changeAvatarButton, backButton;
    private final Dialog changeUsernameDialog, changePasswordDialog,
        deleteAccountDialog, changeAvatarDialog;
    private final TextField newPassword, newUsername;
    private Label errorLabel;
    private final Table table;
    private final Image background;

    public ProfileMenu() {
        this.stage = new Stage(Main.getInstance().getViewport(), Main.getInstance().getBatch());
        this.table = new Table();
        Texture backgroundTexture = new Texture(Gdx.files.internal("background2.jpg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.background = new Image(backgroundTexture);
        this.newPassword = new TextField(LanguageManager.get(TextKey.MENU_PASSWORD_TEXT_FILED), GameAsset.getMenuSkin());
        this.newUsername = new TextField(LanguageManager.get(TextKey.MENU_NEW_USERNAME_TEXT_FIELD), GameAsset.getMenuSkin());
        this.changeAvatarButton = new TextButton(LanguageManager.get(TextKey.MENU_CHANGE_AVATAR_BUTTON), GameAsset.getMenuSkin());
        this.deleteAccountButton = new TextButton(LanguageManager.get(TextKey.MENU_DELETE_ACCOUNT_BUTTON), GameAsset.getMenuSkin());
        this.changePasswordButton = new TextButton(LanguageManager.get(TextKey.MENU_CHANGE_PASSWORD_BUTTON), GameAsset.getMenuSkin());
        this.changeUsernameButton = new TextButton(LanguageManager.get(TextKey.MENU_CHANGE_USERNAME_BUTTON), GameAsset.getMenuSkin());
        this.backButton = new TextButton(LanguageManager.get(TextKey.MENU_BACK_TEXT_BUTTON), GameAsset.getMenuSkin());
        this.changeAvatarDialog = new Dialog(LanguageManager.get(TextKey.MENU_CHANGE_AVATAR_BUTTON), GameAsset.getMenuSkin());
        this.deleteAccountDialog = new Dialog(LanguageManager.get(TextKey.MENU_DELETE_ACCOUNT_BUTTON), GameAsset.getMenuSkin());
        this.changeUsernameDialog = new Dialog(LanguageManager.get(TextKey.MENU_CHANGE_USERNAME_BUTTON), GameAsset.getMenuSkin());
        this.changePasswordDialog = new Dialog(LanguageManager.get(TextKey.MENU_CHANGE_PASSWORD_BUTTON), GameAsset.getMenuSkin());
        errorLabel = new Label("", GameAsset.getMenuSkin());
        errorLabel.setColor(Color.RED);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        setupChangeAvatarDialog();
        setupChangePasswordDialog();
        setupChangeUsernameDialog();
        setupDeleteAccountDialog();
        setupUI();
    }

    private void setupChangeAvatarDialog() {
        Table avatarTable = new Table();
        avatarTable.defaults().pad(10);
        int columns = 4;
        int count = 0;
        List<Image> avatars = new ArrayList<>(GameAsset.getAvatars());
        for (Image avatar : avatars) {
            avatar.setSize(100, 100);
            avatar.setScaling(Scaling.fit);
            avatar.setTouchable(Touchable.enabled);
            avatar.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                    controller.changeAvatar(GameAsset.getAvatars().get(avatars.indexOf(avatar)));
                    changeAvatarDialog.hide();
                }
            });
            avatarTable.add(avatar).width(100).height(100);
            if ((count + 1) % columns==0) {
                avatarTable.row();
            }
            count++;
        }
        TextButton chooseFileButton = new TextButton(LanguageManager.get(TextKey.MENU_SELECT_FILE_FROM_SYSTEM_BUTTON), GameAsset.getMenuSkin());
        chooseFileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                openFileChooser();
            }
        });
        TextButton cancelButton = new TextButton(LanguageManager.get(TextKey.MENU_CANCEL_TEXT_BUTTON), GameAsset.getMenuSkin());
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                changeAvatarDialog.hide();
            }
        });
        changeAvatarDialog.getContentTable().add(avatarTable).pad(20);
        changeAvatarDialog.getButtonTable().defaults().pad(10);
        changeAvatarDialog.button(cancelButton);
        changeAvatarDialog.button(chooseFileButton);
    }

    private void setupDeleteAccountDialog() {
        deleteAccountDialog.getContentTable().clearChildren();
        deleteAccountDialog.getButtonTable().clearChildren();
        TextButton submitButton = new TextButton(LanguageManager.get(TextKey.MENU_CONFIRM_TEXT_BUTTON), GameAsset.getMenuSkin());
        TextButton cancelButton = new TextButton(LanguageManager.get(TextKey.MENU_CANCEL_TEXT_BUTTON), GameAsset.getMenuSkin());

        Table contentTable = deleteAccountDialog.getContentTable();
        contentTable.add(new Label("Are you sure?", GameAsset.getMenuSkin())).padRight(10);
        deleteAccountDialog.getButtonTable().defaults().pad(10);
        deleteAccountDialog.button(submitButton, true);
        deleteAccountDialog.button(cancelButton, false);

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                controller.deleteAccount();
            }
        });
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                deleteAccountDialog.hide();
            }
        });
    }

    private void setupChangeUsernameDialog() {
        changeUsernameDialog.getContentTable().clearChildren();
        changeUsernameDialog.getButtonTable().clearChildren();
        TextButton submitButton = new TextButton(LanguageManager.get(TextKey.MENU_CONFIRM_TEXT_BUTTON), GameAsset.getMenuSkin());
        TextButton cancelButton = new TextButton(LanguageManager.get(TextKey.MENU_CANCEL_TEXT_BUTTON), GameAsset.getMenuSkin());

        Table contentTable = changeUsernameDialog.getContentTable();
        contentTable.add(new Label(LanguageManager.get(TextKey.MENU_NEW_PASSWORD_LABEL), GameAsset.getMenuSkin())).padRight(10);
        contentTable.add(newUsername).width(300).row();
        changeUsernameDialog.getButtonTable().defaults().pad(10);
        changeUsernameDialog.button(submitButton, true);
        changeUsernameDialog.button(cancelButton, false);

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                controller.changeUsername(newUsername.getText());
            }
        });
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                changeUsernameDialog.hide();
            }
        });
    }

    private void setupChangePasswordDialog() {
        changePasswordDialog.getContentTable().clearChildren();
        changePasswordDialog.getButtonTable().clearChildren();
        TextButton submitButton = new TextButton(LanguageManager.get(TextKey.MENU_CONFIRM_TEXT_BUTTON), GameAsset.getMenuSkin());
        TextButton cancelButton = new TextButton(LanguageManager.get(TextKey.MENU_CANCEL_TEXT_BUTTON), GameAsset.getMenuSkin());

        Table contentTable = changePasswordDialog.getContentTable();
        contentTable.add(new Label(LanguageManager.get(TextKey.MENU_NEW_PASSWORD_LABEL), GameAsset.getMenuSkin())).padRight(10);
        contentTable.add(newPassword).width(300).row();
        changePasswordDialog.getButtonTable().defaults().pad(10);
        changePasswordDialog.button(submitButton, true);
        changePasswordDialog.button(cancelButton, false);

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                controller.changePassword(newPassword.getText());
            }
        });
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                changePasswordDialog.hide();
            }
        });
    }

    private void setupUI() {
        table.clear();
        table.setFillParent(true);
        table.defaults().pad(10).width(600).height(40);
        table.align(Align.center);

        Label title = new Label(LanguageManager.get(TextKey.MENU_PROFILE_LABEL), GameAsset.getMenuSkin(), "title");
        title.setAlignment(Align.center);
        table.add(title).colspan(2).center().padTop(40).padBottom(50).row();

        table.add(changeAvatarButton).align(Align.left).width(450).height(100);
        changeAvatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                changeAvatarDialog.show(stage);
            }
        });
        table.add(changeUsernameButton).align(Align.center).width(450).height(100);
        changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                changeUsernameDialog.show(stage);
            }
        });
        table.add(changePasswordButton).align(Align.right).width(450).height(100);
        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                changePasswordDialog.show(stage);
            }
        });
        table.row();
        table.add(deleteAccountButton).align(Align.left).width(450).height(100);
        deleteAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                deleteAccountDialog.show(stage);
            }
        });
        table.add(backButton).align(Align.right).width(450).height(100).row();
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                Main.getInstance().setScreen(new MainMenu());
            }
        });
        table.add(errorLabel).colspan(2).height(20).row();

        background.setFillParent(true);
        stage.addActor(background);
        stage.addActor(table);
    }

    private void openFileChooser() {
        if (Gdx.app.getType()==Application.ApplicationType.Desktop) {
            FileDialog dialog = new FileDialog((Frame) null, "Choose Avatar");
            dialog.setMode(FileDialog.LOAD);
            dialog.setVisible(true);
            String selectedFile = dialog.getFile();
            String selectedDir = dialog.getDirectory();

            if (selectedFile!=null && selectedDir!=null) {
                FileHandle file = Gdx.files.absolute(selectedDir + selectedFile);
                handleSelectedFile(file);
            }
        } else {
            showError("Not supported on this platform");
        }
    }

    public void handleSelectedFile(FileHandle file) {
        try {
            if (!file.extension().matches("png|jpg|jpeg")) {
                showError("invalid format");
                return;
            }

            if (file.length() > 2 * 1024 * 1024) { // 2MB
                showError("choose a smaller file");
                return;
            }
            Texture texture = new Texture(file);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            Image customAvatar = new Image(texture);
            controller.changeAvatar(customAvatar);
            changeAvatarDialog.hide();
        } catch (Exception e) {
            showError("can not load file");
            Gdx.app.error("AvatarUpload", "Error processing image", e);
        }
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

    public Dialog getChangeUsernameDialog() {
        return changeUsernameDialog;
    }

    public Dialog getChangePasswordDialog() {
        return changePasswordDialog;
    }

    public Dialog getDeleteAccountDialog() {
        return deleteAccountDialog;
    }

    public Dialog getChangeAvatarDialog() {
        return changeAvatarDialog;
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
}

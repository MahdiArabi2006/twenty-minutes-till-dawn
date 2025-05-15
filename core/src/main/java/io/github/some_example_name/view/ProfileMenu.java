package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.ProfileMenuController;
import io.github.some_example_name.model.GameAsset;

import java.util.ArrayList;
import java.util.List;

public class ProfileMenu extends Menu {
    private static ProfileMenu instance;
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

    private ProfileMenu() {
        this.stage = new Stage(new ScreenViewport());
        this.table = new Table();
        Texture backgroundTexture = new Texture(Gdx.files.internal("background2.jpg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.background = new Image(backgroundTexture);
        this.newPassword = new TextField("password", GameAsset.getMenuSkin());
        this.newUsername = new TextField("new username", GameAsset.getMenuSkin());
        this.changeAvatarButton = new TextButton("change avatar", GameAsset.getMenuSkin());
        this.deleteAccountButton = new TextButton("delete account", GameAsset.getMenuSkin());
        this.changePasswordButton = new TextButton("change password", GameAsset.getMenuSkin());
        this.changeUsernameButton = new TextButton("change username", GameAsset.getMenuSkin());
        this.backButton = new TextButton("back", GameAsset.getMenuSkin());
        this.changeAvatarDialog = new Dialog("change avatar", GameAsset.getMenuSkin());
        this.deleteAccountDialog = new Dialog("delete account", GameAsset.getMenuSkin());
        this.changeUsernameDialog = new Dialog("change username", GameAsset.getMenuSkin());
        this.changePasswordDialog = new Dialog("change password", GameAsset.getMenuSkin());
        errorLabel = new Label("", GameAsset.getMenuSkin());
        errorLabel.setColor(Color.RED);
    }

    public static ProfileMenu getInstance() {
        if (instance==null) instance = new ProfileMenu();
        return instance;
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
        TextButton chooseFileButton = new TextButton("Select File From System", GameAsset.getMenuSkin());
        chooseFileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openFileChooser();
            }
        });
        TextButton cancelButton = new TextButton("Cancel", GameAsset.getMenuSkin());
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
        TextButton submitButton = new TextButton("confirm", GameAsset.getMenuSkin());
        TextButton cancelButton = new TextButton("cancel", GameAsset.getMenuSkin());

        Table contentTable = deleteAccountDialog.getContentTable();
        contentTable.add(new Label("Are you sure?", GameAsset.getMenuSkin())).padRight(10);
        deleteAccountDialog.getButtonTable().defaults().pad(10);
        deleteAccountDialog.button(submitButton, true);
        deleteAccountDialog.button(cancelButton, false);

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.deleteAccount();
            }
        });
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                deleteAccountDialog.hide();
            }
        });
    }

    private void setupChangeUsernameDialog() {
        changeUsernameDialog.getContentTable().clearChildren();
        changeUsernameDialog.getButtonTable().clearChildren();
        TextButton submitButton = new TextButton("confirm", GameAsset.getMenuSkin());
        TextButton cancelButton = new TextButton("cancel", GameAsset.getMenuSkin());

        Table contentTable = changeUsernameDialog.getContentTable();
        contentTable.add(new Label("new password ", GameAsset.getMenuSkin())).padRight(10);
        contentTable.add(newUsername).width(300).row();
        changeUsernameDialog.getButtonTable().defaults().pad(10);
        changeUsernameDialog.button(submitButton, true);
        changeUsernameDialog.button(cancelButton, false);

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeUsername(newUsername.getText());
            }
        });
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeUsernameDialog.hide();
            }
        });
    }

    private void setupChangePasswordDialog() {
        changePasswordDialog.getContentTable().clearChildren();
        changePasswordDialog.getButtonTable().clearChildren();
        TextButton submitButton = new TextButton("confirm", GameAsset.getMenuSkin());
        TextButton cancelButton = new TextButton("cancel", GameAsset.getMenuSkin());

        Table contentTable = changePasswordDialog.getContentTable();
        contentTable.add(new Label("new password ", GameAsset.getMenuSkin())).padRight(10);
        contentTable.add(newPassword).width(300).row();
        changePasswordDialog.getButtonTable().defaults().pad(10);
        changePasswordDialog.button(submitButton, true);
        changePasswordDialog.button(cancelButton, false);

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changePassword(newPassword.getText());
            }
        });
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changePasswordDialog.hide();
            }
        });
    }

    private void setupUI() {
        table.clear();
        table.setFillParent(true);
        table.defaults().pad(10).width(600).height(40);
        table.align(Align.center);

        table.add(changeAvatarButton).align(Align.left).width(450).height(100);
        changeAvatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeAvatarDialog.show(stage);
            }
        });
        table.add(changeUsernameButton).align(Align.center).width(450).height(100);
        changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeUsernameDialog.show(stage);
            }
        });
        table.add(changePasswordButton).align(Align.right).width(450).height(100);
        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changePasswordDialog.show(stage);
            }
        });
        table.row();
        table.add(deleteAccountButton).align(Align.left).width(450).height(100);
        deleteAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                deleteAccountDialog.show(stage);
            }
        });
        table.add(backButton).align(Align.right).width(450).height(100).row();
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(MainMenu.getInstance());
            }
        });
        table.add(errorLabel).colspan(2).height(20).row();

        background.setFillParent(true);
        stage.addActor(background);
        stage.addActor(table);
    }

    private void openFileChooser() {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("choose avatar");
//        fileChooser.setSelectionMode(FileChooser.SelectionMode.FILES);
//        fileChooser.setFileTypeFilter(new FileChooser.FileTypeFilter("png", "jpg", "jpeg"));
//
//        fileChooser.setListener(new FileChooser.Listener() {
//            @Override
//            public void selected(Array<FileHandle> files) {
//                if (files.size > 0) {
//                    handleSelectedFile(files.first());
//                }
//            }
//
//            @Override
//            public void canceled() {
//                Gdx.app.log("FileChooser", "canceled choose file");
//            }
//        });
//
//        fileChooser.show(stage);
    }

    private void handleSelectedFile(FileHandle file) {
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
            FileHandle userAvatarsDir = Gdx.files.local("user_avatars/");
            if (!userAvatarsDir.exists()) {
                userAvatarsDir.mkdirs();
            }
            String newFileName = "avatar_" + System.currentTimeMillis() + ".png";
            file.copyTo(userAvatarsDir.child(newFileName));
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

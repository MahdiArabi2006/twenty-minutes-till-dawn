package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.GameAsset;
import io.github.some_example_name.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreBoard extends Menu {
    private final Stage stage;
    private final Table table;
    private final TextButton backButton;
    private final SelectBox<String> sortBySelector;
    private final Table userTable;

    public ScoreBoard() {
        this.stage = new Stage(Main.getInstance().getViewport(), Main.getInstance().getBatch());
        this.table = new Table();
        this.backButton = new TextButton("Back", GameAsset.getMenuSkin());
        this.sortBySelector = new SelectBox<>(GameAsset.getMenuSkin());
        this.userTable = new Table(GameAsset.getMenuSkin());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.align(Align.top);

        Label title = new Label("Scoreboard", GameAsset.getMenuSkin(), "title");
        title.setAlignment(Align.center);
        table.add(title).padTop(20).row();

        sortBySelector.setItems("Username", "Score", "Kill", "Survival Time");
        sortBySelector.setSelected("Score");
        sortBySelector.addListener(e -> {
            refreshUserTable();
            return false;
        });

        table.add(new Label("Sort by:", GameAsset.getMenuSkin()));
        table.add(sortBySelector).pad(10).row();
        table.add(userTable).padTop(20).row();

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play();
                Main.getInstance().setScreen(new MainMenu());
            }
        });

        table.add(backButton).padTop(30);
        stage.addActor(table);

        refreshUserTable();
    }

    private void refreshUserTable() {
        userTable.clear();
        userTable.defaults().pad(5).width(300);
        userTable.add("Username").left();
        userTable.add("Score").center();
        userTable.add("Kill").center();
        userTable.add("Survival Time").center().row();

        List<User> sortedUsers = App.getUsers();
        switch (sortBySelector.getSelected()) {
            case "Username":
                sortedUsers = sortedUsers.stream()
                    .sorted(Comparator.comparing(User::getUsername))
                    .collect(Collectors.toList());
                break;
            case "Kill":
                sortedUsers = sortedUsers.stream()
                    .sorted(Comparator.comparingInt(User::getKillNumber).reversed())
                    .collect(Collectors.toList());
                break;
            case "Survival Time":
                sortedUsers = sortedUsers.stream()
                    .sorted(Comparator.comparingInt(User::getMostSurvivalTime).reversed())
                    .collect(Collectors.toList());
                break;
            default:
                sortedUsers = sortedUsers.stream()
                    .sorted(Comparator.comparingInt(User::getScore).reversed())
                    .collect(Collectors.toList());
        }

        for (int i = 0; i < sortedUsers.size(); i++) {
            User user = sortedUsers.get(i);
            Label usernameLabel = new Label(user.getUsername(), GameAsset.getMenuSkin());
            Label scoreLabel = new Label(String.valueOf(user.getScore()), GameAsset.getMenuSkin());
            Label killLabel = new Label(String.valueOf(user.getKillNumber()), GameAsset.getMenuSkin());
            Label survivalLabel = new Label(user.getMostSurvivalTime() + " second", GameAsset.getMenuSkin());

            if (i < 3) {
                usernameLabel.setColor(1, 0.85f, 0.1f, 1);
                scoreLabel.setColor(1, 0.85f, 0.1f, 1);
                killLabel.setColor(1, 0.85f, 0.1f, 1);
                survivalLabel.setColor(1, 0.85f, 0.1f, 1);
            }

            if (user.getUsername().equals(App.getLoggedInUser().getUsername())) {
                usernameLabel.setColor(0.1f, 0.8f, 1f, 1);
                scoreLabel.setColor(0.1f, 0.8f, 1f, 1);
                killLabel.setColor(0.1f, 0.8f, 1f, 1);
                survivalLabel.setColor(0.1f, 0.8f, 1f, 1);
            }

            userTable.add(usernameLabel).left();
            userTable.add(scoreLabel).center();
            userTable.add(killLabel).center();
            userTable.add(survivalLabel).center().row();
        }
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
        stage.dispose();
    }
}

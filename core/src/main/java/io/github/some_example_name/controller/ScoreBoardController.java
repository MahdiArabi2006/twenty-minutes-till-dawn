package io.github.some_example_name.controller;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.GameAsset;
import io.github.some_example_name.model.User;
import io.github.some_example_name.save.UserRepository;
import io.github.some_example_name.view.ScoreBoard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreBoardController {
    private final ScoreBoard view;
    private final UserRepository userRepository = new UserRepository();

    public ScoreBoardController(ScoreBoard view) {
        this.view = view;
    }

    public void refreshUserTable() {
        view.getUserTable().clear();
        view.getUserTable().defaults().pad(5).width(300);
        view.getUserTable().add("Username").left();
        view.getUserTable().add("Score").center();
        view.getUserTable().add("Kill").center();
        view.getUserTable().add("Survival Time").center().row();

        List<User> sortedUsers = userRepository.getAllUsers();

        switch (view.getSortBySelector().getSelected()) {
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

            view.getUserTable().add(usernameLabel).left();
            view.getUserTable().add(scoreLabel).center();
            view.getUserTable().add(killLabel).center();
            view.getUserTable().add(survivalLabel).center().row();
        }
    }
}

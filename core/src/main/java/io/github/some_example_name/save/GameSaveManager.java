package io.github.some_example_name.save;

import com.google.gson.*;
import io.github.some_example_name.model.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GameSaveManager {

    private static final Gson gson;

    static {
        RuntimeTypeAdapterFactory<Enemy> enemyAdapter =
            RuntimeTypeAdapterFactory
                .of(Enemy.class, "type")
                .registerSubtype(Tree.class, "Tree")
                .registerSubtype(Tentacle.class, "Tentacle")
                .registerSubtype(Elder.class,"Elder")
                .registerSubtype(EyeBat.class,"EyeBat");

        gson = new GsonBuilder()
            .registerTypeAdapterFactory(enemyAdapter)
            .setPrettyPrinting()
            .create();
    }

    public static void saveGame(Game game, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(game, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Game loadGame(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Game game = gson.fromJson(reader, Game.class);

            game.getPlayer().initialAfterLoad();
            for (Enemy enemy : game.getEnemies()) {
                enemy.initialAfterLoad();
                if (enemy instanceof EyeBat) {

                    for (Bullet bullet : ((EyeBat) enemy).getBullets()) {
                        bullet.initialAfterLoad();
                    }
                }
            }

            for (Seed seed : game.getSeeds()) {
                seed.initialAfterLoad();
            }


            return game;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteSaveFile(String filePath) {
        try {
            return Files.deleteIfExists(Path.of(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

package io.github.some_example_name.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.some_example_name.model.Game;
import io.github.some_example_name.model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public void saveUser(User user) {
        String sql = "INSERT INTO users(username, password,answer_security_question, avatar, last_game_path, score, survival_time, kill_count, auto_reload) VALUES(?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = SQLiteDB.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getAnswerSecurityQuestion());
            pstmt.setBytes(4, saveAvatar(user.getAvatar()));
            pstmt.setString(5,saveLastGame(user.getLastGame()));
            pstmt.setInt(6,user.getScore());
            pstmt.setInt(7,user.getMostSurvivalTime());
            pstmt.setInt(8,user.getKillNumber());
            pstmt.setBoolean(9,user.isAutoReloadingEnable());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error saving user", e);
        }
    }

    public User getUser(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;

        try (PreparedStatement pstmt = SQLiteDB.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("answer_security_question"),
                    loadAvatar(rs.getBytes("avatar"))
                );

                user.setScore(rs.getInt("score"));
                user.setMostSurvivalTime(rs.getInt("survival_time"));
                user.setKillNumber(rs.getInt("kill_count"));
                user.setAutoReloadingEnable(rs.getBoolean("auto_reload"));
                user.setLastGame(loadGame("last_game_path"));
            }
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error getting user", e);
        }

        return user;
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET " +
            "score = ?, " +
            "survival_time = ?, " +
            "kill_count = ?, " +
            "auto_reload = ?, " +
            "last_game_path= ?," +
            "avatar= ?" +
            "WHERE username = ?";

        try (PreparedStatement pstmt = SQLiteDB.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, user.getScore());
            pstmt.setInt(2, user.getMostSurvivalTime());
            pstmt.setInt(3, user.getKillNumber());
            pstmt.setBoolean(4, user.isAutoReloadingEnable());
            pstmt.setString(5,saveLastGame(user.getLastGame()));
            pstmt.setBytes(6,saveAvatar(user.getAvatar()));
            pstmt.setString(7, user.getUsername());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error updating user", e);
        }
    }

    public void deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

        try (PreparedStatement pstmt = SQLiteDB.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error deleting user", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (PreparedStatement pstmt = SQLiteDB.getConnection().prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("answer_security_question"),
                    loadAvatar(rs.getBytes("avatar"))
                );

                user.setScore(rs.getInt("score"));
                user.setMostSurvivalTime(rs.getInt("survival_time"));
                user.setKillNumber(rs.getInt("kill_count"));
                user.setAutoReloadingEnable(rs.getBoolean("auto_reload"));
                user.setLastGame(loadGame("last_game_path"));

                users.add(user);
            }
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error getting all users", e);
        }

        return users;
    }

    private byte[] saveAvatar(Image avatar) {
        if (avatar == null) return null;
        Texture texture = ((TextureRegionDrawable) avatar.getDrawable()).getRegion().getTexture();
        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        Pixmap pixmap = texture.getTextureData().consumePixmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PixmapIO.PNG writer = new PixmapIO.PNG((int) (pixmap.getWidth() * pixmap.getHeight() * 1.5f));
            writer.setFlipY(false);
            writer.write(baos, pixmap);
            writer.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    private Image loadAvatar(byte[] data) {
        if (data == null || data.length == 0) return null;

        Pixmap pixmap = new Pixmap(data, 0, data.length);
        Texture texture = new Texture(pixmap);
        return new Image(texture);
    }

    private String saveLastGame(Game lastGame){
        String path = "";
        return path;
    }


    private Game loadGame(String path){
        return null;
    }
}

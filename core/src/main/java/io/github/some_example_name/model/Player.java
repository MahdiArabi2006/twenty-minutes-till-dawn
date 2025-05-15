package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
    private final User user;
    private Sprite playerSprite;
    private final Character character;
    private final Weapon weapon;
    private float x;
    private float y;
    private float time;
    private float health;
    private final CollisionRectangle playerCollisionRectangle;
    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning;

    public Player(User user, Character character, WeaponType weaponType) {
        this.user = user;
        this.character = character;
        this.weapon = new Weapon(weaponType);
        this.playerSprite = character.getSprite();
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        playerSprite.setSize(playerSprite.getWidth() * 3, playerSprite.getHeight() * 3);
        playerCollisionRectangle = new CollisionRectangle((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2,
            playerSprite.getWidth() * 3, playerSprite.getHeight() * 3);
    }

    public User getUser() {
        return user;
    }

    public CollisionRectangle getPlayerCollisionRectangle() {
        return playerCollisionRectangle;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public void setPlayerSprite(Sprite playerSprite) {
        this.playerSprite = playerSprite;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getSpeed() {
        return character.getSpeed();
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public boolean isPlayerIdle() {
        return isPlayerIdle;
    }

    public void setPlayerIdle(boolean playerIdle) {
        isPlayerIdle = playerIdle;
    }

    public boolean isPlayerRunning() {
        return isPlayerRunning;
    }

    public void setPlayerRunning(boolean playerRunning) {
        isPlayerRunning = playerRunning;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Character getCharacter() {
        return character;
    }
}

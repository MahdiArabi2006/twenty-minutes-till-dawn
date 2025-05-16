package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
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
    private float XP;
    private float lastReloadWeapon;
    private final CollisionRectangle playerCollisionRectangle;

    public Player(User user, Character character, WeaponType weaponType) {
        this.user = user;
        this.character = character;
        this.health = character.getHP();
        this.weapon = new Weapon(weaponType);
        this.playerSprite = character.getSprite();
        this.x = Gdx.graphics.getWidth() / 2f;
        this.y = Gdx.graphics.getHeight() / 2f;
        playerSprite.setPosition(this.x, this.y);
        playerSprite.setSize(playerSprite.getWidth() * 3, playerSprite.getHeight() * 3);
        playerCollisionRectangle = new CollisionRectangle(this.x, this.y,
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

    public void resetHealth(){
        this.health = this.character.getHP();
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Character getCharacter() {
        return character;
    }

    public float getLastReloadWeapon() {
        return lastReloadWeapon;
    }

    public void setLastReloadWeapon(float lastReloadWeapon) {
        this.lastReloadWeapon = lastReloadWeapon;
    }

    public float getXP() {
        return XP;
    }

    public void setXP(float XP) {
        this.XP = XP;
    }
}

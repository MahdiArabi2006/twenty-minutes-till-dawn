package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.some_example_name.controller.PlayerController;

public class Player {
    private final User user;
    private Sprite playerSprite;
    private final Character character;
    private final Weapon weapon;
    private int projectile;
    private int maxAmmo;
    private int speed;
    private int damage;
    private Ability ability;
    private int level = 1;
    private float timeAbilityBegin;
    private float x;
    private float y;
    private float time;
    private float health;
    private float maxHP;
    private float XP;
    private float lastReloadWeapon;
    private final CollisionRectangle playerCollisionRectangle;

    public Player(User user, Character character, WeaponType weaponType) {
        this.user = user;
        this.character = character;
        this.speed = this.character.getSpeed();
        this.maxHP = character.getHP();
        this.health = character.getHP();
        this.weapon = new Weapon(weaponType);
        this.damage = this.weapon.getWeaponType().getDamage();
        this.projectile = this.weapon.getWeaponType().getProjectile();
        this.maxAmmo = this.weapon.getWeaponType().getAmmoMax();
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
        return this.speed;
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
        updateLevel();
    }

    public Ability getAbility() {
        return ability;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getTimeAbilityBegin() {
        return timeAbilityBegin;
    }

    public void setTimeAbilityBegin(float timeAbilityBegin) {
        this.timeAbilityBegin = timeAbilityBegin;
    }

    public float getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(float maxHP) {
        this.maxHP = maxHP;
    }

    public int getProjectile() {
        return projectile;
    }

    public void setProjectile(int projectile) {
        this.projectile = projectile;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }


    public void resetAmmo(){
        this.weapon.setAmmo(this.maxAmmo);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void changeAbility(Ability ability){
        this.timeAbilityBegin = 0;
        resetPlayerAbility();
        this.ability = ability;
    }

    private void resetPlayerAbility(){
        this.maxHP = this.character.getHP();
        this.projectile = this.weapon.getWeaponType().getProjectile();
        this.maxAmmo = this.weapon.getWeaponType().getAmmoMax();
        this.speed = this.character.getSpeed();
        this.damage = this.weapon.getWeaponType().getDamage();
    }

    private void updateLevel(){
        if (this.level * 20 <= this.XP){
            PlayerController.handleUpdateLevel();
            this.level += 1;
        }
    }
}

package Game.GameView.Units.Enemys;

import Game.GameView.BoardPackaeg.Position;
import Game.GameView.BoardPackaeg.Tile;
import Game.GameView.Units.Players.Player;
import Game.GameView.Units.Units;
import Game.GameView.Units.Health;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class Enemy extends Units {

    protected Health health;
    protected int experienceValue;
    protected boolean isDead;
    protected List<Character> randomMovments;
    public Enemy(char tile, String name, int healthState, int attackPoints, int defensePoints, int experienceValue){
        super(tile, name, healthState, attackPoints, defensePoints);
        this.attack= attackPoints;
        this.defense = defensePoints;
        this.experienceValue = experienceValue;
        this.randomMovments = new ArrayList<>();
        randomMovments.add(0, 'w');
        randomMovments.add(1, 's');
        randomMovments.add(2, 'a');
        randomMovments.add(3, 'd');
    }
    public boolean isDead(){
        return isDead;
    }
    public void remove(){
    }
    public void onDeath(){
        isDead = true;
    }
    public void accept(Player player){
        this.visit(player);
    }
    public void accept(Enemy enemy){
    }
    @Override
    public void visit(Player p) {
        p.accept(this);
    }
    @Override
    public void visit(Enemy e)    {};
    public char toChar(){
        return this.tile;
    }
    public void interact(Tile tile, Player player){
        this.accept(tile);
    }
    public Position gameTick(Player player){
        return this.position;
    }
    public void accept(Tile tile) {
        tile.visit(this);
    }
    public Position gameTick(){
        Collections.shuffle(randomMovments);
        return moveRandomly(randomMovments.get(0));
    }
    public Position moveRandomly(char movment){
        if (movment=='a'){
            return moveLeft();
        }
        if (movment=='w'){
            return moveUp();
        }
        if (movment=='s'){
            return moveDown();
        }
        if (movment=='d'){
            return moveRight();
        }
        else{
           return doNothing();
        }
    }
    public int getExperienceValue(){
        return this.experienceValue;
    }
    public void victory(Units unit){
        unit.victory(this);
    }
    public void victory(Player player){
        messageCallBack.combat(this.name + " won the combat agains "+ player.getName()+ "."+ "Game over for "+player.getName());
        player.onDeath();

    }
    public void combat(Player player) {
        int attacker = new Random().nextInt(0, getAttack());
        messageCallBack.combatResult(this.name + " rolled " + attacker + " attack points");
        int rollDefender = new Random().nextInt(0, player.getDefense());
        messageCallBack.combatResult(player.getName() + " rolled " + rollDefender + " defense points");
        if (attacker - rollDefender > 0) {
            player.setHealth(player.getHealth() - (attacker - rollDefender));
            if (player.getHealth() <= 0) {
                victory(player);
            }
        } else {
            messageCallBack.combatResult(this.name + " attacked " + player.getName() + " and damaged him by " + (attacker - rollDefender) + " points");
        }

    }
}

package Game.GameView.Units.Enemys;

import Game.GameView.BoardPackaeg.Position;
import Game.GameView.Units.Players.Player;
import Game.GameView.Units.Units;
import Game.GameView.Units.Health;

public abstract class Enemy extends Units {

    protected Health health;
    protected int experienceValue;

    public Enemy(char tile, String name, int healthState, int attackPoints, int defensePoints, int experienceValue){
        super(tile, name, healthState, attackPoints, defensePoints);
        this.experienceValue = experienceValue;
    }


    //public Enemy produceEnemy(){}

    public void remove(){

    }

    public void onDeath(){
        this.remove();
    }

    public void accept(Player player){
        this.combat(player);
    }

    //Nothing needs to happen
    public void accept(Enemy enemy){}

    public void accept(Units unit){
        unit.accept(this);
    }


    @Override
    public void visit(Player p) {
        p.accept(this);
    }

    @Override
    public void visit(Enemy e) {
        e.accept(this);
    }

    public char toChar(){
        return this.tile;
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

    @Override
    public Position moveDown() {
        return super.moveDown();
    }

    @Override
    public Position moveLeft() {
        return super.moveLeft();
    }

    @Override
    public Position moveRight() {
        return super.moveRight();
    }

    @Override
    public Position moveUp() {
        return super.moveUp();
    }

    public Position noMove(){
        return this.position;
    }

    @Override
    public Position doNothing() {
        return super.doNothing();
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
        messageCallBack.combatResult(this.name + " won the combat agains "+ player.getName()+ "."+ "Game over for "+player.getName());
        player.onDeath();

    }

}

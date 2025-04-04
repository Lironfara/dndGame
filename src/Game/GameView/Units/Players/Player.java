package Game.GameView.Units.Players;

import Game.GameView.BoardPackaeg.*;
import Game.GameView.MessageCallback;
import Game.GameView.CLI;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Units;

import java.util.List;
import java.util.Random;

public abstract class Player extends Units {
    protected int experience;
    protected int playerLevel;

    protected MessageCallback messageCallBack;

    public Position position;
    private boolean dead;

    public Player(char c, String name, int health, int attackPoints, int defensePoints){
        super('@', name,health, attackPoints, defensePoints);
        this.experience=0;
        this.playerLevel=1;
        dead = false;
        this.messageCallBack = new CLI();
    }

    public boolean isDead(){
        return dead;
    }


    //Imppossible sitiation
    @Override
    public void visit(Player p) {
    }

    @Override
    public void visit(Enemy enemy) {

    }

    public void levelUp(){
        this.experience= experience- 50*playerLevel;
        this.playerLevel++;
        this.health.setHealthPool(health.getHealthPool()+(10*playerLevel));
        this.health.setHealthAmount(health.getHealthPool());
        this.attackPoints =attackPoints+ 4*playerLevel;
        this.defensePoints =defensePoints+ playerLevel;
        messageCallBack.levelUp(this.name, this.playerLevel);
    }

    public Position gameTick(String movment){
        Position newPosition =  move(movment);
        if (experience >= 50*playerLevel){
            levelUp();
        }


        return newPosition;
    }

    @Override
    public void initialize(Position position) {
        super.initialize(position);
        this.position = position;
    }

    public void interact(Tile tile) {
        this.accept(tile);
    };



    public void abilityCastMessage(String m){};
    public abstract List<Enemy> abilityCast(List<Enemy> enemyList);

    public int getExperience() {
        return experience;
    }

    public void onDeath(){

        this.tile = 'X';
        messageCallBack.onDeath(this.Name);
    };

    public void setPosition(Position newPos){
        super.setPosition(newPos);
        this.position = newPos;
    }

    public void accept(Player player) {

    }
    public  void accept(Enemy enemy) {

        combat(enemy);
    }


    public void combat(Enemy enemy){
        int playerAttacker = new Random().nextInt(0, this.attackPoints);
        int rollDefender = new Random().nextInt(0, enemy.getDefense());
        if (playerAttacker - rollDefender > 0) {
            this.experience = this.experience + enemy.getExperienceValue();
            enemy.remove();
            super.setPosition(enemy.getPosition());
            setPosition(enemy.getPosition());
        }


    }

    public void victory(Units unit){
        unit.victory(this);
    }

    public void victory(Enemy enemy){
        setExperience(this.experience+enemy.getExperienceValue());
        enemy.remove();
        this.setPosition(enemy.getPosition());
        messageCallBack.combatResult(this.name + " won the combat agains "+ enemy.getName() + " and gained " +enemy.getExperienceValue() +" experince points");
    }

    public void victory(Player player){}
    public void setExperience(int newex){
        this.experience = newex;
    }

    public void accept(Tile tile){
        tile.visit(this);
    }

    public abstract void visit(Tile tile);


    public void visit(Wall w) {
        visit(w);
    }

    @Override
    public void visit(Empty e) {
    }

    public String getName(){
        return this.name;
    }


    @Override
    public Position getPosition() {
        return super.getPosition();
    }




    public Position move (String movement){
        if (movement.equals("a")){
            return moveLeft();
        }
        if (movement.equals("w")){
            return moveUp();
        }
        if (movement.equals("s")){
            return moveDown();
        }
        if (movement.equals("d")){
            return moveRight();
        }
        else{
            return doNothing();
        }
    }
}

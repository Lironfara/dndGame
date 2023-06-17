package Game.GameView.Units.Players;

import Game.GameView.BoardPackaeg.Empty;
import Game.GameView.BoardPackaeg.Position;
import Game.GameView.BoardPackaeg.Range;
import Game.GameView.MessageCallback;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Units;
import Game.GameView.BoardPackaeg.Wall;

import java.util.Random;

public abstract class Player extends Units {
    protected int experience;
    protected int playerLevel;

    protected MessageCallback messageCallBack;

    public Position position;

    public Player(char c, String name, int health, int attackPoints, int defensePoints){
        super('@', name,health, attackPoints, defensePoints);
        this.experience=0;
        this.playerLevel=1;
    }


    //Imppossible sitiation
    @Override
    public void visit(Player p) {}

    @Override
    public void visit(Enemy e) {
        e.accept(this);
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

    public void gameTick(){
        move();
        if (experience >= 50*playerLevel){
            levelUp();
        }
        super.gameTick();
    }

    @Override
    public void initialize(Position position) {
        super.initialize(position);
        this.position = position;
    }

    public void abilityCastMessage(String m){};
    public abstract void abilityCast();

    public void onDeath(){

        this.tile = 'X';
        messageCallBack.onDeath(this.Name);
    };

    public void accept(Player player) {
    }
    public void accept(Units unit){
        unit.accept(this);
    }
    public void accept(Enemy enemy) {
        this.combat(enemy);
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



    @Override
    public void visit(Wall w) {
        visit(w);
    }

    @Override
    public void visit(Empty e) {
        e.accept(this);
    }

    public String getName(){
        return this.name;
    }

    public Position move(){
        return this.position;
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }




    public Position move (char movement){
        if (movement=='a'){
            return moveLeft();
        }
        if (movement=='w'){
            return moveUp();
        }
        if (movement=='s'){
            return moveDown();
        }
        if (movement=='d'){
            return moveRight();
        }
        if (movement=='e'){
           this.abilityCast();
        }
        else{
            return doNothing();
        }
        return null;
    }
}

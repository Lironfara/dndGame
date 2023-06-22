package Game.GameView.Units.Players;

import Game.GameView.BoardPackaeg.*;
import Game.GameView.CLI;
import Game.GameView.MessageCallback;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Health;
import Game.GameView.Units.Units;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Warrior extends Player{
    protected int abilityCoolDown;
    private int remainingCoolDown;

    private String specialAbilityName;
    private MessageCallback messageCallback;
    private Health health;


    public Warrior(String name, int health, int attackPoints, int defensePoints, int abilityCoolDown){
        super('@', name,health,attackPoints,defensePoints);
        this.abilityCoolDown = abilityCoolDown;
        this.remainingCoolDown = 0;
        this.specialAbilityName = "Avenger's Shield";
        messageCallback = new CLI();
        this.health = new Health(health,health);
    }

    public void onDeath(){
        super.onDeath();

    }

    public List<Enemy> abilityCast(List<Enemy> enemiesOnBoard){
        List<Enemy> enemiesToRemove = new ArrayList<>();
        if (remainingCoolDown>0){
            messageCallback.abilityCast(name+" tried to cast "+ specialAbilityName+",  but there is a cooldown: "+ remainingCoolDown);
        }
        else{
            ArrayList<Enemy> enemiesOnRange = new ArrayList<>() ;
            for (Enemy enemy: enemiesOnBoard) {
                if (new Range(this.getPosition().getPosition(), enemy.getPosition().getPosition()).getRange() < 3){
                    enemiesOnRange.add(enemy);
                }
            }
            Collections.shuffle(enemiesOnRange);

            this.health.setHealthAmount(health.getHealthAmount()+10*defensePoints);
            messageCallback.abilityCast(name + " cast "+ specialAbilityName+" , healing for "+ 10*defensePoints);

            if (enemiesOnBoard.size()>=1) {
                Enemy toAttack = enemiesOnRange.get(0); //Gets random enemy
                toAttack.setHealth(toAttack.getHealth() - this.health.getHealthPool() / 10);
                if (toAttack.getHealth() <= 0) {
                    this.setExperience(toAttack.getExperienceValue() + experience);
                    toAttack.onDeath();
                    enemiesToRemove.add(toAttack);
                    messageCallback.abilityCast(name+" cast "+ specialAbilityName+",  and killed "+ toAttack.getName()+"."
                    + name + " gained another "+ toAttack.getExperienceValue() +" experience points");
                }
                else{
                    messageCallback.abilityCast(name +" cast "+ specialAbilityName + "and damaged " + toAttack.getName() );
                }
            }
            remainingCoolDown = abilityCoolDown;

        }
        return enemiesToRemove;
    }
    @Override
    public void levelUp() {
        super.levelUp();
        remainingCoolDown =0;
        health.setHealthPool(health.getHealthPool()+(5*playerLevel));
        attackPoints = attackPoints+(2*playerLevel);
        defensePoints = defensePoints+(playerLevel);
        messageCallback.levelUp(this.name + " reached level "+ playerLevel+ ":" + "+"+ 5*playerLevel+ " Health"
                +", +"+ 2*playerLevel+ " Attack points " + ", +"+ playerLevel+ " defense points");

    }

    public void setRemainingCoolDown(int newCurr){
        if (newCurr>=0){
            this.remainingCoolDown = newCurr;
        }
    }

    public Position gameTick(String movement){
        setRemainingCoolDown(this.remainingCoolDown-1);
        messageCallback.describe(describe());
        return super.gameTick(movement);
    }

    public String describe(){
        String s = getName() + "   Health :"+ getHealthPool()+"/"+getHealthAmount() + "Attack :"+getAttack() + "   Defense :"+getDefense()+ "   Experience: "+getExperience() + "   Cool down : "+abilityCoolDown+"/"+remainingCoolDown;
        return s;

    }
    public void interact(Tile tile){
        this.accept(tile);
        if (remainingCoolDown>0){
            remainingCoolDown--;
        }
    }

    @Override
    public void visit(Empty e) {

    }


    @Override
    public void visit(Tile tile) {

    }

    @Override
    public void accept(Wall wall) {

    }


    public void accept(Enemy enemy) {
       enemy.visit(this);
    }

    @Override
    public void visit(Wall w) {
        super.visit(w);
    }

    @Override
    public void victory(Player player) {}

    @Override
    public Position getPosition() {
          return this.position;
    }


    public void accept(Units unit, Position newPosition) {
        unit.accept(this);
    }


}

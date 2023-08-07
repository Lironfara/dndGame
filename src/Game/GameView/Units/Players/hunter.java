package Game.GameView.Units.Players;

import Game.GameView.BoardPackaeg.*;
import Game.GameView.CLI;
import Game.GameView.MessageCallback;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Units;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class hunter extends Player {
    protected int range;
    private int arrowsCount;
    private int tickCount;
    private final String specialAbilityName;
    private MessageCallback messageCallback;
    public hunter(String name, int health, int attackPoints, int defensePoints, int range){
        super('@', name,health,attackPoints,defensePoints);
        this.range = range;
        this.arrowsCount = 0;
        this.tickCount = 0;
        this.specialAbilityName = "Avenger's Shield";
        messageCallback = new CLI();
    }
    public List<Enemy> abilityCast(List<Enemy> enemiesOnBoard){
        List<Enemy> enemiesToRemove = new ArrayList<>();
        if (arrowsCount<=0){
            messageCallback.abilityCast(name+" tried to cast "+ specialAbilityName+",  but there is not enough arrows: ");
        }
        else{
            ArrayList<Enemy> enemiesOnRange = new ArrayList<>() ;
            for (Enemy enemy: enemiesOnBoard) {
                if (new Range(this.getPosition().getPosition(), enemy.getPosition().getPosition()).getRange() <= range){
                    enemiesOnRange.add(enemy);
                }
            }
            Collections.shuffle(enemiesOnRange);
            this.setHealthAmount(health.getHealthAmount()+10*defense);
            messageCallback.abilityCast(name + " cast "+ specialAbilityName+" , healing for "+ 10*defense);
            if (enemiesOnRange.size()>=1) {
                Enemy toAttack = enemiesOnRange.get(0); //Gets random enemy
                Double minRange = new Range(this.getPosition().getPosition(), toAttack.getPosition().getPosition()).getRange();
                for (Enemy enemy: enemiesOnRange) {
                    Double tempRange = new Range(this.getPosition().getPosition(), enemy.getPosition().getPosition()).getRange();
                    if(minRange > tempRange){
                        toAttack = enemy;
                        minRange = tempRange;
                    }
                }
                toAttack.setHealth(toAttack.getHealth() - this.attack);
                arrowsCount--;
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
        }
        return enemiesToRemove;
    }
    @Override
    public void levelUp() {
        super.levelUp();
        arrowsCount = arrowsCount+playerLevel*10;
        attack = attack+(2*playerLevel);
        defense = defense+(playerLevel);
    }
    @Override
    public Position gameTick(String movment){
        Position newPosition =  move(movment);
        if(tickCount == 10){
            tickCount = 0;
            arrowsCount = arrowsCount+ playerLevel;
        }
        else{
            tickCount++;
        }
        if (experience >= 50*playerLevel){
            levelUp();
        }
        return newPosition;
    }
    @Override
    public String describe(){
        String s = super.describe()+ " arrow count : " + arrowsCount + " game ticks : "+ tickCount +"\n";
        return s;
    }
    @Override
    public void visit(Tile tile) { }
    @Override
    public void accept(Wall wall) {}
    public void accept(Units unit, Position newPosition) {
        unit.accept(this);
        }
}

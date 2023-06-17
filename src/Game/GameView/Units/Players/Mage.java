package Game.GameView.Units.Players;

import Game.GameView.BoardPackaeg.Empty;
import Game.GameView.BoardPackaeg.Position;
import Game.GameView.BoardPackaeg.Range;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Health;
import Game.GameView.BoardPackaeg.Wall;

import java.util.ArrayList;
import java.util.Collections;

public class Mage extends Player {
    protected int manaPool;
    protected int currentMana;
    protected int manaCost;
    protected int spellPower;
    protected int hitsCount;
    protected int abilityRange;

    protected String specialAbilityName;

    public Mage( String name, int health, int attackPoints, int defensePoints, int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange){
        super('@', name,health,attackPoints,defensePoints);
        this.manaPool= manaPool;
        this.currentMana = manaPool/4;
        this.manaCost =manaCost;
        this.spellPower= spellPower;
        this.hitsCount =hitsCount;
        this.abilityRange= abilityRange;
        this.specialAbilityName = "Blizzard";

    }

    @Override
    public void levelUp(){
        super.levelUp();
        manaPool= manaPool+ 25*playerLevel;
        setCurrentMana(currentMana+ manaPool/4);
        spellPower= spellPower+ 10*playerLevel;
    }


    public void gameTick(){
        setCurrentMana(manaPool+playerLevel);
    }

    @Override
    public void abilityCast() {
        if (currentMana < manaCost) {
            setCurrentMana(currentMana - manaCost);
            int hits = 0;
            ArrayList<Enemy> enemiesOnBoard = new ArrayList<>();
            ArrayList<Enemy> enemiesOnRange = new ArrayList<>();
            for (Enemy enemy : enemiesOnBoard) {
                if (new Range(this.getPosition().getPosition(), enemy.getPosition().getPosition()).getRange() < abilityRange) {
                    enemiesOnRange.add(enemy);
                }
            }
            while (hits < hitsCount && enemiesOnRange != null) {
                Collections.shuffle(enemiesOnRange);
                Enemy toAttack = enemiesOnRange.get(0);
                toAttack.setHealth(toAttack.getHealth() - spellPower);
                if (toAttack.getHealth() <= 0) {
                    this.setExperience(toAttack.getExperienceValue() + experience);
                    toAttack.onDeath();
                    abilityCastMessage(name + " cast " + specialAbilityName + ",  and killed " + toAttack.getName() + "."
                            + name + " gained another " + toAttack.getExperienceValue() + " experience points");
                    enemiesOnRange.remove(toAttack);
                } else {
                    abilityCastMessage(name + " cast " + specialAbilityName + "and damaged " + toAttack.getName());
                }
                hits++;
            }
            // what is enemy tries to defend itself
        }
        else{
            abilityCastMessage(name + "tried to case " + specialAbilityName + " but not enough sources");
        }
    }


    private void setCurrentMana(int newCurrentMana){
        if(newCurrentMana>manaPool)
            currentMana = manaPool;

        else if (newCurrentMana<0){
            //throw new exception not enough current
        }
        else{
            currentMana = newCurrentMana;
        }

    }



    @Override
    public void visit(Empty e) {
        super.visit(e);
    }

    @Override
    public void visit(Wall w) {
        super.visit(w);
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }
}

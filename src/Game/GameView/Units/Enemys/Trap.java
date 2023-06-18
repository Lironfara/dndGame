package Game.GameView.Units.Enemys;

import Game.GameView.BoardPackaeg.*;
import Game.GameView.Units.Health;
import Game.GameView.Units.Players.Player;
import Game.GameView.Units.Units;

public class Trap extends Enemy{

    protected int visibilityTime;
    protected int invisibilityTime;
    protected int tickCount;
    protected boolean visible;

    public Trap(char c, String name, int health, int attackPoints, int defensePoints, int experienceValue,
                int visibilityTime, int invisibilityTime) {
        super(c,name, health, attackPoints, defensePoints, experienceValue);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        tickCount=0;
        visible=true;
    }

    public void returnToBoard(){

    }



    public void removeFromBoard(){

    }


    public void gameTick(Player player){
        visible = tickCount<visibilityTime;
        if (tickCount==(visibilityTime+invisibilityTime)){
            tickCount=0;
        }
        else{
            tickCount= tickCount+1;
        }

        if (new Range(this.position.getPosition(), player.position.getPosition()).getRange() < 2){
            super.combat(player);
        }


    }

    @Override
    public void victory(Enemy enemy) {

    }

    @Override
    public char toChar() {
        if(visible)
            return super.toChar();
        else
            return '.';
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }


    public void accept(Units unit, Position newPosition) {
        unit.accept(this);
    }

}

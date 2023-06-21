package Game.GameView.Units;

import Game.GameView.BoardPackaeg.Position;
import Game.GameView.BoardPackaeg.Tile;
import Game.GameView.MessageCallback;
import Game.GameView.CLI;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Players.Player;

public abstract class Units extends Tile {
    protected String Name;
    protected Health health;

    protected char tile;
    protected String name;

    protected int attack;
    protected int defense;

    protected MessageCallback messageCallBack;

    protected Units(char tile, String name, int healthPool, int attack, int defense) {
        super(tile);
        this.defense = defense;
        this. tile = tile;
        this.name = name;
        this.health = new Health(healthPool,healthPool);
        this.attack = attack;
        this.messageCallBack = new CLI();


    }

    @Override
    public char getTile() {
        return super.getTile();
    }

    protected void initialize(char tile, int[] position, MessageCallback messageCallback){
        this.messageCallBack = messageCallback;

    }

    public Position getPosition(){
        return this.position;
    }


    public int defend(){
        return 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth(){
        return health.healthAmount;
    };
    public void setHealth(int i) {this.health.setHealthAmount(i);}

    public int getAttack(){
        return attack;
    }

    public int getDefense() {
        return defense;
    }




    public void setPosition(Position newPos){
        super.setPosition(newPos);
        this.position = newPos;
    }

    public abstract void victory(Units unit);
    public abstract void victory(Player player);
    public abstract void victory(Enemy enemy);



    public Position moveLeft(){
        return new Position(new int[]{this.position.getPosition()[0] , this.position.getPosition()[1]-1});

    }

    public Position moveUp(){
        return new Position(new int[]{this.position.getPosition()[0]-1 , this.position.getPosition()[1]});
    }

    public Position moveDown(){
        return new Position(new int[]{this.position.getPosition()[0]+1, this.position.getPosition()[1]});
    }

    public Position moveRight(){
        return new Position(new int[]{this.position.getPosition()[0] , this.position.getPosition()[1]+1});

    }
    public Position doNothing(){
        return this.position;
    }

    public int getAttackPoints(){
        return this.attack;
    }
    public void setAttackPoints(int i){
        this.attack=i;
    }
    public void setDefensePoints(int i){
        this.defense=i;
    }


    public void setHealthAmount(int i) {
        if(!(this.health.getHealthPool()<i)){
            this.health.setHealthAmount(i);
        }
        else
            this.health.setHealthAmount(health.healthPool);


    }
}

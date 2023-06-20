package Game.GameView.BoardPackaeg;

import Game.GameView.MessageCallback;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Enemys.Trap;
import Game.GameView.Units.Players.Player;
import Game.GameView.Units.Players.Warrior;
import Game.GameView.Units.Units;


import javax.swing.tree.TreePath;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private List<Tile> tiles;
    private TileFactory tileFactory = new TileFactory();
    private Tile[][] map ;
    private int numberOfEnemies;
    private int height;
    private int width;
    private List<List<String>> boardMapForToString;
    private Player player;
    private List<Enemy> enemiesList;

    public Board(List<List<String>> cuurentLevel, Player player) {
        if (!(this.map == null)) {
            map = null;
            tiles = new ArrayList<>();
        }
        this.player = player;
        this.boardMapForToString = cuurentLevel;
        this.height = cuurentLevel.get(0).size();
        this.width = cuurentLevel.get(0).get(0).length();
        map = new Tile[height][width];
        for (int i = 0; i < height; i++) {
            String line = cuurentLevel.get(0).get(i);
            for (int j = 0; j < width; j++) {
                Position position = new Position(new int[]{i, j});
                Tile t = tileFactory.generate(line.charAt(j), position);
                if (line.charAt(j)=='@'){
                    this.player.setPosition(position);
                    map[i][j] = player;
                }
                else{
                    map[i][j] = t;
                }
            }
        }
        this.enemiesList = getEnemies();
    }

    public void gameTick(String movement){
        Position p = player.gameTick(movement);
        player.interact(map[p.getPosition()[0]][p.getPosition()[1]]);
        for (Enemy enemy: getEnemies()) {
            if (!enemy.isDead()){
                Position p1 = enemy.gameTick();
            }

        }
    }

    public void abilityCast(Player player){
        List<Enemy> enemiesToRemove =  player.abilityCast(getEnemies());
        for (Enemy enemy: enemiesToRemove) {
            removeEnemyFromBoard(enemy);
        }
    }

    public String gameTick(Player player, String movement){
        Position prevPosition = player.getPosition();
        Position newPlayerPosition = player.gameTick(movement);
        Tile tileToStepInto = map[newPlayerPosition.getPosition()[0]][newPlayerPosition.getPosition()[1]];
        player.interact(tileToStepInto);
        if (player.isDead()){
            removePlayerFromBoard(player);
        }
        if (!changedPosition(player.position, prevPosition)){
            map[player.position.getPosition()[0]][player.position.getPosition()[1]] = player;
            map[prevPosition.getPosition()[0]][prevPosition.getPosition()[1]] = tileToStepInto;
        }


        for (Enemy enemy: enemiesList) {
            if (!enemy.isDead()){
                enemy.gameTick(player);
                Position prev = enemy.getPosition();
                Position toInterPos = enemy.gameTick(player);
                Tile toInter = map[toInterPos.getPosition()[0]][toInterPos.getPosition()[1]];
                enemy.interact(toInter, player);
                if (enemy.isDead()){
                    removeEnemyFromBoard(enemy);
                }
                if (!changedPosition(enemy.position, prev) && !enemy.isDead()){
                    map[enemy.position.getPosition()[0]][enemy.position.getPosition()[1]] = enemy;
                    map[prev.getPosition()[0]][prev.getPosition()[1]] = toInter;
                }
                else{
                    map[enemy.position.getPosition()[0]][enemy.position.getPosition()[1]] = enemy;
                }
            }
        }
        return printBoard();
    }


    public void removePlayerFromBoard(Player player){
        map[player.position.getPosition()[0]][player.position.getPosition()[1]] = tileFactory.onDeath(player.position);
    }

    public void removeEnemyFromBoard(Enemy enemy){
        map[enemy.position.getPosition()[0]][enemy.position.getPosition()[1]] = tileFactory.generate('.', enemy.getPosition());;
    }

    public boolean changedPosition(Position p1, Position p2){
        return p1.getPosition()[0]==p2.getPosition()[0] && p1.getPosition()[1]==p2.getPosition()[1];

    }



    public List<Enemy> getEnemies(){
        List<Enemy> enemies = new ArrayList<>();
        for (Tile[] tileline: map) {
            for(Tile tile: tileline){
                if (tile.getTile()!='.' && tile.getTile()!='#' && tile.getTile()!='@'){
                    enemies.add(tileFactory.produceEnemy(tile.getTile(), tile.getPosition()));
                }
            }
        };
        this.numberOfEnemies = enemies.size();
        return enemies;
    }

    public int getNumberOfEnemies(){
        return this.numberOfEnemies;
    }


    public Tile get(int x, int y)  {
        Tile tile = map[x][y];
        return tile;
    }

    public void replace(Tile tile1, Tile tile2) {
        Position pos= tile1.getPosition();
        Position pos2 = tile2.getPosition();
        map[pos.getPosition()[0]][pos.getPosition()[1]] = tile2;
        tile2.initialize(pos);
        map[pos2.getPosition()[0]][pos2.getPosition()[1]] = tile1;
        tile1.initialize(pos2);
    }

    public String printBoard() {
        String board = "";
        List<Tile> lines = new ArrayList<>();
        for (int i =0 ; i<height; i++){
            for (int j=0; j<width; j++){
                lines.add(map[i][j]);
            }
        }
        for (int i=0; i<lines.size(); i++){
            board = board+lines.get(i).getTile();
            if (i!=0 && i%49==48){
                if (i!=(lines.size()-1)){
                    board = board+"\n";
                }
            }
        }

        return board;
    }
}
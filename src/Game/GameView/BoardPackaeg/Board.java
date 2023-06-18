package Game.GameView.BoardPackaeg;

import Game.GameView.MessageCallback;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Players.Player;
import Game.GameView.Units.Players.Warrior;
import Game.GameView.Units.Units;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private List<Tile> tiles;
    private TileFactory tileFactory = new TileFactory();
    private Map<Position, Tile> map = new HashMap<>();
    private Map<Position, Units> mapOfUnits = new TreeMap<>();
    private List<Units> listOfUnitsInBoard = new ArrayList<>();
    private int numberOfEnemies;
    private String boardOutput;
    private int height;
    private int width;
    private List<List<String>> boardMapForToString;
    private Player player;

    public Board(List<List<String>> cuurentLevel, Player player) {
        if (!(this.map == null)) {
            map = new TreeMap<>();
            tiles = new ArrayList<>();
        }
        this.player = player;
        this.boardMapForToString = cuurentLevel;
        this.height = cuurentLevel.get(0).size();
        this.width = cuurentLevel.get(0).get(0).length();
        for (int i = 0; i < height; i++) {
            String line = cuurentLevel.get(0).get(i);
            for (int j = 0; j < width; j++) {
                Position position = new Position(new int[]{i, j});
                Tile t = tileFactory.generate(line.charAt(j), position);
                if (line.charAt(j)=='@'){
                    map.put(position, player);
                }
                else{
                    map.put(position, t);
                }
            }
        }
    }

    public void gameTick(String movement){
        Position p = player.gameTick(movement);
        player.interact(map.get(p));
        for (Enemy enemy: getEnemies()) {
            if (!enemy.isDead()){
                Position p1 = enemy.gameTick();
            }

        }
    }

    public Board gameTick(Player player, String movement){
        Position prevPosition = player.getPosition();
        Position newPosition = player.gameTick(movement);
        player.interact(map.get(newPosition));
        replace(player, map.get(prevPosition));

        List<Enemy> enemies = getEnemies();
        for (Enemy enemy: enemies) {
            if (!enemy.isDead()){
                Position prev = enemy.getPosition();
                enemy.interact(map.get(enemy.gameTick()));
                replace(enemy, map.get(prev));
            }
        }
        return null;


    }



    public List<Enemy> getEnemies(){
        List<Enemy> enemies = new ArrayList<>();
        for (Tile tile: map.values()) {
            if (tile.getTile()!='.' && tile.getTile()!='#' && tile.getTile()!='@'){
                enemies.add(tileFactory.produceEnemy(tile.getTile(), tile.getPosition()));
            }
        };
        this.numberOfEnemies = enemies.size();
        return enemies;
    }

    public int getNumberOfEnemies(){
        return this.numberOfEnemies;
    }


    public Tile get(int x, int y)  {
        Position position = new Position(new int[]{x,y});
        Tile tile= null;
        tile = map.get(position);
        return tile;
    }




    public void remove(Enemy e) {
        tiles.remove(e);
        Position p = e.getPosition();
        tiles.add(new Empty(p));
    }
    public void remove(Units unit){
        tiles.remove(unit);
        Position p = unit.getPosition();
        tiles.add(new Empty(p));
    }

    public void replace(Tile tile1, Tile tile2) {
        map.replace(tile1.getPosition() ,tile2);
        map.replace(tile2.getPosition(), tile1);
    }

    @Override
    public String toString() {
        String board = "";
        List<Character> lines = new ArrayList<>();
        for (Tile tile: map.values()) {
            lines.add(tile.getTile());
        }
        for (int i=0; i<lines.size(); i++){
            board = board+lines.get(i);
            if (i!=0 && i%49==48){
                if (i!=(lines.size()-1)){
                    board = board+"\n";
                }
            }
        }

        return board;
    }
}

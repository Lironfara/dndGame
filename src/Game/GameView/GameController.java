package Game.GameView;

import Game.GameView.BoardPackaeg.Board;
import Game.GameView.BoardPackaeg.Position;
import Game.GameView.BoardPackaeg.Tile;
import Game.GameView.BoardPackaeg.TileFactory;
import Game.GameView.Units.Enemys.Enemy;
import Game.GameView.Units.Players.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GameController {

    private MessageCallback messageCallback;
    public GameController(){
        messageCallback = new MessagesPrinter();
    }

    public List<List<String>> loadLevel(File file){
        File[] files = file.listFiles();
        List<List<String>> levels = new ArrayList<>();
        if (files!=null){
            for (File file1: files) {
                if (file1.isFile() && file1.getName().contains("level")){
                    List<String> lines = new ArrayList<>();
                    try{
                        BufferedReader reader = new BufferedReader(new FileReader(file1));
                        String nextLine;
                        while ((nextLine =reader.readLine()) !=null){
                            lines.add(nextLine);
                        }
                    }
                    catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    levels.add(lines);
                }

            }
        }
        return levels;
    }

    public Player choosePlayer(List<List<String>> cuurentLevel){
        Player player = null;
        Scanner scanner = new Scanner(System.in);
        messageCallback.gameStartOutPut();
        int dx = scanner.nextInt();
        while (1>dx && 6<dx){
           messageCallback.gameStartOutPut();
            dx = scanner.nextInt();
        }
        TileFactory tileFactory = new TileFactory();
        for(int height=0; height<cuurentLevel.get(0).size(); height++){
            String line = cuurentLevel.get(0).get(height);
            for (int width=0; width<line.length(); width++){
               Position position = new Position(new int[]{height, width});

               if (line.charAt(width)=='@'){
                   Position playerPosition = position;
                   player= tileFactory.producePlayer(dx-1, playerPosition);
                   messageCallback.choosePlayerSelection("You have selected:\n " + player.getName());
               }
           }

        }
        return player;
    }

    public Board gameTick(Board board, Player player){
        List<Enemy> enemies = board.getEnemies();
        for (Enemy enemy: enemies) {
            enemy.gameTick();
        }
        player.gameTick();
        return board;
    }


    public Player startGame(List<List<String>> levels) {
        int currentLevel = 1;
        List<List<String>> cuurentLevel = Collections.singletonList(levels.get(currentLevel - 1));
        Player player = choosePlayer(cuurentLevel);
        Board board = new Board(cuurentLevel, player);
        while (!player.isDead() && currentLevel<=4){
            gameTick(board, player);
        }
        return player;
    }

}

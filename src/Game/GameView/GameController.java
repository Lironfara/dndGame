package Game.GameView;

import Game.GameView.BoardPackaeg.Board;
import Game.GameView.BoardPackaeg.Position;
import Game.GameView.BoardPackaeg.TileFactory;
import Game.GameView.Units.Players.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
public class GameController {
    private CLI cli;
    public GameController(){
        this.cli = new CLI();
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
    public Player choosePlayer(List<List<String>> currentLevel){
        Player player = null;
        cli.gameStartOutPut();
        String dx = cli.readerInput();
        List<String> valid = new ArrayList<>();
        valid.add("1"); valid.add("2"); valid.add("3");valid.add("4"); valid.add("5"); valid.add("6"); valid.add("7");
        while (!valid.contains(dx)){
            cli.gameStartOutPut();
           dx = cli.readerInput();
        }
        TileFactory tileFactory = new TileFactory();
        for(int height=0; height<currentLevel.get(0).size(); height++){
            String line = currentLevel.get(0).get(height);
            for (int width=0; width<line.length(); width++){
               Position position = new Position(new int[]{height, width});

               if (line.charAt(width)=='@'){
                   Position playerPosition = position;
                   player= tileFactory.producePlayer(Integer.parseInt(dx)-1, playerPosition);
                   cli.choosePlayerSelection("You have selected:\n " + player.getName());
               }
           }
        }
        return player;
    }
    public Player startGame() {
        int currentLevel = 1;
        Levels levels = new Levels();
        List<List<String>> currentLevelMap = levels.getLevels();
        Player player = choosePlayer((currentLevelMap));
        Board board = new Board((currentLevelMap), player);
        cli.printBoard(board.printBoard());
        while (!player.isDead() && currentLevel<=4){
            String movement = cli.playerMoveSelection();
            if (movement.equals("e")){
                board.abilityCast(player);
                cli.printBoard(board.gameTick(player, "q"));
            }
            else{
                cli.printBoard(board.gameTick(player,movement));
                if (board.getNumberOfEnemies()==0){
                    currentLevel++;
                    //currentLevelMap = (levels.get(currentLevel));
                    board = new Board((currentLevelMap), player);
                    cli.printBoard(board.printBoard());
                }
            }
        }
        return player;
    }
}

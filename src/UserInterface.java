import Game.GameView.BoardPackaeg.Board;
import Game.GameView.GameController;
import Game.GameView.MessageCallback;
import Game.GameView.MessagesPrinter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private MessagesPrinter messagesPrinter;
    private GameController gameController;

    private File file;

    public UserInterface() {

    }


    public static void main(String[] args) {
       GameController gameController = new GameController();
       String path = "C:\\Users\\liron\\Desktop\\BGU\\levels_dir";
       File file = new File(path);
       gameController.startGame(gameController.loadLevel(file));
    }
}

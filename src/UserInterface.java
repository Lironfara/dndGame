import Game.GameView.GameController;
import Game.GameView.CLI;

import java.io.File;

public class UserInterface {
    private CLI CLI;
    private GameController gameController;
    private File file;
    public UserInterface() {
    }
    public static void main(String[] args) {
       GameController gameController = new GameController();
       gameController.startGame();
    }
}

import java.io.*;
import java.io.IOException;
/**
 * This class represents a game
 */
public class Game {
    int X_TILES = 0;
    int Y_TILES = 0;
    int time_game;
    int mines_num;
    int hypermines;

    /**
     *
     * @param path path to the game description file
     * @throws FileNotFoundException if the file is not found
     * @throws InvalidValueException if the values in the file are not valid
     */
    public Game(String path) throws FileNotFoundException, InvalidValueException {
        File gamefile = new File(path);

        BufferedReader reader = new BufferedReader(new FileReader(gamefile));

        String difficulty = null, mines = null, time = null, hypermine = null;

        try {
            difficulty = reader.readLine();
            mines = reader.readLine();
            time = reader.readLine();
            hypermine = reader.readLine();
//

            if (difficulty == null || mines == null || time == null || hypermine == null) {
                throw new InvalidDescriptionException("Invalid game description");
            }
        } catch (IOException | InvalidDescriptionException e) {
            if (e.getClass() == InvalidDescriptionException.class) {
                System.out.println(e.getMessage());
            } else if (e.getClass() == IOException.class) {
                System.out.println("Error reading game description");
            }

            System.exit(0);
        }
        if (difficulty.equals("1")) {
            X_TILES = 9;
            Y_TILES = 9;

//            System.out.println(mines_num);

        } else if (difficulty.equals("2")) {
            X_TILES = 16;
            Y_TILES = 16;

        } else {
            throw new InvalidValueException("Invalid difficulty value");
        }
        if (difficulty.equals("1")) {
            if (!(Integer.parseInt(mines) > 8 && Integer.parseInt(mines) < 12)) {
                throw new InvalidValueException("Invalid mines value");
            }
            if (!(Integer.parseInt(time) > 119 && Integer.parseInt(time) < 181)) {
                throw new InvalidValueException("Invalid time value");
            }
            if (!(Integer.parseInt(hypermine) == 0)) {
                throw new InvalidValueException("Invalid hypermine value");
            }
        } else if (difficulty.equals("2")) {
            if (!(Integer.parseInt(mines) >34 && Integer.parseInt(mines) < 46)) {
                throw new InvalidValueException("Invalid mines value");
            }
            if (!(Integer.parseInt(time) > 239 && Integer.parseInt(time) < 361)) {
                throw new InvalidValueException("Invalid time value");
            }
            if (!(Integer.parseInt(hypermine) == 1)) {
                throw new InvalidValueException("Invalid hypermine value");
            }
        }
        time_game= Integer.parseInt(time);
        mines_num = Integer.parseInt(mines);
        hypermines = Integer.parseInt(hypermine);



        }
    }

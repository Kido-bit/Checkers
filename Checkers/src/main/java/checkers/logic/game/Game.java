package checkers.logic.game;

import checkers.database.dao.MoveDao;
import checkers.database.dao.PlayerDao;
import checkers.database.model.MoveEntity;
import checkers.database.model.PlayerEntity;
import checkers.logic.board.Board;
import checkers.logic.board.Point;
import checkers.logic.board.Spot;
import checkers.logic.player.Move;
import checkers.logic.player.Player;
import checkers.utils.HibernateFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    private GameStatusModule gameStatusModule;
    private List<Player> players;
    public Board board;
    public Player currentPlayer;
    private GameStatus status;
    private int moveCounter = 0;
    private static Scanner scanner = new Scanner(System.in);
    private HibernateFactory hibernateFactory = new HibernateFactory();
    private PlayerDao playerDao = new PlayerDao(hibernateFactory);
    private MoveDao moveDao = new MoveDao(hibernateFactory);

    public void runGame() throws Exception {
        Game game = new Game();
        Menu.mainMenu();
        int menuOption = Menu.getOptionInput();
        switch (menuOption) {
            case 1:
                game.newGame();
                while (game.isActive()) {
                    game.makeMove();
                }
                System.out.println(game.getCurrentPlayer().getName() + " has won the game!");
                break;
            case 2:
                game.continueGame();
                while (game.isActive()) {
                    game.makeMove();
                }
                System.out.println(game.getCurrentPlayer().getName() + " has won the game!");
                break;
            case 3:
                return;
            default:
                throw new IllegalStateException("Unexpected value: " + menuOption);
        }
    }

    public List<Player> createPlayers() {
        players = new ArrayList<>();

        System.out.println("Enter White Player name:");
        Player whitePlayer = new Player(Player.whitePlayerName = scanner.nextLine(), true, 0);
        players.add(whitePlayer);
        playerDao.add(new PlayerEntity(whitePlayer));

        System.out.println("Enter Black Player name:");
        Player blackPlayer = new Player(Player.blackPlayerName = scanner.nextLine(), false, 0);
        players.add(blackPlayer);
        playerDao.add(new PlayerEntity(blackPlayer));

        return players;
    }

    public void newGame() {
        gameStatusModule = new GameStatusModule();
        playerDao.reset();
        moveDao.reset();
        players = createPlayers();
        initializeNewGame();
        getBoard().printBoard();
    }

    public void initializeNewGame() {

        board = new Board();
        board.resetBoard();
        gameStatusModule.setBoard(board);

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        if (player1.isWhite()) {
            currentPlayer = player1;
        } else {
            currentPlayer = player2;
        }
    }

    public void continueGame() throws Exception {
        List<PlayerEntity> all = playerDao.getAll();
        this.players = all.stream()
                .map(Player::new)
                .collect(Collectors.toList());
        initializeContinue();
    }

    public void initializeContinue() throws Exception {

        List<MoveEntity> all = moveDao.getAll();
        List<Move> moves = all.stream()
                .map(Move::new)
                .collect(Collectors.toList());

        board = new Board();
        board.resetBoard();
        gameStatusModule.setBoard(board);

        Player whitePlayer = players.get(0);
        Player blackPlayer = players.get(1);

        if (whitePlayer.isWhite()) {
            currentPlayer = whitePlayer;
        } else {
            currentPlayer = blackPlayer;
        }

        for (Move move : moves) {
            String startInput = move.getStart();
            String startSpotXY = convertPlayerInput(startInput);
            int startX = Integer.parseInt(String.valueOf(startSpotXY.charAt(0)));
            int startY = Integer.parseInt(String.valueOf(startSpotXY.charAt(1))) - 1;
            Point startPoint = new Point(startX, startY);

            String endInput = move.getEnd();
            String endSpotXY = convertPlayerInput(endInput);
            int endX = Integer.parseInt(String.valueOf(endSpotXY.charAt(0)));
            int endY = Integer.parseInt(String.valueOf(endSpotXY.charAt(1))) - 1;
            Point endPoint = new Point(endX, endY);

            board.getBoardSpot(endPoint);
            if (Spot.isEndSpotValid(gameStatusModule)) {
                board.setSpotsAfterMove(gameStatusModule);
                board.advancePiece(gameStatusModule);
            } else if (board.getStartPiece(gameStatusModule).hasKill(gameStatusModule)) {
                if (board.getStartPiece(gameStatusModule).killEnemyPiece(gameStatusModule)) {
                    currentPlayer.killCounter();
                    board.setSpotsAfterMove(gameStatusModule);
                    board.advancePiece(gameStatusModule);
                    startX = endX;
                    startY = endY;
                    if (board.getStartPiece(gameStatusModule).hasKill(gameStatusModule)) {
                        continue;
                    }
                }
            }
            currentPlayer = currentPlayer.switchPlayers(currentPlayer, players);
        }
        board.printBoard();
    }

    public String getPlayerInput() {
        Pattern movePattern = Pattern.compile("[a-hA-H][1-8]");
        String input;
        while (true) {
            do {
                input = scanner.nextLine();
                if (input.equals("exit")) {
                    status = GameStatus.END;
                    return input;
                } else if (input.length() != 2) {
                    System.out.println("Invalid input!");
                }
            } while (input.length() != 2);
            Matcher matcher = movePattern.matcher(input);
            boolean isInputValid = matcher.matches();
            if (isInputValid) {
                break;
            } else {
                System.out.println("Invalid input!");
            }
        }
        return input;
    }

    public String convertPlayerInput(String input) {
        char charX = input.charAt(0);
        char charY = input.charAt(1);
        return String.valueOf(charX - 97) + charY;
    }

    private Point parseInputXY(String input) {
        Point point = new Point();
        point.setX(Integer.parseInt(String.valueOf(input.charAt(0))));
        point.setX(Integer.parseInt(String.valueOf(input.charAt(1))) - 1);
        return point;
    }

    public void getSpot(boolean isStartSpot) throws Exception {
        Spot spot = new Spot();
        gameStatusModule.setStartSpot(spot);
        String stringSpotXY;
        Point point;
        boolean isSpotValid;
        do {
            if (isStartSpot) {
                System.out.println("Which checker to move?");
                String startInput = getPlayerInput();
                stringSpotXY = convertPlayerInput(startInput);
            } else {
                System.out.println("Where to go?");
                String endSpot = getPlayerInput();
                stringSpotXY = convertPlayerInput(endSpot);
            }
            point = parseInputXY(stringSpotXY);
            int startX = point.getX();
            int startY = point.getY();
            gameStatusModule.getStartSpot().getPoint().setX(startX);
            gameStatusModule.getStartSpot().getPoint().setY(startY);
            if (isStartSpot) {
                isSpotValid = Spot.validateStartSpot(gameStatusModule);
            } else {
                isSpotValid = Spot.validateEndSpot(gameStatusModule);
            }
        } while (!isSpotValid);
    }

    public void makeMove() throws Exception {

        GameStatusModule actualTurn = new GameStatusModule(this);
        System.out.println(currentPlayer.getName() + " move.");
        // Choosing checker to move
        getSpot(true);
        while (true) {
            // Choosing where to move
            getSpot(false);
            // Making move
            if (Spot.isEndSpotValid(gameStatusModule)) {
                board.setSpotsAfterMove(gameStatusModule);
                board.getPiece(actualTurn).advancePiece(gameStatusModule);
                moveDao.add(new MoveEntity(this));
                getBoard().printBoard();
                break;
                // Making kill
            } else if (board.getStartPiece(actualTurn).hasKill(gameStatusModule)) {
                if (board.getStartPiece(actualTurn).killEnemyPiece(gameStatusModule)) {
                    currentPlayer.killCounter();
                    board.setSpotsAfterMove(gameStatusModule);
                    board.getPiece(gameStatusModule).advancePiece(gameStatusModule);
                    getBoard().printBoard();
                    gameStatusModule.setStartSpot(gameStatusModule.getEndSpot());
                    moveDao.add(new MoveEntity(this));
                    if (board.getStartPiece(gameStatusModule).hasKill(gameStatusModule)) {
                        gameStatusModule.setStartSpot(gameStatusModule.getEndSpot());
                        System.out.println("Another kill!");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Invalid Spot!");
                }
            }
            moveCounter += 1;
        }
        if (currentPlayer.kills == 12) {
            status = GameStatus.END;
        }
        currentPlayer = currentPlayer.switchPlayers(currentPlayer, players);
    }

    public boolean isActive() {
        return status != GameStatus.END;
    }
}

package checkers.logic.game;

import checkers.database.dao.MoveDao;
import checkers.database.dao.PlayerDao;
import checkers.database.model.MoveEntity;
import checkers.database.model.PlayerEntity;
import checkers.logic.board.Board;
import checkers.logic.board.Spot;
import checkers.logic.player.Move;
import checkers.logic.player.Player;
import checkers.utils.HibernateFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private List<Player> players;
    private Board board;
    private Player currentPlayer;
    private GameStatus status;
    private int moveCounter = 0;
    private List<Move> movesPlayed;
    private static Scanner scanner = new Scanner(System.in);
    private HibernateFactory hibernateFactory = new HibernateFactory();
    private PlayerDao playerDao = new PlayerDao(hibernateFactory);
    private MoveDao moveDao = new MoveDao(hibernateFactory);
    private String startSpot;
    private String endSpot;


    public void runGame() throws Exception {
        Menu.mainMenu();
    }

    public List<Player> createPlayers() {
        players = new ArrayList<>();

        while (true) {
            System.out.println("Enter White Player name:");
            Player whitePlayer = new Player(Player.whitePlayerName = scanner.nextLine(), true, 0);
            if (playerDao.getByName(whitePlayer.getName()).isEmpty()) {
                players.add(whitePlayer);
                playerDao.add(new PlayerEntity(whitePlayer));
                break;
            } else {
                System.out.println("Player already in Data Base!");
            }
        }
        while (true) {
            System.out.println("Enter Black Player name:");
            Player blackPlayer = new Player(Player.blackPlayerName = scanner.nextLine(), false, 0);
            if (playerDao.getByName(blackPlayer.getName()).isEmpty()) {
                players.add(blackPlayer);
                playerDao.add(new PlayerEntity(blackPlayer));
                break;
            } else {
                System.out.println("Player already in Data Base!");
            }
        }
        playerDao.getAll().forEach(System.out::println);
        return players;
    }

    public void newGame() {
        playerDao.reset();
        moveDao.reset();
        players = createPlayers();
        initializeNewGame(players);
        getBoard().printBoard();
    }

    public void continueGame() throws Exception {
        List<PlayerEntity> all = playerDao.getAll();
        this.players = all.stream()
                .map(Player::new)
                .collect(Collectors.toList());
        initializeContinue(players);
    }

    public void initializeNewGame(List<Player> players) {

        board = new Board();
        board.resetBoard();

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        if (player1.isWhite()) {
            currentPlayer = player1;
        } else {
            currentPlayer = player2;
        }

        movesPlayed = new ArrayList<>();
    }

    public void initializeContinue(List<Player> players) throws Exception {

        List<MoveEntity> all = moveDao.getAll();
        List<Move> moves = all.stream()
                .map(Move::new)
                .collect(Collectors.toList());

        board = new Board();
        board.resetBoard();

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        if (player1.isWhite()) {
            currentPlayer = player1;
        } else {
            currentPlayer = player2;
        }

        for (Move move : moves) {
            String startInput = move.getStart();
            String startSpotXY = convertPlayerInput(startInput);
            int startX = Integer.parseInt(String.valueOf(startSpotXY.charAt(0)));
            int startY = Integer.parseInt(String.valueOf(startSpotXY.charAt(1))) - 1;

            String endInput = move.getEnd();
            String endSpotXY = convertPlayerInput(endInput);
            int endX = Integer.parseInt(String.valueOf(endSpotXY.charAt(0)));
            int endY = Integer.parseInt(String.valueOf(endSpotXY.charAt(1))) - 1;

            board.getBoardSpot(endX, endY);
            if (Spot.isEndSpotValid(board, currentPlayer, startX, startY, endX, endY)) {
                board.setSpotsAfterMove(startX, startY, endX, endY);
                board.advancePiece(endX, endY, currentPlayer);
            } else if (board.getPiece(startX, startY).hasKill(board, currentPlayer, startX, startY)) {
                if (board.getPiece(startX, startY).killEnemyPiece(board, currentPlayer, startX, startY, endX, endY)) {
                    currentPlayer.killCounter();
                    board.setSpotsAfterMove(startX, startY, endX, endY);
                    board.advancePiece(endX, endY, currentPlayer);
                    startX = endX;
                    startY = endY;
                    if (board.getPiece(startX, startY).hasKill(board, currentPlayer, startX, startY)) {
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

    public List<Integer> getSpot(boolean isStartSpot) throws Exception {
        List<Integer> spotXY = new ArrayList<>();
        String stringSpotXY;
        boolean isSpotValid;
        do {
            if (isStartSpot) {
                System.out.println("Which checker to move?");
                startSpot = getPlayerInput();
                stringSpotXY = convertPlayerInput(startSpot);
            } else {
                System.out.println("Where to go?");
                endSpot = getPlayerInput();
                stringSpotXY = convertPlayerInput(endSpot);
            }
            int spotX = Integer.parseInt(String.valueOf(stringSpotXY.charAt(0)));
            int spotY = Integer.parseInt(String.valueOf(stringSpotXY.charAt(1))) - 1;
            spotXY.add(spotX);
            spotXY.add(spotY);
            if (isStartSpot) {
                isSpotValid = Spot.validateStartSpot(board, currentPlayer, spotX, spotY);
            } else {
                isSpotValid = Spot.validateEndSpot(board, spotX, spotY);
            }
        } while (!isSpotValid);
        return spotXY;
    }

    public void makeMove() throws Exception {

        System.out.println(currentPlayer.getName() + " move.");
        // Choosing checker to move
        List<Integer> startCheckerXY = getSpot(true);
        int startX = startCheckerXY.get(0);
        int startY = startCheckerXY.get(1);
        // Choosing where to move
        while (true) {
            List<Integer> endSpotXY = getSpot(false);
            int endX = endSpotXY.get(0);
            int endY = endSpotXY.get(1);
            // Making move
            if (Spot.isEndSpotValid(board, currentPlayer, startX, startY, endX, endY)) {
                board.setSpotsAfterMove(startX, startY, endX, endY);
                moveCounter += 1;
                board.advancePiece(endX, endY, currentPlayer);
                moveDao.add(new MoveEntity(moveCounter, currentPlayer.getName(), startSpot, endSpot, currentPlayer.isWhite()));
                getBoard().printBoard();
                break;
                // Making kill
            } else if (board.getPiece(startX, startY).hasKill(board, currentPlayer, startX, startY)) {
                if (board.getPiece(startX, startY).killEnemyPiece(board, currentPlayer, startX, startY, endX, endY)) {
                    currentPlayer.killCounter();
                    board.setSpotsAfterMove(startX, startY, endX, endY);
                    moveCounter += 1;
                    board.advancePiece(endX, endY, currentPlayer);
                    getBoard().printBoard();
                    startX = endX;
                    startY = endY;
                    moveDao.add(new MoveEntity(moveCounter, currentPlayer.getName(), startSpot, endSpot, currentPlayer.isWhite()));
                    if (board.getPiece(startX, startY).hasKill(board, currentPlayer, startX, startY)) {
                        startSpot = endSpot;
                        System.out.println("Another kill!");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Invalid Spot!");
                }
            }
        }
        if (currentPlayer.kills == 12) {
            status = GameStatus.END;
        }
        currentPlayer = currentPlayer.switchPlayers(currentPlayer, players);
    }

    public boolean isActive() {
        if (status == GameStatus.END) {
            return false;
        }
        return true;
    }
}

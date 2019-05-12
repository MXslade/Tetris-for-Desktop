package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    private final int WIDTH = 15;
    private final int HEIGHT = 35;
    private final int SIZE = 15;

    private boolean placed = false;
    private boolean gameOver = false;

    private double time = 0;

    private Figure figure;

    Random random = new Random();

    private Rectangle[][] board;

    private Pane root;

    private void createBoard() {
        board = new Rectangle[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Rectangle rectangle = new Rectangle(SIZE, SIZE);
                rectangle.setX(j * SIZE);
                rectangle.setY(i * SIZE);
                rectangle.setFill(Color.WHITE);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(1);
                board[i][j] = rectangle;
                root.getChildren().add(rectangle);
            }
        }
    }

    private void fillFigure(Figure figure) {
        Color color = Color.RED;
        switch (figure.getFigureType()) {
            case TYPE1:
                color = Color.RED;
                break;
            case TYPE2:
                color = Color.GREEN;
                break;
            case TYPE3:
                color = Color.YELLOW;
                break;
            case TYPE4:
                color = Color.BLUE;
                break;
            case TYPE5:
                color = Color.PURPLE;
                break;
        }
        for (int i = 0; i < 3; i++) {
            board[figure.getFigureType().ys[i] + figure.getBaseY()][figure.getFigureType().xs[i] + figure.getBaseX()].setFill(color);
        }
        board[figure.getBaseY()][figure.getBaseX()].setFill(color);
    }

    private void resetFill(Figure figure) {
        for (int i = 0; i < 3; i++) {
            board[figure.getFigureType().ys[i] + figure.getBaseY()][figure.getFigureType().xs[i] + figure.getBaseX()].setFill(Color.WHITE);
        }
        board[figure.getBaseY()][figure.getBaseX()].setFill(Color.WHITE);
    }

    private void createFigure() {
        figure = new Figure(random.nextInt(5) + 1, WIDTH / 2, 3);
        for (int i = 0; i < 3; i++) {
            if (board[figure.getFigureType().ys[i] + figure.getBaseY()][figure.getFigureType().xs[i] + figure.getBaseX()].getFill() != Color.WHITE) {
                gameOver = true;
                return;
            }
        }
        if (board[figure.getBaseY()][figure.getBaseY()].getFill() != Color.WHITE) {
            gameOver = true;
            return;
        }
        fillFigure(figure);
    }

    private boolean isSelfCollided(int x, int y) {
        for (int i = 0; i < 3; i++) {
            if (x == figure.getFigureType().xs[i] + figure.getBaseX() && y + 1 == figure.getFigureType().ys[i] + figure.getBaseY()) {
                return true;
            }
        }
        if (x == figure.getBaseX() && y + 1 == figure.getBaseY()) {
            return true;
        }
        return false;
    }

    private void checkForCollision() {
        for (int i = 0; i < 3; i++) {
            if (figure.getFigureType().ys[i] + figure.getBaseY() == HEIGHT - 1) {
                placed = true;
                return;
            }
            if (!isSelfCollided(figure.getFigureType().xs[i] + figure.getBaseX(), figure.getFigureType().ys[i] + figure.getBaseY()) && board[figure.getFigureType().ys[i] + figure.getBaseY() + 1][figure.getFigureType().xs[i] + figure.getBaseX()].getFill() != Color.WHITE) {
                placed = true;
                return;
            }
        }
        if (figure.getBaseY() == HEIGHT - 1) {
            placed = true;
            return;
        }
        if (!isSelfCollided(figure.getBaseX(), figure.getBaseY()) && board[figure.getBaseY() + 1][figure.getBaseX()].getFill() != Color.WHITE) {
            placed = true;
            return;
        }
    }

    private boolean canRotate() {
        int[] xs = new int[3];
        int[] ys = new int[3];
        for (int i = 0; i < 3; i++) {
            xs[i] = figure.getFigureType().xs[i];
            ys[i] = figure.getFigureType().ys[i];
        }

        figure.getFigureType().rotate();
        for (int i = 0; i < 3; i++) {
            boolean f = false;
            for (int j = 0; j < 3; j++) {
                if (figure.getFigureType().xs[i] == xs[j] && figure.getFigureType().ys[i] == ys[j]) {
                    f = true;
                    break;
                }
            }
            if (figure.getFigureType().xs[i] + figure.getBaseX() < 0 || figure.getFigureType().xs[i] + figure.getBaseX() >= WIDTH ||
                figure.getFigureType().ys[i] + figure.getBaseY() < 0 || figure.getFigureType().ys[i] + figure.getBaseY() >= HEIGHT) {
                figure.getFigureType().rotateBack();
                return false;
            }
            if (!f && board[figure.getFigureType().ys[i] + figure.getBaseY()][figure.getFigureType().xs[i] + figure.getBaseX()].getFill() != Color.WHITE) {
                figure.getFigureType().rotateBack();
                return false;
            }
        }
        figure.getFigureType().rotateBack();
        return true;
    }

    private boolean canLeft() {
        int[] xs = new int [4];
        int[] ys = new int [4];
        for (int i = 0; i < 3; i++) {
            xs[i] = figure.getFigureType().xs[i] + figure.getBaseX() - 1;
            ys[i] = figure.getFigureType().ys[i] + figure.getBaseY();
        }
        xs[3] = figure.getBaseX() - 1;
        ys[3] = figure.getBaseY();
        for (int i = 0; i < 4; i++) {
            boolean f = false;
            for (int j = 0; j < 3; j++) {
                if (xs[i] == figure.getFigureType().xs[j] + figure.getBaseX() && ys[i] == figure.getFigureType().ys[j] + figure.getBaseY()) {
                    f =true;
                    break;
                }
            }
            if (xs[i] == figure.getBaseX() && ys[i] == figure.getBaseY()) {
                f = true;
            }
            if (xs[i] < 0) {
                return false;
            }
            if (!f && board[ys[i]][xs[i]].getFill() != Color.WHITE) {
                return false;
            }
        }
        return true;
    }

    private boolean canRight() {
        int[] xs = new int [4];
        int[] ys = new int [4];
        for (int i = 0; i < 3; i++) {
            xs[i] = figure.getFigureType().xs[i] + figure.getBaseX() + 1;
            ys[i] = figure.getFigureType().ys[i] + figure.getBaseY();
        }
        xs[3] = figure.getBaseX() + 1;
        ys[3] = figure.getBaseY();
        for (int i = 0; i < 4; i++) {
            boolean f = false;
            for (int j = 0; j < 3; j++) {
                if (xs[i] == figure.getFigureType().xs[j] + figure.getBaseX() && ys[i] == figure.getFigureType().ys[j] + figure.getBaseY()) {
                    f =true;
                    break;
                }
            }
            if (xs[i] == figure.getBaseX() && ys[i] == figure.getBaseY()) {
                f = true;
            }
            if (xs[i] >= WIDTH) {
                return false;
            }
            if (!f && board[ys[i]][xs[i]].getFill() != Color.WHITE) {
                return false;
            }
        }
        return true;
    }

    private void clearRow(int y) {
        for (int i = y; i >= 1; i--) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j].setFill(board[i - 1][j].getFill());
            }
        }
    }

    private void checkForFullRow() {
        for (int i = 0; i < HEIGHT; i++) {
            int counter = 0;
            for (int j = 0; j < WIDTH; j++) {
                if (board[i][j].getFill() != Color.WHITE) {
                    counter++;
                }
            }
            if (counter == WIDTH) {
                clearRow(i);
            }
        }
    }

    private void update() {
        time += 0.17;
        if (time > 2) {


            checkForCollision();
            if (placed) {
                checkForFullRow();
                createFigure();
                placed = false;
            }
            resetFill(figure);
            figure.move();
            fillFigure(figure);
            time = 0;
        }
    }

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(WIDTH * SIZE, HEIGHT * SIZE);
        createBoard();
        createFigure();
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (gameOver) {
                    stop();
                }
                update();
            }
        };
        animationTimer.start();
        return root;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setTitle("Tetris");
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                    if (canRotate()) {
                        resetFill(figure);
                        figure.getFigureType().rotate();
                        fillFigure(figure);
                    }
                    break;
                case LEFT:
                    if (canLeft()) {
                        resetFill(figure);
                        figure.toLeft();
                        fillFigure(figure);
                    }
                    break;
                case RIGHT:
                    if (canRight()) {
                        resetFill(figure);
                        figure.toRight();
                        fillFigure(figure);
                    }
                    break;
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

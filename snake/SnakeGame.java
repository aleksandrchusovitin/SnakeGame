package com.javarush.games.snake;

import com.javarush.engine.cell.*;


public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private Apple apple;
    private int turnDelay;
    private boolean isGameStopped;
    private static final int GOAL = 28;
    private int score;
    
    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }
    
    private void createGame() {
        score = 0;
        setScore(score);
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        createNewApple();
        isGameStopped = false;
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);
    }
    
    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.DARKSEAGREEN, "");
            }
        }  
        snake.draw(this);
        apple.draw(this);
    }
    
    private void createNewApple() {
        do {
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));    
        } while (snake.checkCollision(apple));
    }
    
    private void gameOver() {
        stopTurnTimer();
        this.isGameStopped = true;
        this.showMessageDialog(Color.BLACK, "GAME OVER", Color.RED, 75);
    }
    
    private void win() {
        stopTurnTimer();
        this.isGameStopped = true; 
        this.showMessageDialog(Color.BLACK, "YOU WIN", Color.WHITE, 75);
    }
    
    @Override
    public void onTurn(int i) {
        snake.move(apple); 
        if (apple.isAlive == false) {
            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
            createNewApple();
        }
        
        if (snake.isAlive == false) {
            gameOver();
        }
        
        if (snake.getLength() > GOAL) {
            win();
        }
        drawScene();
    }
    
    @Override
    public void onKeyPress(Key key) {
        if (key == Key.LEFT) {
            snake.setDirection(Direction.LEFT);     
        } else if (key == Key.RIGHT) {
            snake.setDirection(Direction.RIGHT);     
        } else if (key == Key.UP) {
            snake.setDirection(Direction.UP);     
        } else if (key == Key.DOWN) {
            snake.setDirection(Direction.DOWN); 
        } else if (key == Key.SPACE && isGameStopped == true) {
            createGame();
        }
    }
}

package com.javarush.games.snake;

import java.util.*;

import com.javarush.engine.cell.*;

public class Snake {
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    private List<GameObject> snakeParts = new ArrayList<>();
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    Snake(int x, int y) {
        GameObject snake1 = new GameObject(x, y);
        GameObject snake2 = new GameObject(x + 1, y);
        GameObject snake3 = new GameObject(x + 2, y);

        snakeParts.add(snake1);
        snakeParts.add(snake2);
        snakeParts.add(snake3);
    }

    public void draw(Game game) {
        game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, (isAlive ? Color.BLACK : Color.RED), 75);
        for (int i = 1; i < snakeParts.size(); i++) {
            game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, (isAlive ? Color.BLACK : Color.RED), 75);
        }
    }

    public void setDirection(Direction direction) {
        if ((direction == Direction.LEFT && (snakeParts.get(0).y == snakeParts.get(1).y)) ||
                (direction == Direction.RIGHT && (snakeParts.get(0).y == snakeParts.get(1).y)) ||
                (direction == Direction.UP && (snakeParts.get(0).x == snakeParts.get(1).x)) ||
                (direction == Direction.DOWN && (snakeParts.get(0).x == snakeParts.get(1).x))) {
            return;
        }

        if ((direction == Direction.LEFT && this.direction == Direction.RIGHT) ||
                (direction == Direction.RIGHT && this.direction == Direction.LEFT) ||
                (direction == Direction.UP && this.direction == Direction.DOWN) ||
                (direction == Direction.DOWN && this.direction == Direction.UP)) {
            return;
        } else {
            this.direction = direction;
        }

    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        int x = newHead.x;
        int y = newHead.y;
        if (x < 0 || x >= SnakeGame.WIDTH || y < 0 || y >= SnakeGame.HEIGHT) {
            isAlive = false;
        } else if (!checkCollision(newHead)) {
            snakeParts.add(0, newHead);
            if (x == apple.x && y == apple.y) {
                apple.isAlive = false;
            } else {
                removeTail();
            }
        } else {
            isAlive = false;
        }
    }

    public GameObject createNewHead() {
        int x = 0;
        int y = 0;
        switch (direction) {
            case LEFT:
                x = snakeParts.get(0).x - 1;
                y = snakeParts.get(0).y;
                break;
            case RIGHT:
                x = snakeParts.get(0).x + 1;
                y = snakeParts.get(0).y;
                break;
            case UP:
                x = snakeParts.get(0).x;
                y = snakeParts.get(0).y - 1;
                break;
            case DOWN:
                x = snakeParts.get(0).x;
                y = snakeParts.get(0).y + 1;
                break;
            default:
                break;
        }

        return new GameObject(x, y);
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(GameObject go) {
        for (GameObject obj : snakeParts) {
            if (obj.x == go.x && obj.y == go.y) {
                return true;
            }
        }
        return false;
    }

    public int getLength() {
        return snakeParts.size();
    }
}

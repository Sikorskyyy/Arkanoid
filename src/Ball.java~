package javaarkanoid;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {
  private int positionX, positionY;
  private final int SIZE;
  private final int SPEED;
  private int dirX, dirY;

  public Ball() {
    this.positionX = 400;
    this.positionY = 300;
    this.SIZE = 18;
    this.SPEED = 3;
    this.dirX = 1;
    this.dirY = 1;
  }

  public Ball(int x, int y, int size, int speed) {
    this.positionX = x;
    this.positionY = y;
    this.SIZE = size;
    this.SPEED = speed;
    this.dirX = 1;
    this.dirY = 1;
  }

  public void paint(Graphics g) {
    g.setColor(Color.RED);
    g.fillOval(this.positionX, this.positionY, this.SIZE, this.SIZE);
  }

  public void move(int wdt, int hgt) {
    this.positionX = this.positionX + this.SPEED * this.dirX;
    this.positionY = this.positionY + this.SPEED * this.dirY;

    if (this.positionX < 0) {
      this.dirX = 1;
    } else if (positionX > wdt) {
      this.dirX = -1;
    }

    if (this.positionY < 0) {
      this.dirY = 1;
    }
  }

  public int getPosY() {
    return this.positionY;
  }

  public void setPosY(int positionY) {
    this.positionY = positionY;
  }

  public int getPosX() {
    return this.positionX;
  }

  public void setPosX(int positionX) {
    this.positionX = positionX;
  }

  public int getSize() {
    return this.SIZE;
  }

  public int getDirX() {
    return this.dirX;
  }

  public int getDirY() {
    return this.dirY;
  }

  public void setDirY(int dirY) {
    this.dirY = dirY;
  }

  public void setDirX(int dirX) {
    this.dirX = dirX;
  }
}

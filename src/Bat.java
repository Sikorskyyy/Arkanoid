package javaarkanoid;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Bat implements MouseMotionListener, KeyListener {
  private static final int LEFT = -1;
  private static final int RIGTH = 1;
  private static final int LIMITATION = 725;
  private static final int LEFTARROW = 37;
  private static final int RIGHTARROW = 39;
  private static final int OFFSET = 8;
  private boolean isMoved;
  private int direct;
  private int left;
  private int top = 0;
  private int height = 0;
  private int width = 0;

  class MoveThread extends Thread {
    @Override
    public void run() {

      while (true) {

        if (Bat.this.getMoved() == true) {
          if (Bat.this.direct == Bat.LEFT) {
            Bat.this.left -= Bat.OFFSET;
            if (Bat.this.left <= 0){
              Bat.this.left = 0;
            }
          } else if (Bat.this.direct == Bat.RIGTH) {
            Bat.this.left += Bat.OFFSET;
            if (Bat.this.left >= Bat.LIMITATION){
              Bat.this.left = Bat.LIMITATION;
            }
          }
        }
        try {
          Thread.sleep(10);
        } catch (InterruptedException exception) {
        }
      }
    }
  }

  public Bat() {
    this.startMovie();
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    this.left = e.getX();
    if (Bat.this.left >= Bat.LIMITATION){
      Bat.this.left = Bat.LIMITATION;
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    this.left = e.getX();
    if (Bat.this.left >= Bat.LIMITATION){
      Bat.this.left = Bat.LIMITATION;
    }
  }

  @Override
  public void keyTyped(KeyEvent ke) {}

  @Override
  public void keyPressed(KeyEvent ke) {
    if (ke.getKeyCode() == LEFTARROW) {
      if (this.getMoved() == false){
        this.setMoved(true);
      }
      this.direct = Bat.LEFT;
    } else if (ke.getKeyCode() == RIGHTARROW) {
      if (this.getMoved() == false){
        this.setMoved(true);
      }
      this.direct = Bat.RIGTH;
    }
  }

  @Override
  public void keyReleased(KeyEvent ke) {
    if (ke.getKeyCode() == LEFTARROW || ke.getKeyCode() == RIGHTARROW) {
      if (this.getMoved() == true){
        this.setMoved(false);
      }
    }
  }

  public final void startMovie() {
    MoveThread mt = new MoveThread();
    mt.setDaemon(true);
    mt.start();
  }

  public int getTop() {
    return this.top;
  }

  public int getLeft() {
    return this.left;
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }

  public void setTop(int t) {
    this.top = t;
  }

  public void setLeft(int l) {
    this.left = l;
  }

  public void setHeight(int h) {
    this.height = h;
  }

  public void setWidth(int w) {
    this.width = w;
  }

  public void setDir(int dir) {
    this.direct = dir;
  }

  public boolean getMoved() {
    return isMoved;
  }

  public void setMoved(boolean isMoved) {
    this.isMoved = isMoved;
  }
}

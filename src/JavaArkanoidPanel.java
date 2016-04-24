package javaarkanoid;

import replay.Replay;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JavaArkanoidPanel extends JPanel implements Runnable {
  private ArrayList<Rectangle> Bricks;
  private Ball Ball;
  private final Bat Bat = new Bat();
  private int numBlocks;
  private int numRows;
  private State state;
  private BufferedImage image;
  private BufferedImage background;
  private boolean winner = false;
  private boolean dead = false;
  private boolean auto = false;
  private final int IFRAMEX = 100;
  private final int IFRAMEY = 100;
  private final int IFRAMEH = 500;
  private final int IFRAMEW = 850;

  private Replay replay;
  private boolean isReplay = false;

  public Thread gameThread = null;

  public JavaArkanoidPanel() {
    this.addMouseMotionListener(this.Bat);

    setState(State.GAME);
  }

  /**
   * main cycle
   */
  @Override
  public void run() {
    if (isReplay == true) {
      try {
        replay.readFromFile();
      } catch (ClassNotFoundException | IOException e) {
        e.printStackTrace();
      }
    }

    while (true) {

      this.gameOver();
      if (this.dead == true || this.winner == true) {
        repaint();
        break;
      } else {
        if (this.isReplay == true) {
          replay.startReplay();
        } else {
          autoMode(auto);
          this.Ball.move(this.getiFramew(), this.getiFrameh());
          replay.addState(this.Bat.getLeft(), this.Ball.getPosX(), this.Ball.getPosY());
        }
        this.collisionCheck();
        repaint();
        try {
          Thread.sleep(8);
        } catch (Exception exception) {
          System.out.println(exception.getMessage());

        }
      }
    }
  }

  /**
   * Visualization of the game
   */
  @Override
  public void paint(Graphics graphics) {
    if (this.winner == true) {
      graphics.setColor(Color.WHITE);
      graphics.fillRect(0, 0, this.getiFramew(), this.getiFrameh());
      graphics.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 50));
      graphics.setColor(Color.BLACK);
      graphics.drawString("ПОБЕДА!", 80, 200);
      graphics.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
      graphics.drawString("click to restart", 80, 300);

    } else if (this.dead == true) {
      graphics.setColor(Color.WHITE);
      graphics.fillRect(0, 0, this.getiFramew(), this.getiFrameh());
      graphics.setColor(Color.BLACK);
      graphics.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 50));
      graphics.drawString("ПОРАЖЕНИЕ", 80, 200);
      graphics.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
      graphics.drawString("click to restart", 80, 300);

    } else {
      Graphics graphicsBall = this.image.getGraphics();

      graphicsBall.drawImage(background, 0, 0, this.getiFramew(), this.getiFrameh(), null);
      graphicsBall.setColor(Color.RED);

      this.Ball.paint(graphicsBall);

      graphicsBall.setColor(Color.YELLOW);
      for (int i = 0; i < this.Bricks.size(); i++) {
        Rectangle rect = (Rectangle) this.Bricks.get(i);
        graphicsBall.fillRect(rect.x, rect.y, rect.width, rect.height);
      }
      graphicsBall.setColor(Color.GREEN);
      graphicsBall.fillRect(this.Bat.getLeft(), this.Bat.getTop(), this.Bat.getWidth(),
          this.Bat.getHeight());
      graphics.drawImage(this.image, 0, 0, this);
    }
  }

  /**
   * initialization of the game
   */
  public void init() {
    this.dead = false;
    this.winner = false;
    this.numBlocks = 10;
    this.numRows = 4;
    this.gameThread = new Thread(this);
    this.gameThread.setDaemon(true);

    this.startBrick();
    this.startBat();
    this.Bat.setMoved(true);

    this.startBall();

    replay = new Replay();
    replay.setGame(this);
    isReplay = false;

    this.image =
        new BufferedImage(this.getiFramew(), this.getiFrameh(), BufferedImage.TYPE_INT_RGB);

    try {
      this.background = ImageIO.read(new File("Space.jpg"));
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * CollissionCheck
   */
  public void collisionCheck() {
    Rectangle ballColl =
        new Rectangle(this.Ball.getPosX(), this.Ball.getPosY(), this.Ball.getSize(),
            this.Ball.getSize());
    Rectangle xBatCrash =
        new Rectangle(this.Bat.getLeft(), this.Bat.getTop(), this.Bat.getWidth(),
            this.Bat.getHeight());
    for (int i = 0; i < this.Bricks.size(); i++) {
      Rectangle r = (Rectangle) this.Bricks.get(i);
      if (r.intersects(ballColl)) {
        this.Bricks.remove(r);
        getGraphics().clearRect(r.x, r.y, r.width, r.height);
        this.Ball.setDirY(-1 * this.Ball.getDirY());
      }
    }
    if (ballColl.intersects(xBatCrash)) {
      this.Ball.setDirY(-1);
    }
  }

  /**
   * Check of the game state
   */
  public void gameOver() {
    if (this.Bricks.isEmpty() == true) {
      this.winner = true;

      try {
        System.out.println("writing");
        replay.writeToFile();
      } catch (IOException e) {
        e.printStackTrace();
      }

      replay.stopReplay();

    } else if (this.Ball.getPosY() >= this.getiFrameh() - this.Ball.getSize()) {
      this.dead = true;

      try {
        System.out.println("writing");
        replay.writeToFile();
      } catch (IOException e) {
        e.printStackTrace();
      }


      replay.stopReplay();
    }
  }

  /**
   * Visualization of the bricks
   */
  public void startBrick() {
    this.Bricks = new ArrayList<>();
    int blockSize = this.getiFramew() / this.numBlocks;
    int blockHeight = 20;
    for (int rows = 0; rows < this.getiFramew(); rows += blockSize) {
      for (int cols = 0; cols < this.numRows * blockHeight; cols += blockHeight) {
        Rectangle r = new Rectangle(rows, 80 + cols, blockSize - 2, blockHeight - 2);
        this.Bricks.add(r);
      }
    }
  }

  /**
   * Parameters of the Bat
   */
  public void startBat() {
    this.Bat.setTop(400);
    this.Bat.setLeft(200);
    this.Bat.setHeight(20);
    this.Bat.setWidth(125);
  }

  /**
   * start Ball
   */
  public void startBall() {
    repaint();
    int speed = 3;
    if (getState() == State.HARD) {
      speed = speed * 2;
    }
    this.Ball = new Ball(220, 380, 15, speed);
  }

  public void autoMode(boolean auto) {

    if (auto == true) {
      this.Bat.setMoved(true);

      this.Bat.setDir(this.Ball.getDirX());
      if (this.Bat.getLeft() == this.Ball.getPosX()) {
        this.Bat.setDir(0);
      }
      if (this.Bat.getLeft() >= this.Ball.getPosX()) {
        this.Bat.setDir(-1);
      }
      if (this.Bat.getLeft() <= this.Ball.getPosX()) {
        this.Bat.setDir(1);
      }
    } else
      this.Bat.setMoved(false);

  }

  public int getiFramex() {
    return IFRAMEX;
  }

  public int getiFramey() {
    return IFRAMEY;
  }

  public int getiFrameh() {
    return IFRAMEH;
  }

  public int getiFramew() {
    return IFRAMEW;
  }

  public Bat getBat() {
    return this.Bat;
  }

  public Ball getBall() {
    return this.Ball;
  }

  public void setAutoMode(boolean auto) {
    this.auto = auto;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public boolean getIsReplay() {
    return isReplay;
  }

  public void setIsReplay(boolean isReplay) {
    this.isReplay = isReplay;
  }
}

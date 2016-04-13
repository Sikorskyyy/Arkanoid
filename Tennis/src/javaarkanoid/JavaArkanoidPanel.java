package javaarkanoid;

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

class JavaArkanoidPanel extends JPanel implements Runnable {
  private ArrayList<Rectangle> Bricks;
  private Ball Ball;
  private final Bat Bat = new Bat();
  private int numBlocks;
  private int numRows;

  State state;
  private BufferedImage image;
  private BufferedImage backGround;
  private boolean winner = false;
  private boolean dead = false;
  private boolean avto = false ;
  private final int iFramex;
  private final int iFramey;
  private final int iFrameh;
  private final int iFramew;

  public Thread gameThread = null;

  public JavaArkanoidPanel() {
    this.iFramex = 100;
    this.iFramey = 100;
    this.iFrameh = 500;
    this.iFramew = 850;

    this.addMouseMotionListener(this.Bat);

    state = State.Game;
  }

  /**
   * main cycle
   */
  // @Override
  public void run() {
    while (true) {
      this.Ball.move(this.getiFramew(), this.getiFrameh());
     
      this.gameOver();
      if (this.dead == true) {
        
        repaint();
       
        break;
      } 
      else {
        
        avtoMode(avto);
        this.collisionCheck();
        repaint();
        try {
          Thread.sleep(8);
        } catch (Exception ex) {
          System.out.println(ex.getMessage());
          
        }
      }
    }
  }

  /*
   * Visualization of the game
   */
  @Override
  public void paint(Graphics g) {
    if (this.winner == true) {
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, this.getiFramew(), this.getiFrameh());
      g.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 50));
      g.setColor(Color.BLACK);
      g.drawString("ПОБЕДА!", 80, 200);
      g.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
      g.drawString("click to restart", 80, 300);
      
    } else if (this.dead == true) {
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, this.getiFramew(), this.getiFrameh());
      g.setColor(Color.BLACK);
      g.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 50));
      g.drawString("ПОРАЖЕНИЕ", 80, 200);
      g.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
      g.drawString("click to restart", 80, 300);
      
    } else {
      Graphics b = this.image.getGraphics();
  
      b.drawImage(backGround, 0, 0, this.getiFramew(), this.getiFrameh(), null);
      b.setColor(Color.RED);

      this.Ball.paint(b);

      b.setColor(Color.YELLOW);
      for (int i = 0; i < this.Bricks.size(); i++) {
        Rectangle rect = (Rectangle) this.Bricks.get(i);
        b.fillRect(rect.x, rect.y, rect.width, rect.height);
      }
      b.setColor(Color.GREEN);
      b.fillRect(this.Bat.getLeft(), this.Bat.getTop(), this.Bat.getWidth(), this.Bat.getHeight());
      g.drawImage(this.image, 0, 0, this);
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

    this.image =
        new BufferedImage(this.getiFramew(), this.getiFrameh(), BufferedImage.TYPE_INT_RGB);
    
    try {
      this.backGround = ImageIO.read(new File("Space.jpg"));
    } 
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * CollissionCheck
   */
  public void collisionCheck() {
    Rectangle ballColl =
        new Rectangle(this.Ball.getX(), this.Ball.getY(), this.Ball.getSize(), this.Ball.getSize());
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
    } else if (this.Ball.getY() >= this.getiFrameh() - this.Ball.getSize()) {
      this.dead = true;
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
    int speed=3;
    if(state==State.Hard)
    {
      speed=speed*2;
    }
    this.Ball = new Ball(220, 380, 15, speed);
  }
  
  public void avtoMode(boolean A){
    
    if(A==true)
    {
      this.Bat.setMoved(true); 
      
      this.Bat.setDir( this.Ball.getDirX());
      if(this.Bat.getLeft()==this.Ball.getX())
      {
        this.Bat.setDir(0);
      }
      if(this.Bat.getLeft()>=this.Ball.getX())
      {
        this.Bat.setDir(-1);
      }
      if(this.Bat.getLeft()<=this.Ball.getX())
      {
        this.Bat.setDir(1);
      }
    }
    else this.Bat.setMoved(false);
    
  }
  public int getiFramex() {
    return iFramex;
  }

  public int getiFramey() {
    return iFramey;
  }

  public int getiFrameh() {
    return iFrameh;
  }

  public int getiFramew() {
    return iFramew;
  }

  public Bat getBat() {
    return this.Bat;
  }
  
  public void setAvtoMode(boolean A ){
    this.avto=A;
  }
}

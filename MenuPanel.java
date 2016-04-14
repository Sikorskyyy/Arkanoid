package javaarkanoid;

import javax.imageio.ImageIO;
import javax.swing.*;

// import mywindow.mywin2.eHandler;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel {

  private JButton exit, avto, hard, normal;

  static private State state;

  private int iFramex;
  private int iFramey;
  private int iFrameh;
  private int iFramew;

  Butt butt = new Butt();
  
  private BufferedImage backGround;
  
  public MenuPanel() {

    setLayout(null);
    this.iFramex = 100;
    this.iFramey = 100;
    this.iFrameh = 500;
    this.iFramew = 850;
   
    state = State.Menu;
    
    try{
      this.backGround=ImageIO.read(new File("Menu.jpg"));
  }
  catch (IOException e){e.printStackTrace();}
    repaint();

    hard = new JButton("Hard");
    hard.setBounds(225, 150, 350, 30);
    hard.setBackground(Color.red);
    add(hard);
      
    normal = new JButton("Normal");
    normal.setBounds(275, 225, 250, 30);
    normal.setBackground(Color.yellow);
    add(normal);
    
    avto = new JButton("AvtoMode");
    avto.setBounds(325, 300, 150, 30);
    avto.setBackground(Color.green);
    add(avto);
    
    exit= new JButton("exit");
    exit.setBounds(365, 375, 70, 30);
    exit.setBackground(Color.yellow);
    add(exit);

    exit.addActionListener(butt);
    avto.addActionListener(butt);
    normal.addActionListener(butt);
    hard.addActionListener(butt);
    
  }
  
  public void paint(Graphics g)
  {
      g.drawImage(backGround,0,0, this.getiFramew(),this.getiFrameh(),null);
      g.setFont(new Font("Tahoma", Font.BOLD|Font.ITALIC, 50));
      g.setColor(Color.YELLOW);
      g.drawString("Arkanoid", 300, 100);
      
      hard.repaint();
      normal.repaint();
      avto.repaint();
      exit.repaint();
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

  static public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public class Butt extends KeyAdapter implements ActionListener {

    public void actionPerformed(ActionEvent e) {

        if(e.getSource()== hard){
           state = State.Hard;
        }
        
        if(e.getSource()== normal){
          state = State.Game;
          
        }
        if(e.getSource()== avto){
          state = State.Avto;
          
        }
    
         if(e.getSource()== exit){
           System.exit(0);
        }

     }
  }
}


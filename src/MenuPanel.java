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

  private JButton exit, auto, hard, normal;

  static private State state;

  private final int IFRAMEX = 100;
  private final int IFRAMEY = 100;
  private final int IFRAMEH = 500;
  private final int IFRAMEW = 850;

  Butt butt = new Butt();

  private BufferedImage background;

  public MenuPanel() {

    setLayout(null);

    state = State.MENU;

    try {
      this.background = ImageIO.read(new File("Menu.jpg"));
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    repaint();

    hard = new JButton("Hard");
    hard.setBounds(225, 150, 350, 30);
    hard.setBackground(Color.red);
    add(hard);

    normal = new JButton("Normal");
    normal.setBounds(275, 225, 250, 30);
    normal.setBackground(Color.yellow);
    add(normal);

    auto = new JButton("AvtoMode");
    auto.setBounds(325, 300, 150, 30);
    auto.setBackground(Color.green);
    add(auto);

    exit = new JButton("exit");
    exit.setBounds(365, 375, 70, 30);
    exit.setBackground(Color.yellow);
    add(exit);

    exit.addActionListener(butt);
    auto.addActionListener(butt);
    normal.addActionListener(butt);
    hard.addActionListener(butt);

  }

  public void paint(Graphics g) {
    g.drawImage(background, 0, 0, this.getiFramew(), this.getiFrameh(), null);
    g.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 50));
    g.setColor(Color.YELLOW);
    g.drawString("Arkanoid", 300, 100);

    hard.repaint();
    normal.repaint();
    auto.repaint();
    exit.repaint();
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

  static public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public class Butt extends KeyAdapter implements ActionListener {

    public void actionPerformed(ActionEvent event) {

      if (event.getSource() == hard) {
        state = State.HARD;
      }

      if (event.getSource() == normal) {
        state = State.GAME;

      }
      if (event.getSource() == auto) {
        state = State.AUTO;

      }

      if (event.getSource() == exit) {
        System.exit(0);
      }

    }
  }
}

package javaarkanoid;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

enum State {
  Game, Menu, Hard, Avto
}


public class JavaArkanoid {

  public static void main(String[] arg) {

    final JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    JPanel controlPanel = new JPanel();
    
    final JavaArkanoidPanel arkanoidPanel = new JavaArkanoidPanel();
    arkanoidPanel.init();
    
    f.setBounds(arkanoidPanel.getiFramex(), arkanoidPanel.getiFramey(), arkanoidPanel.getiFramew(),
        arkanoidPanel.getiFrameh());

    f.setTitle("Click to start the game");

    f.addKeyListener(arkanoidPanel.getBat());

    final MenuPanel menuPanel = new MenuPanel();

    f.setBounds(arkanoidPanel.getiFramex(), arkanoidPanel.getiFramey(), arkanoidPanel.getiFramew(),
        arkanoidPanel.getiFrameh());

    CardLayout cl = new CardLayout();

    controlPanel.setLayout(cl);
    controlPanel.add(menuPanel, "menu");
    controlPanel.add(arkanoidPanel, "arkanoid");

    cl.show(controlPanel, "menu");

    JMenuBar menu = new JMenuBar();
    f.setJMenuBar(menu);
    JMenu mnGame = new JMenu("back to the menu?");
    menu.add(mnGame);
    JMenuItem mniStart = new JMenuItem("Yes");
    mniStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
    mniStart.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        menuPanel.setState(State.Menu);
        arkanoidPanel.init();
        arkanoidPanel.repaint();
      }
    });
    
    mnGame.add(mniStart);
      
    arkanoidPanel.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent arg0) {
        
          arkanoidPanel.init();
          arkanoidPanel.gameThread.start();
      }
      @Override
      public void mouseEntered(MouseEvent arg0) {}
      
      @Override
      public void mouseExited(MouseEvent arg0) {}
  
      @Override
      public void mousePressed(MouseEvent arg0) {}
       
      @Override
      public void mouseReleased(MouseEvent arg0) {}
       
  });

    f.add(controlPanel);

    while (true) {

      f.setVisible(true);
      try {
        Thread.sleep(500);
      } catch (InterruptedException ie) {
      }
    
      if (menuPanel.getState() == State.Game) {
        cl.show(controlPanel, "arkanoid");
       
        f.setVisible(true);
        arkanoidPanel.state=State.Game;
        arkanoidPanel.setAvtoMode(false);
        arkanoidPanel.setFocusable(true);

      }
      if (menuPanel.getState() == State.Hard) {
        cl.show(controlPanel, "arkanoid");
        arkanoidPanel.state=State.Hard;
        arkanoidPanel.setAvtoMode(false);
        arkanoidPanel.setFocusable(true);
        f.setVisible(true);
      }
      if (menuPanel.getState() == State.Menu) {
        cl.show(controlPanel, "menu");
        menuPanel.setFocusable(false);
        f.setVisible(true);
      }
      if (menuPanel.getState() == State.Avto) {
        cl.show(controlPanel, "arkanoid");
        arkanoidPanel.setAvtoMode(true);
        f.setVisible(true);
      }
     
      f.setVisible(true);
    }

  }
}



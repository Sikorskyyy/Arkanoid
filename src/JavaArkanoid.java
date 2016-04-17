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
  Game, Menu, Hard, Auto
}


public class JavaArkanoid {

  public static void main(String[] arg) {

    final JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel controlPanel = new JPanel();

    final JavaArkanoidPanel arkanoidPanel = new JavaArkanoidPanel();
    arkanoidPanel.init();

    frame.setBounds(arkanoidPanel.getiFramex(), arkanoidPanel.getiFramey(),
        arkanoidPanel.getiFramew(), arkanoidPanel.getiFrameh());

    frame.setTitle("Click to start the game");

    frame.addKeyListener(arkanoidPanel.getBat());

    final MenuPanel menuPanel = new MenuPanel();

    frame.setBounds(arkanoidPanel.getiFramex(), arkanoidPanel.getiFramey(),
        arkanoidPanel.getiFramew(), arkanoidPanel.getiFrameh());

    CardLayout cardlayout = new CardLayout();

    controlPanel.setLayout(cardlayout);
    controlPanel.add(menuPanel, "menu");
    controlPanel.add(arkanoidPanel, "arkanoid");

    cardlayout.show(controlPanel, "menu");

    JMenuBar menuBar = new JMenuBar();
    frame.setJMenuBar(menuBar);
    JMenu menu = new JMenu("back to the menu?");
    menuBar.add(menu);
    JMenuItem mniStart = new JMenuItem("Yes");
    mniStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
    mniStart.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        menuPanel.setState(State.Menu);
        arkanoidPanel.init();
        arkanoidPanel.repaint();
      }
    });

    menu.add(mniStart);

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

    frame.add(controlPanel);

    while (true) {

      frame.setVisible(true);
      try {
        Thread.sleep(500);
      } catch (InterruptedException ie) {
      }

      if (menuPanel.getState() == State.Game) {
        cardlayout.show(controlPanel, "arkanoid");

        frame.setVisible(true);
        arkanoidPanel.setState(State.Game);
        arkanoidPanel.setAutoMode(false);
        arkanoidPanel.setFocusable(true);

      }
      if (menuPanel.getState() == State.Hard) {
        cardlayout.show(controlPanel, "arkanoid");
        arkanoidPanel.setState(State.Hard);
        arkanoidPanel.setAutoMode(false);
        arkanoidPanel.setFocusable(true);
        frame.setVisible(true);
      }
      if (menuPanel.getState() == State.Menu) {
        cardlayout.show(controlPanel, "menu");
        menuPanel.setFocusable(false);
        frame.setVisible(true);
      }
      if (menuPanel.getState() == State.Auto) {
        cardlayout.show(controlPanel, "arkanoid");
        arkanoidPanel.setAutoMode(true);
        frame.setVisible(true);
      }

      frame.setVisible(true);
    }

  }
}

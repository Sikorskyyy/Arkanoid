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
import replay.Replay;

enum State {
  GAME, MENU, HARD, AUTO
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
    JMenu menu = new JMenu("menu");
    menuBar.add(menu);

    JMenuItem menuStart = new JMenuItem("back to the menu");
    menuStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
    menuStart.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        menuPanel.setState(State.MENU);
        arkanoidPanel.init();
        arkanoidPanel.repaint();
      }
    });

    menu.add(menuStart);

    JMenuItem menuReplay = new JMenuItem("replay");
    menuReplay.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
    menuReplay.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        arkanoidPanel.init();
        arkanoidPanel.repaint();
        arkanoidPanel.setIsReplay(true);
        arkanoidPanel.gameThread.start();
      }
    });
    menu.add(menuReplay);

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

      if (menuPanel.getState() == State.GAME) {
        cardlayout.show(controlPanel, "arkanoid");

        frame.setVisible(true);
        arkanoidPanel.setState(State.GAME);
        arkanoidPanel.setAutoMode(false);
        arkanoidPanel.setFocusable(true);

      }
      if (menuPanel.getState() == State.HARD) {
        cardlayout.show(controlPanel, "arkanoid");
        arkanoidPanel.setState(State.HARD);
        arkanoidPanel.setAutoMode(false);
        arkanoidPanel.setFocusable(true);
        frame.setVisible(true);
      }
      if (menuPanel.getState() == State.MENU) {
        cardlayout.show(controlPanel, "menu");
        menuPanel.setFocusable(false);
        frame.setVisible(true);
      }
      if (menuPanel.getState() == State.AUTO) {
        cardlayout.show(controlPanel, "arkanoid");
        arkanoidPanel.setAutoMode(true);
        frame.setVisible(true);
      }

      frame.setVisible(true);
    }

  }
}

package replay;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javaarkanoid.*;

public class Replay {
    /* List of game states */
    private List<StatePos> stateList = null;
    int listIndex = 0;
    JavaArkanoidPanel game;

    public Replay() {
      stateList = new ArrayList<StatePos>();
    }

    public void addState(int posPlayer, int posXBall, int posYBall) {
      stateList.add(new StatePos(posPlayer, posXBall, posYBall));
    }

    public void setGame(JavaArkanoidPanel game) {
      this.game = game;
    }

    public void writeToFile() throws IOException {
      if (game == null) {
        return;
      }

      FileOutputStream fileOutput = new FileOutputStream("replays/last.rep");
      ObjectOutputStream objectOut = new ObjectOutputStream(fileOutput);

      for (int i = 0; i < stateList.size(); ++i) {
        objectOut.writeObject(stateList.get(i));
      }

      objectOut.flush();
      objectOut.close();
      fileOutput.flush();
      fileOutput.close();
    }

    public void readFromFile() throws IOException, ClassNotFoundException {

      FileInputStream fileInput = new FileInputStream("replays//last.rep");
      ObjectInputStream objectInput = new ObjectInputStream(fileInput);

      while (true) {
        StatePos state;
        try {
          state = (StatePos) objectInput.readObject();
        } catch (EOFException e) {
          break;
        }

        if (state == null) {
          break;
        }

        stateList.add(state);
      }
    }

    public void stopReplay() {
      stateList = new ArrayList<StatePos>();
    }

    /*
     * Controls game object using values from loaded list
     */
    public void startReplay() {
      if (listIndex == stateList.size()) {
        return;
      }
      game.getBat().setLeft(stateList.get(listIndex).getPosPlayer());
      game.getBall().setPosX(stateList.get(listIndex).getPosXBall());
      game.getBall().setPosY(stateList.get(listIndex).getPosYBall());
      listIndex++;
    }
  }


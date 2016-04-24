package replay;

import java.io.Serializable;

public class StatePos implements Serializable {
  private int posPlayer;
  private int posXBall;
  private int posYBall;

  public StatePos(int posPlayer, int posXBall, int posYBall) {
    this.posPlayer = posPlayer;
    this.posXBall = posXBall;
    this.posYBall = posYBall;
  }

  public StatePos() {
    this.posPlayer = 0;
    this.posXBall = 0;
    this.posYBall = 0;
  }

  public int getPosPlayer() {
    return posPlayer;
  }

  public void setPosPlayer(int posPlayer) {
    this.posPlayer = posPlayer;
  }

  public int getPosXBall() {
    return posXBall;
  }

  public void setPosXBall(int posXBall) {
    this.posXBall = posXBall;
  }

  public int getPosYBall() {
    return posYBall;
  }

  public void setPosYBall(int posYBall) {
    this.posYBall = posYBall;
  }

}

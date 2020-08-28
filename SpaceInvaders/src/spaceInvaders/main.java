package spaceInvaders;

public class main {
	public static void main(String[] args) {
		GamePlay gamePlay = new GamePlay();
		gamePlay.Start();
		for(;;) {
			gamePlay.Update();
		}
		
	}
}

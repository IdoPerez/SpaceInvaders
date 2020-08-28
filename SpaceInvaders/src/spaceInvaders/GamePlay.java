package spaceInvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GamePlay extends JFrame implements KeyListener {
	private Ship ship = new Ship();
	private Alien[][] aliens;
	private boolean shoot = false;
	private Shot shot;
	
	private ArrayList<Shot> shots = new ArrayList<Shot>();
	private ArrayList<Shot> alienShots = new ArrayList<Shot>();
	
	private ArrayList<ShipJet> Jet = new ArrayList<ShipJet>();
	
	private ArrayList<Powerup> powerUps = new ArrayList<Powerup>();
	
	private int lastJetIndex = -1;
	
	private BufferedImage[] ShipImages = new BufferedImage[8];
	private boolean gameOver = false;
	private int alienShotTimer = 0, bonusTimer = 0;
	private BufferedImage imageHP, ShipImage, jetImage, aliensImage, powerUpsImage;
	private int imgNum = 0;
	
	public GamePlay() {
		setSize(1000, 800);
		setTitle("Space Invaders");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		addKeyListener(this);
	}
	
	public void Start() {
		System.out.println(ShipImages.length);
		aliens = new Alien[650/75][400/75];
		Level();
		String spaceFile = "/LeftMove/space";
		try {
			
			for(int i = 0; i < 4; i ++) {
				ShipImage = ImageIO.read(getClass().getResourceAsStream(spaceFile+String.valueOf(i)+".png"));
				ShipImages[i] = ShipImage;
			}
			int index = 0;
			spaceFile = "/RightMove/RightMove";
			for(int i = 4; i < 8; i++) {
				ShipImage = ImageIO.read(getClass().getResourceAsStream(spaceFile+String.valueOf(index)+".png"));
				ShipImages[i] = ShipImage;
				index++;
			}
			
			jetImage = ImageIO.read(getClass().getResourceAsStream("/SpaceJet/jetPart.png"));
			aliensImage = ImageIO.read(getClass().getResourceAsStream("/aliensImg/aliens.png"));
			imageHP = ImageIO.read(getClass().getResourceAsStream("/ShipHP.png"));
			powerUpsImage = ImageIO.read(getClass().getResourceAsStream("/powerUps/Gift.png"));
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(ShipImages);
		//ShipJet jetPart = new ShipJet(ship);
		//jet.add(jetPart);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1000, 800);
		if(!gameOver) {
			int x = 710 ;
			for(int i = 0; i < ship.getHP(); i ++) {
				g.drawImage(imageHP, x,700,null);
				x+= 80;
			}
			
			//ship.Draw(g);
			//g.drawImage(images[0], (int) ship.getX(), (int) ship.getY(),null);
			
			animation(g);
			
			for(int i = 0; i < aliens.length; i ++) {
				for(int j = 0; j < aliens[0].length; j ++) {
					if(aliens[i][j] != null)
						aliens[i][j].Draw(g, aliensImage);
				}
			}
			
			//if(shoot) {
				for(Shot s: shots) {
					s.Draw(g);
				}
		
				for(Shot shot: alienShots) {
					shot.Draw(g);
				}
			//}
				/*
				if(ship.isHit()) {
					Color c = new Color(255,51,51);
					g.setColor(c);
					g.fillRect(0, 0, 1000, 800);
				}
				*/
				for(Powerup p: powerUps) {
					if(!p.isPicked()) {
						p.draw(g, powerUpsImage);
					}
				}
		}
		else if(gameOver) {
			g.setColor(Color.red);
			g.drawString("Game Over", 500, 400);
		}
	}
	
	
	public void Update() {
		if(!gameOver) {
			ship.move();
			for(int i = 0; i < aliens.length; i ++) {
				for(int j = 0; j < aliens[0].length; j ++) {
					if(aliens[i][j] != null)
						aliens[i][j].move();
						//System.out.println("j:"+j);
				}
			}
			
			for(ShipJet j :Jet) {
				j.move();
			}
			
			if(Jet.isEmpty() || Jet.get(lastJetIndex).getY() >= ship.getY()+90 ) {
				ShipJet jetPart = new ShipJet(ship,0);
				Jet.add(jetPart);
				
				ShipJet jetPart2 = new ShipJet(ship,1);
				Jet.add(jetPart2);
				
				lastJetIndex+=2;
			}
			
			if(Jet.get(0).getY() >= ship.getY()+150) {
				Jet.remove(0);
				lastJetIndex--;
			}
			
			ShipColision();
			
			//if(shoot) {
				for(Shot s: shots) {
					s.move();
				}
				
				for(Shot shot: alienShots) {
					shot.move();
					//System.out.println(shot.getX()+" "+ shot.getY());
				}
				
				ShotColision();
				
				if(!shots.isEmpty()) {
					if(shots.get(0).getY() <= 0)
						shots.remove(0);
				}
				else
					shoot = false;
				
				if(!alienShots.isEmpty()) {
					if(alienShots.get(0).getY() >= 784)
						alienShots.remove(0);
				}
				else
					shoot = false;
				
			//}
				powerUp();
				
				for(Powerup power: powerUps) {
					power.move();
				}
				
				if(!powerUps.isEmpty() && powerUps.get(0).getY() >= 1000-powerUpsImage.getHeight()) 
					powerUps.remove(0);
				
				powerUpColision();
						
				
			if(alienShotTimer >= 100) {
				int x , y;
				do {
					Random rnd1 = new Random();
					x = rnd1.nextInt(aliens.length);
					y = rnd1.nextInt(aliens[0].length);
				} while(aliens[x][y] == null);
				//System.out.println("x="+x+" " +"y="+y);
				Shot shot = new Shot(aliens[x][y].getX()+20,aliens[x][y].getY() + 40);
				shot.setDirDown();
				alienShots.add(shot);
				
				shoot = true;
				alienShotTimer = 0;
			}
			
			alienShotTimer++;
			
			
			
			
		
		}
		
		//System.out.println(shoot);
		repaint();
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void animation(Graphics g) {
			if(ship.getLeftAccel()) {
				g.drawImage(ShipImages[imgNum], (int) ship.getX(), (int) ship.getY(),null);
			if(imgNum != 3)
				imgNum++;
			
			Jet.clear();
			lastJetIndex = -1;
		}
		else if(ship.getRightAccel()) {
			g.drawImage(ShipImages[imgNum+4], (int) ship.getX(), (int) ship.getY(),null);
			if(imgNum != 3)
				imgNum++;
			
			Jet.clear();
			lastJetIndex = -1;
		}
		else {
			imgNum = 0;
			g.drawImage(ShipImages[0], (int) ship.getX(), (int) ship.getY(),null);
			
			for(ShipJet j: Jet) {
				j.draw(g, jetImage);
			}
		}
	}
	
	private void Level() {
		int posX = 100, posY = 100, i = 0, j = 0;
		
		for(int x = posX; x < 750; x+=75) {
			for(int y = posY; y < 500; y += 75) {
				Alien alien = new Alien(x,y);
				if(i < aliens.length && j < aliens[0].length)
					aliens[i][j] = alien;
				j++;
					
			}
				j= 0;
				i++;
		}
	}
	
	private void ShotColision() {
		for(int i = aliens.length-1; i >= 0; i --) {
			for(int j = aliens[0].length-1; j >= 0; j --) {
				if(aliens[i][j] != null && !shots.isEmpty()) {
					for(Shot s: shots) {
						if(aliens[i][j].getX() <= s.getX() && s.getX() <= aliens[i][j].getX()+40 && aliens[i][j].getY() <= s.getY() && s.getY() <= aliens[i][j].getY()+40) {
							aliens[i][j] = null;
							shots.remove(s);
							break;
						}
					}
				}
			}
		}
		for(Shot s: alienShots) {
			if(ship.getY() <= s.getY()+16 && s.getY() <= ship.getY()+60)
				if( (ship.getX() <= s.getX()) && (s.getX()< ship.getX()+60) || (ship.getX() <= s.getX()+10) && (s.getX()+10 <= ship.getX()+60) ) {
					ship.hit();
					ship.setHit(true);
					alienShots.remove(s);
					if(ship.getHP() == 0)
						gameOver = true;
				} 
				else
					ship.setHit(false);
			break;
		}
	}
	
	private void ShipColision() {
		for(int c = aliens.length-1; c > 0; c--) {
			for(int l = aliens[0].length-1; l > 0 ; l--) {
				if(aliens[c][l]!= null && aliens[c][l].getY()+40 >= ship.getY())
					if(aliens[c][l].getX() >= ship.getX() && aliens[c][l].getX() <= ship.getX()+60 || aliens[c][l].getX()+40 >= ship.getX() && aliens[c][l].getX()+40 <= ship.getX()+60) {
						gameOver = true;
						System.out.println(gameOver);
					}
				 
			}
		}
	}
	
	private void powerUpColision() {
		for(Powerup power: powerUps) {
			if((power.getY() > ship.getY() && ship.getY()+ShipImage.getHeight() > power.getY() ) || ( power.getY()+powerUpsImage.getHeight() > ship.getY() && ship.getY()+ShipImage.getHeight() > power.getY()+powerUpsImage.getHeight() ) ) {
				if(ship.getX()<power.getX() && ship.getX()+ShipImage.getWidth() > power.getX() || ship.getX()<power.getX()+powerUpsImage.getWidth() && ship.getX()+ShipImage.getWidth() > power.getX()+powerUpsImage.getWidth()) {
					ship.setPowerUpOn(true);
					power.setPicked(true);
				}
			}
		}
	}
	
	private void powerUp() {
		if(bonusTimer >= 300) {
			Powerup bonus = new Powerup();
			int num, num2;
			do {
			 num = bonus.getRanNum(aliens.length);
			 num2 = bonus.getRanNum(aliens[0].length);
			} while(aliens[num][num2] == null);
			
			bonus.setPos(aliens[num][num2].getX()+27, aliens[num][num2].getY()+25);
			bonus.setPicked(false);
			
			powerUps.add(bonus);
			
			bonusTimer = 0;
		}
		
		bonusTimer++;
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			ship.setRightAccel(true);
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			ship.setLeftAccel(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			shoot = true;
			if(ship.isPowerUpOn()) {
				shot = new Shot(ship.getX()+15, ship.getY()-60);
				shot.setColor(Color.YELLOW);
				shot.setDirUp();
				shots.add(shot);
				
				shot = new Shot(ship.getX()+45, ship.getY()-60);
				shot.setColor(Color.YELLOW);
				shot.setDirUp();
				shots.add(shot);
			}
			else {
			shot = new Shot(ship.getX()+30, ship.getY()-60);
			shot.setDirUp();
			shots.add(shot);
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			ship.setRightAccel(false);
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			ship.setLeftAccel(false);
		}
		
	}
	
}

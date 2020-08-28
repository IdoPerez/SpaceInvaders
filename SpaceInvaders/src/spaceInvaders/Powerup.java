package spaceInvaders;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Powerup {
	private int type;
	private boolean activate,picked;
	private double x,y,yVel;
	private int speed;
	private Random rnd;
	
	public Powerup() {
		rnd = new Random();
		//type = rnd.nextInt();
		//delete
		type = 1;
		speed = 1;
		yVel = 0;
	}
	
	public void move() {
		yVel += speed;
		if(yVel >= 4) {
			yVel = 4;
		}
		
		y+= yVel;
	}
	
	public void draw(Graphics g, BufferedImage img) {
		g.drawImage(img,(int) x, (int) y, null);
	}
	
	public void setActivate(boolean b) {
		activate = b;
	}
	
	public void setPicked(boolean b) {
		picked = b;
	}
	
	public void setPos(double d, double e) {
		x = d; y = e;
	}
	
	public int getRanNum(int length) {
		int num = rnd.nextInt(length);
		return num;
	}
	
	public double getX() {return x;}
	
	public double getY() {return y;}
	
	public int getType() {return type;}
	
	public boolean isActivate() {return activate;}
	
	public boolean isPicked() {return picked;}
}

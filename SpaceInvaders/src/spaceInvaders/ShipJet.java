package spaceInvaders;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ShipJet {
	private int velocity;
	private boolean isActive;
	private int speed;
	private double x,y, yVel;
	private boolean destroy;
	private Ship s;
	
	public ShipJet(Ship ship, int id) {
		isActive = true;
		speed = 4;
		s = ship;
		if(id == 0) {
			x = s.getX()+10; y = s.getY()+65; yVel = 0;
		}
		else {
			x = s.getX()+60; y = s.getY()+65; yVel = 0;
		}
	}
	
	public void move() {
		yVel += speed;
		y = s.getY()+60+yVel;
		if(!isActive)
			yVel = 0;
	}
	
	public void draw(Graphics g, BufferedImage img) {
		g.drawImage(img,(int) x, (int) y , null);
	}
	
	public double getX() {return x;}
	
	public double getY() {return y;}
}

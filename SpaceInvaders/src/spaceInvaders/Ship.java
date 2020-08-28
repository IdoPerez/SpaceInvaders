package spaceInvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Ship {
	private int HP;
	private int speed;
	private int Power;
	private final int STAND = 0, RIGHT = 1, LEFT = 2;
	private int Dir;
	private boolean rightAccel, leftAccel, hit;
	private double x , y, xVel;
	private boolean powerUpOn;
	public Ship() {
		rightAccel = false; leftAccel = false;
		x = 500; y = 600;
		xVel = 0;
		HP = 3;
		speed = 1;
		Power = 1;
		Dir = 0;
		powerUpOn = false;
		
	}
	
	public void setRightAccel(boolean b) {
		this.rightAccel = b;
	}
	
	public void setLeftAccel(boolean b) {
		this.leftAccel = b;
	}
	
	public boolean getRightAccel() {return rightAccel;}
	
	public boolean getLeftAccel() {return leftAccel;}
	
	public void move() {
		if(leftAccel) {
			xVel -= speed;
		} 
		
		else if(rightAccel) {
			xVel += speed;
		}
		
		else if(!rightAccel && !leftAccel) {
			xVel = 0;
		}
		else if(rightAccel && leftAccel) {
			xVel = 0;
		}
		
		if(xVel >= 8)
			xVel = 8;
		else if(xVel < - 5)
			xVel = -5;
		x += xVel;
		
		if(x < 0)
			x = 0;
		else if(x > 940)
			x = 940;
			
	}
	
	public double getX() {return x;}
	
	public double getY() {return y;}
	
	public int getHP() {return HP;}
	
	public boolean isHit() {return hit;}
	
	public boolean isPowerUpOn() {return powerUpOn;}
	
	public void setHit(boolean Hit) {hit = Hit;}
	
	public void setPowerUpOn(boolean p) {powerUpOn = p;}
	
	public void hit() {
		HP--;
	}
	
	public void Draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int) x, (int) y, 60, 60);
	}
}

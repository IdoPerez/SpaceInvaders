package spaceInvaders;

import java.awt.Color;
import java.awt.Graphics;

public class Shot {
	private int power;
	private int speed;
	private double x,y, yVel;
	private boolean UpAccel, DownAccel;
	private boolean destroy;
	private Color c;
	
	public Shot(double x, double y) {
		this.x = x; this.y = y;
		UpAccel = false; DownAccel = false;
		power = 1;
		yVel = 0;
		speed = 2;
		c = Color.RED;
	}
	
	public void move() {
		if(UpAccel) {
			yVel -= speed;
		}
		else if(DownAccel) {
			yVel += speed;
		}
		if(yVel >= 8)
			yVel = 8;
		else if(yVel < - 5)
			yVel = -8;
		
		y += yVel;
		if(y <= 0)
			destroy = true;
		else
			destroy = false;
	}
	
	public void Draw(Graphics g) {
		g.setColor(c);
		g.fillRect((int) x, (int) y, 10, 16);
	}
	
	public boolean getDes() {
		return destroy;
	}
	
	public double getY() {
		return y;
	}
	
	public double getX() {return x;};
	
	public void setDirUp() {
		UpAccel = true;
		DownAccel = false;
	}
	
	public void setColor(Color c) {this.c = c;}
	
	public void setDirDown() {
		DownAccel = true;
		UpAccel = false;
	}
	
	public void setPos(double posx, double posy) {
		x = posx; y = posy;
	}
}

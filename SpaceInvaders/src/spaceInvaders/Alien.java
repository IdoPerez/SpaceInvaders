package spaceInvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Alien {
	private int HP;
	private int speed;
	private double x,y;
	private int power;
	private final int DOWN = 0, RIGHT = 1, LEFT = 2;
	private int Dir;
	private int steps;
	boolean LR = true;
	
	public Alien(int x, int y) {
		steps = 0;
		HP = 1;
		this.power = 1;
		this.speed = 2;
		Dir = RIGHT;
		this.x = x;
		this.y = y;
	}
	public void move() {
		steps++;
		//System.out.println(steps);
		if(steps == 100) {
			setDown();
			steps = 0;
		}
		
		if(Dir == RIGHT) {
			x += speed;
			LR = true;
		}
		else if(Dir == LEFT) {
			x -= speed;
			LR = false;
		}
		else if(Dir == DOWN){
			y += speed+3;
			if(LR) {
				setLeft();
				LR = true;
			}
			else {
				setRight();
				LR = false;
			}
		}
	}
	
	public void setRight() {
		Dir = RIGHT;
	}
	
	public void setLeft() {
		Dir = LEFT;
	}
	
	public void setDown() {
		Dir = DOWN;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public double getY() {return y;}
	
	public double getX() {return x;}
	
	public void Draw(Graphics g,BufferedImage img) {
		g.drawImage(img,(int) x, (int) y, 55,50, null);
	}
}

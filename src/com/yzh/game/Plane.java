package com.yzh.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Plane extends GameObject{
	
	int speed = Constant.PLANE_SPEED;
	boolean left,up,right,down;
	boolean live = true;
	
	//移动
	public void drawSelf(Graphics g) {
		
		//如果飞机没有碰撞
		if(live) {
			g.drawImage(img, (int)x, (int)y, null);
			if(left) {
				x -= speed;
			}else if(right) {
				x += speed;
			}else if(up) {
				y -= speed;
			}else if(down) {
				y += speed;
			}
		}else {

		}
	}
	
	//添加方向
	public void addDirection(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		}
	}
	
	//取消方向
	public void minusDirection(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		}
	}
	
	//初始化
	public Plane(Image img,double x, double y) {
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = 3;
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
	}

	public void move() {
		// TODO Auto-generated method stub
		x = 1000;
		y = 1000;
	}

}

package com.yzh.game;

import java.awt.Graphics;
import java.awt.Image;

public class Explode {
	double x, y;
	int count;
	
	static Image[] imgs = new Image[16];
	//Õº∆¨‘ÿ»Î
	static {
		for(int i=0; i<16; i++) {
			imgs[i] = GameUtil.getImage("images/explode/e"+(i+1)+".gif");
		}
	}
	
	public void draw(Graphics g) {
		if(count <= 15) {
			g.drawImage(imgs[count], (int)x, (int)y, null);
			count++;
		}
	}
	
	public Explode(double x, double y) {
		this.x = x;
		this.y = y;
	}
}

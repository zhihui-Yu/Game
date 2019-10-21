package com.yzh.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Shell extends GameObject{
	double degree;
	
	public Shell() {
		x = 200;
		y = 100;
		width = 10;
		height = 10;
		speed = Constant.BULLET_SPEED;
		degree = Math.random()*Math.PI*2;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.fillOval((int)x, (int)y, width, height);
		
		//炮弹沿着任意角度飞出
		x += speed*Math.cos(degree);
		y += speed*Math.sin(degree);
		
		if(x<10||x>Constant.GAME_WIDTH-width-10) {
			degree = Math.PI - degree;
		}
		if(y<40||y>Constant.GAME_HEIGHT-height-10) {
			degree = - degree;
		}
		
		g.setColor(c);
	}
}

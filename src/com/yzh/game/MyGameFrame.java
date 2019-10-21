package com.yzh.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

public class MyGameFrame extends Frame{
	//载入图片
	Image planeImg = GameUtil.getImage("images/plane.png");
	Image bg = GameUtil.getImage("images/bg.jpg");
	
	//初始化子弹数组
	Shell[] shells = new Shell[Constant.BULLET_COUNT];
	//初始化飞机位置
	Plane plane = new Plane(planeImg,250,250);
	//初始化爆炸图片
	Explode bao ;
	//游戏时间
	Date startTime = new Date();
	Date endTime;
	int period;
	
	
	/**
	 * 画图
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(bg, 0, 0, null);//背景
		
		plane.drawSelf(g);//飞机
		//画子弹
		for(int i = 0; i < shells.length; i++) {
				shells[i].draw(g);
			
			//检测碰撞
			boolean peng = shells[i].getRect().intersects(plane.getRect());
			if(peng) {
				plane.live = false;
				if(bao==null) {
					bao = new Explode(plane.x, plane.y);
					endTime = new Date();
					period = (int)((endTime.getTime()-startTime.getTime())/1000);
				}
				bao.draw(g);
				System.out.println("碰到我啦！！！！！！！！");
				plane.move();
				
			}
			
			if(!plane.live) {
				g.setColor(Color.red);
				Font f = new Font("宋体", Font.BOLD, 50);
				g.setFont(f);
				g.drawString("时间"+period+"秒", (int)200, (int)200);
			}
			
		}
		
	}
	
	/**
	 * 刷新页面
	 * @author listener
	 *
	 */
	class PaintThread extends Thread{
		@Override
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 	监听键盘
	 * @author listener
	 *
	 */
	class KeyMonitor extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			plane.minusDirection(e);
		}
	}
	
	/**
	 * 初始化窗口
	 */
	public void launchFrame() {

		this.setTitle("PlaneGame");
		this.setVisible(true);
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		this.setLocation(300,300);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		new PaintThread().start();//启动重画窗口线程
		addKeyListener(new KeyMonitor());//开启键盘监听
		
		//初始化50个炮弹
		for(int i=0;i<shells.length;i++) {
			shells[i] = new Shell();
		}
	}
	
	//缓存页面
	private Image offScreenImage = null;
	 /**
	  * 将图片缓存
	  */
	public void update(Graphics g) {
	    if(offScreenImage == null)
	        offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//这是游戏窗口的宽度和高度
	     
	    Graphics gOff = offScreenImage.getGraphics();
	    paint(gOff);
	    g.drawImage(offScreenImage, 0, 0, null);
	    
	}
	/**
	 * 主程序
	 * @param args
	 */
	public static void main(String[] args) {
		MyGameFrame f = new MyGameFrame();
		f.launchFrame();
	}
}

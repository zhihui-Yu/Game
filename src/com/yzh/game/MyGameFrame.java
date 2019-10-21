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
	//����ͼƬ
	Image planeImg = GameUtil.getImage("images/plane.png");
	Image bg = GameUtil.getImage("images/bg.jpg");
	
	//��ʼ���ӵ�����
	Shell[] shells = new Shell[Constant.BULLET_COUNT];
	//��ʼ���ɻ�λ��
	Plane plane = new Plane(planeImg,250,250);
	//��ʼ����ըͼƬ
	Explode bao ;
	//��Ϸʱ��
	Date startTime = new Date();
	Date endTime;
	int period;
	
	
	/**
	 * ��ͼ
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(bg, 0, 0, null);//����
		
		plane.drawSelf(g);//�ɻ�
		//���ӵ�
		for(int i = 0; i < shells.length; i++) {
				shells[i].draw(g);
			
			//�����ײ
			boolean peng = shells[i].getRect().intersects(plane.getRect());
			if(peng) {
				plane.live = false;
				if(bao==null) {
					bao = new Explode(plane.x, plane.y);
					endTime = new Date();
					period = (int)((endTime.getTime()-startTime.getTime())/1000);
				}
				bao.draw(g);
				System.out.println("������������������������");
				plane.move();
				
			}
			
			if(!plane.live) {
				g.setColor(Color.red);
				Font f = new Font("����", Font.BOLD, 50);
				g.setFont(f);
				g.drawString("ʱ��"+period+"��", (int)200, (int)200);
			}
			
		}
		
	}
	
	/**
	 * ˢ��ҳ��
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
	 * 	��������
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
	 * ��ʼ������
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

		new PaintThread().start();//�����ػ������߳�
		addKeyListener(new KeyMonitor());//�������̼���
		
		//��ʼ��50���ڵ�
		for(int i=0;i<shells.length;i++) {
			shells[i] = new Shell();
		}
	}
	
	//����ҳ��
	private Image offScreenImage = null;
	 /**
	  * ��ͼƬ����
	  */
	public void update(Graphics g) {
	    if(offScreenImage == null)
	        offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//������Ϸ���ڵĿ�Ⱥ͸߶�
	     
	    Graphics gOff = offScreenImage.getGraphics();
	    paint(gOff);
	    g.drawImage(offScreenImage, 0, 0, null);
	    
	}
	/**
	 * ������
	 * @param args
	 */
	public static void main(String[] args) {
		MyGameFrame f = new MyGameFrame();
		f.launchFrame();
	}
}

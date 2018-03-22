package com.codecraft.javase.planewar.ui;

import com.codecraft.javase.planewar.Factory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 1.继承extends JPanel类<br>
 * 2.实现Runnable接口<br>
 * 3.实现MouseMotionListener<br>
 * 4.实现MouseListener<br>
 * 5.定义构造方法<br>
 * 6.重写paint方法<br>
 * 
 * @author BY
 *
 */
public class GameJPanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	private Factory fty = new Factory();
	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法
	 */
	public GameJPanel() {
		// 添加监听
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		// 开启线程
		new Thread(this).start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		fty.paintBg(g);
		fty.paintHero(g);
		fty.paintBullets(g);
		fty.paintOthers(g);
		fty.paintFlyingObjeacts(g);
		fty.paintGameStatus(g);
		fty.delOutOfBoundsAction();
		fty.heroMove();
		fty.paintFOD(g);
	}

	@Override
	public void run() {
		while (true) {
			// 飞行物的操作
			if (Factory.status == Factory.RUNNING) {
				// 飞行物的入场
				fty.enterAction();
				// 飞行物的移动
				fty.stepAction();
				// 英雄机的射击
				fty.shootAction();
				// 击中
				fty.shotByAction();
				//英雄机无敌检测
				fty.checkHeroInvincibility();
				//写日志
				fty.test();
				// 生成boss
				fty.makeABoss();
				// 检测游戏是否结束
				fty.checkGameOverAction();
			}
			// 重绘
			this.repaint();
			// 休眠
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			// 回车触发游戏状态改变
			switch (Factory.status) {
			case Factory.START:
				Factory.status = Factory.RUNNING;
				break;
			case Factory.OVER:
				fty.clear();
				Factory.status = Factory.START;
				break;
			case Factory.RUNNING:
				Factory.status = Factory.PAUSE;
				break;
			case Factory.PAUSE:
				Factory.status = Factory.RUNNING;
			}
		}

		if (e.getKeyCode() <= 40 && e.getKeyCode() >= 37) {
			switch (e.getKeyCode()) {
			case 37:
				Factory.left = true;
				break;
			case 38:
				Factory.up = true;
				break;
			case 39:
				Factory.right = true;
				break;
			case 40:
				Factory.down = true;
				break;
			default:
				break;

			}
		}

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() <= 40 && e.getKeyCode() >= 37) {
			switch (e.getKeyCode()) {
			case 37:
				Factory.left = false;
				break;
			case 38:
				Factory.up = false;
				break;
			case 39:
				Factory.right = false;
				break;
			case 40:
				Factory.down = false;
				break;
			default:
				break;
			}

		}else if (e.getKeyCode() == 97) {// 按键小键盘1
			fty.boomBoom();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * 鼠标事件
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// 鼠标触发游戏状态改变
		switch (Factory.status) {
		case Factory.START:
			Factory.status = Factory.RUNNING;
			break;
		case Factory.OVER:
			fty.clear();
			Factory.status = Factory.START;
			break;
		case Factory.RUNNING:
			Factory.status = Factory.PAUSE;
			break;
		case Factory.PAUSE:
			Factory.status = Factory.RUNNING;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// 鼠标移入窗口 状态改为RUNNING
		if (Factory.status == Factory.PAUSE) {
			Factory.status = Factory.RUNNING;
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// 鼠标移除窗口改为暂停
		if (Factory.status == Factory.RUNNING) {
			Factory.status = Factory.PAUSE;
		}
	}

	/**
	 * 鼠标运动监听
	 */
	//
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	// 英雄机的移动
	@Override
	public void mouseMoved(MouseEvent e) {
		// 判断游戏状态 运行状态时 英雄机移动
		if (Factory.status == Factory.RUNNING) {
			fty.heroSelf.move(e.getX(), e.getY());
		}
	}

}

package com.codecraft.javase.planewar.ui;

import com.codecraft.javase.planewar.Factory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 1.�̳�extends JPanel��<br>
 * 2.ʵ��Runnable�ӿ�<br>
 * 3.ʵ��MouseMotionListener<br>
 * 4.ʵ��MouseListener<br>
 * 5.���幹�췽��<br>
 * 6.��дpaint����<br>
 * 
 * @author BY
 *
 */
public class GameJPanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	private Factory fty = new Factory();
	private static final long serialVersionUID = 1L;

	/**
	 * ���췽��
	 */
	public GameJPanel() {
		// ��Ӽ���
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		// �����߳�
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
			// ������Ĳ���
			if (Factory.status == Factory.RUNNING) {
				// ��������볡
				fty.enterAction();
				// ��������ƶ�
				fty.stepAction();
				// Ӣ�ۻ������
				fty.shootAction();
				// ����
				fty.shotByAction();
				//Ӣ�ۻ��޵м��
				fty.checkHeroInvincibility();
				//д��־
				fty.test();
				// ����boss
				fty.makeABoss();
				// �����Ϸ�Ƿ����
				fty.checkGameOverAction();
			}
			// �ػ�
			this.repaint();
			// ����
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			// �س�������Ϸ״̬�ı�
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

		}else if (e.getKeyCode() == 97) {// ����С����1
			fty.boomBoom();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * ����¼�
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// ��괥����Ϸ״̬�ı�
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
		// ������봰�� ״̬��ΪRUNNING
		if (Factory.status == Factory.PAUSE) {
			Factory.status = Factory.RUNNING;
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// ����Ƴ����ڸ�Ϊ��ͣ
		if (Factory.status == Factory.RUNNING) {
			Factory.status = Factory.PAUSE;
		}
	}

	/**
	 * ����˶�����
	 */
	//
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	// Ӣ�ۻ����ƶ�
	@Override
	public void mouseMoved(MouseEvent e) {
		// �ж���Ϸ״̬ ����״̬ʱ Ӣ�ۻ��ƶ�
		if (Factory.status == Factory.RUNNING) {
			fty.heroSelf.move(e.getX(), e.getY());
		}
	}

}

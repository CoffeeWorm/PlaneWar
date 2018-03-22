package com.codecraft.javase.planewar.entity;

import com.codecraft.javase.planewar.Factory;
import com.codecraft.javase.planewar.ui.GameMainJFrame;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Hero extends FlyingObject {
	private int lives = 20;


	// @author ����
	// �޸Ļ������͹�������
	public final static int SINGLE = 0;
	public final static int DOUBLE = 1;
	public final static int DISPERSION = 2;
	// ��������
	private int fireType = DISPERSION;
	private int bombs = 3;

	/**
	 * ��������췽��
	 * 
	 * @param posX
	 * @param posY
	 * @param img
	 */
	public Hero(int posX, int posY, BufferedImage img) {
		super(posX, posY, 1, 0, 1, 1, img);
	}

	public Hero() {
		this((GameMainJFrame.WIN_WIDTH - Factory.hugeHero.getWidth()) / 2,
				GameMainJFrame.WIN_HEIGHT - Factory.hugeHero.getHeight() * 2, Factory.hero);
	}

	/**
	 * �޸Ļ������� ���
	 * 
	 * @�޸�ʱ�� 2018-01-05 10:41:39
	 */
	public ArrayList<Bullet> shoot() {
		ArrayList<Bullet> tmpBullet = new ArrayList<Bullet>();
		switch (this.fireType) {
		case Hero.SINGLE:
			tmpBullet.add(new Bullet(this.getPosX() + this.getObjWidth() / 2, this.getPosY()));
			break;
		case Hero.DOUBLE:
			tmpBullet.add(new Bullet(this.getPosX() + this.getObjWidth() / 4, this.getPosY()));
			tmpBullet.add(new Bullet(this.getPosX() + 3 * this.getObjWidth() / 4, this.getPosY()));
			break;
		case Hero.DISPERSION:
			tmpBullet.add(new Bullet(this.getPosX() + this.getObjWidth() / 2, this.getPosY(), -1, 2));
			tmpBullet.add(new Bullet(this.getPosX() + this.getObjWidth() / 2, this.getPosY(), 0, 2));
			tmpBullet.add(new Bullet(this.getPosX() + this.getObjWidth() / 2, this.getPosY(), 1, 2));
			break;
		default:
			break;
		}
		return tmpBullet;
	}

	/**
	 * ���±�д��ײ��� ��ײ
	 * 
	 * @���ߣ�����
	 */
	public boolean isHit(FlyingObject other) {

		boolean xLimit = this.getPosX() + this.getObjWidth() - 10 >= other.getPosX()
				&& this.getPosX() + 10 <= other.getPosX() + other.getObjWidth();
		boolean yLimit = this.getPosY() + 10 <= other.getPosY() + other.getObjHeight()
				&& this.getPosY() + this.getObjHeight() >= other.getPosY() + 30;

		if (xLimit && yLimit) {
			return true;
		}
		return false;
	}

	/**
	 * ��ȡ��ǰ����ֵ
	 * 
	 * @author ����
	 * @return
	 */
	public int getFireType() {
		return this.fireType;
	}

	/**
	 * Ϊ����ӻ������� ���õ�������
	 * 
	 * @�޸�ʱ�� 2018-01-05 10:40:31
	 */
	public void setSingleFire() {
		this.fireType = SINGLE;
	}

	/**
	 * Ϊ����ӻ������� ����˫����������
	 * 
	 * @�޸�ʱ�� 2018-01-05 10:41:02
	 */
	public void setDoubleFire() {
		this.fireType = DOUBLE;
	}

	/**
	 * ��ӻ������� ������������
	 * 
	 * @�޸�ʱ�� 2018-01-05 10:41:20
	 */
	public void setDispersion() {
		this.fireType = DISPERSION;
	}

	/**
	 * ��������
	 */
	public void addLives() {
		this.lives++;
	}

	/**
	 * ��ȡ��ǰ����ֵ
	 * 
	 * @return
	 */
	public int getLives() {
		return this.lives;
	}

	/**
	 * ��С����
	 */
	public void substractLives() {
		this.lives--;
	}

	@Override
	public boolean isOutOfBounds() {
		return false;
	}

	/**
	 * Ӣ�ۻ�������ƶ��ƶ�
	 */
	public void move(int mouseX, int mouseY) {
		this.setPosX(mouseX - this.getObjWidth() / 2);
		this.setPosY(mouseY - this.getObjHeight() / 2);
	}

	/**
	 * ����
	 */
	public void changeState(BufferedImage img) {
		if (!this.getImg().equals(img)) {
			this.setImg(img);
			this.setObjWidth(img.getWidth());
			this.setObjHeight(img.getHeight());
		}
	}

	public int getBombs() {
		return bombs;
	}

	public void setBombs(int bombs) {
		this.bombs = bombs;
	}

	public void subBombs() {
		this.bombs--;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
}

package com.codecraft.javase.planewar.entity;

import com.codecraft.javase.planewar.Factory;

import java.awt.image.BufferedImage;

/**
 * �ӵ��ࣺʵ���ӵ�����ع���<br>
 * 1.�̳з�������<br>
 * 2.���������е��ӵ��ٶ�����<br>
 * 3.ʵ���˷�����ĳ��󷽷�<br>
 * 4.���췽��
 * 
 * @author BY
 *
 */
public class Bullet extends FlyingObject {

	/**
	 * ����
	 */
	public Bullet(int posX, int posY, int speedX, int speedY, int xDir, int yDir, BufferedImage img) {
		super(posX, posY, speedX, speedY, xDir, yDir, img);
	}

	/**
	 * ����ĳ�ʼ�� Ϊ��ɢ��Ч��
	 * 
	 * @author ����
	 */
	public Bullet(int posX, int posY, int speedX, int speedY) {
		this(posX, posY, speedX, speedY, 1, -1, Factory.bullet);
	}

	/**
	 * ԭ�й��췽��
	 * 
	 * @param posX
	 * @param posY
	 */
	public Bullet(int posX, int posY) {
		this(posX, posY, 0, 10, 1, -1, Factory.bullet);
	}

	@Override
	public void move() {
		this.setPosY(this.getPosY() + this.getyDir() * this.getSpeedY());
		this.setPosX(this.getPosX() + this.getxDir() * this.getSpeedX());
	}

	@Override
	public boolean isOutOfBounds() {
		return this.getPosY() + this.getObjHeight() <= 0;
	}
}

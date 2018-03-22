package com.codecraft.javase.planewar.entity;

import com.codecraft.javase.planewar.ui.GameMainJFrame;

import java.awt.image.BufferedImage;

/**
 * ���Ƿ�����ĳ����࣬���������з�����Ĺ������Ժͷ��� 1.��Ա���� 2.��Ա����
 * 
 * @author BY
 *
 */
public abstract class FlyingObject {

	private int posX = 0;
	private int posY = 0;
	private int objWidth = 0;
	private int objHeight = 0;
	private int speedX = 0;
	private int speedY = 0;
	private int xDir = 0;
	private int yDir = 0;
	private BufferedImage img;

	public FlyingObject(int posX, int posY, int speedX, int speedY, int xDir, int yDir, BufferedImage img) {
		this.posX = posX;
		this.posY = posY;
		this.speedX = speedX;
		this.speedY = speedY;
		this.xDir = xDir;
		this.yDir = yDir;
		this.img = img;
		this.objHeight = img.getHeight();
		this.objWidth = img.getWidth();
	}

	public FlyingObject() {
	}

	public void move() {
		// �ı�����
		this.posX += this.speedX * this.xDir;
		this.posY += this.yDir * this.speedY;
		// �����ұڷ���
		if (this.posX < 0 || this.posX + this.objWidth > GameMainJFrame.WIN_WIDTH) {
			this.xDir *= -1;
		}
	}

	public abstract boolean isOutOfBounds();

	/**
	 * �鿴�Ƿ񱻻��� ����true->���У�����false->û�л��� <br>
	 * 
	 * @param bullet
	 * @return boolean
	 */
	public boolean isShot(Bullet bullet) {
		return bullet.getPosX()+bullet.getObjWidth() >= this.posX && bullet.getPosX() <= this.posX + this.objWidth
				&& bullet.getPosY() <= this.posY + this.objHeight && bullet.getPosY() + bullet.getObjHeight() >= this.getPosY();
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getObjWidth() {
		return objWidth;
	}

	public void setObjWidth(int objWidth) {
		this.objWidth = objWidth;
	}

	public int getObjHeight() {
		return objHeight;
	}

	public void setObjHeight(int objHeight) {
		this.objHeight = objHeight;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public int getxDir() {
		return xDir;
	}

	public void setxDir(int xDir) {
		this.xDir = xDir;
	}

	public int getyDir() {
		return yDir;
	}

	public void setyDir(int yDir) {
		this.yDir = yDir;
	}


}

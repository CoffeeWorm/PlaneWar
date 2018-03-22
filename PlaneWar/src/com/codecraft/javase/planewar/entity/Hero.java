package com.codecraft.javase.planewar.entity;

import com.codecraft.javase.planewar.Factory;
import com.codecraft.javase.planewar.ui.GameMainJFrame;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Hero extends FlyingObject {
	private int lives = 20;


	// @author 庞威
	// 修改火力类型公共变量
	public final static int SINGLE = 0;
	public final static int DOUBLE = 1;
	public final static int DISPERSION = 2;
	// 火力类型
	private int fireType = DISPERSION;
	private int bombs = 3;

	/**
	 * 多参数构造方法
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
	 * 修改火力类型 射击
	 * 
	 * @修改时间 2018-01-05 10:41:39
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
	 * 重新编写碰撞检测 碰撞
	 * 
	 * @作者：庞威
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
	 * 获取当前火力值
	 * 
	 * @author 庞威
	 * @return
	 */
	public int getFireType() {
		return this.fireType;
	}

	/**
	 * 为了添加火力类型 设置单倍火力
	 * 
	 * @修改时间 2018-01-05 10:40:31
	 */
	public void setSingleFire() {
		this.fireType = SINGLE;
	}

	/**
	 * 为了添加火力类型 设置双倍火力类型
	 * 
	 * @修改时间 2018-01-05 10:41:02
	 */
	public void setDoubleFire() {
		this.fireType = DOUBLE;
	}

	/**
	 * 添加火力类型 设置三倍火力
	 * 
	 * @修改时间 2018-01-05 10:41:20
	 */
	public void setDispersion() {
		this.fireType = DISPERSION;
	}

	/**
	 * 增加生命
	 */
	public void addLives() {
		this.lives++;
	}

	/**
	 * 获取当前生命值
	 * 
	 * @return
	 */
	public int getLives() {
		return this.lives;
	}

	/**
	 * 减小生命
	 */
	public void substractLives() {
		this.lives--;
	}

	@Override
	public boolean isOutOfBounds() {
		return false;
	}

	/**
	 * 英雄机的鼠标移动移动
	 */
	public void move(int mouseX, int mouseY) {
		this.setPosX(mouseX - this.getObjWidth() / 2);
		this.setPosY(mouseY - this.getObjHeight() / 2);
	}

	/**
	 * 变身
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

package com.codecraft.javase.planewar.entity;

import com.codecraft.javase.planewar.Factory;

import java.awt.image.BufferedImage;

/**
 * 子弹类：实现子弹的相关功能<br>
 * 1.继承飞行物类<br>
 * 2.定义了特有的子弹速度属性<br>
 * 3.实现了飞行物的抽象方法<br>
 * 4.构造方法
 * 
 * @author BY
 *
 */
public class Bullet extends FlyingObject {

	/**
	 * 庞威
	 */
	public Bullet(int posX, int posY, int speedX, int speedY, int xDir, int yDir, BufferedImage img) {
		super(posX, posY, speedX, speedY, xDir, yDir, img);
	}

	/**
	 * 更多的初始化 为了散弹效果
	 * 
	 * @author 庞威
	 */
	public Bullet(int posX, int posY, int speedX, int speedY) {
		this(posX, posY, speedX, speedY, 1, -1, Factory.bullet);
	}

	/**
	 * 原有构造方法
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

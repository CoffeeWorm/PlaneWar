package com.codecraft.javase.planewar.entity;

import java.awt.image.BufferedImage;

/**
 * Created by ÀîÏèwin on 2018/1/6.
 */
public class ExplodeObj extends FlyingObject {

	private int py;

	public ExplodeObj(int posX, int posY, int speedX, int speedY, int xDir, int yDir, BufferedImage img) {
		super(posX, posY, speedX, speedY, xDir, yDir, img);
	}

	public ExplodeObj(int posX, int posY, int speedY,BufferedImage img) {
		this(posX, posY, 0, speedY, 0, 1, img);
		py = posY;
	}

	@Override
	public void move() {
		super.move();
	}

	@Override
	public boolean isOutOfBounds() {
		return this.getPosY() > py + 10;
	}
}

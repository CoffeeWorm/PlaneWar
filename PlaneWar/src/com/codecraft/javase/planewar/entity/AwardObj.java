package com.codecraft.javase.planewar.entity;

import com.codecraft.javase.planewar.ui.GameMainJFrame;

import java.awt.image.BufferedImage;

public abstract class AwardObj extends FlyingObject {

	public AwardObj(int posX, int posY, int speedX, int speedY, int xDir, int yDir, BufferedImage img) {
		super(posX, posY, speedX, speedY, xDir, yDir, img);
	}

	public AwardObj(BufferedImage img) {
		this((int) (Math.random() * (GameMainJFrame.WIN_WIDTH - 60)), -60, 1, 1, Math.random() > 0.5 ? -1 : 1, 1,
				img);
	}
	
	@Override
	public void move() {
		super.move();
	}

	@Override
	public boolean isOutOfBounds() {
		return this.getPosY() >= GameMainJFrame.WIN_HEIGHT;
	}
}

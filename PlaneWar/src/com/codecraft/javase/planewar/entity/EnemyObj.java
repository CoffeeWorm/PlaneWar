package com.codecraft.javase.planewar.entity;

import com.codecraft.javase.planewar.Factory;
import com.codecraft.javase.planewar.ui.GameMainJFrame;

import java.awt.image.BufferedImage;

public abstract class EnemyObj extends FlyingObject {
	private int score = 1;
	
	public EnemyObj(int posX, int posY, int speedX, int speedY, int xDir, int yDir, BufferedImage img) {
		super(posX, posY, speedX, speedY, xDir, yDir, img);
	}
	
	public EnemyObj(int posX, int posY, BufferedImage img) {
		this(posX, posY, (int) (Math.random() * 4) + 1, (int) (Math.random() * 2) + 1,Math.random() > 0.5? -1:1, 1,img);
	}

	public EnemyObj(BufferedImage img) {
		this((int) (Math.random() * (GameMainJFrame.WIN_WIDTH - 160) + 80), -60, img);
	}
	
	public EnemyObj() {
		this((int) (Math.random() * (GameMainJFrame.WIN_WIDTH - 160) + 80), -60, Factory.smallEnemy);
	}

	@Override
	public void move() {
		super.move();
	}

	@Override
	public boolean isOutOfBounds() {
		return this.getPosY() >= GameMainJFrame.WIN_HEIGHT;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}

package com.codecraft.javase.planewar.entity;

import com.codecraft.javase.planewar.Factory;
import com.codecraft.javase.planewar.ui.GameMainJFrame;

public class Boss extends Hero {
	public Boss() {
		super(120, 1, Factory.boss);
		super.setLives(5);
		super.setSpeedY(3);
	}

	@Override
	public void move() {
		// 改变坐标
		this.setPosX(this.getPosX() + this.getSpeedX() * this.getxDir());
		this.setPosY(this.getPosY() + this.getSpeedY() * this.getyDir());
		// 碰左右壁反弹
		if (this.getPosX() < 0 || this.getPosX() + this.getObjWidth() > GameMainJFrame.WIN_WIDTH) {
			this.setxDir(-this.getxDir());
		}
		//上下反弹移动
		if (this.getPosY() < 0 || this.getPosY() + this.getObjHeight() > GameMainJFrame.WIN_HEIGHT - 100) {
			this.setyDir(-this.getyDir());
		}
	}

	public int getScore() {
		return 20;
	}

}

package com.codecraft.javase.planewar.entity;

import com.codecraft.javase.planewar.Factory;

import java.awt.image.BufferedImage;

/**Boss������
 * ʵ�����㽱��������
 * �̳���AwardObj
 * ��д��move����
 * ��move������ �жϽ�����Ӣ�ۻ���λ��Ȼ��ı䷽��
 * Created by ����win on 2018/1/7.
 */
public class BossAward extends AwardObj {

    public BossAward(int posX, int posY, int speedX, int speedY, int xDir, int yDir, BufferedImage img) {
        super(posX, posY, speedX, speedY, xDir, yDir, img);
    }

    public BossAward(int posX, int posY) {
        this(posX, posY, 3, 3, 0, 1, Factory.jewel);
    }

  
    public void move(int heroX, int heroY) {
        // �ı�����
        this.setPosX(this.getPosX() + this.getSpeedX() * this.getxDir());
        this.setPosY(this.getPosY() + this.getSpeedY() * this.getyDir());
//        // �����ұڷ���

            if (this.getPosX() > heroX && this.getPosY() > heroY) {
                this.setxDir(-1);
                this.setyDir(-1);
            } else if (this.getPosX() < heroX && this.getPosY() > heroY) {
                this.setxDir(1);
                this.setyDir(-1);
            } else if (this.getPosX() < heroX && this.getPosY() < heroY) {
                this.setxDir(1);
                this.setyDir(1);
            } else if (this.getPosX() > heroX && this.getPosY() < heroY) {
                this.setxDir(-1);
                this.setyDir(1);
            } else if (this.getPosX() == heroX && this.getPosY() > heroY) {
                this.setxDir(0);
                this.setyDir(-1);
            } else if (this.getPosX() == heroX && this.getPosY() < heroY) {
                this.setxDir(0);
                this.setyDir(1);
            } else if (this.getPosX() > heroX && this.getPosY() == heroY) {
                this.setxDir(-1);
                this.setyDir(0);
            } else if (this.getPosX() < heroX && this.getPosY() == heroY) {
                this.setxDir(1);
                this.setyDir(0);
            }
       // }
    }


}

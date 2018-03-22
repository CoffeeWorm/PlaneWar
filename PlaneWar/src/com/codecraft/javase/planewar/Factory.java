package com.codecraft.javase.planewar;

import com.codecraft.javase.planewar.entity.*;
import com.codecraft.javase.planewar.ui.GameMainJFrame;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Factory {

    /* ���ط������ͼƬ */
    public static BufferedImage smallEnemy;
    public static BufferedImage bullet;
    public static BufferedImage redStone;
    public static BufferedImage purpleStone;
    public static BufferedImage yellowStone;
    public static BufferedImage hero;
    public static BufferedImage hugeHero;
    public static BufferedImage start;
    public static BufferedImage pause;
    public static BufferedImage bg;
    public static BufferedImage gameOver;
    public static BufferedImage hugeEnemy;
    public static BufferedImage boss;
    public static BufferedImage explode;
    public static BufferedImage dazhao;
    public static BufferedImage jewel;
    /* ��ǰ��Ϸ��״̬ */
    public static int status = 0;
    public static final int START = 0;
    public static final int RUNNING = 1;
    public static final int PAUSE = 2;
    public static final int OVER = 3;
    /* �������ұ�־ */
    public static boolean left = false;
    public static boolean right = false;
    public static boolean up = false;
    public static boolean down = false;
    /* �÷� */
    public int score = 0;
    /* ���� */
    public List<Bullet> bullets = new ArrayList<Bullet>();
    /* Ӣ�ۻ� */
    public Hero heroSelf = new Hero();
    /* Ӣ�ۻ��ķ����ٶ� */
    private int moveSpeed = 5;
    /* Ӣ���޵����� */
    private boolean heroInvincibility = false;
    /* Ӣ���޵�ʱ�� */
    private int timeOfInvincibility = 500;
    /* ������ */
    public List<FlyingObject> fos = new ArrayList<FlyingObject>();
    /* ����������� */
    private int numOfFos = 0;
    /* �ӵ������� */
    private int numOfBullets = 0;
    /* ����������ʱ���� */
    private int fosInterval = 80;

    /* �ӵ�����ʱ����ȫ�ֱ��� */
    public final static int BLT_SINGLE_INTERVAL = 40;
    public final static int BLT_DOUBLE_INTERVAL = 70;
    public final static int BLT_DISPERSION_INTERVAL = 100;
    /* �ӵ�����ʱ���� */
    private int bulletInterval = BLT_DISPERSION_INTERVAL;
    /* boss���ּ�ʱ�� */
    private int bossTimer = 0;
    /* boss����ʱ���� */
    private int bossIntervl = 100 * 30;// 100Ϊ1s ����30s
    /* bossָ�� */
    private Boss bossEnemy;
    /* ���ֲ��� */
    Player music = new Player();
    /* ������ */
    public List<FlyingObject> fod = new ArrayList<FlyingObject>();

    /* ��̬����� */
    static {
        try {
            bullet = ImageIO.read(Factory.class.getResource("bullet.png"));
            smallEnemy = ImageIO.read(Factory.class.getResource("smallEnemy.png"));
            redStone = ImageIO.read(Factory.class.getResource("redStone.png"));
            purpleStone = ImageIO.read(Factory.class.getResource("purpleStone.png"));
            yellowStone = ImageIO.read(Factory.class.getResource("yellowStone.png"));
            hero = ImageIO.read(Factory.class.getResource("hero.png"));
            hugeHero = ImageIO.read(Factory.class.getResource("heroAfterChange.png"));
            start = ImageIO.read(Factory.class.getResource("start.png"));
            pause = ImageIO.read(Factory.class.getResource("pause.png"));
            bg = ImageIO.read(Factory.class.getResource("background.png"));
            gameOver = ImageIO.read(Factory.class.getResource("gameover.png"));
            hugeEnemy = ImageIO.read(Factory.class.getResource("hugeenemy.png"));
            boss = ImageIO.read(Factory.class.getResource("boss.png"));
            explode = ImageIO.read(Factory.class.getResource("explode.png"));
            dazhao = ImageIO.read(Factory.class.getResource("dazhao.png"));
            jewel = ImageIO.read(Factory.class.getResource("TNT.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ���ɷ����� */
    public void enterAction() {
        // ����ʱ����
        this.numOfFos++;
        if (this.numOfFos % (20 + this.fosInterval) == 0) {
            // ��������һ��������
            this.fos.add(this.productRandomFlyingObject());
        }
        if (this.numOfFos % 1000 == 0 && this.fosInterval != 0) {
            this.fosInterval -= 10;
        }
    }

    /* ������ɷ����� */
    public FlyingObject productRandomFlyingObject() {
        double rdm = Math.random();

        if (rdm < 0.6) {
            // 1.С�͵л� 0.6
            return new SmallEnemyObj(smallEnemy);
            //return new BossAward();
        } else if (0.6 <= rdm && rdm < 0.88) {
            // 2.���͵л�0.25
            return new HugeEnemyObj(hugeEnemy);
        } else if (0.88 <= rdm && rdm < 0.98) {
            // 3.С�۷� 0.1
            return new LittleBee(redStone);
        } else if (0.98 <= rdm && rdm < 0.99) {
            // 4.�Ʊ�ʯ0.1
            return new AirDrop(purpleStone);
        } else {
            // 5.�ϱ�ʯ0.1
            return new YellowStone(yellowStone);
        }
    }

    /* ��������ƶ� */
    public void stepAction() {
        // ��������ƶ�
        for (int i = 0; i < this.fos.size(); i++) {
            if (this.fos.get(i) instanceof BossAward) {
                ((BossAward) this.fos.get(i)).move(heroSelf.getPosX(), heroSelf.getPosY());
            } else
                this.fos.get(i).move();

        }
        // �ӵ����ƶ�
        for (int i = 0; i < this.bullets.size(); i++) {
            this.bullets.get(i).move();
        }
        // ��ը����ƶ�
        for (int i = 0; i < this.fod.size(); i++) {
            this.fod.get(i).move();
        }
    }

    /**
     * ���ʵ��ɢ��Ч�� ����ÿһ���ӵ��Ƿ����������ײ
     **/
    public void shotByAction() {
        for (int j = 0; j < this.bullets.size(); j++) {
            // ����һ�������з�������±�
            int index = -1;
            // ������Ķ������
            for (int i = 0; i < this.fos.size(); i++) {
                if (this.fos.get(i).isShot(this.bullets.get(j))) {
                    // ��ȡ������Ʒ���±�
                    index = i;
                    if (!(this.fos.get(i) instanceof AirDrop)&&!(this.fos.get(i) instanceof BossAward))
                        this.fod.add(new ExplodeObj(this.fos.get(i).getPosX(), this.fos.get(i).getPosY(), 2, Factory.explode));
                    break;
                }
            }
            if (-1 != index) {
                if (this.fos.get(index) instanceof EnemyObj) {
                    // ��ȡ����
                    EnemyObj tmp = (EnemyObj) this.fos.get(index);
                    // ���ӵ÷�
                    score += tmp.getScore();
                    // ɾ��
                    this.fos.remove(index);
                    // ɾ���ӵ�
                    bullets.remove(j);
                    // �ӵ����в�������
                    music.playBoom();
                } else if (this.fos.get(index) instanceof LittleBee) {
                    // ��������
                    heroSelf.addLives();
                    // ��������
                    this.music.playAward();
                    // ɾ���ӵ�
                    bullets.remove(j);
                    // �Ƴ�������Ʒ
                    this.fos.remove(index);
                } else if (this.fos.get(index).equals(this.bossEnemy)) {
                    this.bossEnemy.substractLives();
                    System.out.println("�ӵ���boss,bossѪ����" + this.bossEnemy.getLives());
                    if (this.bossEnemy.getLives() <= 0) {
                        this.fos.add(new BossAward(this.fos.get(index).getPosX(), this.fos.get(index).getPosY()));
                        this.fos.remove(index);
                        this.bossEnemy = null;
                        //boss���� ������������Ϊ��
                        //this.heroSelf.setBombs(3);
                        System.out.println("boss����");
                    }
                    // ɾ���ӵ�
                    bullets.remove(j);
                    // �ӵ����в�������
                    music.playBoom();
                } else if (this.fos.get(index) instanceof YellowStone) {
                    this.timeOfInvincibility = 500;
                    this.heroInvincibility = true;
                    // ��������
                    this.music.playAward();
                    // ɾ��
                    // ɾ���ӵ�
                    bullets.remove(j);
                    this.fos.remove(index);
                }
            }
        }
    }

    /**
     * ����boss����
     */
    public void makeABoss() {
        this.bossTimer++;
        if (this.bossTimer % this.bossIntervl == 0 && null == this.bossEnemy) {
            this.bossTimer = 0;
            // ����һ��boss
            this.bossEnemy = new Boss();
            this.fos.add(this.bossEnemy);
            System.out.println("������һ��boss");
        }
    }

    /* ɾ���ɳ����ڷ�Χ�ķ��������ӵ� */
    public void delOutOfBoundsAction() {
        // ������ɾ������Խ��ķ�����
        for (int i = 0; i < this.fos.size(); i++) {
            if (this.fos.get(i).isOutOfBounds()) {
                this.fos.remove(i);
            }
        }
        // ������ɾ������Խ����ӵ�
        for (int i = 0; i < this.bullets.size(); i++) {
            if (this.bullets.get(i).isOutOfBounds()) {
                this.bullets.remove(i);
            }
        }
        // ����ɾ�����еĵ�����
        for (int i = 0; i < this.fod.size(); i++) {
            if (this.fod.get(i).isOutOfBounds()) {
                this.fod.remove(i);
            }
        }
    }

    /**
     * Ӣ�ۻ���ײ���л���Ѫ��ײ����Ͷ���ӻ���
     *
     * @����
     **/
    public boolean isGameOver() {
        // �������еķ�����
        for (int i = 0; i < this.fos.size(); i++) {
            // �ж��Ƿ�ײ��������
            if (heroSelf.isHit(this.fos.get(i))) {
                // ���ӱ�ըЧ��
                if(!(this.fos.get(i) instanceof BossAward))
                this.fod.add(new ExplodeObj(this.fos.get(i).getPosX() + this.fos.get(i).getObjWidth() / 2,
                        this.fos.get(i).getPosY() + this.fos.get(i).getObjHeight() / 2, 2, Factory.explode));
                // ���ײ��
                // �ж��Ƿ�ײ����Ͷ
                if (this.fos.get(i) instanceof AirDrop) {
                    // ���ųԿ�Ͷ������
                    music.playAward();
                    switch (heroSelf.getFireType()) {
                        case Hero.SINGLE:
                            this.bulletInterval = BLT_DOUBLE_INTERVAL;
                            heroSelf.setDoubleFire();
                            break;
                        case Hero.DOUBLE:
                            this.bulletInterval = BLT_DISPERSION_INTERVAL;
                            heroSelf.setDispersion();
                            break;
                        case Hero.DISPERSION:
                            if (this.bulletInterval != 0)
                                this.bulletInterval -= 10;
                            break;
                        default:
                            break;
                    }
                    this.fos.remove(i);
                } else if (this.fos.get(i) instanceof Boss && !this.heroInvincibility) {
                    this.heroSelf.substractLives();
                } else if (this.fos.get(i) instanceof YellowStone) {
                    // ���ųԿ�Ͷ������
                    music.playAward();
                    this.heroInvincibility = true;
                    this.timeOfInvincibility = 500;
                    this.fos.remove(i);
                } else if (this.fos.get(i) instanceof BossAward) {
                    // ���ųԿ�Ͷ������
                    music.playAward();
                    this.fos.remove(i);
                    heroSelf.setBombs(3);
                    System.out.println("TNT 3");
                } else if (!this.heroInvincibility) {
                    // ������ǵĻ���ѪȻ�����õ�������
                    heroSelf.substractLives();
                    this.bulletInterval = BLT_SINGLE_INTERVAL;
                    heroSelf.setSingleFire();
                    // �����ײʱ�ı�ը��Ч
                    music.playBoom();
                    this.fos.remove(i);
                } else if (this.fos.get(i) instanceof Boss && this.heroInvincibility) {
                    // �����ײʱ�ı�ը��Ч
                    music.playBoom();
                    Boss tmp = (Boss) this.fos.get(i);
                    this.score += tmp.getScore();
                    this.fos.remove(i);
                    this.bossEnemy = null;
                } else {
                    // �����ײʱ�ı�ը��Ч
                    music.playBoom();
                    this.fos.remove(i);
                }
            }
        }
        return heroSelf.getLives() < 0;
    }

    /* Ӣ�ۻ���� */
    public void shootAction() {
        this.numOfBullets++;
        if (this.numOfBullets % this.bulletInterval == 0) {
            this.bullets.addAll(this.heroSelf.shoot());
            // ���������Ч
            music.playDaDaDa();
        }
        if (null != this.bossEnemy) {

        }
    }

    /**
     * �����Ϸ�Ƿ����
     */
    public void checkGameOverAction() {
        if (this.isGameOver()) {
            status = Factory.OVER;
        }
    }

    /**
     * �ƶ�Ӣ�ۻ�
     */
    public void heroMove() {
        if (status == RUNNING) {
            int posX = heroSelf.getPosX();
            int posY = heroSelf.getPosY();
            if (left) {
                posX -= moveSpeed;
            }
            if (right) {
                posX += moveSpeed;
            }
            if (up) {
                posY -= moveSpeed;
            }
            if (down) {
                posY += moveSpeed;
            }
            boolean xOutOfLimit = posX <= 0 || posX + heroSelf.getObjWidth() >= GameMainJFrame.WIN_WIDTH;
            boolean yOutOfLimit = posY <= 0 || posY + heroSelf.getObjHeight() >= GameMainJFrame.WIN_HEIGHT;
            if (xOutOfLimit) {
                posX = heroSelf.getPosX();
            }
            if (yOutOfLimit) {
                posY = heroSelf.getPosY();
            }
            heroSelf.setPosX(posX);
            heroSelf.setPosY(posY);
        }
    }

    /**
     * ��Ϸ�峡
     */
    public void clear() {
        this.heroSelf = new Hero();
        this.bullets = new ArrayList<Bullet>();
        this.fos = new ArrayList<FlyingObject>();
        this.fod = new ArrayList<FlyingObject>();
        this.bossEnemy = null;
        this.score = 0;
    }

    /**
     * �ͷŴ���
     */
    public void boomBoom() {
        // ʹ�ô���
        // by����
        if (heroSelf.getBombs() > 0) {
            this.fos = new ArrayList<FlyingObject>();
            this.fod.add(new ExplodeObj(0, 0, 1, Factory.dazhao));
            music.playBoom();
            heroSelf.subBombs();
            this.bossEnemy = null;

        }
    }

    /* ������ */
    public void paintBg(Graphics g) {
        g.drawImage(bg, 0, 0, null);
    }

    /* ��Ӣ�ۻ� */
    public void paintHero(Graphics g) {
        g.drawImage(heroSelf.getImg(), heroSelf.getPosX(), heroSelf.getPosY(), null);
    }

    /* ���ӵ� */
    public void paintBullets(Graphics g) {
        for (int i = 0; i < this.bullets.size(); i++) {
            g.drawImage(bullet, this.bullets.get(i).getPosX(), this.bullets.get(i).getPosY(), null);
        }
    }

    /* �������� */
    public void paintFlyingObjeacts(Graphics g) {
        for (int i = 0; i < fos.size(); i++) {
            g.drawImage(fos.get(i).getImg(), fos.get(i).getPosX(), fos.get(i).getPosY(), null);
        }
    }

    /* ����ըЧ�� */
    public void paintFOD(Graphics g) {
        for (int i = 0; i < fod.size(); i++) {
            g.drawImage(fod.get(i).getImg(), this.fod.get(i).getPosX(), this.fod.get(i).getPosY(), null);
        }
    }

    /* ���÷ְ������ֵ */
    public void paintOthers(Graphics g) {
        // ��������
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score:" + score, 20, 30);
        g.drawString("Lives:", 20, 50);
        g.drawString("Bombs:" + heroSelf.getBombs(), 20, 70);
        // ����Ӣ��Ѫ��
        g.setColor(Color.red);
        g.fillRect(80, 35, heroSelf.getLives() * 5, 10);
        // ����bossѪ��
        if (null != this.bossEnemy) {
            g.fillRect(0, 0, this.bossEnemy.getLives() * 80, 5);
        }
    }

    /* ����Ϸ״̬ */
    public void paintGameStatus(Graphics g) {
        switch (status) {
            case START:
                // ���ű�������
//			music.playBg();
                music.playBg();
                g.drawImage(start, 0, 0, null);
                break;
            case RUNNING:

                break;
            case PAUSE:
                g.drawImage(pause, 0, 0, null);
                break;
            case OVER:
                g.drawImage(gameOver, 0, 0, null);
                break;
            default:
                break;
        }
    }

    /* ����Ƿ��޵� */
    public void checkHeroInvincibility() {
        if (heroInvincibility) {
            this.heroSelf.setImg(hugeHero);
            if (--this.timeOfInvincibility == 0) {
                this.timeOfInvincibility = 500;
                this.heroSelf.setImg(hero);
                this.heroInvincibility = false;
            }
        }
    }

    public void test() {

    }
}

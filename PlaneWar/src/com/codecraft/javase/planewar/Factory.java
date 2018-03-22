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

    /* 加载飞行物的图片 */
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
    /* 当前游戏的状态 */
    public static int status = 0;
    public static final int START = 0;
    public static final int RUNNING = 1;
    public static final int PAUSE = 2;
    public static final int OVER = 3;
    /* 上下左右标志 */
    public static boolean left = false;
    public static boolean right = false;
    public static boolean up = false;
    public static boolean down = false;
    /* 得分 */
    public int score = 0;
    /* 弹夹 */
    public List<Bullet> bullets = new ArrayList<Bullet>();
    /* 英雄机 */
    public Hero heroSelf = new Hero();
    /* 英雄机的飞行速度 */
    private int moveSpeed = 5;
    /* 英雄无敌属性 */
    private boolean heroInvincibility = false;
    /* 英雄无敌时间 */
    private int timeOfInvincibility = 500;
    /* 飞行物 */
    public List<FlyingObject> fos = new ArrayList<FlyingObject>();
    /* 飞行物的数量 */
    private int numOfFos = 0;
    /* 子弹计数器 */
    private int numOfBullets = 0;
    /* 飞行物生成时间间隔 */
    private int fosInterval = 80;

    /* 子弹生成时间间隔全局变量 */
    public final static int BLT_SINGLE_INTERVAL = 40;
    public final static int BLT_DOUBLE_INTERVAL = 70;
    public final static int BLT_DISPERSION_INTERVAL = 100;
    /* 子弹生成时间间隔 */
    private int bulletInterval = BLT_DISPERSION_INTERVAL;
    /* boss出现计时器 */
    private int bossTimer = 0;
    /* boss出现时间间隔 */
    private int bossIntervl = 100 * 30;// 100为1s 这是30s
    /* boss指针 */
    private Boss bossEnemy;
    /* 音乐播放 */
    Player music = new Player();
    /* 掉落物 */
    public List<FlyingObject> fod = new ArrayList<FlyingObject>();

    /* 静态代码块 */
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

    /* 生成飞行物 */
    public void enterAction() {
        // 设置时间间隔
        this.numOfFos++;
        if (this.numOfFos % (20 + this.fosInterval) == 0) {
            // 重新生成一个飞行物
            this.fos.add(this.productRandomFlyingObject());
        }
        if (this.numOfFos % 1000 == 0 && this.fosInterval != 0) {
            this.fosInterval -= 10;
        }
    }

    /* 随机生成飞行物 */
    public FlyingObject productRandomFlyingObject() {
        double rdm = Math.random();

        if (rdm < 0.6) {
            // 1.小型敌机 0.6
            return new SmallEnemyObj(smallEnemy);
            //return new BossAward();
        } else if (0.6 <= rdm && rdm < 0.88) {
            // 2.大型敌机0.25
            return new HugeEnemyObj(hugeEnemy);
        } else if (0.88 <= rdm && rdm < 0.98) {
            // 3.小蜜蜂 0.1
            return new LittleBee(redStone);
        } else if (0.98 <= rdm && rdm < 0.99) {
            // 4.黄宝石0.1
            return new AirDrop(purpleStone);
        } else {
            // 5.紫宝石0.1
            return new YellowStone(yellowStone);
        }
    }

    /* 飞行物的移动 */
    public void stepAction() {
        // 飞行物的移动
        for (int i = 0; i < this.fos.size(); i++) {
            if (this.fos.get(i) instanceof BossAward) {
                ((BossAward) this.fos.get(i)).move(heroSelf.getPosX(), heroSelf.getPosY());
            } else
                this.fos.get(i).move();

        }
        // 子弹的移动
        for (int i = 0; i < this.bullets.size(); i++) {
            this.bullets.get(i).move();
        }
        // 爆炸物的移动
        for (int i = 0; i < this.fod.size(); i++) {
            this.fod.get(i).move();
        }
    }

    /**
     * 添加实现散弹效果 遍历每一颗子弹是否与飞行物碰撞
     **/
    public void shotByAction() {
        for (int j = 0; j < this.bullets.size(); j++) {
            // 定义一个被击中飞行物的下标
            int index = -1;
            // 飞行物的对象遍历
            for (int i = 0; i < this.fos.size(); i++) {
                if (this.fos.get(i).isShot(this.bullets.get(j))) {
                    // 获取击中物品的下标
                    index = i;
                    if (!(this.fos.get(i) instanceof AirDrop)&&!(this.fos.get(i) instanceof BossAward))
                        this.fod.add(new ExplodeObj(this.fos.get(i).getPosX(), this.fos.get(i).getPosY(), 2, Factory.explode));
                    break;
                }
            }
            if (-1 != index) {
                if (this.fos.get(index) instanceof EnemyObj) {
                    // 获取对象
                    EnemyObj tmp = (EnemyObj) this.fos.get(index);
                    // 增加得分
                    score += tmp.getScore();
                    // 删除
                    this.fos.remove(index);
                    // 删除子弹
                    bullets.remove(j);
                    // 子弹击中播放音乐
                    music.playBoom();
                } else if (this.fos.get(index) instanceof LittleBee) {
                    // 增加生命
                    heroSelf.addLives();
                    // 播放声音
                    this.music.playAward();
                    // 删除子弹
                    bullets.remove(j);
                    // 移除被碰物品
                    this.fos.remove(index);
                } else if (this.fos.get(index).equals(this.bossEnemy)) {
                    this.bossEnemy.substractLives();
                    System.out.println("子弹打到boss,boss血量：" + this.bossEnemy.getLives());
                    if (this.bossEnemy.getLives() <= 0) {
                        this.fos.add(new BossAward(this.fos.get(index).getPosX(), this.fos.get(index).getPosY()));
                        this.fos.remove(index);
                        this.bossEnemy = null;
                        //boss死亡 大招数量重置为三
                        //this.heroSelf.setBombs(3);
                        System.out.println("boss死了");
                    }
                    // 删除子弹
                    bullets.remove(j);
                    // 子弹击中播放音乐
                    music.playBoom();
                } else if (this.fos.get(index) instanceof YellowStone) {
                    this.timeOfInvincibility = 500;
                    this.heroInvincibility = true;
                    // 播放声音
                    this.music.playAward();
                    // 删除
                    // 删除子弹
                    bullets.remove(j);
                    this.fos.remove(index);
                }
            }
        }
    }

    /**
     * 增加boss方法
     */
    public void makeABoss() {
        this.bossTimer++;
        if (this.bossTimer % this.bossIntervl == 0 && null == this.bossEnemy) {
            this.bossTimer = 0;
            // 增加一个boss
            this.bossEnemy = new Boss();
            this.fos.add(this.bossEnemy);
            System.out.println("增加了一个boss");
        }
    }

    /* 删除飞出窗口范围的飞行物与子弹 */
    public void delOutOfBoundsAction() {
        // 遍历并删除所有越界的飞行物
        for (int i = 0; i < this.fos.size(); i++) {
            if (this.fos.get(i).isOutOfBounds()) {
                this.fos.remove(i);
            }
        }
        // 遍历并删除所有越界的子弹
        for (int i = 0; i < this.bullets.size(); i++) {
            if (this.bullets.get(i).isOutOfBounds()) {
                this.bullets.remove(i);
            }
        }
        // 遍历删除所有的掉落物
        for (int i = 0; i < this.fod.size(); i++) {
            if (this.fod.get(i).isOutOfBounds()) {
                this.fod.remove(i);
            }
        }
    }

    /**
     * 英雄机碰撞到敌机减血，撞到空投增加火力
     *
     * @李翔
     **/
    public boolean isGameOver() {
        // 遍历所有的飞行物
        for (int i = 0; i < this.fos.size(); i++) {
            // 判断是否撞到飞行物
            if (heroSelf.isHit(this.fos.get(i))) {
                // 增加爆炸效果
                if(!(this.fos.get(i) instanceof BossAward))
                this.fod.add(new ExplodeObj(this.fos.get(i).getPosX() + this.fos.get(i).getObjWidth() / 2,
                        this.fos.get(i).getPosY() + this.fos.get(i).getObjHeight() / 2, 2, Factory.explode));
                // 如果撞到
                // 判断是否撞到空投
                if (this.fos.get(i) instanceof AirDrop) {
                    // 播放吃空投的声音
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
                    // 播放吃空投的声音
                    music.playAward();
                    this.heroInvincibility = true;
                    this.timeOfInvincibility = 500;
                    this.fos.remove(i);
                } else if (this.fos.get(i) instanceof BossAward) {
                    // 播放吃空投的声音
                    music.playAward();
                    this.fos.remove(i);
                    heroSelf.setBombs(3);
                    System.out.println("TNT 3");
                } else if (!this.heroInvincibility) {
                    // 如果不是的话减血然后设置单倍火力
                    heroSelf.substractLives();
                    this.bulletInterval = BLT_SINGLE_INTERVAL;
                    heroSelf.setSingleFire();
                    // 添加碰撞时的爆炸音效
                    music.playBoom();
                    this.fos.remove(i);
                } else if (this.fos.get(i) instanceof Boss && this.heroInvincibility) {
                    // 添加碰撞时的爆炸音效
                    music.playBoom();
                    Boss tmp = (Boss) this.fos.get(i);
                    this.score += tmp.getScore();
                    this.fos.remove(i);
                    this.bossEnemy = null;
                } else {
                    // 添加碰撞时的爆炸音效
                    music.playBoom();
                    this.fos.remove(i);
                }
            }
        }
        return heroSelf.getLives() < 0;
    }

    /* 英雄机射击 */
    public void shootAction() {
        this.numOfBullets++;
        if (this.numOfBullets % this.bulletInterval == 0) {
            this.bullets.addAll(this.heroSelf.shoot());
            // 播放射击音效
            music.playDaDaDa();
        }
        if (null != this.bossEnemy) {

        }
    }

    /**
     * 检测游戏是否结束
     */
    public void checkGameOverAction() {
        if (this.isGameOver()) {
            status = Factory.OVER;
        }
    }

    /**
     * 移动英雄机
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
     * 游戏清场
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
     * 释放大招
     */
    public void boomBoom() {
        // 使用大招
        // by李翔
        if (heroSelf.getBombs() > 0) {
            this.fos = new ArrayList<FlyingObject>();
            this.fod.add(new ExplodeObj(0, 0, 1, Factory.dazhao));
            music.playBoom();
            heroSelf.subBombs();
            this.bossEnemy = null;

        }
    }

    /* 画背景 */
    public void paintBg(Graphics g) {
        g.drawImage(bg, 0, 0, null);
    }

    /* 画英雄机 */
    public void paintHero(Graphics g) {
        g.drawImage(heroSelf.getImg(), heroSelf.getPosX(), heroSelf.getPosY(), null);
    }

    /* 画子弹 */
    public void paintBullets(Graphics g) {
        for (int i = 0; i < this.bullets.size(); i++) {
            g.drawImage(bullet, this.bullets.get(i).getPosX(), this.bullets.get(i).getPosY(), null);
        }
    }

    /* 画飞行物 */
    public void paintFlyingObjeacts(Graphics g) {
        for (int i = 0; i < fos.size(); i++) {
            g.drawImage(fos.get(i).getImg(), fos.get(i).getPosX(), fos.get(i).getPosY(), null);
        }
    }

    /* 画爆炸效果 */
    public void paintFOD(Graphics g) {
        for (int i = 0; i < fod.size(); i++) {
            g.drawImage(fod.get(i).getImg(), this.fod.get(i).getPosX(), this.fod.get(i).getPosY(), null);
        }
    }

    /* 画得分板和生命值 */
    public void paintOthers(Graphics g) {
        // 画出文字
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score:" + score, 20, 30);
        g.drawString("Lives:", 20, 50);
        g.drawString("Bombs:" + heroSelf.getBombs(), 20, 70);
        // 画出英雄血条
        g.setColor(Color.red);
        g.fillRect(80, 35, heroSelf.getLives() * 5, 10);
        // 画出boss血条
        if (null != this.bossEnemy) {
            g.fillRect(0, 0, this.bossEnemy.getLives() * 80, 5);
        }
    }

    /* 画游戏状态 */
    public void paintGameStatus(Graphics g) {
        switch (status) {
            case START:
                // 播放背景音乐
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

    /* 检查是否无敌 */
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

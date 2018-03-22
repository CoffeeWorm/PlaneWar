package com.codecraft.javase.planewar.ui;

/**
 * Created by 李翔win on 2018/1/6.
 */

import javax.swing.*;
import java.awt.*;

/**
 * 1、继承extends JFrame类 <br>
 * 2、通过构造方法，完成窗体的初始化 <br>
 * 3、通过main方法，完成窗体的实例化 <br>
 * 4、添加画布类的对象到窗体当中<br>
 *
 * @author BY
 *
 */
public class GameMainJFrame extends JFrame {
    /* 窗体属性 */
    public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final int WIN_WIDTH = 400;
    public static final int WIN_HEIGHT = 600;
    public static final int WIN_POS_X = (SCREEN_WIDTH - WIN_WIDTH) / 2;
    public static final int WIN_POS_Y = (SCREEN_HEIGHT - WIN_HEIGHT) / 2;
    private static final long serialVersionUID = 1L;

    public GameMainJFrame() {
        this.setTitle("Plane War");
        this.setBounds(WIN_POS_X, WIN_POS_Y, WIN_WIDTH + 17, WIN_HEIGHT + 40);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        GameJPanel gjp = new GameJPanel();
        this.add(gjp);
        this.addKeyListener(gjp);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new GameMainJFrame();
    }

}

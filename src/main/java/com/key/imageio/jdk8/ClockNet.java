package com.key.imageio.jdk8;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.time.*;


/**
 * 钟表
 *
 * @date 2019/7/2 11:56
 */
public class ClockNet extends JFrame implements Runnable, ChangeListener, ActionListener {
    // 控制钟表大小的整型变量d。根据拉条提供的值可以计算出d
    int d = 150;
    // 东区的拉条,根据它提供的值，可以计算出控制钟表大小的d的值
    JSlider eSlider;
    // 驱动钟表的线程 timer
    Thread timer;
    // 表盘心的位置
    int xClock, yClock;
    JTextField textfield;
    int duration = 5;
    // 摆的即时角度数组
    int instantAngle[] = {64, 61, 56, 49, 40, 29, 16, 3, -8, -16, -23, -28, -31, -31, -31, -28, -23, -16, -8, 3, 16, 29, 40, 49, 56, 61, 64, 64};
    // 数组下标,变化范围[0,27], 循环使用
    int index = 0;

    public ClockNet() {
        super("Key Clock");
        eSlider = new JSlider(SwingConstants.VERTICAL, 100, 200, 150);
        // 为拉条注册变化监听器
        eSlider.addChangeListener(this);
        // 拉条放到东区
        add(eSlider, BorderLayout.EAST);
        textfield = new JTextField(15);
        textfield.addActionListener(this);
        JPanel p = new JPanel();
        p.add(new JLabel("键入嘀嗒间隔值 ( 单位：秒，TickDuration in seconds)"));
        p.add(textfield);
        p.add(new JLabel("初始值 Initial：5 秒"));
        add(p, BorderLayout.SOUTH);
        // 画布放到中区
        add(new MyDraw(), BorderLayout.CENTER);
        // 调用start方法，以启动线程timer
        start();
        // 框架设置为可见
        setVisible(true);
        // 框架宽和高分别设置为 650 和 700 像素
        setSize(650, 700);
        // 为框架右上角结束按钮注册
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        duration = Integer.parseInt(textfield.getText());
    }

    /**
     * 启动ClockNet类线程的方法
     */
    public void start() {
        if (timer == null) {
            timer = new Thread(this);
            // 启动线程实例 timer
            timer.start();
        }
    }

    /**
     * 实现变化监听器 ChangeListener 的抽象方法
     *
     * @param e
     */
    public void stateChanged(ChangeEvent e) {
        /* 计算控制钟表尺寸的d的值:
         * 游标往上移动，拉条值增大，d 值减小
         * */
        d = 300 - eSlider.getValue();
    }

    public void run() {
        while (timer != null) {
            try {
                //休息150毫秒
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 调用paint方法
            repaint();
        }
    }

    public static void main(String args[]) {
        // 调用ClockNet类的构造方法
        new ClockNet();
    }

    /**
     * 将多边形 放大或缩小到 d 倍
     *
     * @param x
     * @param y
     * @param d
     */
    void enlarge(int[] x, int[] y, double d) {
        for (int i = 0; i < x.length; i++) {
            x[i] *= d;
            y[i] *= d;
        }
    }

    /**
     * 内部类,其实例作为组件放到中区
     */
    class MyDraw extends JPanel {
        public void paint(Graphics gScreen) {
            // 使表盘中心横坐标随d而变
            xClock = 250 * d / 150;
            // 使表盘中心纵坐标随d而变
            yClock = 200 * d / 150;
            String name1[] = {"+", "+", "+", "+"};
            String name[] = {"瑞", "士", "钟", "表"};
            /* 四分之一校徽图标在第一象限的多边形顶点数据 */
//            int xCollege[] = {15, 15, 20, 35, 35, 15, 5, 5, 15};
//            int yCollege[] = {-35, -20, -15, -15, -5, -5, -15, -35, -35};
            // 圆形顶点数据
            int xCollege[] = {0, 20, 30, 30, 20, 20, 15, 0};
            int yCollege[] = {-30, -30, -20, 0, 0, -15, -20, -20, -30};
            /* 表盘 0,3,6,9 点标志(大菱形多边形)顶点位置数据*/
            int x1[] = {0, d / 15, 0, -d / 15, 0};
            int y1[] = {(int) (-d * 1.13), -d, (int) (-d * 0.9), -d, (int) (-d * 1.13)};
            /* 表盘其余钟点标志(小菱形多边形)顶点位置数据 */
            int x[] = {0, d / 30, 0, -d / 30, 0};
            int y[] = {(int) (-d * 1.07), -d, (int) (-d * 0.9), -d, (int) (-d * 1.07)};

            Image buffer = createImage(650, 700); //双缓存技术的影像
            Graphics2D g = (Graphics2D) buffer.getGraphics();//获取其画笔
            g.setPaint(Color.white); //画笔设置为白色
            g.fillRect(0, 0, 650, 700); //背景涂成白色
            g.translate(xClock, yClock); //将坐标原点平移到表盘中心
            g.setColor(Color.green);    //画笔设置为绿色
            g.setStroke(new BasicStroke((float) (3.0 * d / 150)));//定线条粗细
            g.drawOval(-d, -d, 2 * d, 2 * d);//画表盘周边圆
            for (int i = 0; i < 12; i++) {//标出钟点位置
                if (i % 3 == 0) {//0,3,6,9点的标志画为红色较大菱形
                    g.setColor(Color.red); //画笔设置为红色
                    g.fillPolygon(x1, y1, x1.length);//填充菱形
                } else {//其它点画为桔黄色较小菱形
                    g.setColor(Color.orange);//画笔设置为桔黄色
                    g.fillPolygon(x, y, x.length);//填充菱形
                }
                g.rotate(Math.PI / 180 * 30);//钟点标志的间隔为 30 度
            }

            /* 开始画摆 */
            if (index == instantAngle.length) index = 0; //循环使用即时角度的数据
            int degree = instantAngle[index++];// 从摆的即时角度数组中读取数据

            // 坐标轴顺时针旋转相应的弧度
            g.rotate(Math.PI / 180 * degree);
            g.setColor(Color.red);
            g.setFont(new Font("楷体_GB2312", Font.BOLD, 25 * d / 150));
            for (int i = 0; i < name1.length; i++) { //写四个汉字字符的校名
                g.drawString(name1[i], 0, 200 * d / 150);
                g.rotate(-Math.PI / 180 * 8);//每个汉字字符占8度
            }
            g.rotate(Math.PI / 180 * 16); // 将坐标轴调到摆线的角度,以画摆线和摆锤
            // 坐标系必须沿y轴向下平移， 以在四个象限画出作为摆锤的图标
            g.translate(0, 250 * d / 150);
            g.setColor(Color.cyan);//画摆线的颜色设置为青色
            g.setStroke(new BasicStroke((float) (4.0 * d / 150)));
            g.drawLine(0, 0, 0, -250 * d / 150);//画摆线
            /*  将图标多边形顶点坐标的数据，复制到数组xxx和yyy */
            int xxx[] = Arrays.copyOf(xCollege, xCollege.length);
            int yyy[] = Arrays.copyOf(yCollege, yCollege.length);
            enlarge(xxx, yyy, (double) d / 150); //调整图标的尺寸
            g.setColor(Color.green); //设置图标的颜色
            for (int i = 0; i < 4; i++) { //分别在四个象限填充多边形,以完成图标
                g.fillPolygon(xxx, yyy, xxx.length);
                g.rotate(-Math.PI / 2);
            }
            // 画完摆锤,坐标系必须沿y轴向上平移,以还原坐标原点
            g.translate(0, -250 * d / 150);
            g.rotate(-Math.PI / 180 * (degree - 16));//将坐标系还原到正常位置
            /* 画摆完毕  */

            /* 开始画表盘上的图标  */
//            enlarge(xCollege, yCollege, 2.5 * d / 150);//调表盘整图标尺寸
//            g.setColor(Color.magenta);//将图标设置为洋红色
//            // 以表盘中心为坐标原点，分别在四个象限填充多边形,以完成图标
//            for (int i = 0; i < 4; i++) {
//                g.fillPolygon(xCollege, yCollege, xCollege.length);
//                g.rotate(Math.PI / 2);
//            }

            /* 开始画表盘上的文字  */
            g.setColor(Color.black);
            g.setFont(new Font("楷体_GB2312", Font.BOLD, 20 * d / 150));
            g.rotate(Math.PI / 180 * 68); //顺时针旋转坐标系，以写校名
            for (int i = 0; i < name.length; i++) {
                g.drawString(name[i], 0, 110 * d / 150);//字符串在摆盘的下半部
                g.rotate(-Math.PI / 180 * 36);//每个字符占18度的空间
            }
            g.rotate(Math.PI / 180 * 76);//顺时针旋转坐标系,以还原坐标系

            /* 开始画表针  */
            LocalTime now = LocalTime.now();//不含日期的地方时间
            /* 分别计算秒针、分针、时针相对 0 点位置的弧度数 */
            double s = Math.PI / 180 * 6 * now.getSecond();
            double m = Math.PI / 180 * 6 * now.getMinute();
            /* 时针位置，应包括分钟读数引起的附加值 */
            double h = Math.PI / 180 * (30 * now.getHour()) + 30 * now.getMinute() / 60;

            /*	调用 Clock.tick() 方法， 以求得定义了 嘀嗒跨度 tickDuration 的钟表 */
            Duration tickDuration = Duration.ofSeconds(duration);
            Clock clock = Clock.tick(Clock.systemUTC(), tickDuration);
            String ss = clock.instant().toString();
            int s_tick = Integer.parseInt(ss.substring(17, 19));
            //开始画 tick()的结果
            double s_tick_rotate = Math.PI / 180 * 6 * s_tick;
            g.rotate(s_tick_rotate);
            g.fillRect(-8, -d - 8, 16, 16);
//            g.drawLine(0, 0, 0, -d);
            g.rotate(-s_tick_rotate);//画 tick()的结果结束

            g.setColor(Color.blue);//表针颜色为兰
            g.rotate(s); //开始画秒针，它是最细、最长的表针
            g.setStroke(new BasicStroke((float) (3.0 * d / 150)));
            g.drawLine(0, 0, 0, -140 * d / 150);
            g.rotate(-s);//画秒针结束


            g.rotate(m);//开始画分针，它是较粗、较短的表针
            g.setStroke(new BasicStroke((float) (6.0 * d / 150)));
            g.drawLine(0, 0, 0, -90 * d / 150);
            g.rotate(-m);//画分针结束
            g.rotate(h);//开始画时针，它是最粗、最短的表针
            g.setStroke(new BasicStroke((float) (10.0 * d / 150)));
            g.drawLine(0, 0, 0, -45 * d / 150);
            g.rotate(-h);//画时针结束
            /* 画表针完毕  */
            //将在内存中画好的影像，画到桌面上
            gScreen.drawImage(buffer, 0, 0, this);
        }
    }
}
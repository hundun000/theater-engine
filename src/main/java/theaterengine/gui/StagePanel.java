package theaterengine.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import theaterengine.Application;
import theaterengine.entity.Role;
import theaterengine.entity.Screen;
import theaterengine.script.tool.ScalaTool;

/**
 *
 * @author hundun
 * Created on 2019/04/02
 */
public class StagePanel extends JPanel{
	
	
	private final List<Role> roles = new ArrayList<>();
	private final List<Screen> stageScreens = new ArrayList<>();
	private final MainFrame mainFrame;
	
    public StagePanel(MainFrame mainFrame) {
        super();
        this.mainFrame = mainFrame;
    }
    
    public void clearStage() {
    	roles.clear();
    	stageScreens.clear();
	}
    
    public void addRole(Role role) {
    	roles.add(role);
	}
    
    public void addScreen(Screen stageScreen) {
    	stageScreens.add(stageScreen);
	}
    Font speakFont = new Font(null, Font.PLAIN, (int) ScalaTool.meterToPixel(0.5));
    Font fontItemName = new Font(null, Font.PLAIN, (int) ScalaTool.meterToPixel(0.3));
    private void drawScreen(Graphics2D g2d, Screen stageScreen) {
    	g2d.setColor(Color.GRAY);
        g2d.drawRect((int)stageScreen.getX() - stageScreen.getWidth()/2, (int)stageScreen.getY(), stageScreen.getWidth(), stageScreen.getHeight());
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect((int)stageScreen.getX() - stageScreen.getWidth()/2, (int)stageScreen.getY(), stageScreen.getWidth(), stageScreen.getHeight());

        g2d.setColor(Color.BLACK);
        g2d.setFont(fontItemName);
        g2d.drawString(stageScreen.getName(), (int)stageScreen.getX() - fontItemName.getSize(), (int)stageScreen.getY() + stageScreen.getHeight() + fontItemName.getSize());
        
    }
    
    
    private void drawRole(Graphics2D g2d, Role role) {

        
        g2d.setColor(Color.BLACK);
        g2d.drawOval((int)role.getX() - Role.radius, (int)role.getY() - Role.radius, 2 * Role.radius, 2 * Role.radius);

        g2d.setColor(Color.GRAY);
        g2d.fillOval((int)role.getX() - Role.radius, (int)role.getY() - Role.radius, 2 * Role.radius, 2 * Role.radius);

        g2d.setColor(Color.BLACK);
        g2d.setFont(fontItemName);
        
        int roleX = (int) (role.getX() - Role.radius + 0.2 * fontItemName.getSize());
        int roleY = (int) (role.getY() + 0.5 * fontItemName.getSize());
        
        
        g2d.drawString(role.getName(), roleX, roleY);
        
        if (role.getSpeaking().length() != 0) {
            String text = role.getName() + " : " + role.getSpeaking();
            g2d.setColor(Color.BLACK);
            g2d.setFont(speakFont);
            int speakX = Application.stagePanelWidth / 2 - text.length() * speakFont.getSize() / 2;
            int speakY = Application.stagePanelHeight - speakFont.getSize();
            g2d.drawString(text, speakX, speakY);
        }
        
        
    }
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        drawGrids(g2d, (int) ScalaTool.meterToPixel(1));
        
        for (Role role : roles) {
        	drawRole(g2d, role);
        }
        
        for (Screen stageScreen : stageScreens) {
        	drawScreen(g2d, stageScreen);
        }
        
        
        
        g2d.dispose();
    }
	
	private Font gridFont = new Font(null, Font.PLAIN, 10);
	private void drawGrids(Graphics2D g2d, int space) {
		g2d.setColor(Color.RED);
        g2d.setFont(gridFont);
        
        int gridAreaHeight = (int) (this.getHeight() - 1.5 * speakFont.getSize());
        
		for (int x = space ; x < this.getWidth(); x += space) {
			int xx =  x - mainFrame.positionZeroX;
			g2d.drawLine(x, 0, x, gridAreaHeight);
			g2d.drawString(String.valueOf(ScalaTool.pixelToMeter(xx)), x + 5, gridFont.getSize());
		}
		for (int y = space ; y < gridAreaHeight; y += space) {
			int yy = y - mainFrame.positionZeroY;
			g2d.drawLine(0, y, this.getWidth(), y);
			g2d.drawString(String.valueOf(ScalaTool.pixelToMeter(yy)), 0, y);
		}
	}

    private void drawLine(Graphics2D g2d, int x0, int y0, int x1, int y1) {

        // 设置画笔颜色
        g2d.setColor(Color.RED);

        // 1. 两点绘制线段: 点(20, 50), 点(200, 50)
        g2d.drawLine(x0, y0, x1, y1);

//        // 2. 多点绘制折线: 点(50, 100), 点(100, 130), 点(150, 70), 点(200, 100)
//        int[] xPoints = new int[] { 50, 100, 150, 200 };
//        int[] yPoints = new int[] { 100, 120, 80, 100 };
//        int nPoints = 4;
//        g2d.drawPolyline(xPoints, yPoints, nPoints);

//        // 3. 两点绘制线段（设置线宽为5px）: 点(50, 150), 点(200, 150)
//        BasicStroke bs1 = new BasicStroke(5);       // 笔画的轮廓（画笔宽度/线宽为5px）
//        g2d.setStroke(bs1);
//        g2d.drawLine(50, 150, 200, 150);

        // 4. 绘制虚线: 将虚线分为若干段（ 实线段 和 空白段 都认为是一段）, 实线段 和 空白段 交替绘制,
        //             绘制的每一段（包括 实线段 和 空白段）的 长度 从 dash 虚线模式数组中取值（从首
        //             元素开始循环取值）, 下面数组即表示每段长度分别为: 5px, 10px, 5px, 10px, ...
//        float[] dash = new float[] { 5, 10 };
//        BasicStroke bs2 = new BasicStroke(
//                1,                      // 画笔宽度/线宽
//                BasicStroke.CAP_SQUARE,
//                BasicStroke.JOIN_MITER,
//                10.0f,
//                dash,                   // 虚线模式数组
//                0.0f
//        );
//        g2d.setStroke(bs2);
//        g2d.drawLine(50, 200, 200, 200);

    }

    /**
     * 2. 矩形 / 多边形
     */
    private void drawRect(Graphics g) {
        //mainFrame.setTitle("2. 矩形 / 多边形");
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.GRAY);

        // 1. 绘制一个矩形: 起点(30, 20), 宽80, 高100
        g2d.drawRect(30, 20, 80, 100);

        // 2. 填充一个矩形
        g2d.fillRect(140, 20, 80, 100);

        // 3. 绘制一个圆角矩形: 起点(30, 150), 宽80, 高100, 圆角宽30, 圆角高30
        g2d.drawRoundRect(30, 150, 80, 100, 30, 30);

        // 4. 绘制一个多边形(收尾相连): 点(140, 150), 点(180, 250), 点(220, 200)
        int[] xPoints = new int[] { 140, 180, 220};
        int[] yPoints = new int[] { 150,  250, 200};
        int nPoints = 3;
        g2d.drawPolygon(xPoints, yPoints, nPoints);

        g2d.dispose();
    }

    /**
     * 3. 圆弧 / 扇形
     */
    private void drawArc(Graphics g) {
        //mainFrame.setTitle("3. 圆弧 / 扇形");
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.RED);

        // 1. 绘制一条圆弧: 椭圆的外切矩形 左上角坐标为(0, 0), 宽100, 高100,
        //                弧的开始角度为0度, 需要绘制的角度数为-90度,
        //                椭圆右边水平线为0度, 逆时针为正角度, 顺时针为负角度
        g2d.drawArc(0, 0, 100, 100, 0, -90);

        // 2. 绘制一个圆: 圆的外切矩形 左上角坐标为(120, 20), 宽高为100
        g2d.drawArc(120, 20, 100, 100, 0, 360);

        g2d.setColor(Color.GRAY);

        // 3. 填充一个扇形
        g2d.fillArc(80, 150, 100, 100, 90, 270);

        g2d.dispose();
    }

    /**
     * 4. 椭圆 (实际上通过绘制360度的圆弧/扇形也能达到绘制圆/椭圆的效果)
     */
    private void drawOval(Graphics g) {
        //mainFrame.setTitle("4. 椭圆");
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.RED);

        // 1. 绘制一个圆: 圆的外切矩形 左上角坐标为(0, 0), 宽高为100
        g2d.drawOval(0, 0, 100, 100);

        g2d.setColor(Color.GRAY);

        // 2. 填充一个椭圆
        g2d.fillOval(120, 100, 100, 150);

        g2d.dispose();
    }

    /**
     * 5. 图片
     */
    private void drawImage(Graphics g) {
        //mainFrame.setTitle("5. 图片");
        Graphics2D g2d = (Graphics2D) g.create();

        // 从本地读取一张图片
        String filepath = "demo.jpg";
        Image image = Toolkit.getDefaultToolkit().getImage(filepath);

        // 绘制图片（如果宽高传的不是图片原本的宽高, 则图片将会适当缩放绘制）
        g2d.drawImage(image, 50, 50, image.getWidth(this), image.getHeight(this), this);

        g2d.dispose();
    }

    /**
     * 6. 文本
     */
    private void drawString(Graphics g) {
        //mainFrame.setTitle("6. 文本");
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 设置字体样式, null 表示使用默认字体, Font.PLAIN 为普通样式, 大小为 25px
        g2d.setFont(new Font(null, Font.PLAIN, 10));

        // 绘制文本, 其中坐标参数指的是文本绘制后的 左下角 的位置
        // 首次绘制需要初始化字体, 可能需要较耗时
        g2d.drawString("Hello World!", 20, 60);
        g2d.drawString("你好, 世界!", 20, 120);

        g2d.dispose();
    }
}
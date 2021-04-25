package theaterengine.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.core.MessageBus;
import theaterengine.core.World;
import theaterengine.script.Parser;
import theaterengine.script.StatementType;
import theaterengine.script.statement.DelayStatement;
import theaterengine.script.statement.RoleCreateStatement;
import theaterengine.script.statement.RoleOneDirectionMoveStatement;
import theaterengine.script.statement.RoleTalkStatement;
import theaterengine.script.statement.ScreenCreateStatement;
import theaterengine.script.tool.FileTool;
import theaterengine.script.tool.ScalaTool;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class MainFrame extends JFrame{
	
	private static final Logger logger = LoggerFactory.getLogger(MainFrame.class);

	public int positionZeroX = (int)ScalaTool.meterToPixel(9);
    public int positionZeroY = (int)ScalaTool.meterToPixel(4);
    
    private String scriptFilename;
	JPanel panelEdit;
	JTextArea textAreaCode;
	World world;
	StagePanel stagePanel;
	
	
	private void initUI(int width, int height) {
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //置窗口是否可以关闭
        
        int panelEditWidth = 400;
        int buttonPrepareWidth = 150;
        int buttonStartWidth = 150;
        int buttonSaveWidth = 100;
        int buttonHeight = 50;
        panelEdit = new JPanel();
        panelEdit.setBounds(0, 0, panelEditWidth, height);
        
        textAreaCode = new JTextArea();
        JScrollPane scrollPaneCode = new JScrollPane(textAreaCode);
        scrollPaneCode.setBounds(0, 0, panelEditWidth, height - buttonHeight);
        
        JButton buttonPrepare = new JButton("准备");
        buttonPrepare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> docs = Arrays.asList(textAreaCode.getText().split("\n"));
                world.prepare(stagePanel, docs);
            }
        });
        buttonPrepare.setBounds(0, height - buttonHeight, buttonPrepareWidth, buttonHeight);
        
        JButton buttonStart = new JButton("开始");
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world.start();
            }
        });
        buttonStart.setBounds(buttonPrepareWidth, height - buttonHeight, buttonStartWidth, buttonHeight);
        
        
        JButton buttonSave = new JButton("保存");
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileTool.saveTxtFile(scriptFilename, textAreaCode.getText().replaceAll("\n", System.lineSeparator()));
                } catch (IOException e1) {
                    logger.error("写文件失败");
                    e1.printStackTrace();
                }
            }
        });
        buttonSave.setBounds(buttonPrepareWidth + buttonStartWidth, height - buttonHeight, buttonSaveWidth, buttonHeight);
        
        panelEdit.setLayout(null);
        panelEdit.add(scrollPaneCode);
        panelEdit.add(buttonPrepare);
        panelEdit.add(buttonStart);
        panelEdit.add(buttonSave);
        
        stagePanel = new StagePanel(this);
        stagePanel.setBounds(panelEditWidth, 0, width, height);
        
        
        setLayout(null);
        getContentPane().add(stagePanel);
        getContentPane().add(panelEdit);
        
        setSize(width + panelEditWidth + 30, height + 100);    //设置窗口显示尺寸
        setLocationRelativeTo(null);
        
        setVisible(true);    //设置窗口是否可见
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        this.world = new World();
    }
	
	public void loadScriptIntoTextbox(String scriptFilename) {
	    this.scriptFilename = scriptFilename;
        InputStreamReader reader;
        try {
            reader = new InputStreamReader(new FileInputStream(scriptFilename));
            BufferedReader bufferedReader = new BufferedReader(reader); 
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            textAreaCode.setText(builder.toString());
            bufferedReader.close();
        } catch (IOException e) {
            logger.error("加载 {} 异常： {}", scriptFilename, e.getMessage());
            return;
        }
    }
	
	public MainFrame(int width, int height, int positionZeroX, int positionZeroY) {
		this.positionZeroX = positionZeroX;
		this.positionZeroY = positionZeroY;
		
		initUI(width, height);
		
		
        
	}

	
	
	
	

}

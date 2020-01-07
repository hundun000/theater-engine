package theaterengine;

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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.script.Parser;
import theaterengine.script.StatementType;
import theaterengine.script.statement.DelayStatement;
import theaterengine.script.statement.RoleCreateStatement;
import theaterengine.script.statement.RoleOneDirectionMoveStatement;
import theaterengine.script.statement.RoleSpeakStatement;
import theaterengine.script.statement.ScreenCreateStatement;
import theaterengine.script.tool.FileTool;
import theaterengine.script.tool.ScalaTool;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class Stage extends JFrame{
	
	private static final Logger logger = LoggerFactory.getLogger(Stage.class);

	
	public static int positionZeroX = (int)ScalaTool.meterToPixel(9);
	public static int positionZeroY = (int)ScalaTool.meterToPixel(4);
	
	public static int stagePanelWidth = (int)ScalaTool.meterToPixel(18);
    public static int stagePanelHeight = (int)ScalaTool.meterToPixel(8);
	
	JPanel panelEdit;
	JTextArea textAreaCode;
	Scene scene;
	StagePanel stagePanel;
	Parser parser;
	
	final String filename = "play/test.txt";
	
	public Stage(int width, int height, int positionZeroX, int positionZeroY) {
		Stage.positionZeroX = positionZeroX;
		Stage.positionZeroY = positionZeroY;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //置窗口是否可以关闭
		
		
		
		
		int panelEditWidth = 400;
		int buttonRunWidth = 300;
		int buttonRunHeight = 50;
		panelEdit = new JPanel();
		panelEdit.setBounds(0, 0, panelEditWidth, height);
		
		textAreaCode = new JTextArea();
		JScrollPane scrollPaneCode = new JScrollPane(textAreaCode);
		scrollPaneCode.setBounds(0, 0, panelEditWidth, height - buttonRunHeight);
		
		JButton buttonRun = new JButton("运行");
		buttonRun.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> docs = Arrays.asList(textAreaCode.getText().split("\n"));
				restart(docs);
            }
		});
		buttonRun.setBounds(0, height - buttonRunHeight, buttonRunWidth, buttonRunHeight);
		
		JButton buttonSave = new JButton("保存");
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					FileTool.saveTxtFile(filename, textAreaCode.getText().replaceAll("\n", System.lineSeparator()));
				} catch (IOException e1) {
					logger.error("写文件失败");
					e1.printStackTrace();
				}
            }
		});
		buttonSave.setBounds(buttonRunWidth, height - buttonRunHeight, panelEditWidth - buttonRunWidth, buttonRunHeight);
		
		panelEdit.setLayout(null);
		panelEdit.add(scrollPaneCode);
		panelEdit.add(buttonRun);
		panelEdit.add(buttonSave);
		
		stagePanel = new StagePanel();
		stagePanel.setBounds(panelEditWidth, 0, width, height);
		
		
		setLayout(null);
		getContentPane().add(stagePanel);
		getContentPane().add(panelEdit);
        
        setSize(width + panelEditWidth + 30, height + 100);    //设置窗口显示尺寸
        setLocationRelativeTo(null);
        
        setVisible(true);    //设置窗口是否可见
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        
        
        
        
        
        
        parser = new Parser();
		parser.registerGrammars(DelayStatement.grammars, StatementType.DELAY);
		parser.registerGrammars(ScreenCreateStatement.grammars, StatementType.SCREEN_CTEATE);
		parser.registerGrammars(RoleCreateStatement.grammars, StatementType.ROLE_CTEATE);
		parser.registerGrammars(RoleOneDirectionMoveStatement.grammars, StatementType.ROLE_ONE_DIRECTION_MOVE);
		parser.registerGrammars(RoleSpeakStatement.grammars, StatementType.ROLE_SPEAK);
        
		
		
		InputStreamReader reader;
		try {
			reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader bufferedReader = new BufferedReader(reader); 
	        StringBuilder builder = new StringBuilder();
	        String line;
	        while ((line = bufferedReader.readLine()) != null) {
	        	builder.append(line).append("\n");
	        }
	        textAreaCode.setText(builder.toString());
	        bufferedReader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		
	}
	
	
	
	public void restart(List<String> docs) {
		if (scene != null) {
			scene.cancel();
		}
		
		
        
        stagePanel.clearStage();
        
        scene = new Scene(stagePanel, docs, parser);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(scene, 0, 100);
        
        MessageBus.reset();
	}
	
	
	
	public static void main(String[] args) {
		new Stage(stagePanelWidth, stagePanelHeight, positionZeroX, positionZeroY);
	}

}

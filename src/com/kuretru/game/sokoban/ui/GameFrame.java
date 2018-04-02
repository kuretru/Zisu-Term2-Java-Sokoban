package com.kuretru.game.sokoban.ui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.kuretru.game.sokoban.LevelData;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 50;
	private LevelData levelData;

	public GameFrame() {
		levelData = new LevelData("data1"); // 载入关卡数据
		initialize();
		setLevelData();
	}

	// 初始化控件
	private void initialize() {
		this.setSize(new Dimension(levelData.getMaxLineLength() * WIDTH + 10, levelData.getSize() * WIDTH + 40));
		this.setLocationRelativeTo(null);
		this.setTitle("Sokoban - 推箱子");
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("res/background.png"));
		lblBackground.setBounds(0, 0, getWidth(), getHeight());
		// this.getContentPane().add(lblBackground);
		this.setVisible(true);
	}

	// 根据关卡数据，设置控件
	private void setLevelData() {
		for (int i = 0; i < levelData.getSize(); i++) {
			String line = levelData.get(i);
			for (int j = 0; j < line.length(); j++) {
				char c = line.charAt(j);
				if (c == ' ')
					continue;
				JLabel label = new JLabel("");
				label.setName(String.format("lbl%02d%02d", j, i));
				if (c == '#')
					label.setIcon(new ImageIcon("res/wall.png"));
				else if (c == '@')
					label.setIcon(new ImageIcon("res/person-forward.png"));
				else if (c == '.')
					label.setIcon(new ImageIcon("res/target.png"));
				label.setBounds(j * WIDTH, i * WIDTH, 50, 50);
				this.getContentPane().add(label);
			}
		}
		this.getContentPane().repaint();
	}
}
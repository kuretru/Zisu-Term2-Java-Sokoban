package com.kuretru.game.sokoban.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.kuretru.game.sokoban.LevelData;

public class GameFrame extends JFrame {

	public GameFrame() {
		this.initialize();
		this.loadGameData();
	}

	private void initialize() {
		this.setSize(new Dimension(800, 600));
		this.setLocationRelativeTo((Component) null);
		this.setTitle("Sokoban - ÍÆÏä×Ó");
		this.setResizable(false);
		this.setDefaultCloseOperation(3);
		this.getContentPane().setLayout((LayoutManager) null);
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("res/background.png"));
		lblBackground.setBounds(0, 0, 794, 565);
		this.getContentPane().add(lblBackground);
		this.setVisible(true);
	}

	private void loadGameData() {
		new LevelData("data1");
	}
}
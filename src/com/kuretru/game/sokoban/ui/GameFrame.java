package com.kuretru.game.sokoban.ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.kuretru.game.sokoban.Cell;
import com.kuretru.game.sokoban.CellTypeEnum;
import com.kuretru.game.sokoban.LevelData;

public class GameFrame extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 50;
	private static final int[][] offset = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
	private static final String[] imageName = { "left", "backward", "right", "forward" };

	private LevelData levelData;
	private Point person;

	public GameFrame() {
		levelData = new LevelData("data1"); // 载入关卡数据
		setLevelData();
		initialize();
	}

	// 初始化控件
	private void initialize() {
		this.setSize(new Dimension(levelData.getY() * WIDTH + 10, levelData.getX() * WIDTH + 40));
		this.setLocationRelativeTo(null);
		this.setTitle("Sokoban - 推箱子");
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.addKeyListener(this);

		// 拉伸背景图片
		ImageIcon backGround = new ImageIcon("res/background.png");
		backGround.setImage(backGround.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT));
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(backGround);
		lblBackground.setBounds(0, 0, getWidth(), getHeight());
		this.getContentPane().add(lblBackground);
		this.setVisible(true);
	}

	// 根据关卡数据，设置控件
	private void setLevelData() {
		List<JLabel> boxes = new ArrayList<JLabel>();
		JLabel personLabel = null;
		for (int i = 0; i < levelData.getX(); i++) {
			for (int j = 0; j < levelData.getY(); j++) {
				Cell cell = levelData.get(i, j);
				if (cell.type == CellTypeEnum.EMPTY)
					continue;
				JLabel label = new JLabel("");
				label.setName(String.format("lbl%02d%02d", j, i));
				if (cell.type == CellTypeEnum.WALL) {
					label.setIcon(new ImageIcon("res/wall.png"));
					this.getContentPane().add(label);
				} else if (cell.type == CellTypeEnum.PERSON) {
					label.setIcon(new ImageIcon("res/person-forward.png"));
					person = new Point(i, j);
					personLabel = label;
				} else if (cell.type == CellTypeEnum.BOX) {
					label.setIcon(new ImageIcon("res/box.png"));
					boxes.add(label);
				} else if (cell.type == CellTypeEnum.TARGET) {
					label.setIcon(new ImageIcon("res/target.png"));
					this.getContentPane().add(label);
				}
				label.setBounds(j * WIDTH, i * WIDTH, 50, 50);
				cell.label = label;
			}
		}
		for (JLabel label : boxes)
			this.getContentPane().add(label);
		this.getContentPane().add(personLabel);
		this.getContentPane().repaint();
	}

	private void push(int direction) {
		JLabel label = levelData.get(person.x, person.y).label;
		label.setIcon(new ImageIcon(String.format("res/person-%s.png", imageName[direction])));
		Rectangle rectangle = label.getBounds();
		rectangle.x += offset[direction][0] * WIDTH;
		rectangle.y += offset[direction][1] * WIDTH;
		label.setBounds(rectangle);
		this.getContentPane().repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code >= 37 && code <= 40)
			push(code - 37);
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
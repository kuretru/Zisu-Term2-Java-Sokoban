package com.kuretru.game.sokoban.ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.kuretru.game.sokoban.Cell;
import com.kuretru.game.sokoban.CellTypeEnum;
import com.kuretru.game.sokoban.LevelData;
import com.kuretru.game.sokoban.Program;

public class GameFrame extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 50;
	private static final int[][] offset = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
	private static final String[] imageName = { "left", "backward", "right", "forward" };

	private LevelData levelData;
	private Point person;
	public int level = 0;// 关卡号
	public int step = 0;// 走的步数
	private long startTime = 0;
	private long endTime = 0;

	public GameFrame(int level) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				new MenuFrame((level - 1) / 15);
			}
		});
		this.level = level;
		levelData = new LevelData(String.format("data%d", level)); // 载入关卡数据
		setLevelData();
		initialize();
	}

	// 初始化控件
	private void initialize() {
		this.setSize(new Dimension(levelData.getY() * WIDTH + 10, levelData.getX() * WIDTH + 40));
		this.setLocationRelativeTo(null);
		this.setTitle(String.format("Sokoban - 推箱子 - 关卡%d", level));
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.addKeyListener(this);

		// 拉伸背景图片
		ImageIcon backGround = new ImageIcon(Program.class.getResource("/background.png"));
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
					label.setIcon(new ImageIcon(Program.class.getResource("/wall.png")));
					this.getContentPane().add(label);
				} else if (cell.type == CellTypeEnum.PERSON) {
					label.setIcon(new ImageIcon(Program.class.getResource("/person-forward.png")));
					person = new Point(i, j);
					personLabel = label;
				} else if (cell.type == CellTypeEnum.BOX) {
					label.setIcon(new ImageIcon(Program.class.getResource("/box.png")));
					boxes.add(label);
				} else if (cell.type == CellTypeEnum.TARGET) {
					label.setIcon(new ImageIcon(Program.class.getResource("/target.png")));
					this.getContentPane().add(label);
				}
				label.setBounds(j * WIDTH, i * WIDTH, 50, 50);
				cell.label = label;
			}
		}
		this.getContentPane().add(personLabel);
		for (JLabel label : boxes)
			this.getContentPane().add(label);
		this.getContentPane().repaint();
	}

	// 推箱子
	private void push(int direction) {
		JLabel label = levelData.get(person).label;
		label.setIcon(new ImageIcon(Program.class.getResource(String.format("/person-%s.png", imageName[direction]))));
		Point next = new Point(person.x + offset[direction][1], person.y + offset[direction][0]);
		Cell nextCell = levelData.get(next);
		if (nextCell.type == CellTypeEnum.WALL) {
			return;
		} else if (nextCell.type == CellTypeEnum.BOX) {
			Point nnext = new Point(next.x + offset[direction][1], next.y + offset[direction][0]);
			if (levelData.get(nnext).type == CellTypeEnum.WALL || levelData.get(nnext).type == CellTypeEnum.BOX)
				return;
			Rectangle rectangle = nextCell.label.getBounds();
			rectangle.x += offset[direction][0] * WIDTH;
			rectangle.y += offset[direction][1] * WIDTH;
			nextCell.label.setBounds(rectangle);
			if (levelData.isArrived(nnext)) {
				nextCell.label.setIcon(new ImageIcon(Program.class.getResource("/finish.png")));
			} else {
				nextCell.label.setIcon(new ImageIcon(Program.class.getResource("/box.png")));
			}
			levelData.swap(next, nnext);
		}
		levelData.swap(person, next);
		person = next;
		Rectangle rectangle = label.getBounds();
		rectangle.x += offset[direction][0] * WIDTH;
		rectangle.y += offset[direction][1] * WIDTH;
		label.setBounds(rectangle);
	}

	// 检查是否已通关
	private void completeInspect(boolean check) {
		if (check && !levelData.completed())
			return;
		endTime = System.currentTimeMillis();
		new VictoryDialog(this);
		this.setEnabled(false);
	}

	// 获取游戏用时
	public long getTimeCost() {
		if (startTime == 0 || endTime == 0)
			return -1;
		return endTime - startTime;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code >= 37 && code <= 40) {
			if (startTime == 0)
				startTime = System.currentTimeMillis();
			push(code - 37);
			step++;
			completeInspect(true);
		} else if (code == 71) {
			completeInspect(false);
		} else if (code == 27) {
			new GameFrame(this.level);
			this.dispose();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
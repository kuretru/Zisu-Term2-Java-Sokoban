package com.kuretru.game.sokoban.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.kuretru.game.sokoban.PageTurnEnum;

public class MenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int ROW_COUNT = 3;
	private static final int COLUMN_COUNT = 5;
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 80;
	private static final int BUTTON_SPACING = 50;
	private int page = 0;
	private List<JButton> buttonList;
	private JLabel lblPage;

	public MenuFrame(int page) {
		this.page = page;
		buttonList = new ArrayList<JButton>();
		initialize();
		createButtons();
		createControls();
		refreshScreen();
	}

	// 初始化控件
	private void initialize() {
		this.setSize(new Dimension(970, 600));
		this.setLocationRelativeTo(null);
		this.setTitle("Sokoban - 推箱子");
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				new StartFrame();
			}
		});
	}

	// 初始化按钮
	private void createButtons() {
		int spacing = (this.getWidth() - COLUMN_COUNT * BUTTON_WIDTH - (COLUMN_COUNT - 1) * BUTTON_SPACING) / 2;
		int x = spacing;
		int y = 50;
		int end = ROW_COUNT * COLUMN_COUNT;
		for (int i = 1; i <= end; i++) {
			JButton button = new JButton();
			button.setName(String.valueOf(i));
			button.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int level = Integer.parseInt(((JButton) (arg0.getSource())).getName());
					new GameFrame(level + page * ROW_COUNT * COLUMN_COUNT);
					dispose();
				}
			});
			this.getContentPane().add(button);
			buttonList.add(button);
			x += BUTTON_WIDTH + BUTTON_SPACING;
			if (i % COLUMN_COUNT == 0) {
				x = spacing;
				y += BUTTON_HEIGHT + BUTTON_SPACING;
			}
		}
	}

	// 初始化其他控件
	private void createControls() {
		int spacing = (this.getWidth() - COLUMN_COUNT * BUTTON_WIDTH - (COLUMN_COUNT - 1) * BUTTON_SPACING) / 2;

		JButton btnPrevious = new JButton("上一页");
		btnPrevious.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnPrevious.setBounds(spacing, 480, 120, 48);
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pageTurn(PageTurnEnum.UP);
			}
		});
		getContentPane().add(btnPrevious);

		JButton btnNext = new JButton("下一页");
		btnNext.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnNext.setBounds(this.getWidth() - spacing - 120, 480, 120, 48);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pageTurn(PageTurnEnum.DOWN);
			}
		});
		getContentPane().add(btnNext);

		lblPage = new JLabel("第 1 页");
		lblPage.setForeground(Color.RED);
		lblPage.setFont(new Font("幼圆", Font.PLAIN, 32));
		lblPage.setBounds(425, 480, 120, 48);
		getContentPane().add(lblPage);

		ImageIcon backGround = new ImageIcon("res/background.png");
		backGround.setImage(backGround.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT));
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(backGround);
		lblBackground.setBounds(0, 0, getWidth(), getHeight());
		this.getContentPane().add(lblBackground);
		this.setVisible(true);
	}

	// 执行翻页操作
	private void pageTurn(PageTurnEnum turn) {
		if (turn == PageTurnEnum.UP) {
			if (page <= 0)
				return;
			page--;
		} else if (turn == PageTurnEnum.DOWN) {
			if (page >= 5)
				return;
			page++;
		}
		refreshScreen();
	}

	private void refreshScreen() {
		lblPage.setText(String.format("第 %d 页", page + 1));
		for (int i = 0; i < buttonList.size(); i++) {
			JButton button = buttonList.get(i);
			ImageIcon image = new ImageIcon(
					String.format("res/screen/data%d.jpg", page * ROW_COUNT * COLUMN_COUNT + i + 1));
			image.setImage(image.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_DEFAULT));
			button.setIcon(image);
		}
	}
}

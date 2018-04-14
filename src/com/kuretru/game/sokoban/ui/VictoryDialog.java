package com.kuretru.game.sokoban.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import com.kuretru.game.sokoban.Program;

public class VictoryDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private GameFrame parent;

	public VictoryDialog(GameFrame gameFrame) {
		this.parent = gameFrame;
		initialize();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				parent.setEnabled(true);
				btnNext_actionPerformed(null);
			}
		});
	}

	// 初始化控件
	private void initialize() {
		this.setSize(new Dimension(533, 300));
		this.setLocationRelativeTo(null);
		this.setTitle("恭喜您，胜利了！");
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);

		JLabel lblVictory = new JLabel("恭喜您，胜利了！");
		lblVictory.setForeground(new Color(255, 51, 0));
		lblVictory.setFont(new Font("华文琥珀", Font.PLAIN, 48));
		lblVictory.setBounds(68, 36, 390, 60);
		getContentPane().add(lblVictory);

		JLabel lblStep = new JLabel("移动步数：" + parent.step);
		lblStep.setForeground(new Color(255, 204, 204));
		lblStep.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblStep.setBounds(68, 127, 160, 18);
		getContentPane().add(lblStep);

		JLabel lblTime = new JLabel("用时：" + getTimeString(parent.getTimeCost()));
		lblTime.setForeground(new Color(255, 204, 204));
		lblTime.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblTime.setBounds(263, 127, 195, 18);
		getContentPane().add(lblTime);

		JButton btnMenu = new JButton("菜单");
		btnMenu.setFont(new Font("幼圆", Font.PLAIN, 18));
		btnMenu.setBounds(69, 186, 120, 40);
		getContentPane().add(btnMenu);

		JButton btnRetry = new JButton("重试");
		btnRetry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnRetry_actionPerformed(arg0);
			}
		});
		btnRetry.setFont(new Font("幼圆", Font.PLAIN, 18));
		btnRetry.setBounds(203, 186, 120, 40);
		getContentPane().add(btnRetry);

		JButton btnNext = new JButton("下一关");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnNext_actionPerformed(arg0);
			}
		});
		btnNext.setFont(new Font("幼圆", Font.PLAIN, 18));
		btnNext.setBounds(337, 186, 120, 40);
		getContentPane().add(btnNext);

		// 拉伸背景图片
		ImageIcon backGround = new ImageIcon(Program.class.getResource("/background.png"));
		backGround.setImage(backGround.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT));
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(backGround);
		lblBackground.setBounds(0, 0, getWidth(), getHeight());
		this.getContentPane().add(lblBackground);
		this.getRootPane().setDefaultButton(btnNext);
		this.setVisible(true);
	}

	private static String getTimeString(long time) {
		if (time <= 0)
			return String.valueOf(time);
		long milSecond = time % 1000;
		time /= 1000;
		long second = time % 60;
		time /= 60;
		long minute = time % 60;
		return String.format("%d分%d秒 %d", minute, second, milSecond);
	}

	private void btnRetry_actionPerformed(ActionEvent e) {
		new GameFrame(parent.level);
		this.dispose();
		parent.dispose();
	}

	private void btnNext_actionPerformed(ActionEvent e) {
		new GameFrame(parent.level + 1);
		this.dispose();
		parent.dispose();
	}
}

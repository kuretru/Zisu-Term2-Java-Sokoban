package com.kuretru.game.sokoban.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.kuretru.game.sokoban.Program;

public class StartFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public StartFrame() {
		initialize();
	}

	// ��ʼ���ؼ�
	private void initialize() {
		this.setSize(new Dimension(970, 600));
		this.setLocationRelativeTo(null);
		this.setTitle("Sokoban - ������");
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("�� �� ��");
		lblTitle.setFont(new Font("΢���ź�", Font.PLAIN, 62));
		lblTitle.setForeground(Color.RED);
		lblTitle.setBounds(360, 75, 240, 100);
		getContentPane().add(lblTitle);

		JLabel lblSokoban = new JLabel("Sokoban");
		lblSokoban.setForeground(Color.ORANGE);
		lblSokoban.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 36));
		lblSokoban.setBounds(400, 150, 240, 75);
		getContentPane().add(lblSokoban);

		JButton btnStart = new JButton("��ʼ��Ϸ");
		btnStart.setFont(new Font("����", Font.PLAIN, 24));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new MenuFrame(0);
				dispose();
			}
		});
		btnStart.setBounds(390, 300, 160, 48);
		getContentPane().add(btnStart);

		JButton btnTutor = new JButton("��Ϸ��ѧ");
		btnTutor.setFont(new Font("����", Font.PLAIN, 24));
		btnTutor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GameFrame(0);
				dispose();
			}
		});
		btnTutor.setBounds(390, 350, 160, 48);
		getContentPane().add(btnTutor);

		JButton btnExit = new JButton("�˳�");
		btnExit.setFont(new Font("����", Font.PLAIN, 24));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExit.setBounds(390, 400, 160, 48);
		getContentPane().add(btnExit);

		ImageIcon backGround = new ImageIcon(Program.class.getResource("/background.png"));
		backGround.setImage(backGround.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT));
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(backGround);
		lblBackground.setBounds(0, 0, getWidth(), getHeight());
		this.getContentPane().add(lblBackground);
		this.setVisible(true);
	}
}

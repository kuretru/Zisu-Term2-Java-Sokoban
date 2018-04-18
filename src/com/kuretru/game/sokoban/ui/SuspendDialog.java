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
import com.kuretru.game.sokoban.TimeUtiliy;

public class SuspendDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private GameFrame parent;

	public SuspendDialog(GameFrame gameFrame) {
		this.parent = gameFrame;
		initialize();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				btnResume_actionPerformed(null);
			}
		});
	}

	// ³õÊ¼»¯¿Ø¼þ
	private void initialize() {
		this.setSize(new Dimension(533, 300));
		this.setLocationRelativeTo(null);
		this.setTitle(Program.PROGRAM_TITLE);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);

		JLabel lblVictory = new JLabel("ÓÎÏ·ÔÝÍ£ÖÐ......");
		lblVictory.setForeground(new Color(255, 51, 0));
		lblVictory.setFont(new Font("»ªÎÄçúçê", Font.PLAIN, 48));
		lblVictory.setBounds(68, 36, 390, 60);
		getContentPane().add(lblVictory);

		JLabel lblStep = new JLabel("ÒÆ¶¯²½Êý£º" + parent.step);
		lblStep.setForeground(new Color(255, 204, 204));
		lblStep.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		lblStep.setBounds(68, 127, 160, 18);
		getContentPane().add(lblStep);

		JLabel lblTime = new JLabel("ÓÃÊ±£º" + TimeUtiliy.getTimeString(parent.getTimeCost()));
		lblTime.setForeground(new Color(255, 204, 204));
		lblTime.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		lblTime.setBounds(263, 127, 195, 18);
		getContentPane().add(lblTime);

		JButton btnMenu = new JButton("²Ëµ¥");
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnMenu_actionPerformed(arg0);
			}
		});
		btnMenu.setFont(new Font("Ó×Ô²", Font.PLAIN, 18));
		btnMenu.setBounds(69, 186, 120, 40);
		getContentPane().add(btnMenu);

		JButton btnRetry = new JButton("ÖØÊÔ");
		btnRetry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnRetry_actionPerformed(arg0);
			}
		});
		btnRetry.setFont(new Font("Ó×Ô²", Font.PLAIN, 18));
		btnRetry.setBounds(203, 186, 120, 40);
		getContentPane().add(btnRetry);

		JButton btnResume = new JButton("¼ÌÐø");
		btnResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnResume_actionPerformed(arg0);
			}
		});
		btnResume.setFont(new Font("Ó×Ô²", Font.PLAIN, 18));
		btnResume.setBounds(337, 186, 120, 40);
		getContentPane().add(btnResume);

		// À­Éì±³¾°Í¼Æ¬
		ImageIcon backGround = new ImageIcon(Program.class.getResource("/background.png"));
		backGround.setImage(backGround.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT));
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(backGround);
		lblBackground.setBounds(0, 0, getWidth(), getHeight());
		this.getContentPane().add(lblBackground);
		this.getRootPane().setDefaultButton(btnResume);
		this.setVisible(true);
	}

	private void btnMenu_actionPerformed(ActionEvent e) {
		new MenuFrame((parent.level - 1) / 15);
		this.dispose();
		parent.dispose();
	}

	private void btnRetry_actionPerformed(ActionEvent e) {
		new GameFrame(parent.level);
		this.dispose();
		parent.dispose();
	}

	private void btnResume_actionPerformed(ActionEvent e) {
		parent.resume();
		parent.setEnabled(true);
		this.dispose();
	}
}

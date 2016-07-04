package com.unbank;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MyPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon icon; // 用于存储图片
	private String str; // 要按比例显示的图片的绝对路径

	public MyPanel(String str) {
		super();
		icon = new ImageIcon(str);
		this.str = str;
	}

	protected void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		graphic.drawImage(icon.getImage(), 0, 0, this.getWidth(),
				this.getHeight(), null);
	}

	public String getstr() {
		return (str);
	}

	public void SetStr(String str) {
		this.str = str;
	}

}

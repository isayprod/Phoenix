package com.umons.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MenuGUI extends JPanel{

	private static final long serialVersionUID = 1L;
	
	Image image;
	MyButton startButton;
	MyButton quitButton;
	MyButton reloadButton;
	
	public MenuGUI(final JFrame parentFrame) {
		System.out.println("dans le constructeur");
		try {
			image = ImageIO.read(new File("./misc/wallpaper.jpg"));
		}catch (IOException ie) {
			System.err.println("Erreur d'image :");
			ie.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		setLayout(new GridBagLayout());
		
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		setLayout(gb);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		
		startButton = new MyButton("START", new Color(211, 84, 0));
		startButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				((QuoridorGUI)parentFrame).switchToPanel(QuoridorGUI.BOARDGUI);
			}
		});
		gb.setConstraints(startButton, gbc);
		this.add(startButton);
		
		gbc.gridy = 1;
		
		reloadButton = new MyButton("RELOAD", new Color(211, 84, 0));
		reloadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("charge la partie précedemment sauvé");
			}
		});
		gb.setConstraints(reloadButton, gbc);
		this.add(reloadButton);
		
		gbc.gridy = 2;
		quitButton = new MyButton("QUIT", new Color(211, 84, 0));
		quitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("quitte le jeux");
				System.exit(0);
			}
		});
		gb.setConstraints(quitButton, gbc);
		this.add(quitButton);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.BLACK);
		g2d.drawImage(image, -750, -75, this);
	}

}

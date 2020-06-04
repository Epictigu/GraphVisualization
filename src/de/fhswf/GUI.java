package de.fhswf;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class GUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public Knoten k;
	
	public GUI() {
		initWindow();
	}
	
	private void initWindow() {
		setTitle("GDI Projekt");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 650);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null); // setzt das Fenster in die Mitte des Bildschirms
		
		getContentPane().setBackground(new Color(51,51,51));
		
		k = new Knoten(ReadFile.readFileScanner("Abbildung_22_3.gdi"));
		k.setBounds(25, 146, 450, 450);
		add(k);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}

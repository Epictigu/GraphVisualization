package de.fhswf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class GUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	public GUI() {
		initWindow();
	}

	private void initWindow() {
		setTitle("GDI Projekt");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 650);
		setResizable(false);
		setLocationRelativeTo(null); // setzt das Fenster in die Mitte des Bildschirms
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}

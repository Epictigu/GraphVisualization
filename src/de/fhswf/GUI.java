package de.fhswf;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

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

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		getContentPane().setBackground(new Color(51, 51, 51));

		k = new Knoten();
		k.setBounds(25, 146, 450, 450);
		add(k);

		JPanel jp = new JPanel();
		jp.setBounds(0, 0, 500, 30);
		jp.setBackground(Color.GRAY);
		add(jp);

		JButton sFB = new JButton("Select File");
		sFB.setBounds(0, 0, 100, 30);
		sFB.setFocusPainted(false);
		sFB.addActionListener(this);
		sFB.setActionCommand("selectFile");
		add(sFB);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("selectFile")) {

			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("GDIDatei", "gdi");
			fc.setFileFilter(filter);

			fc.setCurrentDirectory(new java.io.File("C:/"));
			fc.setDialogTitle(".gdi Datei auswählen"); // Titel des Dateiexplorers ?
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // glaube sollte nur files zeigen
			if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				k.setFile(ReadFile.readFileScanner(fc.getSelectedFile().getAbsolutePath()));
			}

		}
	}
}

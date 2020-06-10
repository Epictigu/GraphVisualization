package de.fhswf;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	public Knoten k;

	public GUI(Graph g) {
		initWindow(g);
	}

	private void initWindow(Graph g) {
		setTitle("GDI Projekt");
		if(g != null) setTitle(getTitle() + " - " +  g.getPath());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 550);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null); // setzt das Fenster in die Mitte des Bildschirms
		setBounds(getX() + (25 * Main.guiList.size()), getY() + (25 * Main.guiList.size()), 500, 550);
		
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
		k.setBounds(25, 46, 450, 450);
		if (g != null)
			k.setFile(g);
		k.setToolTipText("Platzhalter");
		add(k);

		// TestMenu
		JMenuBar menuBar;
		JMenu menu, color;
		JMenuItem selectFile, exitWindow;

		menuBar = new JMenuBar();

		// File
		menu = new JMenu("File");

		selectFile = new JMenuItem("Select File");
		selectFile.addActionListener(this);
		selectFile.setActionCommand("selectFile");
		menu.add(selectFile);

		exitWindow = new JMenuItem("Exit");
		menu.add(exitWindow);

		// Color
		color = new JMenu("Color");

		menuBar.add(menu);
		menuBar.add(color);

		setJMenuBar(menuBar);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("selectFile")) {
			
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("GDIDatei", "gdi");
			fc.setFileFilter(filter);

			fc.setCurrentDirectory(new File("."));
			fc.setDialogTitle(".gdi Datei auswählen");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				String path = fc.getSelectedFile().getAbsolutePath();
				if(!path.toLowerCase().endsWith(".gdi"))return;
				Graph g = ReadFile.readFileScanner(path);
				
				if(k.graph != null) {
					int result = JOptionPane.showConfirmDialog(null, "Soll der Graph in einem neuen Fenster geöffnet werden?");
					if(result == 2) return;
					if(result == 0) {
						Main.openNewFrame(g);
						return;
					}
				}
				
				setTitle("GDI Projekt - " +  g.getPath());
				k.setFile(g);
			}

		}
	}
}

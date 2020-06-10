package de.fhswf;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
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
		if (g != null)
			setTitle(getTitle() + " - " + g.getPath());
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
		add(k);

		// TestMenu
		JMenuBar menuBar;
		JMenu menu, edit, colorTheme;
		JMenuItem selectFile, exitWindow;
		JRadioButtonMenuItem radioButton1, radioButton2, radioButton3, radioButton4;

		menuBar = new JMenuBar();

		// File
		menu = new JMenu("File");

		selectFile = new JMenuItem("Select File");
		selectFile.addActionListener(this);
		selectFile.setActionCommand("selectFile");
		menu.add(selectFile);

		exitWindow = new JMenuItem("Exit");

		menu.addSeparator();

		menu.add(exitWindow);

		// Edit
		edit = new JMenu("Edit");

		// ColorTheme
		colorTheme = new JMenu("Appearance");

		// RadioButton 1
		ButtonGroup groupBackground = new ButtonGroup();

		radioButton1 = new JRadioButtonMenuItem("Grey");
		radioButton1.setSelected(true);
		radioButton1.addActionListener(this);
		radioButton1.setActionCommand("selectBackgroundGrey");
		groupBackground.add(radioButton1);
		colorTheme.add(radioButton1);

		// RadioButton 2
		radioButton2 = new JRadioButtonMenuItem("Black");
		radioButton2.setSelected(false);
		radioButton2.addActionListener(this);
		radioButton2.setActionCommand("selectBackgroundBlack");
		groupBackground.add(radioButton2);
		colorTheme.add(radioButton2);

		colorTheme.addSeparator();
		ButtonGroup groupGraph = new ButtonGroup();

		// RadioButton 3
		radioButton3 = new JRadioButtonMenuItem("White");
		radioButton3.setSelected(true);
		radioButton3.addActionListener(this);
		radioButton3.setActionCommand("selectGraphWhite");
		groupGraph.add(radioButton3);
		colorTheme.add(radioButton3);

		// RadioButton 4
		radioButton4 = new JRadioButtonMenuItem("Black");
		radioButton4.setSelected(false);
		radioButton4.addActionListener(this);
		radioButton4.setActionCommand("selectGraphBlack");
		groupGraph.add(radioButton4);
		colorTheme.add(radioButton4);

		edit.add(colorTheme);

		menuBar.add(menu);
		menuBar.add(edit);

		setJMenuBar(menuBar);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("selectFile")) {

			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("GDIDatei", "gdi");
			fc.setFileFilter(filter);

			fc.setCurrentDirectory(new java.io.File("C:/"));
			fc.setDialogTitle(".gdi Datei auswählen");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				if (k.graph != null) {
					int result = JOptionPane.showConfirmDialog(null,
							"Soll der Graph in einem neuen Fenster geöffnet werden?");
					if (result == 2)
						return;
					if (result == 0) {
						Main.openNewFrame(ReadFile.readFileScanner(fc.getSelectedFile().getAbsolutePath()));
						return;
					}
				}

				Graph g = ReadFile.readFileScanner(fc.getSelectedFile().getAbsolutePath());
				setTitle("GDI Projekt - " + g.getPath());
				k.setFile(g);
			}

		} else if (e.getActionCommand().equalsIgnoreCase("selectBackgroundGrey")) {
			setBackgroundColor(Colors.Grey);
		} else if (e.getActionCommand().equalsIgnoreCase("selectBackgroundBlack")) {
			setBackgroundColor(Colors.Black);
		} else if (e.getActionCommand().equalsIgnoreCase("selectGraphWhite")) {
			setBackgroundColor(Colors.Grey);
		} else if (e.getActionCommand().equalsIgnoreCase("selectGraphBlack")) {
			setBackgroundColor(Colors.Black);
		}
	}

	public void setBackgroundColor(Colors color) {
		getContentPane().setBackground(color.color);
	}
}

package de.fhswf;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
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

import de.fhswf.utils.Graph;

public class GUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	public GraphPainter k;

	private JMenuBar menuBar;
	private JMenu menu, edit, customColors;
	private JMenuItem selectFile, exitWindow, colorChooser, openWindow, backgroundcolor, graphcolor, fontcolor;

	private boolean allowCustomColors = false;

	public GUI(Graph g) {
		initWindow(g);
	}

	private void initWindow(Graph g) {
		setTitle("GDI Projekt");
		if (g != null)
			setTitle(getTitle() + " - " + g.getPath());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 500);
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

		k = new GraphPainter();
		k.setBounds(0, 0, 493, 500);
		if (g != null)
			k.setFile(g);
		k.setToolTipText("Platzhalter");
		add(k);

		menuBar = new JMenuBar();

		// File
		menu = new JMenu("File");

		selectFile = new JMenuItem("Select File", new ImageIcon("res/folder.png"));
		selectFile.addActionListener(this);
		selectFile.setActionCommand("selectFile");
		menu.add(selectFile);

		openWindow = new JMenuItem("New Window", new ImageIcon("res/newWindow.png"));
		openWindow.addActionListener(this);
		openWindow.setActionCommand("openWindow");
		menu.add(openWindow);

		menu.addSeparator();

		exitWindow = new JMenuItem("Exit");
		exitWindow.addActionListener(this);
		exitWindow.setActionCommand("exitWindow");
		menu.add(exitWindow);

		// --------------------------------- Edit -------------------------
		edit = new JMenu("Edit");

		customColors = new JMenu("Custom Colors");

		ButtonGroup group = new ButtonGroup();
		JRadioButtonMenuItem defaultTheme, darkTheme, wunderlandTheme, customTheme;

		defaultTheme = new JRadioButtonMenuItem("Default Color Theme");
		defaultTheme.setSelected(true);
		defaultTheme.addActionListener(this);
		defaultTheme.setActionCommand("defaultTheme");
		group.add(defaultTheme);
		edit.add(defaultTheme);

		darkTheme = new JRadioButtonMenuItem("Dark Color Theme");
		darkTheme.setSelected(false);
		darkTheme.addActionListener(this);
		darkTheme.setActionCommand("darkTheme");
		group.add(darkTheme);
		edit.add(darkTheme);

		wunderlandTheme = new JRadioButtonMenuItem("Wunderland Color Theme");
		wunderlandTheme.setSelected(false);
		wunderlandTheme.addActionListener(this);
		wunderlandTheme.setActionCommand("wunderlandTheme");
		group.add(wunderlandTheme);
		edit.add(wunderlandTheme);

		customTheme = new JRadioButtonMenuItem("Custom Color Theme");
		customTheme.setSelected(false);
		customTheme.addActionListener(this);
		customTheme.setActionCommand("customTheme");
		group.add(customTheme);
		edit.add(customTheme);

		edit.addSeparator();

		// ----------------------------- Custom ------------------------------
		edit.add(customColors);
		ImageIcon colorWheelIcon = new ImageIcon("res/colorWheel.gif");
		customColors.setIcon(colorWheelIcon);
		customColors.setEnabled(allowCustomColors);

		backgroundcolor = new JMenuItem("Backgroundcolor");
		backgroundcolor.addActionListener(this);
		backgroundcolor.setActionCommand("backgroundcolor");
		customColors.add(backgroundcolor);

		graphcolor = new JMenuItem("Graphcolor");
		graphcolor.addActionListener(this);
		graphcolor.setActionCommand("graphcolor");
		customColors.add(graphcolor);

		fontcolor = new JMenuItem("Fontcolor");
		fontcolor.addActionListener(this);
		fontcolor.setActionCommand("fontcolor");
		customColors.add(fontcolor);

		menuBar.add(menu);
		edit.add(edit);
		menuBar.add(edit);

		setJMenuBar(menuBar);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// ------------------------------- SelectFile ------------------------------
		if (e.getActionCommand().equalsIgnoreCase("selectFile")) {

			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("GDIDatei", "gdi");
			fc.setFileFilter(filter);

			fc.setCurrentDirectory(new File("."));
			fc.setDialogTitle(".gdi Datei auswählen");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				String path = fc.getSelectedFile().getAbsolutePath();
				if (!path.toLowerCase().endsWith(".gdi"))
					return;
				Graph g = ReadFile.readFileScanner(path);

				if (k.graph != null) {
					int result = JOptionPane.showConfirmDialog(null,
							"Soll der Graph in einem neuen Fenster geöffnet werden?");
					if (result == 2)
						return;
					if (result == 0) {
						Main.openNewFrame(g);
						return;
					}
				}

				setTitle("GDI Projekt - " + g.getPath());
				k.setFile(g);
			}

		}
		// ---------------------------- ColorThemes ------------------------
		else if (e.getActionCommand().equalsIgnoreCase("defaultTheme")) {
			allowCustomColors = false;
			customColors.setEnabled(allowCustomColors);
			Color backgroundColor = new Color(51, 51, 51);
			Color graphColor = Color.WHITE;
			Color fontColor = Color.BLACK;
			k.backgroundColor = backgroundColor;
			k.mainColor = graphColor;
			k.fontColor = fontColor;
			k.repaint();
		} else if (e.getActionCommand().equalsIgnoreCase("darkTheme")) {
			allowCustomColors = false;
			customColors.setEnabled(allowCustomColors);
			Color backgroundColor = Color.BLACK;
			Color graphColor = new Color(100, 100, 100);
			Color fontColor = Color.BLACK;
			k.backgroundColor = backgroundColor;
			k.mainColor = graphColor;
			k.fontColor = fontColor;
			k.repaint();
		} else if (e.getActionCommand().equalsIgnoreCase("wunderlandTheme")) {
			allowCustomColors = false;
			customColors.setEnabled(allowCustomColors);
			Color backgroundColor = new Color(43, 45, 66);
			Color graphColor = new Color(239, 35, 60);
			Color fontColor = new Color(237, 242, 244);
			k.backgroundColor = backgroundColor;
			k.mainColor = graphColor;
			k.fontColor = fontColor;
			k.repaint();
		} else if (e.getActionCommand().equalsIgnoreCase("customTheme")) {
			allowCustomColors = true;
			customColors.setEnabled(allowCustomColors);
		}
		// --------------------------------CustomColors--------------------------------
		else if (e.getActionCommand().equalsIgnoreCase("backgroundcolor") && allowCustomColors) {
			Color newColor = JColorChooser.showDialog(this, "Choose a Background Color", new Color(51, 51, 51));
			if (newColor != null) {
				k.backgroundColor = newColor;
				k.repaint();
			}
		} else if (e.getActionCommand().equalsIgnoreCase("graphcolor") && allowCustomColors) {
			Color newColor = JColorChooser.showDialog(this, "Choose a Graph Color", Color.WHITE);
			if (newColor != null) {
				k.mainColor = newColor;
				k.repaint();
			}
		} else if (e.getActionCommand().equalsIgnoreCase("fontcolor") && allowCustomColors) {
			Color newColor = JColorChooser.showDialog(this, "Choose Font Color", Color.BLACK);
			if (newColor != null) {
				k.fontColor = newColor;
				k.repaint();
			}
		} else if (e.getActionCommand().equalsIgnoreCase("openWindow") && allowCustomColors) {
			Main.openNewFrame(null);
		} else if (e.getActionCommand().equalsIgnoreCase("exitWindow")) {
			// Exit Windows hinzufuegen
		}
	}
}

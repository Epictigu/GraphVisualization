package de.fhswf;

public class Main {

//	  TODO:
//	  FileSystem					Gin-Wah, Pascal
//			Dekodieren				Gin-Wah, Pascal
//	  JFrame anlegen				Gin-Wah
//			UI
//	  Zugriff auf Dateiexplorer		Pascal
//	  Zeichnen von Knoten/Kanten	Timo, Dominik
//	  		Pixelberechnung			Timo, Dominik

	public static void main(String[] args) {
		GUI gui = new GUI();
		
		while(true) {
			gui.k.degrees++;
			if(gui.k.degrees >=360)gui.k.degrees-=360;
			gui.repaint();
			try {
				Thread.sleep(10l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

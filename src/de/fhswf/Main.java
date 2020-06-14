package de.fhswf;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static List<GUI> guiList = new ArrayList<GUI>();
	
	public static void main(String[] args) {
		openNewFrame(null);
	}
	
	public static void openNewFrame(Graph g) {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				guiList.add(new GUI(g));
			}
		};
		
		new Thread(r).start();
	}

}

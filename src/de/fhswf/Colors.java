package de.fhswf;

import java.awt.Color;

public enum Colors {
	Grey(new Color(51, 51, 51)), White(Color.WHITE), Black(Color.BLACK), Red(Color.RED), Green(Color.GREEN),
	Blue(Color.BLUE);

	public Color color;

	private Colors(Color color) {
		this.color = color;
	}
}

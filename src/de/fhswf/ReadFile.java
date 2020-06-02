package de.fhswf;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ReadFile
{
	public ReadFile()
	{

	}

	public Graph readFileScanner(String filename)
	{
		Graph graph = new Graph();
		try
		{
			File file = new File(filename);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				decodeLine(line, graph);
			}
			scanner.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return graph;
	}

	public Graph readFileBufferdReader(String filename) throws IOException
	{
		Graph graph = new Graph();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try
		{
			String line = br.readLine();

			while (line != null)
			{
				line = br.readLine();
				decodeLine(line, graph);
			}
		} finally
		{
			br.close();
		}
		return graph;
	}

	// Abbildung_22_3.gdi
	// 1. G = Graph Zahl = anzahl der Knoten

	// 2. V = Knoten
	// 1.Zahl = Nummer des Knotens
	// Zahl/Buchstabe = bennenung desKnotens

	// 3. E = Kante
	// 1.Zahl = Nummer des "Start Knotens"
	// 2.Zahl = Zielknoten der Kante
	// 3.Zahl = PH fuer gerichtet/ungerichtet

	private void decodeLine(String line, Graph graph)
	{
		if (!line.isEmpty())
		{
			String[] array = line.split(" ");

			if (array[0].equals("G"))
			{
				int amountKnots = Integer.parseInt(array[1]);

				graph.init(amountKnots);
			} else if (array[0].equals("V"))
			{
				int indexKnots = Integer.parseInt(array[1]);
				String name = array[2];
				name = name.substring(1, name.length());

				graph.writeToKnotNames(indexKnots, name);
			} else if (array[0].equals("E"))
			{
				int beginningLine = Integer.parseInt(array[1]);
				int endLine = Integer.parseInt(array[2]);
				// int i = Integer.parseInt(array[1]); Platzhalter

				graph.writeLineToAdjacencyMatrix(beginningLine, endLine);
			}
		}
	}

}
package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.BreakoutBlitz;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	static int width = BreakoutBlitz.width;
	static int height = BreakoutBlitz.width;

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);

		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("D:\\Coding\\Breakout-Blitz\\core\\src\\com\\mygdx\\game\\utils\\ScreenSize.txt"));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		int size[] = new int[10];
		int i = 0;
		while (scanner.hasNextInt()) {
			size[i++] = scanner.nextInt();
		}
		width = size[0];
		height = (int) ((double) width * 2 / 3);

		config.setWindowedMode(width, height);
		config.setTitle("BreakoutBlitz");
		new Lwjgl3Application(new BreakoutBlitz(), config);
	}
}

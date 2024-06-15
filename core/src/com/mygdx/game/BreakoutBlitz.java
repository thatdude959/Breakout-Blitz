package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import screens.TitleScreen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BreakoutBlitz extends Game {
    public static int width;
    public static int height;
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public BitmapFont font;
    public static int[] size = new int[100];

    @Override
    public void create() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("D:\\Coding\\Breakout-Blitz\\core\\src\\com\\mygdx\\game\\utils\\ScreenSize.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int i = 0;
        while (scanner.hasNextInt()) {
            size[i++] = scanner.nextInt();
        }
        width = size[0];
        height = (int) ((double) width * 2 / 3);
        Gdx.graphics.setWindowedMode(width, height);

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.getData().setScale(1f,1f);

        setScreen(new TitleScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
}

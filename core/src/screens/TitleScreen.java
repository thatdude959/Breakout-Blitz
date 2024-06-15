package screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.BreakoutBlitz;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class TitleScreen extends ScreenAdapter {
    BreakoutBlitz game;
    private Skin skin;
    private Stage stage;
    private OrthographicCamera camera;

    public TitleScreen(BreakoutBlitz game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new GameScreen(game));
                } else if (keyCode == Input.Keys.ESCAPE) {
                    SettingsFrame fDialog = new SettingsFrame();
                    Writer wr = null;
                    try {
                        wr = new FileWriter("D:\\Coding\\Breakout-Blitz\\core\\src\\com\\mygdx\\game\\utils\\ScreenSize.txt");
                        wr.write("" + fDialog.choice);
                        wr.close();
                        Gdx.graphics.setWindowedMode(BreakoutBlitz.width, BreakoutBlitz.height);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                return true;
            }
        });
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 1080;
        if ((double) width / (double) height < 1.5) {
            camera.viewportHeight = (float) (camera.viewportWidth * (double) height / (double) width);
        } else if ((double) width / (double) height > 1.5) {
            camera.viewportWidth = (float) ((camera.viewportHeight * (double) width) / (double) height);
        }
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch, "Title Screen!", camera.viewportWidth / 2, camera.viewportHeight * .75f);
        game.font.draw(game.batch, "Esc for settings", camera.viewportWidth / 2, camera.viewportHeight / 2);
        game.font.draw(game.batch, "Press space to play.", camera.viewportWidth / 2, camera.viewportHeight * .25f);
        game.batch.end();
    }
}

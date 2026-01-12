package com.nous.snake;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

// cette classe est le Menu principal du jeu Permet : 
//démarrer le jeu 
//activer/désactiver le son 
//choisir un niveau 
//quitter 
public class MenuScreen implements Screen {

    private final SnakeGame game;
    private SpriteBatch batch;
    private BitmapFont titleFont;
    private BitmapFont menuFont;

    private int selected = 0;

    private final String[] options = {
            "Commencer le jeu",
            "Sound : ",
            "Level",
            "Quitter"
    };

    public MenuScreen(SnakeGame game) {
        this.game = game;
        batch = new SpriteBatch();

        titleFont = new BitmapFont();
        titleFont.setColor(Color.GREEN);
        titleFont.getData().setScale(3f);

        menuFont = new BitmapFont();
        menuFont.setColor(Color.WHITE);
        menuFont.getData().setScale(1.5f);
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            selected = (selected + 1) % options.length;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            selected = (selected - 1 + options.length) % options.length;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (selected) {
                case 0:
                    game.setScreen(new GameScreen(game, 0));
                    break;
                case 1:
                    Settings.soundEnabled = !Settings.soundEnabled;
                    break;
                case 2:
                    game.setScreen(new LevelSelectScreen(game));
                    break;
                case 3:
                    Gdx.app.exit();
                    break;
            }
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        float cx = Gdx.graphics.getWidth() / 2f;
        float cy = Gdx.graphics.getHeight() / 2f;

        titleFont.draw(batch, "SNAKE GAME", cx - 180, cy + 200);

        for (int i = 0; i < options.length; i++) {
            String text = options[i];
            if (i == 1) text += (Settings.soundEnabled ? "ON" : "OFF");

            menuFont.setColor(i == selected ? Color.YELLOW : Color.WHITE);
            menuFont.draw(batch, text, cx - 150, cy + 60 - i * 40);
        }

        batch.end();
    }

    @Override public void dispose() {
        batch.dispose();
        titleFont.dispose();
        menuFont.dispose();
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
}

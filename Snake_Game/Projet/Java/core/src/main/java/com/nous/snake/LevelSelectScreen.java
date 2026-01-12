package com.nous.snake;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

//cette classe est le  
// Menu de sélection des niveaux 
//Gère les niveaux verrouillés

public class LevelSelectScreen implements Screen {

    private final SnakeGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private int selected = 0;

    private final String[] levels = {
            "Level 1",
            "Level 2",
            "Level 3",
            "Retour"
    };

    public LevelSelectScreen(SnakeGame game) {
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(1.5f);
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            selected = (selected + 1) % levels.length;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            selected = (selected - 1 + levels.length) % levels.length;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {

            if (selected == 3) {
                game.setScreen(new MenuScreen(game));
                return;
            }

            if (Progress.isUnlocked(selected)) {
                game.setScreen(new GameScreen(game, selected));
            }
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        float cx = Gdx.graphics.getWidth() / 2f;
        float cy = Gdx.graphics.getHeight() / 2f;

        for (int i = 0; i < levels.length; i++) {
            boolean locked = (i < 3 && !Progress.isUnlocked(i));
            font.setColor(i == selected ? Color.YELLOW : (locked ? Color.GRAY : Color.WHITE));
            font.draw(batch, levels[i] + (locked ? " (Locked)" : ""),
                    cx - 120, cy + 60 - i * 40);
        }

        batch.end();
    }

    @Override public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
}

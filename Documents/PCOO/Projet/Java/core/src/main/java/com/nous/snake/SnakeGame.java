package com.nous.snake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class SnakeGame extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }

    @Override
    public void render() {
        super.render();  // très important !!
    }

    @Override
    public void dispose() {
        if (getScreen() != null) getScreen().dispose();
    }
}

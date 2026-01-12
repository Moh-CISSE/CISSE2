package com.nous.snake;

import com.badlogic.gdx.Game;
//cette classe est le “main” du jeu LibGDX 
//Démarre le jeu 
//Gère les écrans 
public class SnakeGame extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        if (getScreen() != null) getScreen().dispose();
    }
}

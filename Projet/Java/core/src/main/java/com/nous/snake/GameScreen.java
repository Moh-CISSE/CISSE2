package com.nous.snake;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;

public class GameScreen implements Screen {
   

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private TiledGameMap gameMap;
    private SpriteBatch batch;

    private float timer = 0;
    private final float SPEED = 0.2f;

    public GameScreen() {
        gameMap = new TiledGameMap("maps/plane.tmx");

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                gameMap.getMapWidthPixels(),
                gameMap.getMapHeightPixels());

        camera.position.set(
                camera.viewportWidth / 2f,
                camera.viewportHeight / 2f,
                0
        );
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(gameMap.getMap());
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();

        timer += delta;
        if (timer >= SPEED) {
            gameMap.snake.move();
            timer = 0;

            // === COLLISIONS ===
            int tileX = (int)(gameMap.snake.getX() / 32);
            int tileY = (int)(gameMap.snake.getY() / 32);

           
        }

        camera.update();
        renderer.setView(camera);
        renderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        gameMap.snake.draw(batch);
        batch.end();
   
       


    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            gameMap.snake.setDirection(Snake.Direction.UP);
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            gameMap.snake.setDirection(Snake.Direction.DOWN);
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
            gameMap.snake.setDirection(Snake.Direction.LEFT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
            gameMap.snake.setDirection(Snake.Direction.RIGHT);
    }

    private void restartLevel() {
        gameMap = new TiledGameMap("maps/plane.tmx");
    }

    @Override public void resize(int w, int h) {}
    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();
    }
}

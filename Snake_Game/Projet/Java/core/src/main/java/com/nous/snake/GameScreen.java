package com.nous.snake;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

//t cette classe est le cœur du jeu 
//Elle gère :le gameplay, les collisions, le score, la pause, le Game Over, la victoire, le son, les niveaux 
public class GameScreen implements Screen {

    private final SnakeGame game;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private SpriteBatch batch;
    private TiledGameMap gameMap;

    private float timer = 0f;
    private float speed = 0.5f;

    private boolean paused = false;
    private boolean gameOver = false;
    private boolean victory = false;

    private int score = 0;
    private int totalFruits = 0;

    //  Pause menu
    private int pauseSelected = 0;
    private final String[] pauseOptions = {
            "Reprendre",
            "Sound : ",
            "Menu principal",
            "Quitter"
    };

    //  Levels
    private final String[] LEVELS = {
            "maps/plane.tmx",
            "maps/plane1.tmx",
            "maps/plane2.tmx"
    };
    private int currentLevel;

    //  UI
    private BitmapFont scoreFont;
    private BitmapFont bigFont;

    //  Audio
    private Sound eatSound;
    private Sound gameOverSound;
    private Sound winSound;
    private Music backgroundMusic;

    public GameScreen(SnakeGame game, int startLevel) {
        this.game = game;
        this.currentLevel = startLevel;

        batch = new SpriteBatch();

        // Sons
        eatSound = Gdx.audio.newSound(Gdx.files.internal("sounds/eat.wav"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.wav"));
        winSound = Gdx.audio.newSound(Gdx.files.internal("sounds/win.wav"));

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/bg.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(Settings.volume);

        scoreFont = new BitmapFont();
        scoreFont.setColor(Color.WHITE);
        scoreFont.getData().setScale(1.5f);

        bigFont = new BitmapFont();
        bigFont.setColor(Color.RED);
        bigFont.getData().setScale(3f);

        loadLevel(currentLevel);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null);
        if (Settings.soundEnabled && backgroundMusic != null) backgroundMusic.play();
    }

    @Override
    public void render(float delta) {

        //  Menu principal
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (backgroundMusic != null) backgroundMusic.stop();
            game.setScreen(new MenuScreen(game));
            return;
        }

        //  GAME OVER 
        if (gameOver) {
            if (!victory && Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                
                loadLevel(currentLevel);
                return;
            }

            //  passe seulement si le prochain niveau est débloqué
            if (victory && Gdx.input.isKeyJustPressed(Input.Keys.N)) {
                int nextLevel = Math.min(currentLevel + 1, LEVELS.length - 1);

                if (Progress.isUnlocked(nextLevel)) {
                    currentLevel = nextLevel;
                    loadLevel(currentLevel);
                }
                return;
            }
        }

        //  Pause
        if (Gdx.input.isKeyJustPressed(Input.Keys.P) && !gameOver) {
            paused = !paused;
            pauseSelected = 0;
        }

        // Navigation menu pause
        if (paused && !gameOver) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
                pauseSelected = (pauseSelected + 1) % pauseOptions.length;

            if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                pauseSelected = (pauseSelected - 1 + pauseOptions.length) % pauseOptions.length;

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                switch (pauseSelected) {
                    case 0: // Reprendre
                        paused = false;
                        break;

                    case 1: // Sound
                        Settings.soundEnabled = !Settings.soundEnabled;
                        if (backgroundMusic != null) {
                            if (Settings.soundEnabled) backgroundMusic.play();
                            else backgroundMusic.stop();
                        }
                        break;

                    case 2: // Menu principal
                        if (backgroundMusic != null) backgroundMusic.stop();
                        game.setScreen(new MenuScreen(game));
                        return;

                    case 3: // Quitter
                        Gdx.app.exit();
                        return;
                }
            }
        }

        // clear écran
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update jeu
        if (!paused && !gameOver) {
            handleInput();
            updateGame(delta);
        }

        // render map
        camera.update();
        renderer.setView(camera);
        renderer.render();

        // render snake + UI
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        gameMap.snake.draw(batch);

        // Score
        drawBoldText(scoreFont, batch,
                "Score : " + score + " / " + totalFruits,
                camera.position.x - camera.viewportWidth / 2 + 10,
                camera.position.y + camera.viewportHeight / 2 - 10
        );

        //  MENU PAUSE
        if (paused && !gameOver) {
            float cx = camera.position.x;
            float cy = camera.position.y;

            drawBoldText(bigFont, batch, "PAUSE", cx - 90, cy + 100);

            for (int i = 0; i < pauseOptions.length; i++) {
                String text = pauseOptions[i];
                if (i == 1) text += (Settings.soundEnabled ? "ON" : "OFF");

                scoreFont.setColor(i == pauseSelected ? Color.YELLOW : Color.WHITE);
                scoreFont.draw(batch, text, cx - 120, cy + 40 - i * 35);
            }
            scoreFont.setColor(Color.WHITE);
        }

        //  FIN DE JEU
        if (gameOver) {
            drawBoldText(scoreFont, batch,
                    "Final Score : " + score + " / " + totalFruits,
                    camera.position.x - 160,
                    camera.position.y
            );

            if (victory) {
                drawBoldText(bigFont, batch, "YOU WIN !",
                        camera.position.x - 120,
                        camera.position.y + 60);

                drawBoldText(scoreFont, batch,
                        "Press N for next level",
                        camera.position.x - 160,
                        camera.position.y - 40);
            } else {
                drawBoldText(bigFont, batch, "GAME OVER",
                        camera.position.x - 140,
                        camera.position.y + 60);

                drawBoldText(scoreFont, batch,
                        "Press R to restart (Level 0)",
                        camera.position.x - 190,
                        camera.position.y - 40);
            }
        }

        batch.end();
    }

    private void updateGame(float delta) {
        timer += delta;

        if (timer >= speed) {
            gameMap.snake.move();

            int x = (int) ((gameMap.snake.getHeadX() + Snake.TILE_SIZE / 2f) / Snake.TILE_SIZE);
            int y = (int) ((gameMap.snake.getHeadY() + Snake.TILE_SIZE / 2f) / Snake.TILE_SIZE);


            if (gameMap.isBlocked(x, y) || gameMap.snake.collidesWithSelf()) {
                if (Settings.soundEnabled) gameOverSound.play(Settings.volume);
                backgroundMusic.stop();
                gameOver = true;
            }

            if (gameMap.hasFruit(x, y)) {
                gameMap.removeFruit(x, y);
                gameMap.snake.grow();
                score++;
                if (Settings.soundEnabled) eatSound.play(Settings.volume);
            }

            if (score >= totalFruits && totalFruits > 0) {
                if (Settings.soundEnabled) winSound.play(Settings.volume);
                backgroundMusic.stop();
                victory = true;
                gameOver = true;
                Progress.unlockNextLevel(currentLevel);
            }

            timer = 0;
        }
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

    private void loadLevel(int index) {
        gameMap = new TiledGameMap(LEVELS[index]);

        if (renderer != null) renderer.dispose();
        renderer = new OrthogonalTiledMapRenderer(gameMap.getMap());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, gameMap.getMapWidthPixels(), gameMap.getMapHeightPixels());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        score = 0;
        totalFruits = gameMap.countFruits();
        timer = 0f;
        paused = false;
        gameOver = false;
        victory = false;

        // vitesse par niveau
        speed = (index == 0) ? 0.25f : (index == 1) ? 0.20f : 0.10f;

        if (Settings.soundEnabled && backgroundMusic != null && !backgroundMusic.isPlaying())
            backgroundMusic.play();
    }

    private void drawBoldText(BitmapFont font, SpriteBatch batch, String text, float x, float y) {
        font.draw(batch, text, x, y);
        font.draw(batch, text, x + 1, y);
        font.draw(batch, text, x, y + 1);
        font.draw(batch, text, x + 1, y + 1);
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (renderer != null) renderer.dispose();
        if (scoreFont != null) scoreFont.dispose();
        if (bigFont != null) bigFont.dispose();
        if (eatSound != null) eatSound.dispose();
        if (gameOverSound != null) gameOverSound.dispose();
        if (winSound != null) winSound.dispose();
        if (backgroundMusic != null) backgroundMusic.dispose();
    }
}

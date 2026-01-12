package com.nous.snake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.LinkedList;
// cette classe  Représente le serpent Gère : 
//déplacement 
//croissance 
//collision avec lui-même 
//affichage
public class Snake extends Entity {
    private boolean growNextMove = false;
    private Texture bodyVertical;
    private Texture bodyHorizontal;


private Texture turnHR, turnHL, turnDR, turnDL;

    public enum Direction { UP, DOWN, LEFT, RIGHT }

    public static final int TILE_SIZE = 32;

    private Direction direction = Direction.RIGHT;
    private Direction nextDirection = Direction.RIGHT;

    private Texture headUp, headDown, headLeft, headRight;
    private Texture tailUp, tailDown, tailLeft, tailRight;

    private LinkedList<float[]> segments = new LinkedList<>();

    public Snake(float x, float y) {
        this.x = x;
        this.y = y;
        width = height = TILE_SIZE;
       
       turnHR = new Texture("tilesets/tourne_hr.png");
       turnHL = new Texture("tilesets/tourne_hl.png");
       turnDR = new Texture("tilesets/tourne_dr.png");
       turnDL = new Texture("tilesets/tourne_dl.png");

       
        bodyVertical = new Texture("tilesets/corps_a.png");
        bodyHorizontal = new Texture("tilesets/corps_v.png");
  
        headUp    = new Texture("tilesets/head_h.png");
        headDown  = new Texture("tilesets/head_b.png");
        headLeft  = new Texture("tilesets/head_g.png");
        headRight = new Texture("tilesets/head_d.png");

        tailUp    = new Texture("tilesets/til_h.png");
        tailDown  = new Texture("tilesets/til_b.png");
        tailLeft  = new Texture("tilesets/til_g.png");
        tailRight = new Texture("tilesets/til_d.png");

        segments.add(new float[]{x, y});
        segments.add(new float[]{x - TILE_SIZE, y});
    }

    public void setDirection(Direction newDir) {
        if ((direction == Direction.UP && newDir == Direction.DOWN) ||
            (direction == Direction.DOWN && newDir == Direction.UP) ||
            (direction == Direction.LEFT && newDir == Direction.RIGHT) ||
            (direction == Direction.RIGHT && newDir == Direction.LEFT)) {
            return;
        }
        nextDirection = newDir;
    }

    public void move() {
    direction = nextDirection;

    float[] head = segments.getFirst();
    float newX = head[0];
    float newY = head[1];

    if (direction == Direction.UP) newY += TILE_SIZE;
    if (direction == Direction.DOWN) newY -= TILE_SIZE;
    if (direction == Direction.LEFT) newX -= TILE_SIZE;
    if (direction == Direction.RIGHT) newX += TILE_SIZE;

    segments.addFirst(new float[]{newX, newY});

    //  SEULEMENT si on ne grandit pas
    if (!growNextMove) {
        segments.removeLast();
    } else {
        growNextMove = false; 
    }

    this.x = newX;
    this.y = newY;
}


   public void grow() {
    growNextMove = true;
}

    public boolean collidesWithSelf() {
        float[] head = segments.getFirst();
        for (int i = 1; i < segments.size(); i++) {
            float[] s = segments.get(i);
            if (head[0] == s[0] && head[1] == s[1]) return true;
        }
        return false;
    }

public void draw(SpriteBatch batch) {

    //  TÊTE
    float[] head = segments.getFirst();
    batch.draw(getHeadTexture(), head[0], head[1]);

    //  CORPS 
   
  for (int i = 1; i < segments.size() - 1; i++) {

    float[] prev = segments.get(i - 1);
    float[] curr = segments.get(i);
    float[] next = segments.get(i + 1);

    float dx1 = curr[0] - prev[0];
    float dy1 = curr[1] - prev[1];
    float dx2 = next[0] - curr[0];
    float dy2 = next[1] - curr[1];

    Texture bodyTex;

    // ligne droite
    if (dx1 == 0 && dx2 == 0) {
        bodyTex = bodyVertical;
    }
    else if (dy1 == 0 && dy2 == 0) {
        bodyTex = bodyHorizontal;
    }

    // virages 
    else if (
        (dx1 > 0 && dy2 > 0) || (dy1 < 0 && dx2 < 0)
    ) {
        bodyTex = turnHL;
    }
    else if (
        (dx1 < 0 && dy2 > 0) || (dy1 < 0 && dx2 > 0)
    ) {
        bodyTex = turnHR;
    }
    else if (
        (dx1 < 0 && dy2 < 0) || (dy1 > 0 && dx2 > 0)
    ) {
        bodyTex = turnDR;
    }
    else {
        bodyTex = turnDL;
    }

    batch.draw(bodyTex, curr[0], curr[1]);
}


    // QUEUE
    float[] tail = segments.getLast();
    batch.draw(getTailTexture(), tail[0], tail[1]);
}


    private Texture getHeadTexture() {
        if (direction == Direction.UP) return headUp;
        if (direction == Direction.DOWN) return headDown;
        if (direction == Direction.LEFT) return headLeft;
        return headRight;
    }


 private Texture getTailTexture() {
    if (segments.size() < 2) return tailRight;

    float[] tail = segments.getLast();
    float[] beforeTail = segments.get(segments.size() - 2);

    float dx = beforeTail[0] - tail[0];
    float dy = beforeTail[1] - tail[1];

    if (dx > 0) return tailRight;
    if (dx < 0) return tailLeft;
    if (dy > 0) return tailUp;
    return tailDown;
}



    public float getHeadX() {
    return segments.getFirst()[0];
}

public float getHeadY() {
    return segments.getFirst()[1];
}



    public Direction getDirection() { return direction; }

    @Override
    public boolean collidesWith(float px, float py) { return false; }
}

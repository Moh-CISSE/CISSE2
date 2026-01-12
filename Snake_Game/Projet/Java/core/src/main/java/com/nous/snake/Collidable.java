package com.nous.snake;
// cette interface ? 
//Définit tout objet pouvant entrer en collision 
public interface Collidable {
    boolean collidesWith(float x, float y);
}

package com.dfrttkj.FluidSim2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Particle {
    public static float radius = 5;
    public Vector2 pos;
    public float density;
    public Vector2 velocity;
    // public Vector2 acceleration;

    public Particle(float posX, float posY) {
        this.pos = new Vector2(posX, posY);
        this.density = 0;
        this.velocity = new Vector2();
    }

    public void draw(ShapeRenderer renderer) {
        // System.out.println(pos.toString());
        renderer.circle(pos.x, pos.y, radius);
    }

    public void resolveCollisions() {
        if (pos.x < 0 || pos.x > Gdx.graphics.getHeight()) {
            pos.x = MathUtils.clamp(pos.x, 0, Gdx.graphics.getHeight());
            velocity.x *= -.1f;
        }
        if (pos.y < 0 || pos.y > Gdx.graphics.getWidth()) {
            pos.y = MathUtils.clamp(pos.y, 0, Gdx.graphics.getWidth());
            velocity.y *= -.1f;
        }
    }

    public Vector2 getPos() {
        return pos;
    }
}

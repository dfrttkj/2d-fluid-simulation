package com.dfrttkj.FluidSim2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Particle {
    public static float radius = 10;
    public Vector2 pos;

    public Vector2 velocity;
    // public Vector2 acceleration;

    public Particle(float posX, float posY) {
        this.pos = new Vector2(posX, posY);
        this.velocity = new Vector2((float) Math.random(), (float) Math.random());
        this.velocity.scl(50);

    }

    public void draw(ShapeRenderer renderer) {
        logic();
        renderer.circle(pos.x, pos.y, radius);
    }

    private void logic() {
        float delta = Gdx.graphics.getDeltaTime();
        pos.add(velocity.cpy().scl(delta));
        if (pos.x < 0 || pos.x > 500) {
            velocity.x *= -1;
        }
        if (pos.y < 0 || pos.y > 500) {
            velocity.y *= -1;
        }
    }
}

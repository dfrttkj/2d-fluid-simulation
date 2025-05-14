package com.dfrttkj.FluidSim2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Particle {
    Vector2 position;
    Vector2 velocity;
    Vector2 force;

    float density;
    float pressure;

    public Particle(float x, float y) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2();
        this.force = new Vector2();
        this.density = Main.REST_DENSITY;
        this.pressure = 0;
    }

    public void applyForce(Vector2 f) { // f = force
        this.force.add(f);
    }

    public void integrate(float dt) { // dt = delta time
        // Semi-implicit Euler
        this.velocity.add(this.force.cpy().scl(dt / this.density));
        this.position.add(this.velocity.cpy().scl(dt));

        // Simple boundary conditions
        if (this.position.x < Main.PARTICLE_RADIUS) {
            this.velocity.x *= -.5f;
            this.position.x = Main.PARTICLE_RADIUS;
        }
        if (this.position.x > (Main.WINDOW_WIDTH - Main.PARTICLE_RADIUS)) {
         this.velocity.x *= -.5f;
         this.position.x = (Main.WINDOW_WIDTH - Main.PARTICLE_RADIUS);
        }

        if (this.position.y < Main.PARTICLE_RADIUS) {
         this.velocity.y *= -.5f;
         this.position.y = Main.PARTICLE_RADIUS;
        }
        if (this.position.y > (Main.WINDOW_HEIGHT - Main.PARTICLE_RADIUS)) {
         this.velocity.y *= -.5f;
         this.position.y = (Main.WINDOW_HEIGHT - Main.PARTICLE_RADIUS);
        }
    }

    public void draw(ShapeRenderer renderer) {
        renderer.circle(this.position.x, this.position.y, Main.PARTICLE_RADIUS);
    }
}

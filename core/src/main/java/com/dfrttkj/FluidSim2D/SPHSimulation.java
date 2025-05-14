package com.dfrttkj.FluidSim2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SPHSimulation {
    List<Particle> particles;

    public SPHSimulation() {
        particles = new ArrayList<>(Main.NUM_PARTICLES);
        // Initialize particles in a block
        for (int i = 0; i < Main.NUM_PARTICLES; i++) {
            // random() * (max-min) + min)
            float x = (float) (Math.random() * (Main.WINDOW_WIDTH - Main.PARTICLE_RADIUS) + Main.PARTICLE_RADIUS);
            float y = (float) (Math.random() * (Main.WINDOW_HEIGHT - Main.PARTICLE_RADIUS) + Main.PARTICLE_RADIUS);
            particles.add(new Particle(x, y));
        }
    }

    public void computeDensityPressure() {
        Vector2 rij; // distance between p_i and P_j
        float r2; // r2 = rij.x^2 + rij.y^2

        for (Particle p_i : particles) {
            p_i.density = 0;
            for (Particle p_j : particles) {
                rij = p_i.position.cpy().sub(p_j.position);
                r2 =  rij.x*rij.x + rij.y*rij.y;

                if (r2 < Main.H2) {
                    p_i.density += (float) (Main.POLY6 * Math.pow((Main.H2 - r2), 3));
                }
            }

            p_i.density *=  1; // mult by particle mass
            p_i.pressure = Main.GAS_CONSTANT * (p_i.density - Main.REST_DENSITY);
        }
    }

    public void computeForces() {
        Vector2 f_pressure = new Vector2();
        Vector2 f_viscosity = new Vector2();
        Vector2 rij; // distance between p_i and p_j
        float r;
        Vector2 grad;
        float pressure_term;
        Vector2 vel_diff;
        Vector2 f_gravity;

        for (Particle p_i : particles) {
            f_pressure.set(0, 0);
            f_viscosity.set(0, 0);

            for (Particle p_j : particles) {
                if (Objects.equals(p_i, p_i)) continue;

                rij = p_i.position.cpy().sub(p_j.position);
                r = rij.len();

                if (r < Main.H) {
                    // Pressure fore contribution
                    grad = rij.cpy().nor().scl((float) Math.pow(Main.SPIKY_GRAD * (Main.H - r), 2));
                    pressure_term = (p_i.pressure + p_j.pressure) / (2 * p_j.density);
                    f_pressure.sub(grad.cpy().scl(pressure_term));

                    // Viscosity force contribution
                    vel_diff = p_j.velocity.cpy().sub(p_i.velocity);
                    f_viscosity.add(vel_diff.cpy().scl(Main.VISC_LAPL * (Main.H - r)));
                }
            }
            // Gravity
            f_gravity = Main.GRAVITY.cpy().scl(p_i.density);

            // Sum forces
            p_i.applyForce(f_pressure.add(f_viscosity.scl(Main.VISCOSITY)).add(f_gravity));
        }
    }

    public void step(float dt) { // dt = delta time
        // Reset forces
        for (Particle p : particles) {
            p.force.set(0, 0);
        }

        computeDensityPressure();
        computeForces();

        // Integrate motion
        for (Particle p : particles) {
            p.integrate(dt);
        }
    }

    public void render() {
        // clearWindow()  // assumed function to clear the 500×500 window
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Main.renderer.begin(ShapeRenderer.ShapeType.Filled);
        Main.renderer.setColor(Color.CYAN);

        for (Particle p : particles) {
            p.draw(Main.renderer);
        }

        Main.renderer.end();
    }
}


package com.dfrttkj.FluidSim2D;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    int numParticles = 529;
    float mass = 1f;
    float gravity = 10;

    float targetDensity = 3;
    float pressureMultiplier = 50;
    float smoothingRadius = 20;

    float[] densities = new float[numParticles];

    Random rand;

    ShapeRenderer shape;
    ArrayList<Particle> points;

    @Override
    public void create() {

        this.rand = new Random(0);

        this.shape = new ShapeRenderer();
        this.points = new ArrayList<>(numParticles);
        for (int i = 0; i < Math.sqrt(numParticles); i++) {
            for (int j = 0; j < Math.sqrt(numParticles); j++) {
                points.add(new Particle(i*smoothingRadius * 0.99f, j*smoothingRadius));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setTitle("FPS: " + Gdx.graphics.getFramesPerSecond());

        float deltaTime = Gdx.graphics.getDeltaTime();

        for (int i = 0; i < numParticles; i++) {
            points.get(i).velocity.y -= gravity * deltaTime; // apply Gravity
            points.get(i).density = CalculateDensity(i); // Update Densitys
        }

        for (int i = 0; i < numParticles; i++) {
            Vector2 pressureForce = CalculatePressureForce(i);
            Vector2 pressureAcceleration = pressureForce.cpy().scl(1/points.get(i).density);
            points.get(i).velocity.add(pressureAcceleration.scl(deltaTime));
        }

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.RED);

        for (Particle P : points) {
            P.pos.add(P.velocity.cpy().scl(deltaTime));
            P.resolveCollisions();
            P.draw(shape);
        }

        shape.end();
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        // Destroy application's resources here.
    }


    public float ConvertDensitieToPressure(float density) {
        float densityError = density - targetDensity;
        float pressure = densityError * pressureMultiplier;
        return pressure;
    }

    public float CalculateSharedPressure(float densityA, float densityB) {
        float PressureA = ConvertDensitieToPressure(densityA);
        float PressureB = ConvertDensitieToPressure(densityB);
        return (PressureA + PressureB) / 2;
    }

    public float SmoothingKernel(float radius, float dst) {
        if (dst >= radius) return 0;

        float volume = (float) ((Math.PI + Math.pow(radius, 4)) / 6);
        return (radius - dst) * (radius - dst) / volume;
    }

    public float SmoothingKernelDerivative(float radius, float dst) {
        if (dst >= radius) return 0;

        float volume = (float)((Math.PI + Math.pow(radius,4)) / 6);
        // derivative of (h - r)^2  is  -2*(h - r)
        return -2 * (radius - dst) / volume;
    }

    public float CalculateDensity(int idx) {
        Particle pi = points.get(idx);
        float density = 0;

        for (int j = 0; j < numParticles; j++) {
            Particle pj = points.get(j);
            Vector2 offset = pj.pos.cpy().sub(pi.pos);
            float r = offset.len();

            if (r < smoothingRadius) {
                density += mass * SmoothingKernel(smoothingRadius, r);
            }
        }

        return density;
    }

    public Vector2 CalculatePressureForce(int ParticleIndex) {
        Vector2 pressureForce = new Vector2();

        for (int i = 0; i < numParticles; i++) {
            if (ParticleIndex == i) { continue; }
            Vector2 point = points.get(i).getPos();

            Vector2 offset = point.cpy().sub(points.get(ParticleIndex).getPos());
            float dst = offset.len();
            Vector2 dir = dst == 0 ? offset.cpy().add(rand.nextFloat()-.5f, rand.nextFloat()-.5f) : offset.cpy().scl(1 / dst);

            float slope = SmoothingKernelDerivative(smoothingRadius, dst);
            float density = points.get(i).density;
            float sharedPressure = CalculateSharedPressure(density, points.get(i).density);
            pressureForce.add(dir.cpy().scl( sharedPressure * slope * mass / density));
        }

        return pressureForce.scl(-1);
    }
}

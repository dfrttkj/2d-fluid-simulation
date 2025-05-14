package com.dfrttkj.FluidSim2D;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    static int WINDOW_WIDTH  = 500;
    static int WINDOW_HEIGHT = 500;

    static int NUM_PARTICLES = 500;
    static float PARTICLE_RADIUS = 3f;
    static float REST_DENSITY = 1000.0f;
    static float GAS_CONSTANT = 2000.0f;
    static float VISCOSITY = 2500.0f;
    static float TIME_STEP = 0.3f;
    static Vector2 GRAVITY = new Vector2(0, 0);

    // Smoothing kernel radius
    static float H = 160.0f;
    static float H2 = H * H;

    // Precomputed constants for kernels
    static float POLY6 = (float) (315.0 / (64.0 * Math.PI * Math.pow(H, 9)));
    static float SPIKY_GRAD = (float) (-45.0 / (Math.PI * Math.pow(H, 6)));
    static float VISC_LAPL = (float) (45.0 / (Math.PI * Math.pow(H, 6)));

    static ShapeRenderer renderer;
    SPHSimulation simulation;

    @Override
    public void create() {
        renderer = new ShapeRenderer();
        simulation= new SPHSimulation();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
    }

    @Override
    public void render() {
        simulation.step(TIME_STEP);
        simulation.render();
        // pollEvents()  // handle input/events
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
}

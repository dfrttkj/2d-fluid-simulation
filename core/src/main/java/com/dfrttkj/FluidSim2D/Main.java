package com.dfrttkj.FluidSim2D;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.math.Vector2;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    static int WINDOW_WIDTH  = 500;
    static int WINDOW_HEIGHT = 500;

    static int NUM_PARTICLES = 500;
    static float PARTICLE_RADIUS = 3f;
    static float REST_DENSITY = 1000.0f;
    static float GAS_CONSTANT = 2000.0f;
    static float VISCOSITY = 250.0f;
    static float TIME_STEP = 0.003f;
    static Vector2 GRAVITY = new Vector2(0, 1200);

    // Smoothing kernel radius
    float H = 16.0f;
    float H2 = H * H;

    // Precomputed constants for kernels
    float POLY6 = (float) (315.0 / (64.0 * Math.PI * Math.pow(H, 9)));
    float SPIKY_GRAD = (float) (-45.0 / (Math.PI * Math.pow(H, 6)));
    float VISC_LAPL = (float) (45.0 / (Math.PI * Math.pow(H, 6)));

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
    }

    @Override
    public void render() {
        // Draw your application here.
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

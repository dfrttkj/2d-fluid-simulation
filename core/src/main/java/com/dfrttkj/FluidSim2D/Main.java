package com.dfrttkj.FluidSim2D;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    Random rand;

    ShapeRenderer shape;
    ArrayList<Particle> test;

    @Override
    public void create() {
        this.rand = new Random(0);

        this.shape = new ShapeRenderer();
        this.test = new ArrayList<>(20);
        for (int i = 0; i < 500; i++) {
            test.add(new Particle(rand.nextFloat(0, 500), rand.nextFloat(0, 500)));
        }
    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        shape.begin(ShapeRenderer.ShapeType.Filled);

        shape.setColor(Color.RED);
        for (Particle P : test) {
            P.draw(shape);
        }

        shape.setColor(Color.GRAY);
        shape.rect(500, 0, 250, 500);

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
}

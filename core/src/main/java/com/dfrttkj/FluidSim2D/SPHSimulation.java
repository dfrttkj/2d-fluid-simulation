package com.dfrttkj.FluidSim2D;

import java.util.ArrayList;
import java.util.List;

public class SPHSimulation {
    List<Particle> particles;

    public SPHSimulation() {
        particles = new ArrayList<>(Main.NUM_PARTICLES);
        // Initialize particles in a block
        for (int i = 0; i < Main.NUM_PARTICLES; i++) {
            // random() * (max-min) + min)
            float x = (float) (Math.random() * (Main.WINDOW_WIDTH - Main.PARTICLE_RADIUS) + Main.PARTICLE_RADIUS);
            float y = (float) (Math.random() * (Main.WINDOW_HEIGHT - Main.PARTICLE_RADIUS) + Main.PARTICLE_RADIUS);
            particles.set(i, new Particle(x, y));
        }
    }
}


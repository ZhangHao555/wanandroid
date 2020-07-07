package com.ahao.particles;

public class ParticleShooter {

    private final Point position;
    private final Vector direction;
    private final int color;

    public ParticleShooter(Point position, Vector direction, int color) {
        this.position = position;
        this.direction = direction;
        this.color = color;
    }

    public void addParticles(ParticleSystem particleSystem, float currentTime, int count) {
        for (int i = 0; i < count; i++) {
            particleSystem.addParticle(position, color, direction, currentTime);
        }
    }
}

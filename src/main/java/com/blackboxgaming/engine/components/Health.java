package com.blackboxgaming.engine.components;

public class Health implements IComponent {

    public float maxHealth;
    public float currentHealth;

    public Health(float maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = this.maxHealth;
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }

}

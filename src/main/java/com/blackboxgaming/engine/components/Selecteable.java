package com.blackboxgaming.engine.components;

public class Selecteable implements IComponent {

    public boolean selecteable;

    public Selecteable() {
        this.selecteable = true;
    }

    public Selecteable(boolean selecteable) {
        this.selecteable = selecteable;
    }
    
}

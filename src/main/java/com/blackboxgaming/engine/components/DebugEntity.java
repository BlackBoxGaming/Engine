package com.blackboxgaming.engine.components;

import com.blackboxgaming.engine.Entity;

public class DebugEntity implements IComponent {

    public Entity entity;

    public DebugEntity(Entity entity) {
        this.entity = entity;
    }

}

package com.blackboxgaming.engine.collision.collisionShapes;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public abstract class Shape implements IShape {

    public Vector3 position = new Vector3();
    public Vector3 center = new Vector3();
    public Vector3 dimensions = new Vector3();

    public Shape(BoundingBox bounds) {
        center.set(bounds.getCenter());
        dimensions.set(bounds.getDimensions());
    }

}

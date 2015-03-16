/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Layer;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Adrian
 */
public class LayerRendererSystem implements ISystem, Disposable {

    private final List<Entity> layers = new LinkedList();

    @Override
    public void add(Entity entity) {
        if (!layers.contains(entity)) {
            layers.add(entity);
        }
    }

    @Override
    public void remove(Entity entity) {
        layers.remove(entity);
    }

    @Override
    public void update(float delta) {
        for (Entity entity : layers) {
            Layer layer = entity.get(Layer.class);
            layer.stage.act(delta);
            layer.stage.draw();
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        for (Entity entity : layers) {
            entity.dispose();
        }
    }

}

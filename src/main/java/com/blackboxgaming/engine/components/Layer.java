/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackboxgaming.engine.components;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Engine;

/**
 *
 * @author Adrian
 */
public class Layer implements IComponent, Disposable {

    public final String layerName;
    public final Stage stage;

    public Layer(String layerName, Stage stage) {
        this.layerName = layerName;
        this.stage = stage;
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass() + " " + layerName);
        Engine.inputManager.remove(stage);
        stage.dispose();
    }
}

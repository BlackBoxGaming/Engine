package com.blackboxgaming.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Health;
import com.blackboxgaming.engine.components.Model;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.factories.ModelFactory;
import com.blackboxgaming.engine.systems.LevelProgressionSystem;
import com.blackboxgaming.engine.util.Global;
import com.blackboxgaming.engine.util.WorldSetupUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class DebugKeyListener implements InputProcessor {
    
    private Entity grid;
    private Entity mouse;
    private boolean debugMouse = false;
    private final Vector3 x0zPoint = new Vector3();
    private final Plane x0zPlane = new Plane(new Vector3(0, 1, 0), new Vector3(0, 0, 0));
    private final Ray ray = new Ray(Vector3.Zero, Vector3.Zero);
    
    @Override
    public boolean keyDown(int i) {
        switch (i) {
            case Keys.F1:
                if (grid == null) {
                    grid = WorldSetupUtil.addGrid(100);
                } else {
                    Engine.garbageManager.markForDeletion(grid);
                    grid = null;
                }
                break;
            case Keys.F2:
                debugMouse = !debugMouse;
                if (debugMouse && mouse == null) {
                    ray.set(Global.getCamera().getPickRay(Gdx.input.getX(), Gdx.input.getY()));
                    Intersector.intersectRayPlane(ray, x0zPlane, x0zPoint);
                    mouse = new Entity();
                    mouse.add(new Transform(x0zPoint));
                    mouse.add(new Model(ModelFactory.getSphereModel(0.5f)));
                    Engine.entityManager.add(mouse);
                }
                if (mouse != null && !debugMouse) {
                    Engine.garbageManager.markForDeletion(mouse);
                    mouse = null;
                }
                break;
            case Keys.F3:
                Global.DEBUG_FRUSTRUM_CULLING_SHAPES = !Global.DEBUG_FRUSTRUM_CULLING_SHAPES;
                System.out.println("DEBUG_FRUSTRUM_CULLING_SHAPES = " + Global.DEBUG_FRUSTRUM_CULLING_SHAPES);
                break;
            case Keys.F4:
                Global.DEBUG_ROTATION = !Global.DEBUG_ROTATION;
                System.out.println("DEBUG_ROTATION = " + Global.DEBUG_ROTATION);
                break;
            case Keys.F5:
                Global.SHADOW = !Global.SHADOW;
                if (!Global.SHADOW) {
                    Global.getEnvironment().shadowMap = null;
                } else {
                    Global.getEnvironment().shadowMap = Global.getShadowLight();
                }
                System.out.println("SHADOW = " + Global.SHADOW);
                break;
            case Keys.F6:
                Global.DEBUG_PHYSICS = !Global.DEBUG_PHYSICS;
                System.out.println("DEBUG_PHYSICS = " + Global.DEBUG_PHYSICS);
                break;
            case Keys.F7:
                Global.BOOST = !Global.BOOST;
                break;
            case Keys.F8:
                Global.SYNC_KEYBOARD_CAM_ROTATION = !Global.SYNC_KEYBOARD_CAM_ROTATION;
                System.out.println("SYNC_KEYBOARD_CAM_ROTATION = " + Global.SYNC_KEYBOARD_CAM_ROTATION);
                break;
            case Keys.C:
                WorldSetupUtil.addRandomPhysicsObject(1, 10);
                break;
            case Keys.T:
                WorldSetupUtil.addRandomTree();
                break;
            case Keys.X:
                if (Global.mainCharacter.has(Health.class)) {
                    Health health = Global.mainCharacter.get(Health.class);
                    if (!health.isDead()) {
                        health.currentHealth--;
                    }
                }
                break;
            case Keys.ESCAPE:
                Gdx.app.exit();
                break;
            case Keys.V:
                cycleCamera();
                break;
            case Keys.L:
                if (Engine.systemManager.has(LevelProgressionSystem.class)) {
                    for (Entity entity : Engine.systemManager.get(LevelProgressionSystem.class).entities) {
                        Engine.garbageManager.markForDeletion(entity);
                    }
                }
                break;
        }
        return false;
    }
    
    @Override
    public boolean keyUp(int i) {
        return false;
    }
    
    @Override
    public boolean keyTyped(char c) {
        return false;
    }
    
    @Override
    public boolean touchDown(int i, int i1, int i2, int button) {
        return false;
    }
    
    @Override
    public boolean touchUp(int i, int i1, int i2, int button) {
        return false;
    }
    
    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }
    
    @Override
    public boolean mouseMoved(int i, int j) {
        if (debugMouse && mouse != null) {
            ray.set(Global.getCamera().getPickRay(i, j));
            if (Intersector.intersectRayPlane(ray, x0zPlane, x0zPoint)) {
                mouse.get(Transform.class).transform.setTranslation(x0zPoint);
            }
        }
        return false;
    }
    
    @Override
    public boolean scrolled(int i) {
        return false;
    }
    
    private List<Vector3> cameraPositions;
    private ListIterator<Vector3> cameraPositionsIterator;
    
    private void cycleCamera() {
        if (cameraPositions == null) {
            cameraPositions = new ArrayList();
            cameraPositions.add(new Vector3(-7.5f, 16.0f, 4.5f));
            cameraPositions.add(new Vector3(-7.5f, 20.5f, 0f));
            cameraPositions.add(Global.getCamera().position.cpy());
            cameraPositionsIterator = cameraPositions.listIterator();
        }
        if (!cameraPositionsIterator.hasNext()) {
            cameraPositionsIterator = cameraPositions.listIterator();
        }
        
        Global.getCamera().position.set(cameraPositionsIterator.next());
        Global.getCamera().lookAt(0, 0, 0);
        Global.getCamera().up.set(Vector3.Y);
        Global.getCamera().update();
    }
    
}

package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.util.Global;
import com.blackboxgaming.engine.util.WorldSetupUtil;
import java.util.ArrayList;
import java.util.List;

public class ObstacleSpawnerSystem implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();
    public int maxObstacles;

    public ObstacleSpawnerSystem(int maxObstacles) {
        this.maxObstacles = maxObstacles;
    }

    @Override
    public void add(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        }
    }

    @Override
    public void remove(Entity entity) {
        entities.remove(entity);
    }

    @Override
    public void update(float delta) {
        while (entities.size() < maxObstacles) {
            List<Entity> bricks = WorldSetupUtil.createWall(Global.boxWidth - Global.boxWidth / 6f, Global.boxLength / 3f, 8);
            for (Entity brick : bricks) {
                entities.add(brick);
            }
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
    }

}

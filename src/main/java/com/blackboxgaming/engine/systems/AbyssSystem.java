package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.AssetManager;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Original;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.components.Velocity;
import com.blackboxgaming.engine.util.Global;
import com.blackboxgaming.engine.util.OnDeath;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class AbyssSystem implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();
    private float abyss = -50f;
    private final Vector3 v = new Vector3();
    private final OnDeath onDeath;
    private boolean killed = false;
    private final Sound destroySound;

    public AbyssSystem(OnDeath onDeath) {
        this.onDeath = onDeath;
        destroySound = Gdx.audio.newSound(Gdx.files.internal(AssetManager.pathToSounds + "buzz.mp3"));
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
        for (Entity entity : entities) {
            if (entity.get(Transform.class).transform.getTranslation(v).y < abyss) {
                System.out.println(entity + " destroyed by abyss");
                Engine.garbageManager.markForDeletion(entity);
                killed = true;
                ((ImageTextButton) Global.scoreButton).setText("" + 0);
                destroySound.play();
                Global.touchLeft = false;
                Global.touchRight = false;
            }
        }
        if (killed) {
            if (onDeath != null) {
                onDeath.onDeath();
            }
            killed = false;
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
        destroySound.dispose();
    }

}

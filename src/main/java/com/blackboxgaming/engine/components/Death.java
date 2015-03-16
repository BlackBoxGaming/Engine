package com.blackboxgaming.engine.components;

public class Death implements IComponent {

    public final long startTime;
    public final int timeToLive;

    public Death(int timeToLive) {
        this.startTime = System.currentTimeMillis();
        this.timeToLive = timeToLive;
    }

}

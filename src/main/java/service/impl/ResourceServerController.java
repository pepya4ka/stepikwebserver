package service.impl;

import service.ResourceServer;
import service.ResourceServerControllerMXBean;

public class ResourceServerController implements ResourceServerControllerMXBean {

    private final ResourceServer resourceServer;

    public ResourceServerController(ResourceServer resourceServer) {
        this.resourceServer = resourceServer;
    }

    @Override
    public String getName() {
        return resourceServer.getName();
    }

    @Override
    public int getAge() {
        return resourceServer.getAge();
    }
}

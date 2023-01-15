package service.impl;

import resources.TestResource;
import service.ResourceServer;

public class ResourceServerImpl implements ResourceServer {

    private TestResource testResource;

    public ResourceServerImpl() {
    }

    public ResourceServerImpl(TestResource testResource) {
        this.testResource = testResource;
    }

    public void setTestResource(TestResource testResource) {
        this.testResource = testResource;
    }

    @Override
    public String getName() {
        return testResource.getName();
    }

    @Override
    public int getAge() {
        return testResource.getAge();
    }
}

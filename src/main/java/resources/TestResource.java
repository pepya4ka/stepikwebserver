package resources;

public class TestResource {
    private String name;
    private int age;

    public TestResource() {
        this.name = "";
        this.age = 0;
    }

    public TestResource(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
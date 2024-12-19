package utils;

import org.picocontainer.DefaultPicoContainer;

public class TestSetUp {
    public static final DefaultPicoContainer picoContainer = new DefaultPicoContainer();

    static {
        picoContainer.addComponent(TestContext.class, new TestContext());
    }

    public static DefaultPicoContainer getPicoContainer() {
        return picoContainer;
    }
}

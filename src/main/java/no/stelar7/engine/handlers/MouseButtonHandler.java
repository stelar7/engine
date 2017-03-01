package no.stelar7.engine.handlers;

import org.lwjgl.glfw.*;

public class MouseButtonHandler implements GLFWMouseButtonCallbackI
{
    
    @Override
    public void invoke(long window, int button, int action, int mods)
    {
        InputHandler.setKeyForWindow(button, action);
    }
}

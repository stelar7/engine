package no.stelar7.engine.handlers;

import org.lwjgl.glfw.*;

public class KeyboardHandler implements GLFWKeyCallbackI
{
    @Override
    public void invoke(long window, int key, int scancode, int action, int mods)
    {
        InputHandler.setKeyForWindow(key, action);
    }
}

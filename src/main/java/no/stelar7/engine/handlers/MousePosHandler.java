package no.stelar7.engine.handlers;

import org.lwjgl.glfw.*;

public class MousePosHandler extends GLFWCursorPosCallback
{
    @Override
    public void invoke(long window, double xpos, double ypos)
    {
        InputHandler.setMousePosForWindow(xpos, ypos);
    }
}

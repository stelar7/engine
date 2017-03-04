package no.stelar7.engine.handlers;

import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public final class InputHandler
{
    private InputHandler()
    {
        // Hide public constructor
    }
    
    private static float windowHeight;
    private static float windowWidth;
    private static long  windowHandle;
    
    private static final boolean[] keys  = new boolean[GLFW_KEY_LAST];
    private static final Vector2f  mouse = new Vector2f();
    
    private static boolean mouseLock;
    
    public static void setMousePosForWindow(double xpos, double ypos)
    {
        mouse.set((float) xpos, (float) ypos);
        
        if (mouseLock)
        {
            glfwSetCursorPos(windowHandle, windowHeight / 2, windowWidth / 2);
        }
    }
    
    public static void setCursor(int cursor)
    {
        glfwSetInputMode(windowHandle, GLFW_CURSOR, cursor);
        mouseLock = cursor == GLFW_CURSOR_DISABLED;
    }
    
    public static void setKeyForWindow(int key, int action)
    {
        keys[key] = action != GLFW_RELEASE;
    }
    
    /**
     * This is the offset from last movement if the cursor is set to GLFW_CURSOR_DISABLED
     */
    public static Vector2f getMousePosition()
    {
        return mouse;
    }
    
    public static boolean isKeyDown(int key)
    {
        return keys[key];
    }
    
    public static void setWindowHeight(float windowHeight)
    {
        InputHandler.windowHeight = windowHeight;
    }
    
    public static void setWindowWidth(float windowWidth)
    {
        InputHandler.windowWidth = windowWidth;
    }
    
    public static void setWindowHandle(long windowHandle)
    {
        InputHandler.windowHandle = windowHandle;
    }
}

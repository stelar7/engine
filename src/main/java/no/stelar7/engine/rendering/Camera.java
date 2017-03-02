package no.stelar7.engine.rendering;

import no.stelar7.engine.handlers.InputHandler;
import no.stelar7.engine.rendering.models.Transform;
import org.joml.*;
import org.joml.Math;

import static org.lwjgl.glfw.GLFW.*;

public class Camera extends GameObject
{
    private Matrix4f projection = new Matrix4f();
    private float    fovDegrees = 75;
    
    public Camera()
    {
        super();
        transform.setPosition(0, 0, -2);
        projection.setPerspective((float) Math.toRadians(fovDegrees), 800f / 600f, 0.1f, 1000f);
    }
    
    public Matrix4f getViewMatrix()
    {
        return transform.getCurrentTransfom();
    }
    
    public void setProjection(Matrix4f proj)
    {
        projection.set(proj);
    }
    
    public Matrix4f getProjectionMatrix()
    {
        return projection;
    }
    
    public Matrix4f calculateMVPMatrix(Transform model)
    {
        return getProjectionMatrix().mul(getViewMatrix().mul(model.getCurrentTransfom(), new Matrix4f()), new Matrix4f());
    }
    
    public void update()
    {
        float speed = 0.1f;
        
        // WASD
        if (InputHandler.isKeyDown(GLFW_KEY_S))
        {
            transform.move(0, 0, -speed);
        }
        if (InputHandler.isKeyDown(GLFW_KEY_W))
        {
            transform.move(0, 0, speed);
        }
        if (InputHandler.isKeyDown(GLFW_KEY_A))
        {
            transform.move(speed, 0, 0);
        }
        if (InputHandler.isKeyDown(GLFW_KEY_D))
        {
            transform.move(-speed, 0, 0);
        }
        
        // Up/down
        if (InputHandler.isKeyDown(GLFW_KEY_Q))
        {
            transform.move(0, speed, 0);
        }
        if (InputHandler.isKeyDown(GLFW_KEY_E))
        {
            transform.move(0, -speed, 0);
        }
        
        // Rotation
        if (InputHandler.isKeyDown(GLFW_KEY_R))
        {
            transform.rotate(speed, 0, 0, 1);
        }
        if (InputHandler.isKeyDown(GLFW_KEY_T))
        {
            transform.rotate(-speed, 0, 0, 1);
        }
        if (InputHandler.isKeyDown(GLFW_KEY_F))
        {
            transform.rotate(speed, 0, 1, 0);
        }
        if (InputHandler.isKeyDown(GLFW_KEY_G))
        {
            transform.rotate(-speed, 0, 1, 0);
        }
        if (InputHandler.isKeyDown(GLFW_KEY_V))
        {
            transform.rotate(speed, 1, 0, 0);
        }
        if (InputHandler.isKeyDown(GLFW_KEY_B))
        {
            transform.rotate(-speed, 1, 0, 0);
        }
        
        // fov
        if (InputHandler.isKeyDown(GLFW_KEY_Z))
        {
            projection.setPerspective((float) Math.toRadians(--fovDegrees), 800f / 600f, 0.1f, 1000f);
        }
        if (InputHandler.isKeyDown(GLFW_KEY_C))
        {
            projection.setPerspective((float) Math.toRadians(++fovDegrees), 800f / 600f, 0.1f, 1000f);
        }
        
    }
}

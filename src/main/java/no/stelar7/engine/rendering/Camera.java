package no.stelar7.engine.rendering;

import no.stelar7.engine.handlers.InputHandler;
import org.joml.*;
import org.joml.Math;

import static org.lwjgl.glfw.GLFW.*;

public class Camera
{
    private Transform transform  = new Transform();
    private Matrix4f  projection = new Matrix4f();
    private Matrix4f  view       = new Matrix4f();
    
    public Camera()
    {
        transform.setPosition(0, 0, -2);
        
        projection = new Matrix4f();
        projection.setPerspective((float) Math.toRadians(75), 800f / 600f, 0.1f, 1000f);
    }
    
    public Matrix4f getViewMatrix()
    {
        if (!transform.hasChanged())
        {
            return view;
        }
        
        Matrix4f mat = new Matrix4f();
        Vector3f rot = transform.getRotation().getEulerAnglesXYZ(new Vector3f());
        
        mat.rotate(new AxisAngle4f(rot.x(), 1, 0, 0));
        mat.rotate(new AxisAngle4f(rot.y(), 0, 1, 0));
        mat.rotate(new AxisAngle4f(rot.z(), 0, 0, 1));
        
        mat.translate(transform.getPosition(), view);
        
        return view;
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
        float speed = 0.01f;
        
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
        if (InputHandler.isKeyDown(GLFW_KEY_Z))
        {
            transform.move(0, speed, 0);
        }
        if (InputHandler.isKeyDown(GLFW_KEY_C))
        {
            transform.move(0, -speed, 0);
        }
        
        // Rotation
        if (InputHandler.isKeyDown(GLFW_KEY_Q))
        {
            transform.rotate(speed, 0, 0, 1);
        }
        if (InputHandler.isKeyDown(GLFW_KEY_E))
        {
            transform.rotate(-speed, 0, 0, 1);
        }
    }
}

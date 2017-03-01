package no.stelar7.engine.rendering;

import org.joml.*;

public class Camera
{
    private Transform transform;
    
    public Camera()
    {
        this.transform = new Transform();
        transform.setPosition(0, 0, -2);
        
    }
    
    public Matrix4f getViewMatrix()
    {
        Matrix4f mat = new Matrix4f();
        mat.identity();
        
        
        Vector3f rot = transform.getRotation().getEulerAnglesXYZ(new Vector3f());
        
        mat.rotate(new AxisAngle4f(rot.z(), 0, 0, 1));
        mat.rotate(new AxisAngle4f(rot.y(), 0, 1, 0));
        mat.rotate(new AxisAngle4f(rot.x(), 1, 0, 0));
        
        mat.translate(transform.getPosition().negate(new Vector3f()));
        return mat;
    }
    
    
}

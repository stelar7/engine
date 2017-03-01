package no.stelar7.engine.rendering;

import org.joml.*;

public class Transform
{
    private Model model;
    
    private Vector3f    position = new Vector3f(0);
    private Quaternionf rotation = new Quaternionf();
    private Vector3f    scale    = new Vector3f(1);
    
    private Matrix4f currentTransform = new Matrix4f();
    
    public Transform()
    {
        updateTransform();
    }
    
    private void updateTransform()
    {
        new Matrix4f().translation(position).rotate(rotation).scale(scale, currentTransform);
    }
    
    public void setScale(float scl)
    {
        setScale(new Vector3f(scl));
    }
    
    public void setPosition(float x)
    {
        setPosition(x, x, x);
    }
    
    public void setRotation(float x)
    {
        setRotation(x, x, x);
    }
    
    public void setPosition(float x, float y, float z)
    {
        setPosition(new Vector3f(x, y, z));
    }
    
    
    public void setRotation(float x, float y, float z)
    {
        setRotation(new Quaternionf(x, y, z));
    }
    
    public void setScale(float x, float y, float z)
    {
        setScale(new Vector3f(x, y, z));
    }
    
    
    public void setPosition(Vector3f pos)
    {
        position.set(pos);
        updateTransform();
    }
    
    public void setRotation(Quaternionf rot)
    {
        rotation.set(rot);
        updateTransform();
    }
    
    public void setScale(Vector3f scl)
    {
        scale.set(scl);
        updateTransform();
    }
    
    public Quaternionf getRotation()
    {
        return rotation;
    }
    
    public Vector3f getScale()
    {
        return scale;
    }
    
    public Matrix4f getCurrentTransfom()
    {
        return currentTransform;
    }
    
    public Vector3f getPosition()
    {
        return position;
    }
}

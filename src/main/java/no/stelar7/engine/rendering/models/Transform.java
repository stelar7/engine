package no.stelar7.engine.rendering.models;

import org.joml.*;

public class Transform
{
    private Vector3f    position = new Vector3f(0);
    private Quaternionf rotation = new Quaternionf();
    private Vector3f    scale    = new Vector3f(1);
    
    private Matrix4f currentTransform = new Matrix4f();
    private Matrix4f lastTransform    = new Matrix4f();
    
    public Transform()
    {
        updateTransform();
    }
    
    
    private void updateTransform()
    {
        lastTransform.set(currentTransform);
        //new Matrix4f().translation(position).rotate(rotation).scale(scale, currentTransform);
        new Matrix4f().scaling(scale).rotate(rotation).translate(position, currentTransform);
    }
    
    public boolean hasChanged()
    {
        return !lastTransform.equals(currentTransform);
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
    
    public void move(float x, float y, float z)
    {
        position.add(x, y, z);
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
    
    public void rotate(float angle, float x, float y, float z)
    {
        rotation.rotateAxis(angle, x, y, z);
        updateTransform();
    }
    
    public void set(Matrix4f tran)
    {
        lastTransform.set(currentTransform);
        currentTransform.set(tran);
    }
    
    @Override
    public String toString()
    {
        return "Transform{" + "currentTransform=" + currentTransform + '}';
    }
}

package no.stelar7.engine.rendering;

import no.stelar7.engine.rendering.models.*;

public class GameObject
{
    
    protected Transform transform = new Transform();
    
    private Mesh model;
    
    public void setModel(Mesh model)
    {
        this.model = model;
    }
    
    public Mesh getModel()
    {
        return model;
    }
    
    public Transform getTransform()
    {
        return transform;
    }
}

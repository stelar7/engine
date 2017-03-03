package no.stelar7.engine.rendering;

import no.stelar7.engine.rendering.models.*;

public class GameObject
{
    
    protected Transform transform = new Transform();
    
    private Mesh mesh;
    
    public void setMesh(Mesh mesh)
    {
        this.mesh = mesh;
    }
    
    public Mesh getMesh()
    {
        return mesh;
    }
    
    public Transform getTransform()
    {
        return transform;
    }
}

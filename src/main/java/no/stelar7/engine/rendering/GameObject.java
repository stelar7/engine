package no.stelar7.engine.rendering;

import no.stelar7.engine.rendering.models.*;

public class GameObject
{
    
    protected Transform transform = new Transform();
    
    private Mesh        mesh;
    private TextureData texture;
    
    public TextureData getTexture()
    {
        return texture;
    }
    
    public void setTexture(TextureData texture)
    {
        
        this.texture = texture;
    }
    
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

package no.stelar7.engine.rendering;

public class GameObject
{
    
    protected Transform transform = new Transform();
    
    private Model model;
    
    public void setModel(Model model)
    {
        this.model = model;
    }
    
    public Model getModel()
    {
        return model;
    }
    
    public Transform getTransform()
    {
        return transform;
    }
}

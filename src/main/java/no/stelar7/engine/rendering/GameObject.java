package no.stelar7.engine.rendering;

public class GameObject
{
    
    private Model model;
    private Transform transform = new Transform();
    
    public GameObject(Model model)
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

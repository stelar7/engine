package no.stelar7.engine.rendering;

import no.stelar7.engine.handlers.InputHandler;

public class FPSCamera extends Camera
{
    public FPSCamera()
    {
        super();
        //InputHandler.setCursor(GLFW_CURSOR_DISABLED);
    }
    
    @Override
    public void update()
    {
        float horizontalSensitivity = 0.1f;
        float verticalSensitivity   = 0.1f;
        
        transform.getRotation().rotationX(InputHandler.getMousePosition().x() * horizontalSensitivity);
        transform.getRotation().rotationY(InputHandler.getMousePosition().y() * verticalSensitivity);
        
        if (transform.getRotation().x() < -90)
        {
            transform.getRotation().x = -90;
        }
        
        if (transform.getRotation().x() > 90)
        {
            transform.getRotation().x = 90;
        }
        
        if (transform.getRotation().y() < 0)
        {
            transform.getRotation().y += 360;
        }
        
        if (transform.getRotation().y() > 360)
        {
            transform.getRotation().y -= 360;
        }
        
    }
}

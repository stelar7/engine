package no.stelar7.engine.rendering.shaders;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram
{
    
    private int id;
    
    public int getId()
    {
        return id;
    }
    
    public ShaderProgram()
    {
        this.id = glCreateProgram();
    }
    
    public ShaderProgram(Shader... shaders)
    {
        this.id = glCreateProgram();
        
        for (final Shader shader : shaders)
        {
            attachShader(shader);
        }
        
        link();
        
        for (final Shader shader : shaders)
        {
            deleteShader(shader);
        }
        
        validate();
    }
    
    public void validate()
    {
        glValidateProgram(id);
    }
    
    public void deleteShader(final Shader shader)
    {
        glDetachShader(id, shader.getId());
        shader.delete();
    }
    
    public void attachShader(final Shader shader)
    {
        glAttachShader(id, shader.getId());
    }
    
    public void link()
    {
        glLinkProgram(id);
        
        if (glGetProgrami(id, GL_LINK_STATUS) != GL_TRUE)
        {
            throw new RuntimeException(glGetProgramInfoLog(id));
        }
    }
    
    public void delete()
    {
        glDeleteProgram(id);
    }
    
    public void unbind()
    {
        glUseProgram(0);
    }
    
    public void bind()
    {
        glUseProgram(id);
    }
}

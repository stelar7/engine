package no.stelar7.engine.rendering.shaders;

import no.stelar7.engine.EngineUtils;
import org.joml.*;
import org.lwjgl.*;

import java.nio.FloatBuffer;
import java.util.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram
{
    
    private int id;
    private Map<String, Integer> uniformLocations = new HashMap<>();
    
    public int getId()
    {
        return id;
    }
    
    public ShaderProgram(Shader vertex, Shader fragment)
    {
        generate();
        
        attachShader(vertex);
        attachShader(fragment);
        
        link();
        
        validate();
        
        deleteShader(vertex);
        deleteShader(fragment);
    }
    
    public void generate()
    {
        this.id = glCreateProgram();
        EngineUtils.log("glCreateProgram() = %s", id);
    }
    
    
    private int getUniformLocation(String uniform)
    {
        if (uniformLocations.containsKey(uniform))
        {
            return uniformLocations.get(uniform);
        }
        int uniformValue = glGetUniformLocation(id, uniform);
        EngineUtils.log("glGetUniformLocation(%s, %s) = %s", id, uniform, uniformValue);
        
        if (uniformValue == -1)
        {
            throw new RuntimeException("Invalid glUniformLocation for uniform: " + uniform);
        }
        
        uniformLocations.put(uniform, uniformValue);
        return uniformValue;
    }
    
    public void setUniform1f(String uniform, float value)
    {
        int location = getUniformLocation(uniform);
        
        EngineUtils.log("glUniform1f(%s, %s)", location, value);
        glUniform1f(location, value);
    }
    
    public void setUniformVector3(String uniform, Vector3f data)
    {
        int location = getUniformLocation(uniform);
        
        FloatBuffer value = BufferUtils.createFloatBuffer(3);
        data.get(value);
        
        EngineUtils.log("glUniform3fv(%s, %s)", location, EngineUtils.bufferToString(value));
        glUniform3fv(location, value);
    }
    
    public void setUniformMatrix4(String uniform, Matrix4f data)
    {
        int location = getUniformLocation(uniform);
        
        FloatBuffer value = BufferUtils.createFloatBuffer(16);
        data.get(value);
        
        EngineUtils.log("glUniformMatrix4fv(%s, %s, %s)", location, false, EngineUtils.bufferToString(value));
        glUniformMatrix4fv(location, false, value);
    }
    
    public void validate()
    {
        glValidateProgram(id);
        EngineUtils.log("glValidateProgram(%s)", id);
        
        int status = glGetProgrami(id, GL_VALIDATE_STATUS);
        EngineUtils.log("glGetProgrami(%s, %s) = %s", id, EngineUtils.glTypeToString(GL_VALIDATE_STATUS), EngineUtils.glTypeToString(status));
        if (status != GL_TRUE)
        {
            String log = glGetProgramInfoLog(id);
            EngineUtils.log("glGetProgramInfoLog(%s)", id);
            EngineUtils.log(log);
            
            throw new RuntimeException(log);
        }
        
    }
    
    public void deleteShader(final Shader shader)
    {
        glDetachShader(id, shader.getId());
        EngineUtils.log("glDetachShader(%s, %s)", id, shader.getId());
        shader.delete();
    }
    
    public void attachShader(final Shader shader)
    {
        glAttachShader(id, shader.getId());
        EngineUtils.log("glAttachShader(%s, %s)", id, shader.getId());
    }
    
    public void link()
    {
        glLinkProgram(id);
        EngineUtils.log("glLinkProgram(%s)", id);
        
        int status = glGetProgrami(id, GL_LINK_STATUS);
        EngineUtils.log("glGetProgrami(%s, %s) = %s", id, EngineUtils.glTypeToString(GL_LINK_STATUS), EngineUtils.glTypeToString(status));
        if (status != GL_TRUE)
        {
            String log = glGetProgramInfoLog(id);
            EngineUtils.log("glGetProgramInfoLog(%s)", id);
            EngineUtils.log(log);
            
            throw new RuntimeException(log);
        }
    }
    
    public void delete()
    {
        unbind();
        glDeleteProgram(id);
        EngineUtils.log("glDeleteProgram(%s)", id);
    }
    
    public void unbind()
    {
        glUseProgram(0);
        EngineUtils.log("glUseProgram(%s)", 0);
    }
    
    public void bind()
    {
        glUseProgram(id);
        EngineUtils.log("glUseProgram(%s)", id);
    }
}

package no.stelar7.engine.game;

import no.stelar7.engine.EngineUtils;
import no.stelar7.engine.rendering.*;
import no.stelar7.engine.rendering.shaders.*;
import org.joml.Matrix4f;
import org.lwjgl.system.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;


public class TestGame implements Game
{
    
    private ShaderProgram shader;
    private GameObject    obj;
    private Camera        camera;
    private Matrix4f      projection;
    
    public TestGame()
    {
        Shader vertexShader = new Shader(GL_VERTEX_SHADER);
        vertexShader.setSource(EngineUtils.readShader("basic.vert"));
        vertexShader.compile();
        
        Shader fragmentShader = new Shader(GL_FRAGMENT_SHADER);
        fragmentShader.setSource(EngineUtils.readShader("basic.frag"));
        fragmentShader.compile();
        
        shader = new ShaderProgram(vertexShader, fragmentShader);
        obj = new GameObject(new Model(new float[]{-1, -1, 0, 1, -1, 0, 0, 1, 0}, new int[]{0, 1, 2}));
        
        camera = new Camera();
        projection = new Matrix4f();
        projection.setPerspective(75, 800f / 600f, 0.1f, 1000f);
        
        shader.bind();
        //shader.setUniformMatrix4("view", camera.getViewMatrix());
        shader.setUniformMatrix4("projection", projection);
        
        glClearColor(.8f, 0, 0, 0);
        EngineUtils.log("glClearColor(%s, %s, %s, %s)", .8f, 0, 0, 0);
        
    }
    
    private float scale;
    
    @Override
    public void update()
    {
        scale += 0.01f;
        
        //obj.getTransform().setScale(1);
        //obj.getTransform().setPosition((float) Math.cos(scale), 0, obj.getTransform().getPosition().z() + 0.001f);
        //obj.getTransform().setRotation(0, 0, 0);
        
        System.out.println(obj.getTransform().getPosition());
        
        shader.bind();
        shader.setUniformMatrix4("transformation", obj.getTransform().getCurrentTransfom());
        
    }
    
    
    @Override
    public void render()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        EngineUtils.log("glClear(%s | %s)", EngineUtils.glTypeToString(GL_COLOR_BUFFER_BIT), EngineUtils.glTypeToString(GL_DEPTH_BUFFER_BIT));
        
        shader.bind();
        obj.getModel().bind();
        
        glDrawElements(GL_TRIANGLES, obj.getModel().getVertexCount(), GL_UNSIGNED_INT, MemoryUtil.NULL);
        EngineUtils.log("glDrawElements(%s, %s, %s, %s)", EngineUtils.glTypeToString(GL_TRIANGLES), obj.getModel().getVertexCount(), EngineUtils.glTypeToString(GL_UNSIGNED_INT), MemoryUtil.NULL);
    }
    
    @Override
    public void delete()
    {
        obj.getModel().delete();
        shader.delete();
    }
}

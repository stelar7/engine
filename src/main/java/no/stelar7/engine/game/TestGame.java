package no.stelar7.engine.game;

import no.stelar7.engine.EngineUtils;
import no.stelar7.engine.rendering.*;
import no.stelar7.engine.rendering.models.*;
import no.stelar7.engine.rendering.shaders.*;
import org.joml.Math;
import org.lwjgl.system.*;

import java.util.*;
import java.util.Map.Entry;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;


public class TestGame implements Game
{
    
    private ShaderProgram shader;
    private Camera        camera;
    private Map<Mesh, List<GameObject>> entities = new HashMap<>();
    
    public TestGame()
    {
        Shader vertexShader = new Shader(GL_VERTEX_SHADER);
        vertexShader.setSource(EngineUtils.readShader("basic.vert"));
        vertexShader.compile();
        
        Shader fragmentShader = new Shader(GL_FRAGMENT_SHADER);
        fragmentShader.setSource(EngineUtils.readShader("basic.frag"));
        fragmentShader.compile();
        
        shader = new ShaderProgram(vertexShader, fragmentShader);
        
        float[]     vert  = new float[]{-1, -1, 0, 1, -1, 0, 0, 1, 0};
        int[]       ind   = new int[]{0, 1, 2};
        float[]     texd  = new float[]{0, 0, 1, 0, .5f, 1};
        TextureData tex   = new TextureData(texd, EngineUtils.loadTexture("arrow.png"));
        Mesh        model = new Mesh(vert, ind, tex);
        
        GameObject obj = new GameObject();
        obj.setModel(model);
        
        GameObject obj2 = new GameObject();
        obj2.setModel(model);
        obj2.getTransform().move(5, 0, 0);
        
        List<GameObject> objs = new ArrayList<>();
        
        objs.add(obj);
        objs.add(obj2);
        entities.put(model, objs);
        
        camera = new Camera();
        
        glClearColor(.8f, 0, 0, 0);
        EngineUtils.log("glClearColor(%s, %s, %s, %s)", .8f, 0, 0, 0);
        
    }
    
    private float scale;
    
    @Override
    public void update()
    {
        camera.update();
        scale += 0.01f;
        
        entities.forEach((k, v) ->
                         {
                             int cnt = 0;
                             for (GameObject o : v)
                             {
                                 cnt++;
                                 int dir = cnt % 2 == 0 ? 1 : -1;
                                 o.getTransform().setPosition((float) Math.sin(scale) * dir, 0, (float) Math.cos(scale) * dir);
                             }
                         });
    }
    
    
    @Override
    public void render()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        EngineUtils.log("glClear(%s | %s)", EngineUtils.glTypeToString(GL_COLOR_BUFFER_BIT), EngineUtils.glTypeToString(GL_DEPTH_BUFFER_BIT));
        
        shader.bind();
        for (Entry<Mesh, List<GameObject>> entry : entities.entrySet())
        {
            entry.getKey().bind();
            for (GameObject gameObject : entry.getValue())
            {
                shader.setUniformMatrix4("mvp", camera.calculateMVPMatrix(gameObject.getTransform()));
                glDrawElements(GL_TRIANGLES, gameObject.getModel().getVertexCount(), GL_UNSIGNED_INT, MemoryUtil.NULL);
                EngineUtils.log("glDrawElements(%s, %s, %s, %s)", EngineUtils.glTypeToString(GL_TRIANGLES), gameObject.getModel().getVertexCount(), EngineUtils.glTypeToString(GL_UNSIGNED_INT), MemoryUtil.NULL);
            }
        }
    }
    
    @Override
    public void delete()
    {
        entities.forEach((k, v) -> k.delete());
        shader.delete();
    }
}

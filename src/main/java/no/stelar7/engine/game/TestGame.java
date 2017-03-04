package no.stelar7.engine.game;

import no.stelar7.engine.EngineUtils;
import no.stelar7.engine.rendering.*;
import no.stelar7.engine.rendering.models.*;
import no.stelar7.engine.rendering.shaders.*;
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
        
        float[] vert = new float[]{-1, -1, 0, 1, -1, 0, 0, 1, 0};
        int[]   ind  = new int[]{0, 1, 2};
        float[] texd = new float[]{0, 0, 1, 0, .5f, 1};
        
        
        Mesh        triangle = new Mesh(vert, ind, texd);
        Mesh        cube     = MeshLoader.loadFromObj("cube.obj");
        TextureData arrow    = EngineUtils.loadTexture("arrow.png");
        TextureData wall     = EngineUtils.loadTexture("wall.jpg");
        
        GameObject obj = new GameObject();
        obj.setMesh(cube);
        obj.setTexture(arrow);
        
        GameObject obj2 = new GameObject();
        obj2.setMesh(cube);
        obj2.setTexture(wall);
        obj2.getTransform().move(10, 0, 0);
        
        addToEntityList(obj);
        addToEntityList(obj2);
        
        camera = new Camera();
        
        glClearColor(.8f, 0, 0, 0);
        EngineUtils.log("glClearColor(%s, %s, %s, %s)", .8f, 0, 0, 0);
    }
    
    private void addToEntityList(GameObject obj)
    {
        Mesh             model = obj.getMesh();
        List<GameObject> ents  = entities.getOrDefault(model, new ArrayList<>());
        
        ents.add(obj);
        entities.put(model, ents);
    }
    
    private float scale;
    
    @Override
    public void update()
    {
        camera.update();
        scale += 0.01f;
        
        entities.forEach((k, v) ->
                         {
                             for (GameObject o : v)
                             {
                                 o.getTransform().setRotation(0, scale, 0);
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
                if (gameObject.getTexture() != null)
                {
                    gameObject.getTexture().bind();
                }
                
                shader.setUniformMatrix4("mvp", camera.calculateMVPMatrix(gameObject.getTransform()));
                glDrawElements(GL_TRIANGLES, gameObject.getMesh().getVertexCount(), GL_UNSIGNED_INT, MemoryUtil.NULL);
                EngineUtils.log("glDrawElements(%s, %s, %s, %s)", EngineUtils.glTypeToString(GL_TRIANGLES), gameObject.getMesh().getVertexCount(), EngineUtils.glTypeToString(GL_UNSIGNED_INT), MemoryUtil.NULL);
                
                if (gameObject.getTexture() != null)
                {
                    gameObject.getTexture().unbind();
                }
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

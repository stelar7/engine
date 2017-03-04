package no.stelar7.engine.rendering.models;

import no.stelar7.engine.EngineUtils;
import org.joml.*;

import java.util.*;

public class MeshLoader
{
    public static Mesh loadFromObj(String filename)
    {
        String[] data = EngineUtils.readObjFile(filename).split("\n");
        
        List<Vector3f> vertex   = new ArrayList<>();
        List<Vector3f> tempNorm = new ArrayList<>();
        List<Vector2f> tempTex  = new ArrayList<>();
        
        List<Integer> vIndex = new ArrayList<>();
        List<Integer> nIndex = new ArrayList<>();
        List<Integer> tIndex = new ArrayList<>();
        
        List<Vector3f> normals  = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        
        for (String line : data)
        {
            if (line.trim().isEmpty())
            {
                continue;
            }
            if (line.startsWith("#"))
            {
                continue;
            }
            
            String[] lineParts = line.split(" ");
            
            if ("v".equals(lineParts[0]))
            {
                float x = Float.parseFloat(lineParts[1]);
                float y = Float.parseFloat(lineParts[2]);
                float z = Float.parseFloat(lineParts[3]);
                vertex.add(new Vector3f(x, y, z));
            }
            
            if ("vt".equals(lineParts[0]))
            {
                float u = Float.parseFloat(lineParts[1]);
                float v = Float.parseFloat(lineParts[2]);
                tempTex.add(new Vector2f(u, v));
            }
            
            if ("vn".equals(lineParts[0]))
            {
                float x = Float.parseFloat(lineParts[1]);
                float y = Float.parseFloat(lineParts[2]);
                float z = Float.parseFloat(lineParts[3]);
                tempNorm.add(new Vector3f(x, y, z));
            }
            
            if ("f".equals(lineParts[0]))
            {
                if (lineParts[1].matches("\\d/\\d/\\d"))
                {
                    int part1 = Integer.parseInt(lineParts[1].split("/")[0].trim());
                    int part2 = Integer.parseInt(lineParts[2].split("/")[0].trim());
                    int part3 = Integer.parseInt(lineParts[3].split("/")[0].trim());
                    vIndex.add(part1);
                    vIndex.add(part2);
                    vIndex.add(part3);
                    
                    part1 = Integer.parseInt(lineParts[1].split("/")[1].trim());
                    part2 = Integer.parseInt(lineParts[2].split("/")[1].trim());
                    part3 = Integer.parseInt(lineParts[3].split("/")[1].trim());
                    tIndex.add(part1);
                    tIndex.add(part2);
                    tIndex.add(part3);
                    
                    part1 = Integer.parseInt(lineParts[1].split("/")[2].trim());
                    part2 = Integer.parseInt(lineParts[2].split("/")[2].trim());
                    part3 = Integer.parseInt(lineParts[3].split("/")[2].trim());
                    nIndex.add(part1);
                    nIndex.add(part2);
                    nIndex.add(part3);
                }
            }
        }
        
        for (Integer vertexPointer : vIndex)
        {
            int vptr = vertexPointer - 1;
            
            Vector2f tex = tempTex.get(tIndex.get(vptr) - 1);
            textures.add(tex);
            
            Vector3f norm = tempNorm.get(nIndex.get(vptr) - 1);
            normals.add(norm);
        }
        
        return new Mesh(EngineUtils.vector3fListToBuffer(vertex), EngineUtils.intListToBuffer(vIndex), EngineUtils.vector3fListToBuffer(normals), EngineUtils.vector2fListToBuffer(textures));
    }
}

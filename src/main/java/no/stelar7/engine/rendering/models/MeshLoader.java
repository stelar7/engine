package no.stelar7.engine.rendering.models;

import no.stelar7.engine.EngineUtils;
import org.joml.*;

import java.util.*;

public class MeshLoader
{
    public static Mesh loadFromObj(String filename)
    {
        String[] data = EngineUtils.readObjFile(filename).split("\n");
        
        List<Vector3f> vertex = new ArrayList<>();
        List<Vector3f> tempNorm = new ArrayList<>();
        List<Vector2f> tempTex  = new ArrayList<>();
        
        List<Integer> vIndex = new ArrayList<>();
        List<Integer> nIndex = new ArrayList<>();
        List<Integer> tIndex = new ArrayList<>();
        
        List<Vector3f> normal  = new ArrayList<>();
        List<Vector2f> texture = new ArrayList<>();
        
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
                if (lineParts[1].matches("\\d"))
                {
                    int part1 = Integer.parseInt(lineParts[1]);
                    int part2 = Integer.parseInt(lineParts[2]);
                    int part3 = Integer.parseInt(lineParts[3]);
                    vIndex.add(part1);
                    vIndex.add(part2);
                    vIndex.add(part3);
                }
                if (lineParts[1].matches("\\d/\\d"))
                {
                    int part1 = Integer.parseInt(lineParts[1].split("/")[0]);
                    int part2 = Integer.parseInt(lineParts[2].split("/")[0]);
                    int part3 = Integer.parseInt(lineParts[3].split("/")[0]);
                    vIndex.add(part1);
                    vIndex.add(part2);
                    vIndex.add(part3);
                    
                    part1 = Integer.parseInt(lineParts[1].split("/")[1]);
                    part2 = Integer.parseInt(lineParts[2].split("/")[1]);
                    part3 = Integer.parseInt(lineParts[3].split("/")[1]);
                    tIndex.add(part1);
                    tIndex.add(part2);
                    tIndex.add(part3);
                }
                if (lineParts[1].matches("\\d/\\d/\\d"))
                {
                    int part1 = Integer.parseInt(lineParts[1].split("/")[0]);
                    int part2 = Integer.parseInt(lineParts[2].split("/")[0]);
                    int part3 = Integer.parseInt(lineParts[3].split("/")[0]);
                    vIndex.add(part1);
                    vIndex.add(part2);
                    vIndex.add(part3);
                    
                    part1 = Integer.parseInt(lineParts[1].split("/")[1]);
                    part2 = Integer.parseInt(lineParts[2].split("/")[1]);
                    part3 = Integer.parseInt(lineParts[3].split("/")[1]);
                    tIndex.add(part1);
                    tIndex.add(part2);
                    tIndex.add(part3);
                    
                    part1 = Integer.parseInt(lineParts[1].split("/")[2]);
                    part2 = Integer.parseInt(lineParts[2].split("/")[2]);
                    part3 = Integer.parseInt(lineParts[3].split("/")[2]);
                    nIndex.add(part1);
                    nIndex.add(part2);
                    nIndex.add(part3);
                }
                if (lineParts[1].matches("\\d//\\d"))
                {
                    int part1 = Integer.parseInt(lineParts[1].split("//")[0]);
                    int part2 = Integer.parseInt(lineParts[2].split("//")[0]);
                    int part3 = Integer.parseInt(lineParts[3].split("//")[0]);
                    vIndex.add(part1);
                    vIndex.add(part2);
                    vIndex.add(part3);
                    
                    part1 = Integer.parseInt(lineParts[1].split("//")[1]);
                    part2 = Integer.parseInt(lineParts[2].split("//")[1]);
                    part3 = Integer.parseInt(lineParts[3].split("//")[1]);
                    nIndex.add(part1);
                    nIndex.add(part2);
                    nIndex.add(part3);
                }
            }
        }
        
        for (Integer normalIndex : nIndex)
        {
            normal.add(tempNorm.get(normalIndex - 1));
        }
        for (Integer textureIndex : tIndex)
        {
            texture.add(tempTex.get(textureIndex - 1));
        }
        return new Mesh(vertex, vIndex, normal, texture);
    }
}

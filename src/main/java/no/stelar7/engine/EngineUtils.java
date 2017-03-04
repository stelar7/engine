package no.stelar7.engine;

import no.stelar7.engine.rendering.models.TextureData;
import org.joml.*;
import org.lwjgl.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;

import static org.lwjgl.opengl.GL11.*;

public final class EngineUtils
{
    
    private EngineUtils()
    {
        // hide public constructor
    }
    
    static
    {
        try
        {
            Handler handler = new FileHandler("opengl.log");
            handler.setFormatter(new Formatter()
            {
                @Override
                public String format(LogRecord record)
                {
                    return record.getMessage() + "\n";
                }
            });
            
            final Logger logger = Logger.getLogger("OpenGL");
            logger.addHandler(handler);
            logger.setLevel(Level.ALL);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static String glErrorToString(final int status)
    {
        switch (status)
        {
            case 0x0500:
                return "Invalid Enum";
            case 0x0501:
                return "Invalid Value";
            case 0x0502:
                return "Invalid Operation";
            case 0x0503:
                return "Stack Overflow";
            case 0x0504:
                return "Stack Underflow";
            case 0x0505:
                return "Out Of Memory";
            case 0x0506:
                return "Invalid Framebuffer Operation";
            case 0x0507:
                return "Context Lost";
            case 0x0508:
                return "Table Too Large";
            default:
                return "Unknown error code " + status;
        }
    }
    
    public static String glTypeToString(final int status)
    {
        switch (status)
        {
            case 0x8B30:
                return "GL_FRAGMENT_SHADER";
            case 0x8B31:
                return "GL_VERTEX_SHADER";
            case 0x8892:
                return "GL_ARRAY_BUFFER";
            case 0x8893:
                return "GL_ELEMENT_ARRAY_BUFFER";
            case 0x8B81:
                return "GL_COMPILE_STATUS";
            case 0x8B82:
                return "GL_LINK_STATUS";
            case 0x8B83:
                return "GL_VALIDATE_STATUS";
            case 0x1406:
                return "GL_FLOAT";
            case 0x1405:
                return "GL_UNSIGNED_INT";
            case 0x100:
                return "GL_DEPTH_BUFFER_BIT";
            case 0x4000:
                return "GL_COLOR_BUFFER_BIT";
            case 0x4:
                return "GL_TRIANGLES";
            case 0x88E4:
                return "GL_STATIC_DRAW";
            case 1:
                return "GL_TRUE";
            default:
                return "Unknown type code " + status;
        }
    }
    
    public static FloatBuffer vector3fListToBuffer(List<Vector3f> data)
    {
        FloatBuffer buff = BufferUtils.createFloatBuffer(data.size() * 3);
        for (int i = 0; i < data.size(); i++)
        {
            data.get(i).get(i * 3, buff);
        }
        return buff;
    }
    
    public static IntBuffer intListToBuffer(List<Integer> data)
    {
        IntBuffer buff = BufferUtils.createIntBuffer(data.size());
        for (Integer item : data)
        {
            buff.put(item);
        }
        buff.flip();
        return buff;
    }
    
    public static FloatBuffer floatListToBuffer(List<Float> data)
    {
        FloatBuffer buff = BufferUtils.createFloatBuffer(data.size());
        for (Float item : data)
        {
            buff.put(item);
        }
        buff.flip();
        return buff;
    }
    
    public static FloatBuffer vector2fListToBuffer(List<Vector2f> data)
    {
        FloatBuffer buff = BufferUtils.createFloatBuffer(data.size() * 2);
        for (int i = 0; i < data.size(); i++)
        {
            data.get(i).get(i * 2, buff);
        }
        return buff;
    }
    
    public static FloatBuffer floatArrayToBuffer(float[] tex)
    {
        FloatBuffer buff = BufferUtils.createFloatBuffer(tex.length);
        buff.put(tex);
        buff.flip();
        return buff;
    }
    
    public static IntBuffer intArrayToBuffer(int[] ind)
    {
        IntBuffer buff = BufferUtils.createIntBuffer(ind.length);
        buff.put(ind);
        buff.flip();
        return buff;
    }
    
    public static String readShader(String filename)
    {
        return readTextFile("/shaders/" + filename);
    }
    
    public static String readTextFile(String pathToFile)
    {
        try
        {
            File             file       = new File(EngineUtils.class.getResource(pathToFile).toURI());
            RandomAccessFile raf        = new RandomAccessFile(file, "r");
            FileChannel      channel    = raf.getChannel();
            MappedByteBuffer buffer     = channel.map(MapMode.READ_ONLY, 0, channel.size());
            byte[]           bufferData = new byte[(int) channel.size()];
            buffer.get(bufferData);
            return new String(bufferData, StandardCharsets.UTF_8);
        } catch (IOException | URISyntaxException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String readObjFile(String filename)
    {
        return readTextFile("/models/" + filename);
    }
    
    public static TextureData loadTexture(String pathToFile)
    {
        try
        {
            BufferedImage         image  = ImageIO.read(EngineUtils.class.getResourceAsStream("/textures/" + pathToFile));
            Map<Integer, Integer> params = new HashMap<>();
            params.put(GL_TEXTURE_WRAP_S, GL_REPEAT);
            params.put(GL_TEXTURE_WRAP_T, GL_REPEAT);
            params.put(GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            params.put(GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            
            TextureData textureData = new TextureData();
            textureData.generateTexture();
            textureData.bind();
            textureData.setParameters(params);
            textureData.setImageData(image);
            return textureData;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public static void log(String data, Object... params)
    {
        if (EngineRunner.DEBUG_MODE)
        {
            Logger.getLogger("OpenGL").fine(String.format(data, params));
        }
    }
    
    public static Object bufferToString(FloatBuffer data)
    {
        StringBuilder result = new StringBuilder("(");
        
        data.mark();
        
        while (data.remaining() > 0)
        {
            result.append(data.get()).append(", ");
        }
        
        result.reverse().deleteCharAt(0).deleteCharAt(0).reverse().append(")");
        data.reset();
        
        return result;
    }
    
    public static Object bufferToString(IntBuffer data)
    {
        StringBuilder result = new StringBuilder("(");
        
        data.mark();
        
        while (data.remaining() > 0)
        {
            result.append(data.get()).append(", ");
        }
        
        result.reverse().deleteCharAt(0).deleteCharAt(0).reverse().append(")");
        data.reset();
        
        return result;
    }
    
    public static Object bufferToString(ByteBuffer data)
    {
        StringBuilder result = new StringBuilder("(");
        
        data.mark();
        
        while (data.remaining() > 0)
        {
            result.append(data.get()).append(", ");
        }
        
        result.reverse().deleteCharAt(0).deleteCharAt(0).reverse().append(")");
        data.reset();
        
        return result;
    }
    
}

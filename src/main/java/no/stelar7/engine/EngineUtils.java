package no.stelar7.engine;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public final class EngineUtils
{
    
    private EngineUtils()
    {
        // Hide public constructor
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
    
    public static String readShader(String filename)
    {
        return readTextFile("/shaders/" + filename);
    }
    
    public static String readTextFile(String pathToFile)
    {
        InputStream stream = EngineUtils.class.getResourceAsStream(pathToFile);
        try (InputStreamReader isr = new InputStreamReader(stream, StandardCharsets.UTF_8))
        {
            try (BufferedReader br = new BufferedReader(isr))
            {
                return br.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
}

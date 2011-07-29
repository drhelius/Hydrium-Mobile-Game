/*
 * SoundEngine.java
 *
 * Created on 16 de septiembre de 2007, 1:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


import javax.microedition.media.*;
import javax.microedition.media.control.*;
import java.io.*;
import javax.microedition.media.Player;


public class SoundEngine implements Runnable
{
    private static long m_lLastUpdate;
    public static boolean ACTIVE = false;
    private String path;
    private Player thePlayer;
    private boolean priority;
        
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public SoundEngine()
    {
        priority = false;        
    }
    
    public SoundEngine(boolean highpriority)
    {
        priority = highpriority;                   
    }
    
    public final void setup(String path)
    {
        this.path = path;
    }
    
    public final void startThread()
    {
        if ((ACTIVE) && (MainTimer.TotalMillis - m_lLastUpdate > (priority ? 800 : 2500)))
	{   
	    m_lLastUpdate = MainTimer.TotalMillis;
            
            (new Thread(this)).start();
	}		
    }
    
    public void run()
    {        
        try 
	{
            InputStream in = getClass().getResourceAsStream(path);
            thePlayer = Manager.createPlayer(in, "audio/x-wav");
            //thePlayer.realize();
            //thePlayer.prefetch();        
            thePlayer.start();
        }
        catch(Exception e)
        {  
            
        }	
    }
}

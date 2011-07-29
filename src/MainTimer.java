/*
 * MainTimer.java
 *
 * Creado el 30 de enero de 2006, 10:55
 *
 * Autor: Nacho Sánchez
 *
 */

public class MainTimer
{
    public static float FPS;
    public static long TotalMillis ;
    public static long DeltaMillis;
    
    public static float DeltaTime;
    public static float HalfDeltaTime;
    
    private static int m_lFrameCount; 
    
    private static long m_lLastUpdate;
    private static long m_lStartTime;    
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public static void start()
    {
	MainCanvas.UTIL_OutputDebugString("MainTimer.start()");
	m_lStartTime = System.currentTimeMillis();
	TotalMillis = 0;
	DeltaMillis = 0;
	DeltaTime = 0;
	m_lLastUpdate = 0;
	m_lFrameCount = 0;
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public static void update()
    {
	DeltaMillis = (System.currentTimeMillis() - m_lStartTime) - TotalMillis;
	
	TotalMillis += DeltaMillis;
	
	DeltaTime = DeltaMillis * 0.001f;
	
	HalfDeltaTime = DeltaTime / 2.0f;
	
	// Aumentamos el nº de frames
	m_lFrameCount++;
	
        /*
	// Calculamos el frame-rate
	if (TotalMillis - m_lLastUpdate > 500)
	{
	    FPS = ((float)m_lFrameCount / ((float)TotalMillis - (float)m_lLastUpdate)) * 1000.0f;
	    m_lFrameCount = 0;
	    m_lLastUpdate = TotalMillis;
	}
        */
    }
}

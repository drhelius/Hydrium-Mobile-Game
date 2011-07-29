/*
 * ShotManager.java
 *
 * Creado el 15 de febrero de 2006, 12:06
 *
 * Autor: Nacho Sánchez
 *
 */

public class ShotManager
{
    public static Shot[] m_ShotArray;
    public static int m_ShotArrayFreeSlot;
    
    public static final int COUNT = 30;
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public static void reset()
    {	
	for (int i=0; i<COUNT; i++)
	{	    
	    m_ShotArray[i].nextSlot = i+1;	
	    m_ShotArray[i].disable = true;
	}
	
	m_ShotArray[COUNT-1].nextSlot = -1;
	
	m_ShotArrayFreeSlot = 0;
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public static void start()
    {
	m_ShotArray = new Shot[COUNT];	
		
	for (int i=0; i<COUNT; i++)
	{
	    m_ShotArray[i] = new Shot();
	}
	
	reset();	
	
	Shot.start();
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public static void addShot(float posx, float posy, float velx, float vely, byte type)
    {
	if (m_ShotArrayFreeSlot < 0)
	{
	    return;
	}
	
	if (velx > 70.0f)
	    velx = 70.0f;
	else if (velx < -70.0f)
	    velx = -70.0f;
	
	if (vely > 70.0f)
	    vely = 70.0f;
	else if (vely < -70.0f)
	    vely = -70.0f;    
	
	m_ShotArray[m_ShotArrayFreeSlot].fx = posx;
	m_ShotArray[m_ShotArrayFreeSlot].fy = posy;
	m_ShotArray[m_ShotArrayFreeSlot].velX = velx;
	m_ShotArray[m_ShotArrayFreeSlot].velY = vely;
	m_ShotArray[m_ShotArrayFreeSlot].disable = false;
	m_ShotArray[m_ShotArrayFreeSlot].type = type;
	m_ShotArray[m_ShotArrayFreeSlot].LastFrameUpdate = MainTimer.TotalMillis;
	
	m_ShotArrayFreeSlot = m_ShotArray[m_ShotArrayFreeSlot].nextSlot;   
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public static void clearAll()
    {
	for (int i=0; i<COUNT; i++)
	{
	    //if (!m_ShotArray[i].disable)
	    {
		m_ShotArray[i].rendered = false;
	    }
	}    
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public static void update()
    {
	for (int i=0; i<COUNT; i++)
	{
	    if (!m_ShotArray[i].disable)
	    {	
		int last = 0;
		
		switch (m_ShotArray[i].type)
		{
		    ///--- disparo amarillo bola
		    ///--- disparo rojo bola
		    case 0:		    
		    case 1:
		    {
			last = 2000;
			break;
		    }
		    ///--- disparo recto enemigo
		    case 3:
		    {
			last = 4000;
			break;
		    }
		    ///--- disparo dirigido enemigo
		    case 4:
		    {
			last = 3000;
			break;
		    }
		}
			
		if ((MainTimer.TotalMillis-m_ShotArray[i].LastFrameUpdate) > last)
		{
		    m_ShotArray[i].disable = true;
		    m_ShotArray[i].nextSlot = m_ShotArrayFreeSlot;
		    m_ShotArrayFreeSlot = i;
		}
		else
		{
		    m_ShotArray[i].update();
		    
		    if ((m_ShotArray[i].type >= 2) && m_ShotArray[i].collision)
		    {
			m_ShotArray[i].disable = true;
			m_ShotArray[i].nextSlot = m_ShotArrayFreeSlot;
			m_ShotArrayFreeSlot = i;			
		    } 
		    ///--- disparos de los enemigos
		    else if (m_ShotArray[i].type > 2)
		    {
			///--- si colisiona
			if (((m_ShotArray[i].x + 8) > (MainCanvas.TheBall.PosX - MainCanvas.TheBall.Radius)) && ((m_ShotArray[i].y + 8) > (MainCanvas.TheBall.PosY - MainCanvas.TheBall.Radius)) && ((MainCanvas.TheBall.PosX + MainCanvas.TheBall.Radius) > m_ShotArray[i].x) && ((MainCanvas.TheBall.PosY + MainCanvas.TheBall.Radius) > m_ShotArray[i].y))
			{
			    m_ShotArray[i].disable = true;
			    m_ShotArray[i].nextSlot = m_ShotArrayFreeSlot;
			    m_ShotArrayFreeSlot = i;
			    
			    MainCanvas.TheBall.addMediumExplosion(m_ShotArray[i].x + 4, m_ShotArray[i].y + 4);
			    
			    MainCanvas.TheBall.fRadius -= 0.5f;
			    MainCanvas.TheBall.Radius = (int)MainCanvas.TheBall.fRadius;
			    
			    int dirx = (int)(MainCanvas.TheBall.PosX) - m_ShotArray[i].x;
			    dirx *= 10;
			    int diry = (int)(MainCanvas.TheBall.PosY) - m_ShotArray[i].y;
			    diry *= 10;

			    float dis = (float)(Math.sqrt((dirx * dirx) + (diry * diry)));
			    float dirxnor = dirx / dis;
			    float dirynor = diry / dis;

			    MainCanvas.TheBall.VelX += (dirxnor * 30.0f);
			    MainCanvas.TheBall.VelY += (dirynor * 30.0f);
			    
			    if (MainCanvas.TheBall.VelX < -MainCanvas.m_iMaxVelocity)
			    {
				MainCanvas.TheBall.VelX = -MainCanvas.m_iMaxVelocity;		
			    }  
			    else if (MainCanvas.TheBall.VelX > MainCanvas.m_iMaxVelocity)
			    {
				MainCanvas.TheBall.VelX = MainCanvas.m_iMaxVelocity;		
			    }

			    if (MainCanvas.TheBall.VelY < -MainCanvas.m_iMaxVelocity)
			    {
				MainCanvas.TheBall.VelY = -MainCanvas.m_iMaxVelocity;		
			    }  
			    else if (MainCanvas.TheBall.VelY > MainCanvas.m_iMaxVelocity)
			    {
				MainCanvas.TheBall.VelY = MainCanvas.m_iMaxVelocity;		
			    }
			}		   
		    }
		    ///--- disparos de la bola
		    else
		    {
			for (int h=0; h<Enemies.EnemyArray.length; h++)
			{
			    if (Enemies.EnemyArray[h].disable)
				continue;
			    
			    if (((m_ShotArray[i].x + 16) > Enemies.EnemyArray[h].x) && ((m_ShotArray[i].y + 16) > Enemies.EnemyArray[h].y) && ((Enemies.EnemyArray[h].x + 16) > m_ShotArray[i].x) && ((Enemies.EnemyArray[h].y + 16) > m_ShotArray[i].y))
			    {
				Enemies.EnemyArray[h].disable = true;
				
				m_ShotArray[i].disable = true;
				m_ShotArray[i].nextSlot = m_ShotArrayFreeSlot;
				m_ShotArrayFreeSlot = i;
                                
                                SoundEngine se = new SoundEngine();
                                se.setup(MainCanvas.SOUNDPATH_GEMEXPLO);
                                se.startThread(); 
				
				MainCanvas.TheBall.addSmallExplosion(Enemies.EnemyArray[h].x + 8, Enemies.EnemyArray[h].y + 8);
			    }		    
			}
			
			for (int h=0; h<Map.StaticObjectsArray.length; h++)
			{
			    if (Map.StaticObjectsArray[h].type != 2)
				continue;
			    
			    if (Map.StaticObjectsArray[h].disable)
				continue;
			    
			    if (((m_ShotArray[i].x + 16) > Map.StaticObjectsArray[h].x) && ((m_ShotArray[i].y + 16) > Map.StaticObjectsArray[h].y) && ((Map.StaticObjectsArray[h].x + 16) > m_ShotArray[i].x) && ((Map.StaticObjectsArray[h].y + 16) > m_ShotArray[i].y))
			    {
				Map.StaticObjectsArray[h].disable = true;
				
				m_ShotArray[i].disable = true;
				m_ShotArray[i].nextSlot = m_ShotArrayFreeSlot;
				m_ShotArrayFreeSlot = i;
                                
                                SoundEngine se = new SoundEngine();
                                se.setup(MainCanvas.SOUNDPATH_GEMEXPLO);
                                se.startThread(); 
				
				MainCanvas.TheBall.addSmallExplosion(Map.StaticObjectsArray[h].x + 8, Map.StaticObjectsArray[h].y + 8);
			    }
			}
		    }
		}
	    }
	}    
    }
}

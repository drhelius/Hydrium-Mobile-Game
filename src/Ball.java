/*
 * Ball.java
 *
 * Creado el 27 de enero de 2006, 10:06
 *
 * Autor: Nacho Sánchez
 *
 */

import java.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class Ball extends RenderObject
{
    public float PosX;
    public float PosY;
    
    public float VelX;
    public float VelY;
    
    private int m_iPosX;
    private int m_iPosY;    
   
    public int Radius;
    public float fRadius;   
    
    private Image m_BallImage;
    private Sprite m_BallSprite;
    
    private Image m_BallImageRed;
    private Sprite m_BallSpriteRed;
    
    private Image m_BallImageYellow;
    private Sprite m_BallSpriteYellow;
    
    public MapSector Sector1, Sector2, Sector3, Sector4;
    
    public Smoke[] m_SmokeArray;
    public int m_SmokeArrayFreeSlot;
    
    private static final float m_fSpeedDecrement = 0.7f;
   
    private final static int[] FRAME_SEQUENCE = {0, 1 , 2, 3, 4};
       
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public Ball()
    {
	Radius = 8;
	fRadius = 8.99f;
	Sector1 = Sector2 = Sector3 = Sector4 = null;
	VelY = VelX = 0;
	m_iPosX = 0;
	m_iPosY = 0;		
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void reset()
    {
	Radius = 8;
	fRadius = 8.99f;
	Sector1 = Sector2 = Sector3 = Sector4 = null;
	VelY = VelX = 0;
	m_iPosX = 0;
	m_iPosY = 0;	
	
	for (int i=0; i<30; i++)
	{	    
	    m_SmokeArray[i].nextSlot = i+1;	
	    m_SmokeArray[i].disable = true;
	}
	
	m_SmokeArray[29].nextSlot = -1;
	
	m_SmokeArrayFreeSlot = 0;
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void update()
    {
	
        Radius = (int)fRadius;
		
	if (Radius >= 8)
	{
	    m_BallSprite.setFrame(0);
	    m_BallSpriteRed.setFrame(0);
	    m_BallSpriteYellow.setFrame(0);
	}
	else if (Radius == 7)
	{
	    m_BallSprite.setFrame(1);
	    m_BallSpriteRed.setFrame(1);
	    m_BallSpriteYellow.setFrame(1);
	}
	else if (Radius == 6)
	{
	    m_BallSprite.setFrame(2);	
	    m_BallSpriteRed.setFrame(2);
	    m_BallSpriteYellow.setFrame(2);
	}
	else if (Radius == 5)
	{
	    m_BallSprite.setFrame(3);	
	    m_BallSpriteRed.setFrame(3);
	    m_BallSpriteYellow.setFrame(3);
	}
	else if (Radius <= 4)
	{
	    m_BallSprite.setFrame(4);
	    m_BallSpriteRed.setFrame(4);
	    m_BallSpriteYellow.setFrame(4);
	}
	
	Sector2 = Sector3 = Sector4 = null;
	
	PosX += (VelX * MainTimer.DeltaTime);
	PosY += (VelY * MainTimer.DeltaTime);
	
	CollisionData.velX = VelX;
	CollisionData.velY = VelY;
	CollisionData.x = (int)PosX;
	CollisionData.y = (int)PosY;
	CollisionData.radius = Radius;	
	
	int sectorx = (m_iPosX - Radius) / 64;
	int sectory = (m_iPosY - Radius) / 64;
		
	int sectorx1 = (m_iPosX + Radius) / 64;		
	int sectory1 = (m_iPosY + Radius) / 64;
	
	boolean col = false;
	
	Sector1 = Map.SectorArray[sectorx][sectory];
	
	if (Map.testCollisionSector(Sector1, true))
	{	    
	    col = true;
	}
	
	Sector1.dynamicObjects.addElement(this);
	
	if (sectorx1 != sectorx)
	{
	    Sector2 = Map.SectorArray[sectorx1][sectory];
	    
	    Sector2.dynamicObjects.addElement(this);
	   
	    if (Map.testCollisionSector(Sector2, true))
	    {		
		col = true;
	    }
	}
	else
	    Sector2 = null;
	
	if (sectory1 != sectory)
	{
	    Sector3 = Map.SectorArray[sectorx][sectory1];
	    
	    Sector3.dynamicObjects.addElement(this);
	    
	    if (Map.testCollisionSector(Sector3, true))
	    {		
		col = true;
	    }
	}
	else
	    Sector3 = null;
	
	if ((sectorx1 != sectorx) && (sectory1 != sectory))
	{
	    Sector4 = Map.SectorArray[sectorx1][sectory1];
	    
	    Sector4.dynamicObjects.addElement(this);
	    
	    if (Map.testCollisionSector(Sector4, true))
	    {
		col = true;
	    }		
	}
	else
	    Sector4 = null;
	
	if (col)
	{
	    VelX = CollisionData.velX;
	    VelY = CollisionData.velY;
	    PosX = CollisionData.x;
	    PosY = CollisionData.y;
	   
            VelX *= m_fSpeedDecrement;
            VelY *= m_fSpeedDecrement;	
            
            if ((VelX < 8.0f) && (VelX > -8.0f))
            {
                VelX = 0;
            }

            if ((VelY < 8.0f) && (VelY > -8.0f))
            {
                VelY = 0;
            }
	}      
	
	m_iPosX = (int)PosX;
	m_iPosY = (int)PosY;	
	
	distance = ((1500 - m_iPosX) + m_iPosY );	
	
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void loadGFX()
    {
	try
	{
	    m_BallImage = Image.createImage("/gfx/bola.png");
	    m_BallImageRed = Image.createImage("/gfx/bola_red.png");
	    m_BallImageYellow = Image.createImage("/gfx/bola_yellow.png");
	}
	catch(IOException e)
	{            
	    System.err.println("Error al cargar /gfx/bola.png - " + e);
	}
	
	m_BallSprite = new Sprite(m_BallImage, 16, 16);	
	m_BallSprite.setFrameSequence(FRAME_SEQUENCE);
	m_BallSprite.setFrame(0);
	
	m_BallSpriteRed = new Sprite(m_BallImageRed, 16, 16);	
	m_BallSpriteRed.setFrameSequence(FRAME_SEQUENCE);
	m_BallSpriteRed.setFrame(0);
	
	m_BallSpriteYellow = new Sprite(m_BallImageYellow, 16, 16);	
	m_BallSpriteYellow.setFrameSequence(FRAME_SEQUENCE);
	m_BallSpriteYellow.setFrame(0);
	
	m_SmokeArray = new Smoke[30];	
		
	for (int i=0; i<30; i++)
	{
	    m_SmokeArray[i] = new Smoke();
	    m_SmokeArray[i].nextSlot = i+1;	
	    m_SmokeArray[i].disable = true;
	}
	
	m_SmokeArray[29].nextSlot = -1;
	
	m_SmokeArrayFreeSlot = 0;
	
	Smoke.start();
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void render()
    {
	if (MainCanvas.m_iRedShots > 0)
	{
	    m_BallSpriteRed.setPosition(m_iPosX - Map.CameraX - 8, m_iPosY - Map.CameraY - 8);
	    m_BallSpriteRed.paint(MainCanvas.GFX);	
	}
	else if (MainCanvas.m_iYellowShots > 0)
	{	    
	    m_BallSpriteYellow.setPosition(m_iPosX - Map.CameraX - 8, m_iPosY - Map.CameraY - 8);
	    m_BallSpriteYellow.paint(MainCanvas.GFX);	
	}
	else
	{
	    m_BallSprite.setPosition(m_iPosX - Map.CameraX - 8, m_iPosY - Map.CameraY - 8);
	    m_BallSprite.paint(MainCanvas.GFX);
	}
	
	
	//HydriumCanvas.GFX.drawImage(m_BallImage, m_iPosX - Map.CameraX, m_iPosY - Map.CameraY, Graphics.HCENTER | Graphics.VCENTER);	
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void renderSmoke()
    {
	for (int i=0; i<30; i++)
	{
	    if (!m_SmokeArray[i].disable)
	    {
		m_SmokeArray[i].update();
		
		if (m_SmokeArray[i].fRadius < 0)
		{
		    m_SmokeArray[i].disable = true;
		    m_SmokeArray[i].nextSlot = m_SmokeArrayFreeSlot;
		    m_SmokeArrayFreeSlot = i;
		}
		else
		{
		    m_SmokeArray[i].render();
		}
	    }
	}	
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void addSmoke(float posx, float posy, float velx, float vely, float radius)
    {
	if (m_SmokeArrayFreeSlot < 0)
	{
	    return;
	}
	
	m_SmokeArray[m_SmokeArrayFreeSlot].fx = posx;
	m_SmokeArray[m_SmokeArrayFreeSlot].fy = posy;
	m_SmokeArray[m_SmokeArrayFreeSlot].velX = velx;
	m_SmokeArray[m_SmokeArrayFreeSlot].velY = vely;
	m_SmokeArray[m_SmokeArrayFreeSlot].fRadius = radius;
	m_SmokeArray[m_SmokeArrayFreeSlot].disable = false;
	
	m_SmokeArrayFreeSlot = m_SmokeArray[m_SmokeArrayFreeSlot].nextSlot;   
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void addSmallExplosion(float posx, float posy)
    {
	addSmoke(posx, posy, 0, -30, 5.0f);
	addSmoke(posx, posy, 0, 30, 5.0f);
	addSmoke(posx, posy, -30, 0, 5.0f);
	addSmoke(posx, posy, 30, 0, 5.0f);
	addSmoke(posx, posy, -21, -21, 5.0f);
	addSmoke(posx, posy, 21, -21, 5.0f);
	addSmoke(posx, posy, -21, 21, 5.0f);
	addSmoke(posx, posy, 21, 21, 5.0f);
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void addBigExplosion(float posx, float posy)
    {
	addSmoke(posx, posy, 0, -100, 5.0f);
	addSmoke(posx, posy, 0, 100, 5.0f);
	addSmoke(posx, posy, -100, 0, 5.0f);
	addSmoke(posx, posy, 100, 0, 5.0f);
	addSmoke(posx, posy, -70, -70, 5.0f);
	addSmoke(posx, posy, 70, -70, 5.0f);
	addSmoke(posx, posy, -70, 70, 5.0f);
	addSmoke(posx, posy, 70, 70, 5.0f);
    }
        
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void addMediumExplosion(float posx, float posy)
    {
	addSmoke(posx, posy, 0, -50, 5.0f);
	addSmoke(posx, posy, 0, 50, 5.0f);
	addSmoke(posx, posy, -50, 0, 5.0f);
	addSmoke(posx, posy, 50, 0, 5.0f);
	addSmoke(posx, posy, -35, -35, 5.0f);
	addSmoke(posx, posy, 35, -35, 5.0f);
	addSmoke(posx, posy, -35, 35, 5.0f);
	addSmoke(posx, posy, 35, 35, 5.0f);
    }
}

/*
 * Map.java
 *
 * Creado el 26 de enero de 2006, 9:03
 *
 * Autor: Nacho Sánchez
 *
 */

import java.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.util.*;

public class Map
{   
    
    private final static float TIME_LIGHT = 0.48f;
    public static MapSector[][] SectorArray;
    
    public static MapSector[][] SectorArray1;
    public static MapSector[][] SectorArray2;
    
    public static TransPath m_iTranportPaths[];
    
    private static int m_iSectorCountX;
    private static int m_iSectorCountY;
    private static int m_iBackgroundCountX;
    private static int m_iBackgroundCountY;
    private static int m_iScreenSectorCountX;
    private static int m_iScreenSectorCountY;
    private static int m_iSectorCount;
    
    private static int m_iSectorCountX1;
    private static int m_iSectorCountY1;
    private static int m_iSectorCount1;
    private static int m_iSectorCountX2;
    private static int m_iSectorCountY2;
    private static int m_iSectorCount2;
    
    private static final byte m_CubeImagesCount = 14;
    private static final byte m_ItemImagesCount = 9;
    private static final byte m_EnemyImagesCount = 4;
    
    public static int StaticObjectCount;
    public static StaticObject[] StaticObjectsArray;  
    
    public static int StaticObjectCount1;
    public static StaticObject[] StaticObjectsArray1;  
    public static int StaticObjectCount2;
    public static StaticObject[] StaticObjectsArray2;  
    
    public static int DynamicObjectCount;
    public static DynamicObject[] DynamicObjectsArray;
   
    public static Image[] CubeImageArray;
    public static Image[] ItemImageArray;
    public static Image[] EnemyImageArray;
    public static Image BackgroundImage;
    public static Image Trans;
    
    public static int CameraX;
    public static int CameraY;
    
    private static float m_fCameraX;
    private static float m_fCameraY;
    
    public static int ScreenWidth;
    public static int ScreenHeight;
    private static int m_iScreenWidthHalf;
    private static int m_iScreenHeightHalf;
    
    private static DynamicList m_RenderVector;    
    
    public static int GemsLeft;
    
    private final static int[] CUBE_FRAME_SEQUENCE = {0, 1 , 2, 3, 4};
    private final static int[] DOOR_FRAME_SEQUENCE = {3, 2 , 1, 0};
    private final static int[] OFFSET_FRAME_SEQUENCE = {0, 1, 2, 3};
    private final static int[] TRANSPORT_FRAME_SEQUENCE = {0, 0, 0, 1, 2, 1};
    private final static int[] GEMS_FRAME_SEQUENCE = {0, 1, 2, 3, 4, 5, 6};
    
    private static long m_lLastUpdateCintas;
    public static int m_iFrameCintas;
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public static void loadAll(int scrWidth, int scrHeight)
    {		
	ScreenWidth = scrWidth;
	ScreenHeight = scrHeight;
	
	m_iBackgroundCountX = (ScreenWidth / 128) + 2 ;
	m_iBackgroundCountY = (ScreenHeight / 128) + 2 ;
	
	m_iScreenWidthHalf = (ScreenWidth / 2);
	m_iScreenHeightHalf = (ScreenHeight / 2);
	
	///--- número de sectores que caben en la pantalla
	m_iScreenSectorCountX = ((ScreenWidth / 64) + 1);
	m_iScreenSectorCountY = ((ScreenHeight / 64) + 2);
	
	m_iTranportPaths = new TransPath[512];
	
	for (int i = 0; i < 512; i++)
	{
	    m_iTranportPaths[i] = new TransPath();
	    m_iTranportPaths[i].origin = null;
	    m_iTranportPaths[i].destination = null;
	}
	
	CubeImageArray = new Image[m_CubeImagesCount];
		
	for (int i = 0; i < m_CubeImagesCount; i++)
	{
	    try
	    {
		CubeImageArray[i] = Image.createImage("/gfx/cubos/" + (((i+1) < 10) ? "0" : "") + (i + 1) + ".png");
	    }
	    catch(IOException e)
	    {            
		System.err.println("Error al cargar /gfx/cubos/" + (((i+1) < 10) ? "0" : "") + (i + 1) + ".png - " + e);
	    }
	}
	
	ItemImageArray = new Image[m_ItemImagesCount];
	
	try
	{
	    ItemImageArray[0] = Image.createImage("/gfx/salida.png");
	    ///--- dejar el 1 libre
	    ItemImageArray[2] = Image.createImage("/gfx/gema1.png");
	    ItemImageArray[3] = Image.createImage("/gfx/gema2.png");
	    ItemImageArray[4] = Image.createImage("/gfx/gema3.png");
	    ItemImageArray[5] = Image.createImage("/gfx/gema4.png");
	    ItemImageArray[6] = Image.createImage("/gfx/cinta.png");
	    ItemImageArray[7] = Image.createImage("/gfx/llave1.png");
	    ItemImageArray[8] = Image.createImage("/gfx/llave2.png");
	}
	catch(IOException e)
	{            
	    System.err.println("Error al cargar gráfico de item - " + e);
	}
	
	EnemyImageArray = new Image[m_EnemyImagesCount];
	
	try
	{
	    EnemyImageArray[0] = Image.createImage("/gfx/ene1.png");
	    EnemyImageArray[1] = Image.createImage("/gfx/ene2.png");
	    EnemyImageArray[2] = Image.createImage("/gfx/ene3.png");
	    EnemyImageArray[3] = Image.createImage("/gfx/ene4.png");
	}
	catch(IOException e)
	{            
	    System.err.println("Error al cargar gráfico de enemigo - " + e);
	}
	
	try
	{
	    Trans = Image.createImage("/gfx/trans.png");
	}
	catch(IOException e)
	{            
	    System.err.println("Error al cargar eñ gráfico de transportador - " + e);
	}
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////    
    
    public static void updateCamera(Ball b)
    {
	float tempx = ((int)b.PosX - m_iScreenWidthHalf - CameraX) * 1.7f * MainTimer.DeltaTime;  
	float tempy = ((int)b.PosY - m_iScreenHeightHalf - CameraY) * 1.7f * MainTimer.DeltaTime;
	
	m_fCameraX += tempx;
	m_fCameraY += tempy;
	
	CameraX = (int)m_fCameraX;
	CameraY = (int)m_fCameraY;
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////    
    
    
    public static void unloadMap()
    {		
	BackgroundImage = null;
	StaticObjectsArray = null;
	StaticObjectsArray1 = null;
	StaticObjectsArray2 = null;
	DynamicObjectsArray = null;
	SectorArray = null;
	SectorArray1 = null;
	SectorArray2 = null;
	m_RenderVector = null;
	System.gc();
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public static void loadMap(DataInputStream dis)
    {		
	try
	{
	    m_RenderVector = new DynamicList();	
	
	    int actualTrans = 0;
	    
	    for (int i = 0; i < 512; i++)
	    {		
		m_iTranportPaths[i].origin = null;
		m_iTranportPaths[i].destination = null;
	    }
	    
	    GemsLeft=0;	    
	    
	    ///--- leemos el tipo de fondo
	    int fondo = dis.readByte() + 1;	    
	
	    BackgroundImage = Image.createImage("/gfx/bg/" + ((fondo < 10) ? "0" : "") + fondo + ".png");
	    
	    ///--- leemos el número de objetos estáticos
	    StaticObjectCount = dis.readShort();	    
	    	    
	    StaticObjectsArray = new StaticObject[StaticObjectCount];
	    
	    ///--- leemos todos los objetos estáticos
	    for (int i = 0; i < StaticObjectCount; i++)
	    {
		StaticObjectsArray[i] = new StaticObject();
		StaticObjectsArray[i].type = dis.readByte();
		StaticObjectsArray[i].id = dis.readByte();
		StaticObjectsArray[i].x = dis.readShort() * 4;
		StaticObjectsArray[i].y = dis.readShort() * 4;
		StaticObjectsArray[i].rotation = dis.readByte();
		
		///--- si es un cubo leemos los datos de los laterales
		if (StaticObjectsArray[i].type == 0)
		{
		    StaticObjectsArray[i].sides = dis.readByte();
		    StaticObjectsArray[i].sprite = new Sprite(CubeImageArray[StaticObjectsArray[i].id], 19, 22);
		    
		    if (StaticObjectsArray[i].id == 10 || StaticObjectsArray[i].id == 11)
		    {
			StaticObjectsArray[i].sprite.setFrameSequence(DOOR_FRAME_SEQUENCE);			
		    }
		    else
		    {
			StaticObjectsArray[i].sprite.setFrameSequence(CUBE_FRAME_SEQUENCE);
		    }
		   
		}	
		///--- es un item
		else if (StaticObjectsArray[i].type == 1)
		{
                    ///--- es una gema
		    if (StaticObjectsArray[i].id > 1 && StaticObjectsArray[i].id < 6)
		    {
			StaticObjectsArray[i].sprite = new Sprite(ItemImageArray[StaticObjectsArray[i].id], 16, 16);
			
			StaticObjectsArray[i].sprite.setFrameSequence(GEMS_FRAME_SEQUENCE);	
                        
                        StaticObjectsArray[i].sprite.setFrame(i%7);
			
                        if (StaticObjectsArray[i].id == 2)
                            GemsLeft++;
		    }
                    ///--- una llave
                    else if (StaticObjectsArray[i].id > 6 && StaticObjectsArray[i].id < 9)
		    {
			StaticObjectsArray[i].sprite = new Sprite(ItemImageArray[StaticObjectsArray[i].id], 16, 16);
			
			StaticObjectsArray[i].sprite.setFrameSequence(GEMS_FRAME_SEQUENCE);	
                        
                        StaticObjectsArray[i].sprite.setFrame(i%7);
                    }
		    ///--- cinta trans
		    else if (StaticObjectsArray[i].id == 6)
		    {
			StaticObjectsArray[i].sprite = new Sprite(ItemImageArray[StaticObjectsArray[i].id], 16, 16);
			
			StaticObjectsArray[i].sprite.setFrameSequence(OFFSET_FRAME_SEQUENCE);
			
			switch (StaticObjectsArray[i].rotation)
			{
			   case 1:
			   {
			       StaticObjectsArray[i].sprite.setTransform(Sprite.TRANS_ROT90);
			       break;
			   }
			   case 2:
			   {
			       StaticObjectsArray[i].sprite.setTransform(Sprite.TRANS_ROT180);
			       break;
			   }
			   case 3:
			   {
			       StaticObjectsArray[i].sprite.setTransform(Sprite.TRANS_ROT270);
			       break;
			   }
			}			
		    }
                    ///--- salida
                    else if (StaticObjectsArray[i].id == 0)
		    {
			StaticObjectsArray[i].sprite = new Sprite(ItemImageArray[StaticObjectsArray[i].id], 16, 16);
			
			StaticObjectsArray[i].sprite.setFrameSequence(OFFSET_FRAME_SEQUENCE);				
		    }                    
		    else
		    {					    
			if (StaticObjectsArray[i].id !=1)
			    StaticObjectsArray[i].sprite = new Sprite(ItemImageArray[StaticObjectsArray[i].id]);
		    }
		}
		///--- es un enemigo
		else if (StaticObjectsArray[i].type == 2)
		{
		   StaticObjectsArray[i].sprite = new Sprite(EnemyImageArray[StaticObjectsArray[i].id]);
		   
		   switch (StaticObjectsArray[i].rotation)
		   {
		       case 1:
		       {
			   StaticObjectsArray[i].sprite.setTransform(Sprite.TRANS_ROT90);
			   break;
		       }
		       case 2:
		       {
			   StaticObjectsArray[i].sprite.setTransform(Sprite.TRANS_ROT180);
			   break;
		       }
		       case 3:
		       {
			   StaticObjectsArray[i].sprite.setTransform(Sprite.TRANS_ROT270);
			   break;
		       }
		   }		  
		}
		///--- es un transportador
		else if (StaticObjectsArray[i].type == 3)
		{
		    StaticObjectsArray[i].sprite = new Sprite(Trans, 16, 16);	  
                    
                    StaticObjectsArray[i].sprite.setFrameSequence(TRANSPORT_FRAME_SEQUENCE);		   
		    		    
		    if (m_iTranportPaths[StaticObjectsArray[i].rotation].origin == null)
		    {
			m_iTranportPaths[StaticObjectsArray[i].rotation].origin = StaticObjectsArray[i];
		    }
		    else
		    {
			m_iTranportPaths[StaticObjectsArray[i].rotation].destination = StaticObjectsArray[i];
		    }		    
		}
		
		///--- los objetos q siempre se pintan en el fondo tienen distancia 0
		if (StaticObjectsArray[i].type == 1 && (StaticObjectsArray[i].id == 0 || StaticObjectsArray[i].id == 6))
		    StaticObjectsArray[i].distance = 0;
		///--- los transports
		else if (StaticObjectsArray[i].type == 3)
		    StaticObjectsArray[i].distance = 0;
		///--- los demas usan su distancia
		else		    
		    StaticObjectsArray[i].distance = (short)((1500 - (StaticObjectsArray[i].x + 10)) + StaticObjectsArray[i].y + 5);		

		///--- desde aquí debe salir la bola
		if (StaticObjectsArray[i].type == 1 && StaticObjectsArray[i].id == 1)
		{
		    StaticObjectsArray[i].disable = true;
		    
		    MainCanvas.TheBall.PosX = StaticObjectsArray[i].x + MainCanvas.TheBall.Radius;
		    MainCanvas.TheBall.PosY = StaticObjectsArray[i].y + MainCanvas.TheBall.Radius;
		    
		    m_fCameraX = MainCanvas.TheBall.PosX - m_iScreenWidthHalf;
		    m_fCameraY = MainCanvas.TheBall.PosY - m_iScreenHeightHalf;
		    
		    CameraX = (int)m_fCameraX;
		    CameraY = (int)m_fCameraY;
		}
		
		//StaticObjectsArray[i].start();
	    }
	    
	    ///--- leemos el número de objetos dinámicos
	    DynamicObjectCount = dis.readShort();
	    
	    DynamicObjectsArray = new DynamicObject[DynamicObjectCount];
	    
	    ///--- leemos todos los objetos dinámicos
	    for (int i = 0; i < DynamicObjectCount; i++)
	    {
		byte type = dis.readByte();
		byte id = dis.readByte();
		
		if (type == 2)
		{
		    ///--- bouncy
		    if (id == 0)
		    {
			DynamicObjectsArray[i] = new BounceEnemy();
			DynamicObjectsArray[i].type = type;
			DynamicObjectsArray[i].id = id;
			DynamicObjectsArray[i].fx = DynamicObjectsArray[i]. x = dis.readShort() * 4;
			DynamicObjectsArray[i].fy = DynamicObjectsArray[i]. y = dis.readShort() * 4;
			DynamicObjectsArray[i].rotation = dis.readByte();
			DynamicObjectsArray[i].distance = (short)((1500 - StaticObjectsArray[i].x + 6) + StaticObjectsArray[i].y - 8);
			((BounceEnemy)DynamicObjectsArray[i]).start();
		    }		   
		    ///--- search
		    else if (id == 3)
		    {
			DynamicObjectsArray[i] = new SearchEnemy();
			DynamicObjectsArray[i].type = type;
			DynamicObjectsArray[i].id = id;
			DynamicObjectsArray[i].fx = DynamicObjectsArray[i]. x = dis.readShort() * 4;
			DynamicObjectsArray[i].fy = DynamicObjectsArray[i]. y = dis.readShort() * 4;
			DynamicObjectsArray[i].rotation = dis.readByte();
			DynamicObjectsArray[i].distance = (short)((1500 - StaticObjectsArray[i].x + 6) + StaticObjectsArray[i].y - 8);
			((SearchEnemy)DynamicObjectsArray[i]).start();
		    }
		}
	    }
	    
	    ///--- cargamos los enemigos
	    Enemies.loadAll();
	    
	    ///--- leemos el número de sectores
	    m_iSectorCountX = dis.readShort();
	    m_iSectorCountY = dis.readShort();    
 	    
	    SectorArray = new MapSector[m_iSectorCountX][m_iSectorCountY];
	    	    
	    m_iSectorCount = (m_iSectorCountX * m_iSectorCountY);
	    
	    ///--- leemos todos los sectores
	    for (int j = 0; j < m_iSectorCountY; j++)
	    {		
		for (int i = 0; i < m_iSectorCountX; i++)
		{
		    int objectCount = dis.readByte();
		    
		    SectorArray[i][j] = new MapSector();
		    
		    SectorArray[i][j].staticObjectsIndices = new short[objectCount];
		    
		    ///--- leemos los índices de cada sector
		    for (int a = 0; a < objectCount; a++)		
		    {
			SectorArray[i][j].staticObjectsIndices[a] = dis.readShort();
		    }	
		}
	    }
	    
	    
	    ///////////////////
	    
	    
	    ///--- leemos el número de objetos estáticos
	    StaticObjectCount1 = dis.readShort();	    
	    	    
	    StaticObjectsArray1 = new StaticObject[StaticObjectCount1];
	    
	    ///--- leemos todos los objetos estáticos
	    for (int i = 0; i < StaticObjectCount1; i++)
	    {
		StaticObjectsArray1[i] = new StaticObject();
		StaticObjectsArray1[i].type = dis.readByte();
		StaticObjectsArray1[i].id = dis.readByte();
		StaticObjectsArray1[i].x = dis.readShort() * 4;
		StaticObjectsArray1[i].y = dis.readShort() * 4;
		StaticObjectsArray1[i].rotation = dis.readByte();
		
		///--- si es un cubo leemos los datos de los laterales
		if (StaticObjectsArray1[i].type == 0)
		{
		    StaticObjectsArray1[i].sides = dis.readByte();
		    StaticObjectsArray1[i].sprite = new Sprite(CubeImageArray[StaticObjectsArray1[i].id], 19, 22);
		    StaticObjectsArray1[i].sprite.setFrameSequence(CUBE_FRAME_SEQUENCE);
		   
		}	
		
		StaticObjectsArray1[i].distance = (short)((1500 - (StaticObjectsArray1[i].x + 10)) + StaticObjectsArray1[i].y + 5);			
	    }
	    
	    ///--- leemos el número de objetos dinámicos
	    short dynCount = dis.readShort();	    
	   	    
	    ///--- leemos el número de sectores
	    m_iSectorCountX1 = dis.readShort();
	    m_iSectorCountY1 = dis.readShort();    
 	    
	    SectorArray1 = new MapSector[m_iSectorCountX1][m_iSectorCountY1];
	    	    
	    m_iSectorCount1 = (m_iSectorCountX1 * m_iSectorCountY1);
	    
	    ///--- leemos todos los sectores
	    for (int j = 0; j < m_iSectorCountY1; j++)
	    {		
		for (int i = 0; i < m_iSectorCountX1; i++)
		{
		    int objectCount = dis.readByte();
		    
		    SectorArray1[i][j] = new MapSector();
		    
		    SectorArray1[i][j].staticObjectsIndices = new short[objectCount];
		    
		    ///--- leemos los índices de cada sector
		    for (int a = 0; a < objectCount; a++)		
		    {
			SectorArray1[i][j].staticObjectsIndices[a] = dis.readShort();
		    }	
		}
	    }	
	    
	    
	    ///////////////////
	    
	    
	    ///--- leemos el número de objetos estáticos
	    StaticObjectCount2 = dis.readShort();	    
	    	    
	    StaticObjectsArray2 = new StaticObject[StaticObjectCount2];
	    
	    ///--- leemos todos los objetos estáticos
	    for (int i = 0; i < StaticObjectCount2; i++)
	    {
		StaticObjectsArray2[i] = new StaticObject();
		StaticObjectsArray2[i].type = dis.readByte();
		StaticObjectsArray2[i].id = dis.readByte();
		StaticObjectsArray2[i].x = dis.readShort() * 4;
		StaticObjectsArray2[i].y = dis.readShort() * 4;
		StaticObjectsArray2[i].rotation = dis.readByte();
		
		///--- si es un cubo leemos los datos de los laterales
		if (StaticObjectsArray2[i].type == 0)
		{
		    StaticObjectsArray2[i].sides = dis.readByte();
		    StaticObjectsArray2[i].sprite = new Sprite(CubeImageArray[StaticObjectsArray2[i].id], 19, 22);
		    StaticObjectsArray2[i].sprite.setFrameSequence(CUBE_FRAME_SEQUENCE);
		   
		}	
		
		StaticObjectsArray2[i].distance = (short)((1500 - (StaticObjectsArray2[i].x + 10)) + StaticObjectsArray2[i].y + 5);			
	    }
	    
	    ///--- leemos el número de objetos dinámicos
	    dynCount = dis.readShort();	    
	   	    
	    ///--- leemos el número de sectores
	    m_iSectorCountX2 = dis.readShort();
	    m_iSectorCountY2 = dis.readShort();    
 	    
	    SectorArray2 = new MapSector[m_iSectorCountX2][m_iSectorCountY2];
	    	    
	    m_iSectorCount2 = (m_iSectorCountX2 * m_iSectorCountY2);
	    
	    ///--- leemos todos los sectores
	    for (int j = 0; j < m_iSectorCountY2; j++)
	    {		
		for (int i = 0; i < m_iSectorCountX2; i++)
		{
		    int objectCount = dis.readByte();
		    
		    SectorArray2[i][j] = new MapSector();
		    
		    SectorArray2[i][j].staticObjectsIndices = new short[objectCount];
		    
		    ///--- leemos los índices de cada sector
		    for (int a = 0; a < objectCount; a++)		
		    {
			SectorArray2[i][j].staticObjectsIndices[a] = dis.readShort();
		    }	
		}
	    }	 
	}
	catch (EOFException e)
	{
	    System.err.println("Se ha llegado al final de " + dis.toString() + " - " + e);
	}
	catch (IOException e)
	{
	    System.err.println("Error al leer " + dis.toString() + " - " + e);
	}
	catch (Exception e)
	{
	    System.err.println(e.toString());
	    e.printStackTrace();
	}
	
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public static void renderOverMap()
    {
	m_RenderVector.removeAll();	
			
	int sectorX = ((CameraX-14) / 64);
	int sectorY = ((CameraY-2) / 64);
	
	for (int h = 0; h < m_iScreenSectorCountY; h++)
	{	      
	    for (int j = m_iScreenSectorCountX; j >= 0; j--)
	    {
		if (((sectorX + j) >= 0) && ((sectorX + j) < m_iSectorCountX1) && ((sectorY + h) >= 0) && ((sectorY + h) < m_iSectorCountY1))
		{
		    MapSector s = SectorArray1[(sectorX + j)][(sectorY + h)];	
		    
		    ///--- pasamos todos los objetos estáticos a la lista final
		    for (int i = 0; i < s.staticObjectsIndices.length; i++)
		    {
			StaticObject obj = StaticObjectsArray1[s.staticObjectsIndices[i]];
					
			if (obj.disable)
			    continue;			
			
			if (obj.rendered)
			    continue;		
			
			obj.rendered=true;
			
			boolean insertado = false;

			m_RenderVector.Iterator = m_RenderVector.StartNode;
			
			for (int a = 0; a < m_RenderVector.ElementCount; a++)
			{
			    RenderObject object2 = (RenderObject)m_RenderVector.Iterator.TheObject;		   

			    ///--- si la distancia es menor hay que pintarlo primero
			    if (obj.distance <= object2.distance)
			    {
				m_RenderVector.insertElement(obj);
				insertado = true;
				break;
			    }
			    
			    m_RenderVector.Iterator = m_RenderVector.Iterator.Next;
			}    

			if (!insertado)
			{
			    m_RenderVector.addElement(obj);
			}			
		    }
		}		
	    }
	}	
	
	m_RenderVector.Iterator = m_RenderVector.StartNode;
	
	MainCanvas.GFX.translate(3, -6);
	
	for (int i = 0; i < m_RenderVector.ElementCount; i++)
	{
	    RenderObject object = (RenderObject)m_RenderVector.Iterator.TheObject;
	    
	    m_RenderVector.Iterator = m_RenderVector.Iterator.Next;

	    object.render();
	}
	
	
	///////////////////////
	
	
	m_RenderVector.removeAll();	
			
	for (int h = 0; h < m_iScreenSectorCountY; h++)
	{	      
	    for (int j = m_iScreenSectorCountX; j >= 0; j--)
	    {
		if (((sectorX + j) >= 0) && ((sectorX + j) < m_iSectorCountX2) && ((sectorY + h) >= 0) && ((sectorY + h) < m_iSectorCountY2))
		{
		    MapSector s = SectorArray2[(sectorX + j)][(sectorY + h)];	
		    
		    ///--- pasamos todos los objetos estáticos a la lista final
		    for (int i = 0; i < s.staticObjectsIndices.length; i++)
		    {
			StaticObject obj = StaticObjectsArray2[s.staticObjectsIndices[i]];
					
			if (obj.disable)
			    continue;			
			
			if (obj.rendered)
			    continue;		
			
			obj.rendered=true;
			
			boolean insertado = false;

			m_RenderVector.Iterator = m_RenderVector.StartNode;
			
			for (int a = 0; a < m_RenderVector.ElementCount; a++)
			{
			    RenderObject object2 = (RenderObject)m_RenderVector.Iterator.TheObject;		   

			    ///--- si la distancia es menor hay que pintarlo primero
			    if (obj.distance <= object2.distance)
			    {
				m_RenderVector.insertElement(obj);
				insertado = true;
				break;
			    }
			    
			    m_RenderVector.Iterator = m_RenderVector.Iterator.Next;
			}    

			if (!insertado)
			{
			    m_RenderVector.addElement(obj);
			}			
		    }
		}		
	    }
	}	
	
	m_RenderVector.Iterator = m_RenderVector.StartNode;
	
	MainCanvas.GFX.translate(3, -6);
	
	for (int i = 0; i < m_RenderVector.ElementCount; i++)
	{
	    RenderObject object = (RenderObject)m_RenderVector.Iterator.TheObject;
	    
	    m_RenderVector.Iterator = m_RenderVector.Iterator.Next;

	    object.render();
	}
	
	MainCanvas.GFX.translate(-6, 12);	
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public static void renderMap()
    {	   
	if (MainTimer.TotalMillis - m_lLastUpdateCintas > 100)	
	{
	    m_iFrameCintas++;
	    m_iFrameCintas &= 3;	
	    m_lLastUpdateCintas = MainTimer.TotalMillis;
	}
    
	m_RenderVector.removeAll();
	
	int sectorX = (CameraX / 64);
	int sectorY = (CameraY / 64);
	
	int backSectorX = (CameraX / 2) % 128;
	int backSectorY = (CameraY / 2) % 128;
	
	for (int h = -1; h < m_iBackgroundCountY; h++)
	{
	    for (int j = -1; j < m_iBackgroundCountX; j++)
	    {
		MainCanvas.GFX.drawImage(BackgroundImage, (j*128) - backSectorX, (h*128) - backSectorY, Graphics.TOP | Graphics.LEFT);		
	    }
	}
		
	for (int h = 0; h < m_iScreenSectorCountY; h++)
	{	      
	    for (int j = m_iScreenSectorCountX; j >= 0; j--)
	    {
		if (((sectorX + j) >= 0) && ((sectorX + j) < m_iSectorCountX) && ((sectorY + h) >= 0) && ((sectorY + h) < m_iSectorCountY))
		{
		    MapSector s = SectorArray[(sectorX + j)][(sectorY + h)];	
		    
		    ///--- pasamos todos los objetos estáticos a la lista final
		    for (int i = 0; i < s.staticObjectsIndices.length; i++)
		    {
			StaticObject obj = StaticObjectsArray[s.staticObjectsIndices[i]];
					
			if (obj.disable)
			    continue;			
			
			if (obj.rendered)
			    continue;		
			
			obj.rendered=true;
			
			boolean insertado = false;

			m_RenderVector.Iterator = m_RenderVector.StartNode;
			
			for (int a = 0; a < m_RenderVector.ElementCount; a++)
			{
			    RenderObject object2 = (RenderObject)m_RenderVector.Iterator.TheObject;		   

			    ///--- si la distancia es menor hay que pintarlo primero
			    if (obj.distance <= object2.distance)
			    {
				m_RenderVector.insertElement(obj);
				insertado = true;
				break;
			    }
			    
			    m_RenderVector.Iterator = m_RenderVector.Iterator.Next;
			}    

			if (!insertado)
			{
			    m_RenderVector.addElement(obj);
			}			
		    }
		}		
	    }
	}
	
	for (int h = 0; h < m_iScreenSectorCountY; h++)
	{	      
	    for (int j = m_iScreenSectorCountX; j >= 0; j--)
	    {
		if (((sectorX + j) >= 0) && ((sectorX + j) < m_iSectorCountX) && ((sectorY + h) >= 0) && ((sectorY + h) < m_iSectorCountY))
		{
		    MapSector s = SectorArray[(sectorX + j)][(sectorY + h)];
		    
		    ///--- vemos todos los objetos de este sector
		    ///--- y los ordenamos por distancia para que se vean
		    ///--- en orden correcto
		    
		    s.dynamicObjects.Iterator = s.dynamicObjects.StartNode;
		    
		    for (int i = 0; i < s.dynamicObjects.ElementCount; i++)
		    {
			RenderObject object = (RenderObject)s.dynamicObjects.Iterator.TheObject;
			
			if (object.rendered)
			{
			    s.dynamicObjects.Iterator =  s.dynamicObjects.Iterator.Next;
			    continue;
			}
			
			object.rendered = true;

			boolean insertado = false;
			
			m_RenderVector.Iterator = m_RenderVector.StartNode;

			for (int a = 0; a < m_RenderVector.ElementCount; a++)
			{
			    RenderObject object2 = (RenderObject)m_RenderVector.Iterator.TheObject;		   

			    ///--- si la distancia es menor hay que pintarlo primero
			    if (object.distance <= object2.distance)
			    {
				m_RenderVector.insertElement(object);
				insertado = true;
				break;
			    }
			    
			    m_RenderVector.Iterator = m_RenderVector.Iterator.Next;
			}    

			if (!insertado)
			{
			    m_RenderVector.addElement(object);
			}
			
			 s.dynamicObjects.Iterator =  s.dynamicObjects.Iterator.Next;
		    }		    
		}
	    }
	}
	
	m_RenderVector.Iterator = m_RenderVector.StartNode;
	
	for (int i = 0; i < m_RenderVector.ElementCount; i++)
	{
	    RenderObject object = (RenderObject)m_RenderVector.Iterator.TheObject;
	    
	    m_RenderVector.Iterator = m_RenderVector.Iterator.Next;

	    object.render();
	}
    }
        
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public static void clearSectors()
    {	
	try
	{
	    MainCanvas.TheBall.rendered = false;

	    for (int i = 0; i < StaticObjectCount; i++)	    
	    {
		StaticObjectsArray[i].rendered = false;
	    }
	    
	    for (int i = 0; i < StaticObjectCount1; i++)	    
	    {
		StaticObjectsArray1[i].rendered = false;
	    }
	    
	    for (int i = 0; i < StaticObjectCount2; i++)	    
	    {
		StaticObjectsArray2[i].rendered = false;
	    }

	    for (int i = 0; i < DynamicObjectCount; i++)	    
	    {
	       DynamicObjectsArray[i].rendered = false;
	    }	
	    
	    ShotManager.clearAll();
	    
	    int sectorX = (CameraX / 64);
	    int sectorY = (CameraY / 64);	
		
	    for (int h = 0; h < m_iScreenSectorCountY; h++)
	    {	      
		for (int j = m_iScreenSectorCountX; j >= 0; j--)
		{
		    if (((sectorX + j) >= 0) && ((sectorX + j) < m_iSectorCountX) && ((sectorY + h) >= 0) && ((sectorY + h) < m_iSectorCountY))
		    {
			SectorArray[(sectorX + j)][(sectorY + h)].dynamicObjects.removeAll();
		    }
		}
	    }
/*
	    for (int j = 0; j < m_iSectorCountY; j++)
	    {	      
		for (int i = 0; i < m_iSectorCountX; i++)
		{
		    if(SectorArray != null)
			SectorArray[i][j].dynamicObjects.removeAll();			
		}	
	    }    */
	}
	catch(Exception e) { }
    }
       
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public static boolean testSimpleCollisionSector(MapSector s)
    {	
	
	// TODO: Hacer que la colisión se estire en un rectángulo
	
	for (int i = 0; i < s.staticObjectsIndices.length; i++)
	{
	    StaticObject so = StaticObjectsArray[s.staticObjectsIndices[i]];
	    
	    if (so.disable)
		continue;
	    
 	    if (so.type == 0)
	    {
		if (((so.x + 16) > CollisionData.x) && ((so.y + 16) > CollisionData.y) && ((CollisionData.x + CollisionData.radius) > so.x) && ((CollisionData.y + CollisionData.radius) > so.y))
		{		    
		    return true;
		}		
	    }	    
	}
	
	return false;
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public static boolean testCollisionSector(MapSector s, boolean esLaBola)
    {	
        
        boolean ret = false;
        
	for (int i = 0; i < s.staticObjectsIndices.length; i++)
	{
	    StaticObject so = StaticObjectsArray[s.staticObjectsIndices[i]];
	    
	    if (so.disable)
		continue;
	    
 	    if (so.type == 0)
	    {
		int cubox = so.x;
                int cuboy = so.y;
		
		if (esLaBola)
		{
		     cubox += 2;
		     cuboy -= 2;			    
		}
		else
		{
		    cubox += 1;
		    cuboy += 1;		    
		}
                
                int cubox2 = cubox + 15;
                int cuboy2 = cuboy + 15;
                               
                boolean colLeft = false;
                boolean colRight = false;
                boolean colTop = false;
                boolean colDown = false;
		
		boolean dirLeft = false;
                boolean dirRight = false;
                boolean dirTop = false;
                boolean dirDown = false;

                ///--- vemos los lados con los que podemos colisionar
                if ((so.sides & 8) == 0)
                    colLeft = true;

                if ((so.sides & 2) == 0)
                    colRight = true;

                if ((so.sides & 1) == 0)
                    colTop = true;

                if ((so.sides & 4) == 0)
                    colDown = true;
		
		
		if (CollisionData.velX < 0)
                    dirLeft = true;

                if (CollisionData.velX > 0)
                    dirRight = true;

                if (CollisionData.velY < 0)
                    dirTop = true;

                if (CollisionData.velY > 0)
                    dirDown = true;
		
	
		if ((CollisionData.x >= cubox) && (CollisionData.x <= cubox2))
		{
		    /// esta arriba
		    if (dirDown && colTop)
		    {
			if (((CollisionData.y - cuboy) > -CollisionData.radius) && (CollisionData.y < cuboy2))
			{
			    CollisionData.velY = -CollisionData.velY;
			    CollisionData.y -= (CollisionData.y + CollisionData.radius - cuboy);

			    if (esLaBola)
			    {
				so.timestamp = TIME_LIGHT;
			    }

			    ret = true;
			}
		    }
		    /// esta abajo
		    else if (dirTop && colDown)
		    {
			if (((cuboy2 - CollisionData.y) > -CollisionData.radius) && (CollisionData.y > cuboy))
			{
			    CollisionData.velY = -CollisionData.velY;
			    CollisionData.y += (cuboy2 - (CollisionData.y - CollisionData.radius));

			    if (esLaBola)
			    {
				so.timestamp = TIME_LIGHT;
			    }

			    ret = true;
			}			
		    }
		}
		
		else if ((CollisionData.y >= cuboy) && (CollisionData.y <= cuboy2))
		{	
		    /// esta izqda
		    if (dirRight && colLeft)
		    {
			if (((CollisionData.x - cubox) > -CollisionData.radius) && (CollisionData.x < cubox2))
			{
			    CollisionData.velX = -CollisionData.velX;
			    CollisionData.x -= (CollisionData.x + CollisionData.radius - cubox );

			    if (esLaBola)
			    {
				so.timestamp = TIME_LIGHT;
			    }

			    ret = true;
			}   
		    }
		    /// esta dcha
		    else if (dirLeft && colRight)
		    {
			if (((cubox2 - CollisionData.x) > -CollisionData.radius) && (CollisionData.x > cubox))
			{
			    CollisionData.velX = -CollisionData.velX;
			    CollisionData.x += (cubox2 - (CollisionData.x - CollisionData.radius));

			    if (esLaBola)
			    {
				so.timestamp = TIME_LIGHT;
			    }

			    ret = true;
			}		
		    }
		}		
	
                
		/// esta arriba izqda
		else if ((CollisionData.y < cuboy) && (CollisionData.x < cubox))
		{	
		    if ((dirDown || dirRight) && colTop && colLeft)
		    {
			int disy = cuboy - CollisionData.y;
			int disx = cubox - CollisionData.x;
			int distanciaVertice = (disx * disx) + (disy * disy); 

			if (distanciaVertice < (CollisionData.radius * CollisionData.radius))
			{
			    float longdis = (float)(Math.sqrt(distanciaVertice));
			    float disxnor = ((float)-disx) / longdis;
			    float disynor = ((float)-disy) / longdis;
			    float vel = (float)(Math.sqrt((CollisionData.velX * CollisionData.velX) + (CollisionData.velY * CollisionData.velY)));
			    CollisionData.velX = disxnor * vel;
			    CollisionData.velY = disynor * vel;
			    
			    CollisionData.x += (int)(disxnor * ( CollisionData.radius - longdis));
			    CollisionData.y += (int)(disynor * ( CollisionData.radius - longdis));

			    if (esLaBola)
			    {
				so.timestamp = TIME_LIGHT;
			    }

			    ret = true;
			}        
		    }
		}
		/// esta arriba dcha
		else if ((CollisionData.y < cuboy) && (CollisionData.x > cubox2))
		{
		    if ((dirDown || dirLeft) && colTop && colRight)
		    {
			int disy = cuboy - CollisionData.y;
			int disx = CollisionData.x - cubox2;
			int distanciaVertice = (disx * disx) + (disy * disy); 

			if (distanciaVertice < (CollisionData.radius * CollisionData.radius))
			{
			    float longdis = (float)(Math.sqrt(distanciaVertice));
			    float disxnor = ((float)disx) / longdis;
			    float disynor = ((float)-disy) / longdis;
			    float vel = (float)(Math.sqrt((CollisionData.velX * CollisionData.velX) + (CollisionData.velY * CollisionData.velY)));
			    CollisionData.velX = disxnor * vel;
			    CollisionData.velY = disynor * vel;
			    
			    CollisionData.x += (int)(disxnor * ( CollisionData.radius - longdis));
			    CollisionData.y += (int)(disynor * ( CollisionData.radius - longdis));

			    if (esLaBola)
			    {
				so.timestamp = TIME_LIGHT;
			    }

			    ret = true;
			}
		    }
		}
		/// esta abajo izqda
		else if ((CollisionData.y > cuboy2) && (CollisionData.x < cubox))
		{
		    if ((dirTop || dirRight) && colDown && colLeft)
		    {
			int disy = CollisionData.y - cuboy2;
			int disx = cubox - CollisionData.x;
			int distanciaVertice = (disx * disx) + (disy * disy); 

			if (distanciaVertice < (CollisionData.radius * CollisionData.radius))
			{
			    float longdis = (float)(Math.sqrt(distanciaVertice));
			    float disxnor = ((float)-disx) / longdis;
			    float disynor = ((float)disy) / longdis;
			    float vel = (float)(Math.sqrt((CollisionData.velX * CollisionData.velX) + (CollisionData.velY * CollisionData.velY)));
			    CollisionData.velX = disxnor * vel;
			    CollisionData.velY = disynor * vel;
			    
			    CollisionData.x += (int)(disxnor * ( CollisionData.radius - longdis));
			    CollisionData.y += (int)(disynor * ( CollisionData.radius - longdis));

			    if (esLaBola)
			    {
				so.timestamp = TIME_LIGHT;
			    }

			    ret = true;
			}        
		    }
		}
		/// esta abajo dcha
		else if ((CollisionData.y > cuboy2) && (CollisionData.x > cubox2))
		{
		    if ((dirTop || dirLeft) && colDown && colRight)
		    {
			int disy = CollisionData.y - cuboy2;
			int disx = CollisionData.x - cubox2;
			int distanciaVertice = (disx * disx) + (disy * disy); 

			if (distanciaVertice < (CollisionData.radius * CollisionData.radius))
			{
			    float longdis = (float)(Math.sqrt(distanciaVertice));
			    float disxnor = ((float)disx) / longdis;
			    float disynor = ((float)disy) / longdis;
			    float vel = (float)(Math.sqrt((CollisionData.velX * CollisionData.velX) + (CollisionData.velY * CollisionData.velY)));
			    CollisionData.velX = disxnor * vel;
			    CollisionData.velY = disynor * vel;
			    
			    CollisionData.x += (int)(disxnor * ( CollisionData.radius - longdis));
			    CollisionData.y += (int)(disynor * ( CollisionData.radius - longdis));

			    if (esLaBola)
			    {
				so.timestamp = TIME_LIGHT;
			    }

			   ret = true;
			}
		    }
		}               
            }
        }
	
	return ret;    
    }
}

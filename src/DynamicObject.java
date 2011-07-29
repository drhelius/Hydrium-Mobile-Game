/*
 * DynamicObject.java
 *
 * Creado el 1 de febrero de 2006, 12:08
 *
 * Autor: Nacho Sánchez
 *
 */

public abstract class DynamicObject extends RenderObject
{   
    public byte type;
    public byte id;
    public int x;
    public int y;
    public float fx;
    public float fy;
    public float velX;
    public float velY;
    public byte rotation;
    public boolean disable;
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public abstract void update();
}

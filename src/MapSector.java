/*
 * MapSector.java
 *
 * Creado el 27 de enero de 2006, 11:01
 *
 * Autor: Nacho Sánchez
 *
 */

import java.util.*;

public class MapSector
{ 
    public short[] staticObjectsIndices;
    public DynamicList dynamicObjects;
    
    public MapSector()
    {
	dynamicObjects = new DynamicList();
    }
}

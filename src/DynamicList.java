/*
 * DynamicList.java
 *
 * Creado el 7 de febrero de 2006, 10:01
 *
 * Autor: Nacho Sánchez
 *
 */

public class DynamicList
{
    public int ElementCount;
    
    public DynamicListNode FreeNode;
    
    public DynamicListNode StartNode;
    
    public DynamicListNode LastNode;
    
    public DynamicListNode Iterator;
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void addElement(Object o)
    {	
	if (FreeNode == null)
	{
	    if (ElementCount != 0)
	    {
		DynamicListNode temp = new DynamicListNode();
		temp.TheObject = o;
		LastNode.Next = temp;
		temp.Back = LastNode;
		LastNode = temp;	
	    }
	    else
	    {
		StartNode = new DynamicListNode();
		StartNode.TheObject = o;
		
		Iterator = LastNode = StartNode;		
	    }
	}
	else
	{
	    if (ElementCount != 0)
	    {
		DynamicListNode temp = FreeNode;
		FreeNode = FreeNode.Next;

		temp.Next = temp.Back = null;		

		temp.TheObject = o;


		LastNode.Next = temp;
		temp.Back = LastNode;
		LastNode = temp;
	    }
	    else
	    {
		DynamicListNode temp = FreeNode;
		FreeNode = FreeNode.Next;

		temp.Next = temp.Back = null;
		
		temp.TheObject = o;
		
		LastNode = StartNode = Iterator = temp;
		
	    }
	}

	ElementCount++;
    } 
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void insertElement(Object o)
    {
	if (FreeNode == null)
	{
	    if (ElementCount != 0)
	    {
		DynamicListNode temp = new DynamicListNode();
		temp.TheObject = o;
		
		if (Iterator.Back != null)
		{
		    Iterator.Back.Next = temp;		    
		}
		else
		{
		    StartNode = temp;
		}		    
		
		temp.Back = Iterator.Back;		
		Iterator.Back = temp;
		temp.Next = Iterator;	
		
		ElementCount++;
	    }
	    else
	    {
		addElement(o);		
	    }
	}
	else
	{
	    if (ElementCount != 0)
	    {
		DynamicListNode temp = FreeNode;
		FreeNode = FreeNode.Next;

		temp.Next = temp.Back = null;
		
		temp.TheObject = o;
		
		if (Iterator.Back != null)
		{
		    Iterator.Back.Next = temp;		    
		}
		else
		{
		    StartNode = temp;		    
		}
		
		temp.Back = Iterator.Back;		
		Iterator.Back = temp;
		temp.Next = Iterator;
				
		ElementCount++;		
	    }
	    else
	    {
		addElement(o);		
	    }
	}			
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void removeAll()
    {
	ElementCount = 0;
	
	FreeNode = StartNode;	

	LastNode = StartNode = null;
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
/*    
    public void removeElement()
    {
	ElementCount--;
	
	DynamicListNode tmp = Iterator.Next;
			
	Iterator.Next = FreeNode;
		
	FreeNode = Iterator;
	
	if (Iterator.Back==null && Iterator.Next==null)
	{
	    Iterator = null;
	    LastNode = StartNode = null;
	}
	else if (Iterator.Back!=null && Iterator.Next==null)
	{
	    LastNode = Iterator.Back;	
	    LastNode.Next = null;
	    Iterator = null;
	}	
	else if (Iterator.Back==null && Iterator.Next!=null)
	{
	    StartNode = tmp;	
	    StartNode.Back = null;
	    Iterator = tmp;
	}
	else
	{
	    tmp.Next.Back = Iterator.Back;
	    Iterator.Back.Next = tmp;	    
	    Iterator = tmp;
	}
    }*/
}

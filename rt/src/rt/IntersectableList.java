package rt;

import java.util.ArrayList;
import java.util.Iterator;

import rt.intersectables.Aggregate;

public class IntersectableList extends Aggregate{
	ArrayList<Intersectable> primitiveList = new  ArrayList<Intersectable>();
	
	/**
	 * add an intersectable primitive to the intersectable list
	 * @param primitive an intersectable geometry
	 */
	public void add(Intersectable primitive){
		primitiveList.add(primitive);
	}
	
	@Override
	public Iterator<Intersectable> iterator() {
		return primitiveList.iterator();
	}
	
}

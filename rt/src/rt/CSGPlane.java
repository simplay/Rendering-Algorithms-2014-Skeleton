package rt;

import javax.vecmath.Vector3f;
import java.util.ArrayList;

/**
 * A plane for CSG operations. The plane represents a solid that fills a whole half-space.
 * The plane normal is assumed to point into the empty half-space. The plane occupies the 
 * half-space opposite of the normal.
 */
public class CSGPlane extends CSGSolid {
	
	Vector3f normal;
	float d;
	public Material material;
	
	/**
	 * Makes a CSG plane.
	 * 
	 * @param normal the plane normal
	 * @param d distance of the plane to the origin, along the normal direction (sign is important!)
	 */
	public CSGPlane(Vector3f normal, float d)
	{		
		this.normal = new Vector3f(normal);
		this.normal.normalize();
		this.d = d;
	}
		
	public ArrayList<IntervalBoundary> getIntervalBoundaries(Ray r)
	{
		ArrayList<IntervalBoundary> boundaries = new ArrayList<IntervalBoundary>();
		
		IntervalBoundary b1, b2;
		b1 = new IntervalBoundary();
		b2 = new IntervalBoundary();
		
		HitRecord hitRecord = intersectPlane(r);
		if(hitRecord != null)
		{
			b1.hitRecord = hitRecord;
			b1.t = hitRecord.t;
			b2.hitRecord = null;
			
			// Determine if ray entered or left the half-space defined by the plane
			if(normal.dot(r.direction) < 0)
			{
				b1.type = BoundaryType.START;
				b2.type = BoundaryType.END;
				b2.t = Float.POSITIVE_INFINITY;			
			} else
			{
				b1.type = BoundaryType.END;
				b2.type = BoundaryType.START;
				b2.t = Float.NEGATIVE_INFINITY;			
			}
			
			boundaries.add(b1);
			boundaries.add(b2);
		}
		
		return boundaries;
	}
		
	/**
	 * Computes ray-plane intersection.
	 * @param r the ray
	 * @return the hit record of the intersection point, or null 
	 */
	private HitRecord intersectPlane(Ray r) {

		float tmp = normal.dot(r.direction);
		
		if(tmp!=0)
		{
			// t parameter of hit point
			float t = -(normal.dot(r.origin) + d) / tmp;
		
			// Hit position
			Vector3f position = new Vector3f(r.direction);
			position.scaleAdd(t, r.origin);
			
			// Hit normal
			Vector3f retNormal = new Vector3f(normal);
			
			// Incident direction, convention is that it points away from the hit position
			Vector3f wIn = new Vector3f(r.direction);
			wIn.negate();
		
			return new HitRecord(t, position, retNormal, wIn, null, null, material, 0.f, 0.f);
		} else
		{
			return null;
		}
	}
}
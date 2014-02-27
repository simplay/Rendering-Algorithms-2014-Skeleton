package rt.intersectables;

import javax.vecmath.Point2f;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

import rt.HitRecord;
import rt.Intersectable;
import rt.Material;
import rt.Ray;
import rt.Spectrum;
import rt.materials.Diffuse;

public class Sphere implements Intersectable{
	
	private float radius;
	private Point3f center;
	private Material material;
	

	public Sphere(){
		this.radius = 1f;
		this.center = new Point3f();
	}
	
	public Sphere(Point3f center, float radius){
		this.radius = radius;
		this.center = center;
		material = new Diffuse(new Spectrum(1.f, 1.f, 1.f));
	}
	

	private Float getNiceZero(Ray r){
		Vector3f esubc = new Vector3f();
		Vector3f e = r.origin;
		Vector3f d = r.direction;
		esubc.sub(e, center);
		
		float dottedesubc = esubc.dot(esubc);
		float R2 = radius*radius;
		
		float a = d.dot(d);
		float b = 2f*d.dot(esubc);
		float c = dottedesubc-R2;
		
		float sqrtPart = (float) Math.sqrt(b*b-4*a*c);
		float dominator = 2*a;
		float t1 = (-b + sqrtPart) / dominator;
		float t2 = (-b - sqrtPart) / dominator;
		
		float t = (t1 > t2) ? t1 : t2;
		return  (t > 0f) ? t : null;
	}
	
	
	private Vector3f getNormal(Ray r){
		Vector3f esubc = new Vector3f();
		Vector3f normal = new Vector3f();
		Vector3f d = r.direction;
		Vector3f e = r.origin;
		esubc.sub(e, center);
		normal.sub(center, d);
		normal.scale(1f/radius);
		return normal;
	}
	
	@Override
	public HitRecord intersect(Ray r) {
		Float t = getNiceZero(r);
		if(t != null){
			System.out.println("im in dog " + t);
			Vector3f dirT = r.direction;
			dirT.scale(t);
			Vector3f position = new Vector3f();
			position.add(r.origin, dirT);
			Vector3f normal = getNormal(r);
			System.out.println("is null " + material == null);
			return new HitRecord(t, position, normal, r.direction, this, this.material, 0.f, 0.f);
			
		}else{
			return null;
		}

	}

}

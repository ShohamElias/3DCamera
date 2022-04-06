package renderer;
import primitives.*;
import primitives.Util;
public class Camera 
{
	//----- camera ------
	private Point p0; //location of the camera
	private Vector vUp;
	private Vector vTo;
	private Vector vRight;
	
	//------ veiw plane -----
	private double width;
	private double height;
	private double distance;
	
	/**
	 * this constructor creates a camera
	 * @param p0
	 * @param vTo
	 * @param vUp
	 * @throws IllegalArgumentException
	 */
	public Camera(Point p0, Vector vTo, Vector vUp) 
	{
		if(!Util.isZero(vTo.dotProduct(vUp))) // if vTo doesn't orthogonal to vUp
			throw new IllegalArgumentException("vUp doesnt ortogonal to vTo");
		
		//all the vectors need to be normalize:
		this.vTo = vTo.normalize();
		this.vUp = vUp.normalize();
		vRight = (vTo.crossProduct(vUp)).normalize();
		this.p0 = p0;

	}
	
	/**
	 * this method we get two elements: the width and height and after updating the size of the View Plane we return the camera itself.
	 * by doing so we implement the design pattern "Builder",which is very useful 
	 * @param width
	 * @param height
	 * @return camera 
	 */
	public Camera setVPSize(double width, double height)
	{
		this.width=width;
		this.height=height;
		return this;
	}
	
	/**
	 * this method we get the distance and after updating the distance of the camera from the view plane we return the camera itself.
	 * by doing so we implement the design pattern "Builder",which is very useful 
	 * @param distance
	 * @return camera
	 */
	public Camera setDistance(double distance) {
		this.distance=distance;
		return this;
	}
	
	public Ray constructRay(int nX, int nY, int j, int i)
	{
		if (Util.isZero(distance)) //the distance between the VP and the camera can't be 0
			throw new IllegalArgumentException("the distance between the VP and the camera can't be 0");
		Point centerOfVP= p0.add(vTo.scale(distance));//a point that presents the center of the VP that is straight forward to the camera.
		double heightY =height / nY;// y value on VP
		double widthX = width / nX;	// x value on VP

		double yi = ((i - nY / 2d) * heightY + heightY / 2d);//calculates the y value of the wanted pixel
		double xj = ((j - nX / 2d) * widthX + widthX / 2d);//calculates the x value of the wanted pixel

		Point Pij = centerOfVP;// Pij starts to march the VP from the center
		//we need to move Pij ONLY if xj/yi isn't zero   
		if (!Util.isZero(xj)) 
			Pij = Pij.add(vRight.scale(xj)); //Pij to the center of the wanted pixel
		if (!Util.isZero(yi)) 
			Pij = Pij.add(vUp.scale(-yi));  //  Pij to the center of the wanted pixel

		Vector Vij = Pij.subtract(p0);//Vij =  vector from the camera to Pij
		return new Ray(p0, Vij);//create the ray and return it
	}
	
	public double getDistance() 
	{
		return distance;
	}
	public double getHeight() 
	{
		return height;
	}
	public double getWidth() 
	{
		return width;
	}
	public Vector getvRight() 
	{
		return vRight;
	}
	public Vector getvTo() 
	{
		return vTo;
	}
	public Vector getvUp() 
	{
		return vUp;
	}
	public Point getP0() 
	{
		return p0;
	}

}

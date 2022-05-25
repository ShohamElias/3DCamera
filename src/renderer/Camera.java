package renderer;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import lighting.AmbientLight;
import primitives.*;
import primitives.Util;
import scene.Scene;
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
	
	private ImageWriter imgWriter;
	private RayTracerBase rayTracer;
	 private int NumOfRaysSupersampling=10;
	    private boolean isSupersampling=true;
	    public Camera setSupersampling(boolean isSupersampling) {
			this.isSupersampling = isSupersampling;
			return this;
		}

		/**
	     * setter for the num of rays --builder 
	     * @param numOfRaysSupersampling
	     * @return
	     */
		public Camera setNumOfRaysSupersampling(int numOfRaysSupersampling) {
			NumOfRaysSupersampling = numOfRaysSupersampling;
			return this;
		}
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
	public Camera setDistance(double distance) 
	{
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

	/**
	 * PDS: gets the AmbientLight and set's it in the scene.
	 * we are using the Builder design pattern,and that's why we returned the scene itself
	 * @param background
	 * @return scene
	 */
	public Camera setImageWriter(ImageWriter img)
	{
		this.imgWriter = img;
		return this;
	}
	
	/**
	 * PDS: gets the AmbientLight and set's it in the scene.
	 * we are using the Builder design pattern,and that's why we returned the scene itself
	 * @param background
	 * @return scene
	 */
	public Camera setRayTracerBase(RayTracerBase rtb)
	{
		this.rayTracer = rtb;
		return this;
	}
	

	/**
	 * This function renders image's pixel color map from the scene included with
	 * the Renderer object
	 */
	public Camera renderImage()
	{
		if ( imgWriter == null)
			throw new MissingResourceException("Renderer resource not set", "RENDER_CLASS", "IMAGE_WRITER");
		if (rayTracer == null)
			throw new MissingResourceException("Renderer resource not set", "RENDER_CLASS", "RAY_TRACER");

		int nX = imgWriter.getNx();
		int nY = imgWriter.getNy();

		for (int i = 0; i < nX; ++i)
			for (int j = 0; j < nY; ++j)
			{
				castRay(nX, nY, j, i);
				//Ray ray = constructRay(nX, nY, j, i);
				//Color rayColor = rayTracer.traceRay(ray);
	           //  List<Ray> rays = constructRaysThroughPixel(nX, nY, j, i, NumOfRaysSupersampling);
	            // imgWriter.writePixel(j,i, rayTracer.calcColorForSupersampling(rays));
				//imgWriter.writePixel(j, i, rayColor); 
			}
		return this;

	}
	/**
	 * Cast ray from camera in order to color a pixel
	 * @param nX resolution on X axis (number of pixels in row)
	 * @param nY resolution on Y axis (number of pixels in column)
	 * @param col pixel's column number (pixel index in row)
	 * @param row pixel's row number (pixel index in column)
	 */
	private void castRay(int nX, int nY, int col, int row) {
		Ray ray = constructRay(nX, nY, col, row);
		Color color = rayTracer.traceRay(ray);
		imgWriter.writePixel(col, row, color);
	  // supersampling
		    if(isSupersampling==true) {
             List<Ray> rays = constructRaysThroughPixel(nX, nY, col, row, NumOfRaysSupersampling);
             imgWriter.writePixel(col,row, rayTracer.calcColorForSupersampling(rays));
		    }
	}
	
	public void printGrid(int interval, Color color) 
	{
		if (imgWriter == null)
			throw new MissingResourceException("Renderer resource not set", "RENDER_CLASS", "IMAGE_WRITER");

		int nX = imgWriter.getNx();
		int nY = imgWriter.getNy();

		for (int i = 0; i < nY; ++i)
			for (int j = 0; j < nX; ++j)
				if (j % interval == 0 || i % interval == 0)
					imgWriter.writePixel(j, i, color);
	}
	
	/**
	 * A function that finally creates the image.
	 * This function delegates the function of a class imageWriter
	 * */
	public void writeToImage()
	{
		if (imgWriter == null)
			throw new MissingResourceException("This function must have values in all the fileds", "ImageWriter", "imageWriter");
		
		imgWriter.writeToImage();
	}
	
	//SuperSampling
		public List<Ray> constructRaysThroughPixel (int nX, int nY, int j, int i, int raysAmount)
		{
			
			List<Ray> Rays = new ArrayList<Ray>();
			int numOfRays = (int)Math.floor(Math.sqrt(raysAmount)); //number of rays in each row or column

			if (numOfRays==1) return List.of(constructRay(nX, nY, j, i));

			Point Pc;
			Point point=(p0.add(vTo.scale(distance)));
			if (distance!=0)
				Pc = new Point(point.getXyz()); //reach the view plane
			else
				Pc = new Point(p0.getXyz());

			//height and width of a single pixel
			double Ry = height / nY;
			double Rx = width / nX;
			//center of pixel:
			double yi = (i - nY / 2d)*Ry + Ry / 2d;
			double xj = (j - nX / 2d)*Rx + Rx / 2d;

			Point Pij = Pc;

			if (xj != 0)
			{
				Pij = Pij.add(this.vRight.scale(-xj)); //move Pij to width center
			}
			if (yi != 0)
			{
				Pij = Pij.add(this.vUp.scale(-yi)); //move Pij to height center
			}
			Vector Vij=Pij.subtract(p0);
			Rays.add(new Ray(p0,Vij)); //the original vector

			double PRy = Ry / numOfRays; //height distance between each ray
			double PRx = Rx / numOfRays; //width distance between each ray

			Point tmp = Pij; //center

			//creating a grid in the pixel:
			for (int row=0; row<numOfRays; row++) {
				double Pxj = (row - (numOfRays/2d)) * PRx + PRx/2d;//the distance to move on x, and its whole column
				for (int column=0; column<numOfRays; column++) {
					double Pyi = (column - (numOfRays/2d)) * PRy + PRy/2d;
					if (Pxj != 0)
						Pij = Pij.add(this.vRight.scale(-Pxj));
					if (Pyi != 0)
						Pij = Pij.add(this.vUp.scale(-Pyi)); 
					Rays.add(new Ray(p0, Pij.subtract(p0)));
					Pij = tmp; //restart
				}
			}
			return Rays;
		}

}

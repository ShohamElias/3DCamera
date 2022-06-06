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
	 * the Renderer object - with multi-threading
	 */
	private void renderImageThreaded()
	{
		final int nX = imgWriter.getNx();
		final int nY = imgWriter.getNy();
		final Pixel thePixel = new Pixel(nY, nX);
		// Generate threads
		Thread[] threads = new Thread[threadsCount];
		for (int i = threadsCount - 1; i >= 0; --i) {
			threads[i] = new Thread(() -> {
				Pixel pixel = new Pixel();
				while (thePixel.nextPixel(pixel))
					castRay(nX, nY, pixel.col, pixel.row);
			});
		}
		// Start threads
		for (Thread thread : threads)
			thread.start();

		// Print percents on the console
		thePixel.print();

		// Ensure all threads have finished
		for (Thread thread : threads)
			try {
				thread.join();
			} catch (Exception e) {
			}

		if (print)
			System.out.print("\r100%");
	}

	/**
	 * This function renders image's pixel color map from the scene included with
	 * the Renderer object
	 */
	public Camera renderImage() {
		if ( imgWriter == null)
			throw new MissingResourceException("Renderer resource not set", "RENDER_CLASS", "IMAGE_WRITER");
		if (rayTracer == null)
			throw new MissingResourceException("Renderer resource not set", "RENDER_CLASS", "RAY_TRACER");

		final int nX = imgWriter.getNx();
		final int nY = imgWriter.getNy();
		if (threadsCount == 0)
			for (int i = 0; i < nY; ++i)
				for (int j = 0; j < nX; ++j)
					castRay(nX, nY, j, i);
		else
			renderImageThreaded();
		return this;

	}

	/**
	 * This function renders image's pixel color map from the scene included with
	 * the Renderer object
	 *
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
	*/
	
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
	
	
	public List<Ray> constructRaysThroughPixel (int nX, int nY, int j, int i, int raysAmount)
	{
	int numOfRays = (int)Math.floor(Math.sqrt(raysAmount)); //num of rays in each row or column
		
		if (numOfRays==1) 
			return List.of(constructRayThroughPixel(nX, nY, j, i));
//
		Point Pc;
		if ( Util.isZero(distance))
			Pc=p0;
		else
			Pc=p0.add(vTo.scale(distance));
		
		double Ry= height/nY;
		double Rx=width/nX;
		double Yi=(i-(nY-1)/2d)*Ry;
		double Xj=(j-(nX-1)/2d)*Rx;
//        
        double PRy = Ry / numOfRays; //height distance between each ray
        double PRx = Rx / numOfRays; //width distance between each ray
//        
	      //The distance between the screen and the camera cannot be 0
        if ( Util.isZero(distance))
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }

        List<Ray> sampleRays = new ArrayList<>();


        for (int row = 0; row < numOfRays; ++row) 
        {//foreach place in the pixel grid
            for (int column = 0; column < numOfRays; ++column)
            {
                sampleRays.add(constructRaysThroughPixel(PRy,PRx,Yi, Xj, row, column));//add the ray
            }
        }
        sampleRays.add(constructRayThroughPixel(nX, nY, j, i));//add the center screen ray
        return sampleRays;
	}
	

private Ray constructRaysThroughPixel(double Ry,double Rx, double yi, double xj, int j, int i)
{
    Point Pc = p0.add(vTo.scale(distance)); //the center of the screen point

    double ySampleI =  (i *Ry + Ry/2d); //The pixel starting point on the y axis
    double xSampleJ=   (j *Rx + Rx/2d); //The pixel starting point on the x axis

    Point Pij = Pc; //The point at the pixel through which a beam is fired
    //Moving the point through which a beam is fired on the x axis
    if (! Util.isZero(xSampleJ + xj))
    {
        Pij = Pij.add(vRight.scale(xSampleJ + xj));
    }
    //Moving the point through which a beam is fired on the y axis
    if (! Util.isZero(ySampleI + yi))
    {
        Pij = Pij.add(vUp.scale(-ySampleI -yi ));
    }
    Vector Vij = Pij.subtract(p0);
    
    return new Ray(p0,Vij);//create the ray throw the point we calculate here
}
public Ray constructRayThroughPixel(int nX, int nY, int j, int i ) 
{
	Point Pc;
	if ( Util.isZero(distance))
		Pc=p0;
	else
		Pc=p0.add(vTo.scale(distance));
	
	double Ry= height/nY;
	double Rx=width/nX;
	double Yi=(i-(nY-1)/2d)*Ry;
	double Xj=(j-(nX-1)/2d)*Rx;
	
	if( Util.isZero(Xj) &&  Util.isZero(Yi))
		return new Ray (p0, Pc.subtract(p0));
	
	Point Pij = Pc;
	
	if(! Util.isZero(Xj))
		Pij = Pij.add(vRight.scale(Xj));
	
	if(! Util.isZero(Yi))
		Pij = Pij.add(vUp.scale(-Yi));
	
	Vector Vij = Pij.subtract(p0);
	
	if(Pij.equals(p0))
		return new Ray(p0, new Vector(Pij.getXyz()));
	return new Ray(p0, Vij);

}
//_______________________________________
private int threadsCount = 0;
private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
private boolean print = false; // printing progress percentage 

/**
 * Set multi-threading <br>
 * - if the parameter is 0 - number of cores less 2 is taken
 * 
 * @param threads number of threads
 * @return the Render object itself
 */
public Camera setMultithreading(int threads) {
	if (threads < 0)
		throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
	if (threads != 0)
		this.threadsCount = threads;
	else {
		int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
		this.threadsCount = cores <= 2 ? 1 : cores;
	}
	return this;
}

/**
 * Set debug printing on
 * 
 * @return the Render object itself
 */
public Camera setDebugPrint() {
	print = true;
	return this;
}

/**
 * Pixel is an internal helper class whose objects are associated with a Render
 * object that they are generated in scope of. It is used for multithreading in
 * the Renderer and for follow up its progress.<br/>
 * There is a main follow up object and several secondary objects - one in each
 * thread.
 * 
 * @author Dan
 *
 */
private class Pixel {
	private long maxRows = 0;
	private long maxCols = 0;
	private long pixels = 0;
	public volatile int row = 0;
	public volatile int col = -1;
	private long counter = 0;
	private int percents = 0;
	private long nextCounter = 0;

	/**
	 * The constructor for initializing the main follow up Pixel object
	 * 
	 * @param maxRows the amount of pixel rows
	 * @param maxCols the amount of pixel columns
	 */
	public Pixel(int maxRows, int maxCols) {
		this.maxRows = maxRows;
		this.maxCols = maxCols;
		this.pixels = (long) maxRows * maxCols;
		this.nextCounter = this.pixels / 100;
		if (Camera.this.print)
			System.out.printf("\r %02d%%", this.percents);
	}

	/**
	 * Default constructor for secondary Pixel objects
	 */
	public Pixel() {
	}

	/**
	 * Internal function for thread-safe manipulating of main follow up Pixel object
	 * - this function is critical section for all the threads, and main Pixel
	 * object data is the shared data of this critical section.<br/>
	 * The function provides next pixel number each call.
	 * 
	 * @param target target secondary Pixel object to copy the row/column of the
	 *               next pixel
	 * @return the progress percentage for follow up: if it is 0 - nothing to print,
	 *         if it is -1 - the task is finished, any other value - the progress
	 *         percentage (only when it changes)
	 */
	private synchronized int nextP(Pixel target) {
		++col;
		++this.counter;
		if (col < this.maxCols) {
			target.row = this.row;
			target.col = this.col;
			if (Camera.this.print && this.counter == this.nextCounter) {
				++this.percents;
				this.nextCounter = this.pixels * (this.percents + 1) / 100;
				return this.percents;
			}
			return 0;
		}
		++row;
		if (row < this.maxRows) {
			col = 0;
			target.row = this.row;
			target.col = this.col;
			if (Camera.this.print && this.counter == this.nextCounter) {
				++this.percents;
				this.nextCounter = this.pixels * (this.percents + 1) / 100;
				return this.percents;
			}
			return 0;
		}
		return -1;
	}

	/**
	 * Public function for getting next pixel number into secondary Pixel object.
	 * The function prints also progress percentage in the console window.
	 * 
	 * @param target target secondary Pixel object to copy the row/column of the
	 *               next pixel
	 * @return true if the work still in progress, -1 if it's done
	 */
	public boolean nextPixel(Pixel target) {
		int percent = nextP(target);
		if (Camera.this.print && percent > 0)
			synchronized (this) {
				notifyAll();
			}
		if (percent >= 0)
			return true;
		if (Camera.this.print)
			synchronized (this) {
				notifyAll();
			}
		return false;
	}

	/**
	 * Debug print of progress percentage - must be run from the main thread
	 */
	public void print() {
		if (Camera.this.print)
			while (this.percents < 100)
				try {
					synchronized (this) {
						wait();
					}
					System.out.printf("\r %02d%%", this.percents);
					System.out.flush();
				} catch (Exception e) {
				}
	}
}

}


package scene;
import lighting.LightSource;

import java.util.Arrays;
import java.util.LinkedList;

import lighting.AmbientLight;
import geometries.Geometries;
import primitives.Color;
import java.util.List;
/**
 * Scene class represents a scene composed by the name,background,ambient light and geometries.
 * @author Adi
 *this class is PSD and thats why all of the fields are public properties
 */
public class Scene {

	public String name;//the name of the scene
	public Color background=Color.BLACK;//the color of the background
	public AmbientLight ambientLight=new AmbientLight();//the ambient light with default value -black
	public Geometries geometries=null;//a geometry model with default geometry value.
	public List<LightSource> lights;	//the light sources in the scene

	////////////// ctor ////////////////////
	/**
	 * a constructor that sets the name of the scene from a given string and initializes 
	 * the field 'geometries', and the field 'lights'-(by LinkedList). 
	 * @param name
	 */
	public Scene(String name) 
	{
		this.name = name;
		geometries=new Geometries();
		lights=new LinkedList<LightSource>();//reset empty list of lights

	}
	
	/////////////  set  /////////////////////
	/**
	 * gets a color and set's it in the scene.
	 * we are using the Builder design pattern,and that's why we returned the scene itself
	 * @param background
	 * @return scene
	 */
	public Scene setBackground(Color background) 
	{
		this.background = background;
		return this;
	}
	/**
	 * gets the AmbientLight and set's it in the scene.
	 * we are using the Builder design pattern,and that's why we returned the scene itself
	 * @param background
	 * @return scene
	 */
	public Scene setAmbientLight(AmbientLight ambientLight)
	{
		this.ambientLight = ambientLight;
		return this;
	}
	/**
	 * gets the Geometries and set's theme in the scene.
	 * we are using the Builder design pattern,and that's why we returned the scene itself
	 * @param background
	 * @return scene
	 */
	public Scene setGeometries(Geometries geometries)
	{
		this.geometries = geometries;
		return this;
	}
	
	/**
	 * @param lights
	 * @return the scene itself to allow design pattern of builder- to concatenate calls to setters.
	 */
	public Scene setLights(LightSource...lights) 
	{  
        this.lights.addAll(Arrays.asList(lights));
        return this;
	}
	
}
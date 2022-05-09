package primitives;
import primitives.Double3;

/**
 * 
 * this class contains the features of a material- the shinines, kd and ks,
 * all the parameters that affect the light comes from and to this material.
 * the class is PDS and goes by the builder design pattern 
 *
 */
public class Material 
{
	public Double3 kD = new Double3(0);	//difusive mekadem--how much the light obsetves into the material
    public Double3 kS= new Double3(0);	//specular mekadem--how much the light is returned by the material
    public int nShininess=0;//shininess of the material
   
	
	//******************setters****************
    /**
     * set kd-difusive mekadem--how much the light obsetves into the material
     * @param _kD
     * @return the material itself to allow design pattern of builder- to concatenate calls to setters.
     */
	public Material setKd(double _kD) 
	{
		this.kD = new Double3(_kD);
		return this;
	}
	/**
	 * set ks--specular mekadem--how much the light is returnad by the material
	 * @param _kS
	 * @return the material itself to allow design pattern of builder- to concatenate calls to setters.
	 */
	public Material setKs(double _kS) 
	{
		this.kS = new Double3(_kS);
		return this;
	}
	/**
	 * set shininess
	 * @param _nShininess
	 * @return the material itself to allow design pattern of builder- to concatenate calls to setters.
	 */
	public Material set_nShininess(int _nShininess) 
	{
		this.nShininess = _nShininess;
		return this;
	}
	
	/**
     * set kd-difusive mekadem--how much the light obsetves into the material
     * @param _kD
     * @return the material itself to allow design pattern of builder- to concatenate calls to setters.
     */
	public Material setKd(Double3 _kD) 
	{
		this.kD =_kD;
		return this;
	}
	/**
	 * set ks--specular mekadem--how much the light is returnad by the material
	 * @param _kS
	 * @return the material itself to allow design pattern of builder- to concatenate calls to setters.
	 */
	public Material setKs(Double3 _kS) 
	{
		this.kS = _kS;
		return this;
	}
	
}
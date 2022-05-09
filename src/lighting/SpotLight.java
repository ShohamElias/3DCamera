package lighting;

import primitives.*;

/**
 * spot light- extends point light, but has direction to the light.
 * 
 * @author adi
 */
public class SpotLight extends PointLight 
{
    private Vector direction;		//the direction of the spot light

    /* ********* Constructors ***********/

    /**
     * a new spotlight
     *
     * @param color the color of the light
     * @param position the position of the light source
     * @param direction the direction of the light
     */
    public SpotLight(Color color, Point position, Vector direction) 
    {
        super(color, position);
        this.direction = direction.normalize();
    }

    /* ************* Getters & setters *******/

    /**
     * get light intensity
     * @param p the point
     * @return light
     */
    @Override
    public Color getIntensity(Point p) 
    {
        double pl = Util.alignZero(direction.dotProduct(getL(p)));
        if (pl <= 0)
            return Color.BLACK;
        return super.getIntensity(p).scale(pl);
    }
    
/**
 * there is no implementation of "get light" because spotLight extends pointLight and 
 * the implementation is there.
 */
}
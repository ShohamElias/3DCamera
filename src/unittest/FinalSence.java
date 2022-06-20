package unittest;

import static java.awt.Color.YELLOW;

import org.junit.Test;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

public class FinalSence {

	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test
	public void test() {
        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point(-40, 95, 1000), new Vector(0.07, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(400, 400).setDistance(900)        		;
        
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.50));

        scene.geometries.add(

            new Plane(new Point(0, -0, 0), new Point(60, -0, 0), new Point(30, -0, 51.9615242271)) //
		    .setEmission(new Color(java.awt.Color.PINK)) //
			.setMaterial(new Material().setKr(0.05).setKd(0.5).setKs(0.7).set_nShininess(20)),
		    new Plane(new Point(300000, 0, 0), new Point(300000, 300000, 0), new Point(300000, 0, 300000)) //
		    .setEmission(new Color(java.awt.Color.darkGray)) //
			.setMaterial(new Material().setKd(0.5).setKs(0.7).set_nShininess(20)),
		    new Plane(new Point(-300000, 0, 0), new Point(-300000, 300, 0), new Point(-300000, 0, 300)) //
		    .setEmission(new Color(java.awt.Color.darkGray)) //
			.setMaterial(new Material().setKd(0.5).setKs(0.7).set_nShininess(20)),
			
			
            new Triangle(new Point(0, -0, 0), new Point(60, -0, 0), new Point(30, 120, 25.9807621)) //
            .setEmission(new Color(java.awt.Color.GRAY)) //
			.setMaterial(new Material().setKd(0.5).setKs(0.7).set_nShininess(60)),
            new Triangle(new Point(0, -0, 0), new Point(30, -0, 51.9615242271), new Point(30, 120, 25.9807621)) //
            .setEmission(new Color(java.awt.Color.GRAY)) //
			.setMaterial(new Material().setKd(0.5).setKs(0.7).set_nShininess(60)),
            new Triangle(new Point(60, -0, 0), new Point(30, -0, 51.9615242271), new Point(30, 120, 25.9807621)) //
            .setEmission(new Color(java.awt.Color.GRAY)) //
			.setMaterial(new Material().setKd(0.5).setKs(0.7).set_nShininess(60)),
            new Triangle(new Point(0, -0, 0), new Point(60, -0, 0), new Point(30, -0, 51.9615242271)) //
            .setEmission(new Color(java.awt.Color.GRAY)) //
			.setMaterial(new Material().setKd(0.5).setKs(0.7).set_nShininess(60)),

            new Triangle(new Point(0, 300, 0), new Point(60, 300, 0), new Point(30, 180, 25.9807621)) //
            .setEmission(new Color(java.awt.Color.GRAY)) //
			.setMaterial(new Material().setKd(0.5).setKs(0.7).set_nShininess(60)),
            new Triangle(new Point(0, 300, 0), new Point(30, 300, 51.9615242271), new Point(30, 180, 25.9807621)) //
            .setEmission(new Color(java.awt.Color.GRAY)) //
			.setMaterial(new Material().setKd(0.5).setKs(0.7).set_nShininess(60)),
            new Triangle(new Point(60, 300, 0), new Point(30, 300, 51.9615242271), new Point(30, 180, 25.9807621)) //
            .setEmission(new Color(java.awt.Color.GRAY)) //
			.setMaterial(new Material().setKd(0.5).setKs(0.7).set_nShininess(60)),
            new Triangle(new Point(0, 300, 0), new Point(30, 300, 51.9615242271), new Point(60, 300, 0)) //
            .setEmission(new Color(java.awt.Color.GRAY)) //
			.setMaterial(new Material().setKd(0.5).setKs(0.7).set_nShininess(30)),
			
            new Sphere(30, new Point(30, 150, 25.9807621)) //
            .setEmission(new Color(java.awt.Color.BLUE)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.7).set_nShininess(60)),
            
            new Sphere(30, new Point(160, 150, 25.9807621)) //
            .setEmission(new Color(java.awt.Color.RED)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.7).set_nShininess(60)),
             
            new Sphere(30, new Point(-100, 150, 25.9807621)) //
            .setEmission(new Color(java.awt.Color.PINK)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.7).set_nShininess(60))
        );
        
        
        scene.lights.add(
                new DirectionalLight(new Color(150, 150, 150), new Vector(90, -40, -10)));
         
       scene.lights.add( //
                new SpotLight(new Color(700, 700, 400), new Point(0, 0, 0), new Vector(100, -100, -40)) //
                       .setKl(4E-4).setKq(2E-5));
        scene.lights.add( //
                new SpotLight(new Color(700, 700, 400), new Point(-200, 250, 150), new Vector(10, -10, -40)) //
                       .setKl(4E-4).setKq(2E-5));
       
        
		scene.geometries.createBox();
		scene.geometries.createGeometriesTree();

       ImageWriter imageWriter = new ImageWriter("FinalSceneOF_THE_DOOM2", 600, 600);
      final Camera camera2 = new Camera(new Point(-40, 95, 1000), new Vector(0.07, 0, -1), new Vector(0, 1, 0)) //
    			.setDistance(900).setVPSize(400,400) //
    			.setImageWriter(imageWriter)
    			.setSuperSamp()//
    			//.setMultithreading(3)
    			.setDebugPrint();
 		camera2.setRayTracerBase(new RayTracerBasic(scene)).renderImage().writeToImage();

	}
}
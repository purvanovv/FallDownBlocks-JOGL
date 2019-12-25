package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;

public class Entity {
	private RawModel model;
	private Vector3f position;

	public Entity(RawModel model, Vector3f position) {
		this.model = model;
		this.position = position;
	}

	public RawModel getModel() {
		return model;
	}

	public void setModel(RawModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void move() {
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			System.out.println(position.x);
			if(position.x < 0.8f){
				position.x += 0.02f;
			}
			
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			System.out.println(position.x);
			if(position.x > -0.8f) {
				position.x -= 0.02f;				
			}
		}
	}

}

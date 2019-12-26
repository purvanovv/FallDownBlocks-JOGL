package entities;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;

public abstract class Entity {
	protected RawModel model;
	protected Vector3f position;

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
	
	public abstract void move();
}

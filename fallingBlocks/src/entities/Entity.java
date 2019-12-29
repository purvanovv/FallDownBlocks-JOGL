package entities;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import textures.ModelTexture;

public abstract class Entity {
	protected RawModel model;
	protected Vector3f position;
	protected ModelTexture modelTexture;

	public Entity(RawModel model, Vector3f position, ModelTexture modelTexture) {
		this.model = model;
		this.position = position;
		this.modelTexture = modelTexture;
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

	public ModelTexture getModelTexture() {
		return modelTexture;
	}

	public void setModelTexture(ModelTexture modelTexture) {
		this.modelTexture = modelTexture;
	}
	
}

package entities;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import textures.ModelTexture;

public class FallObject extends Entity {

	private float speed;

	public FallObject(RawModel model, Vector3f position, float speed, ModelTexture modelTexture) {
		super(model, position, modelTexture);
		this.speed = speed;
	}

	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}

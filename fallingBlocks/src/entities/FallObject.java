package entities;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;

public class FallObject extends Entity {

	public FallObject(RawModel model, Vector3f position) {
		super(model, position);
	}

	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	@Override
	public void move() {
		float dy = -0.02f;
		if (position.y < -1.5f) {
			position.y = 1.5f;
		} else {
			increasePosition(0, dy, 0);
		}
	}

}

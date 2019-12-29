package entities;

import java.util.Random;

public class FallObject {

	private float speed;

	private Entity entity;

	private FallObjType type;

	private float minPosX;

	private float maxPosX;

	private float minSpeed;

	private float maxSpeed;

	public FallObject(Entity entity, FallObjType type, float minPosX, float maxPosX, float minSpeed, float maxSpeed) {
		this.entity = entity;
		this.type = type;
		this.minSpeed = maxSpeed;
		this.maxSpeed = maxSpeed;
		this.minPosX = minPosX;
		this.maxPosX = maxPosX;
		this.begin();
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public FallObjType getType() {
		return type;
	}

	public void setType(FallObjType type) {
		this.type = type;
	}

	public boolean collapseWithEntity(Entity collapsed) {
		if (this.entity.getPosition().y + 0.5 <= collapsed.getPosition().y
				&& (this.entity.getPosition().x <= collapsed.getPosition().x + 0.2f + 0.05f
						&& this.entity.getPosition().x >= collapsed.getPosition().x - 0.2f - 0.05f)) {
			return true;
		}
		return false;
	}

	public void begin() {
		this.speed = getRandom(minSpeed, maxSpeed);
		this.entity.getPosition().y = 1.5f;
		this.entity.getPosition().x = getRandom(minPosX, maxPosX);
	}

	private float getRandom(float min, float max) {
		Random rand = new Random();
		return min + rand.nextFloat() * (max - min);
	}

}

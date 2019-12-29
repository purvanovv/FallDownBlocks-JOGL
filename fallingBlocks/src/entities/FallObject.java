package entities;

public class FallObject {

	private float speed;
	
	private Entity entity;
	
	private FallObjType type;

	public FallObject(float speed, Entity entity, FallObjType type) {
		this.speed = speed;
		this.entity = entity;
		this.type = type;
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

	public void increasePosition(float dx, float dy, float dz) {
		this.entity.getPosition().x += dx;
		this.entity.getPosition().y += dy;
		this.entity.getPosition().z += dz;
	}


}

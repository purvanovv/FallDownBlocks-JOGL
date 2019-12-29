package entities;

public enum FallObjType {
	BANANA(0), APPLE(1), BOMB(2);

	private final int value;

	private FallObjType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}

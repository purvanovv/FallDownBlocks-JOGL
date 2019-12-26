package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;

public class Board extends Entity {

	public Board(RawModel model, Vector3f position) {
		super(model, position);
	}

	@Override
	public void move() {
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			if (position.x < 0.8f) {
				position.x += 0.02f;
			}

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			if (position.x > -0.8f) {
				position.x -= 0.02f;
			}
		}
	}

}

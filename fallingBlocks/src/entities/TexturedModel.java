package entities;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import textures.ModelTexture;

public class TexturedModel extends Entity {

	public TexturedModel(RawModel rawModel, ModelTexture modelTexture, Vector3f position) {
		super(rawModel, position,modelTexture);
	}

}

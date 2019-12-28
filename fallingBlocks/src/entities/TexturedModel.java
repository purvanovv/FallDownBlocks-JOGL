package entities;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import textures.ModelTexture;

public class TexturedModel extends Entity {
	private ModelTexture texture;

	public TexturedModel(RawModel rawModel, ModelTexture texture, Vector3f position) {
		super(rawModel, position);
		this.texture = texture;
	}

	public ModelTexture getTexture() {
		return texture;
	}

}

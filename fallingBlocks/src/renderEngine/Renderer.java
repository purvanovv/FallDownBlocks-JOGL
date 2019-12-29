package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import models.RawModel;
import shaders.StaticShader;
import toolbox.Maths;

public class Renderer {

	public void prepare() {
		GL11.glClearColor(1, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}

	public void render(Entity entity, StaticShader shader) {
		RawModel model = entity.getModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition());

		shader.loadTransformationMatrix(transformationMatrix);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getModelTexture().getTextureID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}

}
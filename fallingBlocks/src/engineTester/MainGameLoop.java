package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;

public class MainGameLoop {
	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();

		float[] vertices = { 
				-0.2f, -0.6f, 0,
				-0.2f, -0.7f, 0,
				 0.2f, -0.6f, 0,
				 0.2f, -0.7f, 0
				 };

		int[] indices = { 0, 1, 2, 1, 3, 2 };

		RawModel rawModel = loader.loadToVAO(vertices, indices);
		Entity entity = new Entity(rawModel, new Vector3f(0,0,0));

		while (!Display.isCloseRequested()) {
			renderer.prepare();
			entity.move();
			shader.start();
			renderer.render(entity,shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}

		loader.cleanUp();
		DisplayManager.closeDiplay();
	}
}

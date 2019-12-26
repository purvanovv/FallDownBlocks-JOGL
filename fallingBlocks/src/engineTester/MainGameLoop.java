package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Board;
import entities.FallObject;
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

		float[] vertices = { -0.2f, -0.6f, 0, -0.2f, -0.7f, 0, 0.2f, -0.6f, 0, 0.2f, -0.7f, 0 };

		int[] indices = { 0, 1, 2, 1, 3, 2 };

		float[] vertices2 = { -0.8f, -0.8f, 0, -0.8f, -0.4f, 0, -0.6f, -0.8f, 0, -0.6f, -0.4f, 0 };

		int[] indices2 = { 0, 1, 2, 2, 1, 3 };

		RawModel rawModelBoard = loader.loadToVAO(vertices, indices);
		Board board = new Board(rawModelBoard, new Vector3f(0, 0, 0));

		RawModel rawModelFallObj = loader.loadToVAO(vertices2, indices2);
		FallObject fallObject = new FallObject(rawModelFallObj, new Vector3f(0, 1.5f, 0));

		while (!Display.isCloseRequested()) {
			renderer.prepare();
			board.move();
			fallObject.move();
			shader.start();
			renderer.render(board, shader);
			renderer.render(fallObject, shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}

		loader.cleanUp();
		DisplayManager.closeDiplay();
	}
}

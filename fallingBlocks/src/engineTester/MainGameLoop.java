package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

		float[] vertices2 = { -0.05f, 0.1f, 0, 0.05f, 0.1f, 0, -0.05f, -0.1f, 0, 0.05f, -0.1f, 0 };

		int[] indices2 = { 0, 2, 1, 2, 3, 1 };

		RawModel rawModelBoard = loader.loadToVAO(vertices, indices);
		Board board = new Board(rawModelBoard, new Vector3f(0, 0, 0));

		RawModel rawModelFallObj = loader.loadToVAO(vertices2, indices2);
		List<FallObject> objects = initializeFallObjects(rawModelFallObj);

		while (!Display.isCloseRequested()) {
			renderer.prepare();
			board.move();
			shader.start();
			renderer.render(board, shader);

			increaseObjectsPosition(objects, board);
			multipleRenderObj(renderer, shader, objects);

			shader.stop();
			DisplayManager.updateDisplay();
		}

		loader.cleanUp();
		DisplayManager.closeDiplay();
	}

	private static List<FallObject> initializeFallObjects(RawModel rawModel) {
		List<FallObject> objects = new ArrayList<FallObject>();
		for (int i = 0; i < 5; i++) {
			float min = -0.95f;
			float max = 0.95f;
			float posX = getRandom(min, max);
			float minSpeed = -0.005f;
			float maxSpeed = -0.03f;
			float speed = getRandom(minSpeed, maxSpeed);
			FallObject fallObject = new FallObject(rawModel, new Vector3f(posX, 0, 0), speed);
			objects.add(fallObject);
		}
		return objects;
	}

	private static float getRandom(float min, float max) {
		Random rand = new Random();
		return min + rand.nextFloat() * (max - min);
	}

	private static void multipleRenderObj(Renderer renderer, StaticShader shader, List<FallObject> objects) {
		for (FallObject object : objects) {
			renderer.render(object, shader);
		}
	}

	private static void increaseObjectsPosition(List<FallObject> objects, Board board) {
		for (FallObject object : objects) {
			if (object.getPosition().y <= -1.5f || isObjectCollapseWithBoard(object, board)) {
				object.getPosition().y = 1.5f;
				float min = -0.95f;
				float max = 0.95f;
				object.getPosition().x = getRandom(min, max);
				float minSpeed = -0.005f;
				float maxSpeed = -0.03f;
				float speed = getRandom(minSpeed, maxSpeed);
				object.setSpeed(speed);
			} else {
				object.increasePosition(0, object.getSpeed(), 0);
			}
		}
	}

	private static boolean isObjectCollapseWithBoard(FallObject object, Board board) {
		if (object.getPosition().y + 0.5 <= board.getPosition().y
				&& (object.getPosition().x <= board.getPosition().x + 0.2f + 0.05f
						&& object.getPosition().x >= board.getPosition().x - 0.2f - 0.05f)) {
			return true;
		}
		return false;
	}

}

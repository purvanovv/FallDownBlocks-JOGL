package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Board;
import entities.FallObject;
import entities.TexturedModel;
import models.RawModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();

		float[] verticesFallObj = { 
				   -0.05f, 0.1f, 0,			
				   -0.05f, -0.1f, 0,
					0.05f, 0.1f, 0,
					0.05f, -0.1f, 0
					};
		
		float[] verticesBoard = { 
			   -0.2f, -0.6f, 0,
			   -0.2f, -0.7f, 0,
				0.2f, -0.6f, 0,
				0.2f, -0.7f, 0 
				};
		
		float[] vrticesBackground = { 
				-1f, 1f, 0,
				-1f, -1f, 0,		
				 1f, 1f, 0,
				 1f, -1f, 0
				 };

		int[] indices = { 
				0, 1, 2,
				1, 3, 2 
				};

		float[] textureCordinates = { 
				0, 0,
				0, 1,
				1, 0,
				1, 1
				};

		
		RawModel rawModelBackground = loader.loadToVAO(vrticesBackground, textureCordinates, indices);
		ModelTexture modelTextureBackground = new ModelTexture(loader.loadTexture("background.png"));
		TexturedModel backgroundModel = new TexturedModel(rawModelBackground, modelTextureBackground, new Vector3f(0, 0, 0));

		RawModel rawModelBoard = loader.loadToVAO(verticesBoard, textureCordinates, indices);
		ModelTexture modelTextureBoard = new ModelTexture(loader.loadTexture("box.png"));
		Board board = new Board(rawModelBoard, new Vector3f(0, 0, 0), modelTextureBoard);

		RawModel rawModelBomb = loader.loadToVAO(verticesFallObj, textureCordinates, indices);
		RawModel rawModelBananas = loader.loadToVAO(verticesFallObj, textureCordinates, indices);
		RawModel rawModelApple = loader.loadToVAO(verticesFallObj, textureCordinates, indices);
		List<FallObject> objects = initializeFallObjects(rawModelBomb, rawModelBananas, rawModelApple, loader);

		while (!Display.isCloseRequested()) {
			renderer.prepare();
			board.move();
			shader.start();
			renderer.render(backgroundModel, shader);
			renderer.render(board, shader);

			increaseObjectsPosition(objects, board);
			multipleRenderObj(renderer, shader, objects);

			shader.stop();
			DisplayManager.updateDisplay();
		}

		loader.cleanUp();
		DisplayManager.closeDiplay();
	}

	private static List<FallObject> initializeFallObjects(RawModel rawModelBomb, RawModel rawModelBananas,
			RawModel rawModelApple, Loader loader) {
		List<FallObject> objects = new ArrayList<FallObject>();
		float minPosX = -0.95f;
		float maxPosX = 0.95f;
		float minSpeed = -0.005f;
		float maxSpeed = -0.03f;

		// create bombs
		ModelTexture bombTexture = new ModelTexture(loader.loadTexture("bomb.png"));
		for (int i = 0; i < 2; i++) {
			float posX = getRandom(minPosX, maxPosX);
			float speed = getRandom(minSpeed, maxSpeed);
			FallObject fallObject = new FallObject(rawModelBomb, new Vector3f(posX, 0, 0), speed, bombTexture);
			objects.add(fallObject);
		}
		// create bananas
		ModelTexture bananasTexture = new ModelTexture(loader.loadTexture("bananas.png"));
		for (int i = 0; i < 2; i++) {
			float posX = getRandom(minPosX, maxPosX);
			float speed = getRandom(minSpeed, maxSpeed);
			FallObject fallObject = new FallObject(rawModelBananas, new Vector3f(posX, 0, 0), speed, bananasTexture);
			objects.add(fallObject);
		}
		// create apples
		ModelTexture appleTexture = new ModelTexture(loader.loadTexture("apple.png"));
		for (int i = 0; i < 2; i++) {
			float posX = getRandom(minPosX, maxPosX);
			float speed = getRandom(minSpeed, maxSpeed);
			FallObject fallObject = new FallObject(rawModelApple, new Vector3f(posX, 0, 0), speed, appleTexture);
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

package renderEngine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entities.FallObjType;
import entities.FallObject;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import textures.ModelTexture;

public class GameManager {

	private static final String BACKGROUND_IMAGE_FILE = "background.png";
	private static final String BOX_IMAGE_FILE = "box.png";
	private static final String BANANA_IMAGE_FILE = "bananas.png";
	private static final String APPLE_IMAGE_FILE = "apple.png";
	private static final String BOMB_IMAGE_FILE = "bomb.png";

	private static final int GAME_LIFES = 3;
	private static final int BANANA_COUNT = 2;
	private static final int BOMB_COUNT = 2;
	private static final int APPLE_COUNT = 2;

	private static final int BANANA_POINTS = 5;
	private static final int APPLE_POINTS = 3;

	private Entity boxEntity;
	private Entity backgroundEntity;
	private List<FallObject> fallObjects;
	private Loader loader;
	private Renderer renderer;
	private StaticShader shader;
	private int gameLifes;
	private int gamePoints;
	private boolean isGameRuning;

	public GameManager(Renderer renderer, StaticShader shader, Loader loader) {
		this.renderer = renderer;
		this.shader = shader;
		this.loader = loader;
		this.gameLifes = GAME_LIFES;
		this.gamePoints = 0;
		this.initGameModels();
	}

	private void initGameModels() {
		float[] verticesFallObj = { -0.05f, 0.1f, 0, -0.05f, -0.1f, 0, 0.05f, 0.1f, 0, 0.05f, -0.1f, 0 };

		float[] verticesBox = { -0.2f, -0.6f, 0, -0.2f, -0.7f, 0, 0.2f, -0.6f, 0, 0.2f, -0.7f, 0 };

		float[] verticesBackground = { -1f, 1f, 0, -1f, -1f, 0, 1f, 1f, 0, 1f, -1f, 0 };

		int[] indices = { 0, 1, 2, 1, 3, 2 };

		float[] textureCord = { 0, 0, 0, 1, 1, 0, 1, 1 };

		this.backgroundEntity = initEntity(new Vector3f(0, 0, 0), BACKGROUND_IMAGE_FILE, verticesBackground,
				textureCord, indices);
		this.boxEntity = initEntity(new Vector3f(0, 0, 0), BOX_IMAGE_FILE, verticesBox, textureCord, indices);
		this.fallObjects = initFallObjects(verticesFallObj, textureCord, indices);
	}

	public void start() {
		this.isGameRuning = true;
		Light light = new Light(new Vector3f(1, 1, 1), new Vector3f(1, 1, 1));
		while (!Display.isCloseRequested() && isGameRuning) {
			renderer.prepare();
			move(boxEntity);
			shader.start();
			renderer.render(backgroundEntity, shader);
			shader.loadLight(light);
			renderer.render(boxEntity, shader);

			increaseObjectsPosition(fallObjects, boxEntity);
			renderFallObjects();

			shader.stop();
			DisplayManager.updateDisplay();
		}

		System.out.println("Game lifes: " + this.gameLifes);
		System.out.println("Game points: " + this.gamePoints);

	}

	private void increaseObjectsPosition(List<FallObject> objects, Entity boardEntity) {
		for (FallObject object : objects) {
			Entity entity = object.getEntity();
			boolean isFallObjCollapseWithBox = object.collapseWithEntity(boardEntity);
			if (isFallObjCollapseWithBox) {
				switch (object.getType()) {
				case BANANA: {
					this.gamePoints += BANANA_POINTS;
					break;
				}
				case APPLE: {
					this.gamePoints += APPLE_POINTS;
					break;
				}
				case BOMB: {
					this.gameLifes -= 1;
					if (gameLifes < 0) {
						this.isGameRuning = false;
					}
					break;
				}

				default: {
					break;
				}
				}
				object.begin();
			} else if (object.getEntity().getPosition().y <= -1.5f) {
				object.begin();
			} else {
				entity.increasePosition(0, object.getSpeed(), 0);
			}
		}
	}

	private List<FallObject> initFallObjects(float[] verticesFallObj, float[] textureCord, int[] indices) {
		List<FallObject> fallObjects = new ArrayList<FallObject>();
		// create bombs
		for (int i = 0; i < BOMB_COUNT; i++) {
			FallObject fallObject = createFallObject(verticesFallObj, textureCord, indices, FallObjType.BOMB,
					BOMB_IMAGE_FILE);
			fallObjects.add(fallObject);
		}
		// create bananas
		for (int i = 0; i < BANANA_COUNT; i++) {
			FallObject fallObject = createFallObject(verticesFallObj, textureCord, indices, FallObjType.BANANA,
					BANANA_IMAGE_FILE);
			fallObjects.add(fallObject);
		}
		// create apples
		for (int i = 0; i < APPLE_COUNT; i++) {
			FallObject fallObject = createFallObject(verticesFallObj, textureCord, indices, FallObjType.APPLE,
					APPLE_IMAGE_FILE);
			fallObjects.add(fallObject);
		}

		return fallObjects;
	}

	private void renderFallObjects() {
		fallObjects.forEach(obj -> renderer.render(obj.getEntity(), shader));
	}

	private FallObject createFallObject(float[] verticesFallObj, float[] textureCord, int[] indices, FallObjType type,
			String texture) {
		float minPosX = -0.95f;
		float maxPosX = 0.95f;
		float minSpeed = -0.005f;
		float maxSpeed = -0.01f;
		Entity entity = initEntity(new Vector3f(0, 0, 0), texture, verticesFallObj, textureCord, indices);
		return new FallObject(entity, type, minPosX, maxPosX, minSpeed, maxSpeed);
	}

	private Entity initEntity(Vector3f position, String texture, float[] vertices, float[] textureCord, int[] indices) {
		RawModel rawModel = loader.loadToVAO(vertices, textureCord, indices);
		ModelTexture textureModel = new ModelTexture(loader.loadTexture(texture));
		TexturedModel model = new TexturedModel(rawModel, textureModel);
		return new Entity(model, position);
	}

	public void move(Entity entity) {
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			if (entity.getPosition().x < 0.8f) {
				entity.getPosition().x += 0.02f;
			}

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			if (entity.getPosition().x > -0.8f) {
				entity.getPosition().x -= 0.02f;
			}
		}
	}

}

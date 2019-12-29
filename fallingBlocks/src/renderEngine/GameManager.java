package renderEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entities.FallObjType;
import entities.FallObject;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import textures.ModelTexture;

public class GameManager {

	private Entity boxEntity;
	private Entity backgroundEntity;
	private List<FallObject> fallObjects;
	private Loader loader;
	private Renderer renderer;
	private StaticShader shader;

	public GameManager(Renderer renderer,StaticShader shader, Loader loader) {
		this.renderer = renderer;
		this.shader = shader;
		this.loader = loader;

		this.initGameModels();
	}

	private void initGameModels() {
		float[] verticesFallObj = { -0.05f, 0.1f, 0, -0.05f, -0.1f, 0, 0.05f, 0.1f, 0, 0.05f, -0.1f, 0 };

		float[] verticesBox = { -0.2f, -0.6f, 0, -0.2f, -0.7f, 0, 0.2f, -0.6f, 0, 0.2f, -0.7f, 0 };

		float[] verticesBackground = { -1f, 1f, 0, -1f, -1f, 0, 1f, 1f, 0, 1f, -1f, 0 };

		int[] indices = { 0, 1, 2, 1, 3, 2 };

		float[] textureCord = { 0, 0, 0, 1, 1, 0, 1, 1 };

		this.backgroundEntity = initEntity(new Vector3f(0, 0, 0), "background.png", verticesBackground, textureCord,
				indices);
		this.boxEntity = initEntity(new Vector3f(0, 0, 0), "box.png", verticesBox, textureCord, indices);
		this.fallObjects = initFallObjects(verticesFallObj, textureCord, indices);
	}

	public void start() {

		while (!Display.isCloseRequested()) {
			renderer.prepare();
			move(boxEntity);
			shader.start();
			renderer.render(backgroundEntity, shader);
			renderer.render(boxEntity, shader);

			increaseObjectsPosition(fallObjects, boxEntity);
			renderFallObjects();

			shader.stop();
			DisplayManager.updateDisplay();
		}
		
	}

	private void increaseObjectsPosition(List<FallObject> objects, Entity boardEntity) {
		for (FallObject object : objects) {
			Entity objEntity = object.getEntity();
			if (objEntity.getPosition().y <= -1.5f || isObjectCollapseWithBoard(objEntity, boardEntity)) {
				objEntity.getPosition().y = 1.5f;
				float min = -0.95f;
				float max = 0.95f;
				objEntity.getPosition().x = getRandom(min, max);
				float minSpeed = -0.005f;
				float maxSpeed = -0.03f;
				float speed = getRandom(minSpeed, maxSpeed);
				object.setSpeed(speed);
			} else {
				object.increasePosition(0, object.getSpeed(), 0);
			}
		}
	}

	private boolean isObjectCollapseWithBoard(Entity objEntity, Entity boardEntity) {
		if (objEntity.getPosition().y + 0.5 <= boardEntity.getPosition().y
				&& (objEntity.getPosition().x <= boardEntity.getPosition().x + 0.2f + 0.05f
						&& objEntity.getPosition().x >= boardEntity.getPosition().x - 0.2f - 0.05f)) {
			return true;
		}
		return false;
	}

	private List<FallObject> initFallObjects(float[] verticesFallObj, float[] textureCord, int[] indices) {
		List<FallObject> fallObjects = new ArrayList<FallObject>();
		// create bombs
		for (int i = 0; i < 2; i++) {
			FallObject fallObject = createFallObject(verticesFallObj, textureCord, indices, FallObjType.BOMB,
					"bomb.png");
			fallObjects.add(fallObject);
		}
		// create bananas
		for (int i = 0; i < 2; i++) {
			FallObject fallObject = createFallObject(verticesFallObj, textureCord, indices, FallObjType.BANANA,
					"bananas.png");
			fallObjects.add(fallObject);
		}
		// create apples
		for (int i = 0; i < 2; i++) {
			FallObject fallObject = createFallObject(verticesFallObj, textureCord, indices, FallObjType.APPLE,
					"apple.png");
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
		float maxSpeed = -0.03f;
		float posX = getRandom(minPosX, maxPosX);
		float speed = getRandom(minSpeed, maxSpeed);

		Entity entity = initEntity(new Vector3f(posX, 0, 0), texture, verticesFallObj, textureCord, indices);
		return new FallObject(speed, entity, type);
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

	private float getRandom(float min, float max) {
		Random rand = new Random();
		return min + rand.nextFloat() * (max - min);
	}

}

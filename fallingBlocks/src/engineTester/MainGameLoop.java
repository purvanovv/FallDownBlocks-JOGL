package engineTester;

import renderEngine.DisplayManager;
import renderEngine.GameManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();
		GameManager gameManager = new GameManager(renderer, shader, loader);

		gameManager.start();

		loader.cleanUp();
		DisplayManager.closeDiplay();
	}

}

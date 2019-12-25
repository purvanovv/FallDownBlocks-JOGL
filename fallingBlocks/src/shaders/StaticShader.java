package shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram{
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";

	private int location_transformationMatrix;

	public StaticShader() {
		super(VERTEX_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

}

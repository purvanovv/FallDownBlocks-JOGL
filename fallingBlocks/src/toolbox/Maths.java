package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Maths {
	public static Matrix4f createTransformationMatrix(Vector3f translation) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		return matrix;
	}
}

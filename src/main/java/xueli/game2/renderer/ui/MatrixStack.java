package xueli.game2.renderer.ui;

import org.lwjgl.utils.vector.Matrix3f;
import org.lwjgl.utils.vector.Vector2f;

import java.util.Stack;

public class MatrixStack {

    // Just store the result so that repeat calculation can be saved
    private final Stack<Matrix3f> matResults = new Stack<>();

    public MatrixStack() {
    }

    public void pushMatrix(Matrix3f mat) {
        if(matResults.empty()) {
            matResults.push(mat);
            return;
        }

        var lastResult = matResults.peek();
        var result = Matrix3f.mul(mat, lastResult, null);
        matResults.push(result);

    }

    public void popMatrix() {
        if(matResults.empty()) return;
        matResults.pop();

    }

    public void pushMark() {

    }

    public void popToMark() {

    }

    public Vector2f transform(Vector2f src) {
        if(matResults.empty()) return src;
        var transMat = matResults.peek();
        return new Vector2f(
                    transMat.m00 * src.x + transMat.m10 * src.y + transMat.m20,
                    transMat.m01 * src.x + transMat.m11 * src.y + transMat.m21
                );
    }

    public Vector2f delta(Vector2f src) {
        if(matResults.empty()) return src;
        var transMat = matResults.peek();
        return new Vector2f(
                transMat.m00 * src.x + transMat.m01 * src.y,
                transMat.m10 * src.x + transMat.m11 * src.y
        );
    }

}

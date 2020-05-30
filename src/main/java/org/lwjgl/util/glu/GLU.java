package org.lwjgl.util.glu;

import static org.lwjgl.opengles.GLES20.*;

public class GLU {

	public static final int GLU_INVALID_ENUM = 100900;
	public static final int GLU_INVALID_VALUE = 100901;
	public static final int GLU_OUT_OF_MEMORY = 100902;
	public static final int GLU_INCOMPATIBLE_GL_VERSION = 100903;

	public static String gluErrorString(int error_code) {
		switch (error_code) {
		case GLU_INVALID_ENUM:
			return "Invalid enum (glu)";
		case GLU_INVALID_VALUE:
			return "Invalid value (glu)";
		case GLU_OUT_OF_MEMORY:
			return "Out of memory (glu)";
		case GL_NO_ERROR:
			return "No error";
		case GL_INVALID_ENUM:
			return "Invalid enum";
		case GL_INVALID_VALUE:
			return "Invalid value";
		case GL_INVALID_OPERATION:
			return "Invalid operation";
		case GL_OUT_OF_MEMORY:
			return "Out of memory";
		}
		return null;
	}

}

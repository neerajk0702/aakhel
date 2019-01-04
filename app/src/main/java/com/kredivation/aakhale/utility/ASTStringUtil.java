package com.kredivation.aakhale.utility;


import com.kredivation.aakhale.framework.AppController;

/**
 * @author AST Inc.
 */
public class ASTStringUtil {

	public static String getStringForID(int id) {
		return AppController.getInstance().getResources().getString(id);
	}

	public static String getStringForID(int id, Object... formatArgs) {
		return AppController.getInstance().getResources().getString(id, formatArgs);
	}

	public static String getStringForName(String name) {
		return getStringForID(getIdForName(name));
	}

	public static int getIdForName(String name) {
		return AppController.getInstance().getResourceId(name, "string");
	}

	public static int getIdForName(String name, String packageName) {
		return AppController.getInstance().getResourceId(name, "string", packageName);
	}
}

package GUI;

public class Settings {
	private static final int MAIN_FRAME_WIDTH = 800;
	
	private static final int MAIN_FRAME_HEIGHT = 500;
	
	private static final int REFRESH_TIME_MIN = 2;
	
	private static final int REFRESH_TIME_MAX = 10;
	
	private static final int SPEED_TICK_SPACING = 2;
	
	private static final int INITIAL_MANUAL_ANGLE = 90;
	
	private static final int MANUAL_ANGLE_DELTA = 10;
	
	private static final String MANUAL_MODE = "m";
	
	private static final String SINGLE_MODE = "s";
	 
	private static final String AUTO_MODE = "a";

	/**
	 * @return the mainFrameWidth
	 */
	public static int getMainFrameWidth() {
		return MAIN_FRAME_WIDTH;
	}

	/**
	 * @return the mainFrameHeight
	 */
	public static int getMainFrameHeight() {
		return MAIN_FRAME_HEIGHT;
	}

	/**
	 * @return the refreshTimeMin
	 */
	public static int getRefreshTimeMin() {
		return REFRESH_TIME_MIN;
	}

	/**
	 * @return the refreshTimeMax
	 */
	public static int getRefreshTimeMax() {
		return REFRESH_TIME_MAX;
	}

	public static int getSpeedTickSpacing() {
		return SPEED_TICK_SPACING;
	}

	public static int getInitialManualAngle() {
		return INITIAL_MANUAL_ANGLE;
	}

	public static String getManualMode() {
		return MANUAL_MODE;
	}

	public static String getSingleMode() {
		return SINGLE_MODE;
	}

	public static String getAutoMode() {
		return AUTO_MODE;
	}

	public static int getManualAngleDelta() {
		return MANUAL_ANGLE_DELTA;
	}
	
}

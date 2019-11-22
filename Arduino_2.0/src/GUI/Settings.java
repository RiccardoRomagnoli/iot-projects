package GUI;

public class Settings {
	private static final int MAIN_FRAME_WIDTH = 800;
	
	private static final int MAIN_FRAME_HEIGHT = 500;
	
	private static final int REFRESH_TIME_MIN = 2;
	
	private static final int REFRESH_TIME_MAX = 10;
	
	private static final int SPEED_TICK_SPACING = 2;
	
	private static final String INITIAL_MANUAL_ANGLE = "90°";

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

	public static String getInitialManualAngle() {
		return INITIAL_MANUAL_ANGLE;
	}
	
}

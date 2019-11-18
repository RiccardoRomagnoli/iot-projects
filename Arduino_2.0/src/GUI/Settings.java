package GUI;

public class Settings {
	private static final int MAIN_FRAME_WIDTH = 800;
	
	private static final int MAIN_FRAME_HEIGHT = 500;
	
	private static final int REFRESH_TIME_MIN = 1;
	
	private static final int REFRESH_TIME_MAX = 10;

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
	
}

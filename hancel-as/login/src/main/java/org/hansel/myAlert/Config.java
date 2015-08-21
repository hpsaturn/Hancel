package org.hansel.myAlert;

/**
 * Created by hasus on 8/12/15.
 */
public class Config {

    // location
 	public static final int     TIME_AFTER_START   = 15;  // Start on x seconds after init Scheduler
	public static final long    DEFAULT_INTERVAL   = 1000 * 30 * 3;  // Default interval for background service
	public static final long 	DEFAULT_INTERVAL_FASTER = 1000 * 30 * 2;
	public static final float   ACCURACY 	= 200;
	public static final long    LOCATION_ROUTE_INTERVAL = 1000 * 60;
	public static final long    LOCATION_MAP_INTERVAL = 1000 * 120;

}

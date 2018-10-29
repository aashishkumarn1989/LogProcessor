package com.cs.logprocessor.constants;

public interface IApplicationConstants {


	public static final String PATH = "/com/aashish/logprocessor/logFile/LogFile.json";
	public static final String UTF = "UTF-8";

	public static final String CHILD_STEP = "child";
	public static final String PARALLEL_JOB = "parallelJob";
	public static final String PARENT_STEP = "parent";

	public static final String EMPTY_STRING ="";
	public static final String EVENT_LOG_TABLE_NAME = "EVENT_LOG_ALERT";
	public static final String EVENT_LOG_ALERT_INSERT_SQL = "INSERT INTO EVENT_LOG_ALERT(EVENT_ID,EVENT_TYPE,EVENT_HOST,EVENT_ALRT,EVENT_DURATION) VALUES (?,?,?,?,?)";
	public static final String EVENt_LOG_ALERT_COUNT_SQL = "SELECT COUNT(*) FROM EVENT_LOG_ALERT";
	public static final int DEFAULT_BATCH_SIZE = 500;
	public static final int DEFAULT_GRID_SIZE = 1;
	public static final int DEFAULT_THROTTLE_LIMIT = 5;
	public static final int DEFAULT_CHUNK_SIZE = 100000;


}

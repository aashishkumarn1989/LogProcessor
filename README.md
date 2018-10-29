
Requirements

Our custom-build server logs different events to a file. Every event has 2 entries in a log - one entry when the event was started and another when the event was finished. The entries in a log file have no specific order (it can occur that a specific event is logged before the event starts)

Every line in the file is a JSON object containing event data:

id - the unique event identifier
state - whether the event was started or finished (can have values "STARTED" or "FINISHED"
timestamp - the timestamp of the event in milliseconds

Application Server logs also have the additional attributes:

type - type of log
host - hostname

The program should:

Take the input file path as input argument
Flag any long events that take longer than 4ms with a column in the database called "alert" 
Write the found event details to file-based HSQLDB (http://hsqldb.org/)
 
in the working folder 
The application should a new table if necessary and enter the following values: Event id
Event duration
Type and Host if applicable
"alert" true is applicable

*********************************************************************************************
Solution:

LogProcessor : LogProcessor(spring-boot-batch-logprocessing) helps to address to above scenario by processing the file logs in parrallel processing using partitioning i.e via Multithreading and insert those records in H2 database(in memory database).

Technical details: 
1) Spring Boot for configuration
2) Spring Batch for processing the logs by executing the jobs through different parallel steps.
3) H2 Database to store the processed data.
4) JDK 8
5) Gradle as build tool. 
	
How to run the application: 
gradle clean bootRun

Input file path : 
currently the file has been placed in the context location with file name : LogFile.json. Inorder to give any file please change the location in application properties.


"# SpringBootLogProcessor" 

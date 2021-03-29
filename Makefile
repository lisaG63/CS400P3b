###############################################################################
# Makefile is a config file used by the Linux make command
# Linux/Max Users: to run the various commands, use these commands:
#                  make junit5      
#                  make sample_profiler      
#                  make my_profiler
#       
# Windows Users:   use batch files, unzip the make_batch_files.zip file
#                  to run the make_junit5.bat, use these commands:
#                  .\make_junit5
#                  .\make_sample_profiler      
#                  .\make_my_profiler
#
# Students may need to edit Makefile and or batch (bat) files 
# to work with their home environment.
# These build utility files are not required to be submitted.
###############################################################################
SAMPLE_PROFILE_SETTINGS = java -XX:+FlightRecorder -XX:StartFlightRecording=settings=profile,filename=sampleprofile_data.jfr

MY_PROFILE_SETTINGS = java -XX:+FlightRecorder -XX:StartFlightRecording=settings=profile,filename=myprofile_data.jfr

junit5:
	javac -cp .:./junit-platform-console-standalone-1.5.2.jar *.java
	java -jar junit-platform-console-standalone-1.5.2.jar --class-path . -p ""

sample_profiler:
	javac SampleProfilerApplication.java
	$(SAMPLE_PROFILE_SETTINGS) SampleProfilerApplication 10000000

my_profiler:
	javac MyProfiler.java
	$(MY_PROFILE_SETTINGS) MyProfiler 10000000

clean:
	\rm -f *.class
	\rm -f *.jfr
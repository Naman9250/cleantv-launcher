#!/bin/sh
set -e
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar
exec gradle "$@"

#!/bin/sh
export DYLD_ROOT_PATH="$SDKROOT"
export DYLD_FRAMEWORK_PATH="$CONFIGURATION_BUILD_DIR"
export IPHONE_SIMULATOR_ROOT="$SDKROOT"
export CFFIXED_USER_HOME="$USER_LIBRARY_DIR/Application Support/iPhone Simulator/User"
"$TARGET_BUILD_DIR/$EXECUTABLE_PATH" -RegisterForSystemEvents 2> "$PROJECT_DIR/errorLogFromLastBuild.txt"

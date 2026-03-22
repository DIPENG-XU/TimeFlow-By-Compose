#!/bin/bash
# Simple build script for convenient packaging

echo "Cleaning project..."
./gradlew clean

echo "Building release APK..."
./gradlew assembleTimeflowRelease

echo "Building release AAB..."
./gradlew bundleTimeflowRelease

# Get version name from build.gradle.kts
VERSION=$(grep "versionName" app/build.gradle.kts | sed 's/.*versionName = "\(.*\)".*/\1/')

echo "Copying outputs to timeflow/release..."
mkdir -p timeflow/release
cp app/build/outputs/apk/timeflow/release/app-timeflow-release.apk timeflow/release/timeflow-${VERSION}.apk
cp app/build/outputs/bundle/timeflowRelease/app-timeflow-release.aab timeflow/release/timeflow-${VERSION}.aab

echo "Build complete. Check timeflow/release/ for APK and AAB files."

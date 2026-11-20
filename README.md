# Exercise 4 Android APK — Ripeness Tally

GitHub account: **yei-241**.

An offline Android tool for manually counting strawberry samples by ripeness. It is useful during image collection or dataset review: record ripe, medium-ripe, and unripe samples and see the class distribution.

## Features

- Count three ripeness categories with one tap.
- Display the total and category percentages.
- Save counts locally and restore them after reopening.
- Reset with a confirmation dialog.
- No account, network permission, or server is required.

This is a manual tally tool. The app does not run a ripeness detection model.

## Build and download

Push to `main`, or open **Actions → Build Android APK → Run workflow**, selecting `main`.

The workflow compiles the app, runs Android Lint, verifies the APK signature, uploads the APK artifact, and publishes a Release. Download `app-debug.apk` from **Releases**, or from the `exercise4-apk` artifact in a successful run.

Build environment: JDK 17, Gradle Wrapper 8.11.1, Android Gradle Plugin 8.9.2, compile/target SDK 35. Minimum device version: Android 6.0 (API 23).

## Classroom verification

The base exercise requires a generated Android APK. A successful workflow and its downloadable APK provide the build evidence.

For a physical-device demonstration, install the APK on an Android phone, count samples in all three categories, reopen the app to verify saved values, and test reset/cancel. Explain how category counts help identify imbalance while collecting a strawberry dataset. Device installation and functional testing must be recorded separately from CI build success.

This is a debug APK for classroom demonstration. Different clean runners may use different debug signing keys; updates from a differently signed build can require uninstalling the old app, which clears its stored counts.

## Source acknowledgment

The build setup is adapted from the earlier classroom example at https://github.com/Seven17777/session2-android-apk. The three-category tally interface and app package are implemented for this exercise. This repository is created independently, with its own Git history.

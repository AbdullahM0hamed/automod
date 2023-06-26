# Downloading

To download automod, simply download the [raw script](https://github.com/AbdullahM0hamed/automod/raw/master/automod) and place it in $PATH.

# Dependencies

• dx

• aria2c/curl/wget (any, prioritised in that order)

• kotlinc (optional - but if you are paranoid and want App.kt to be compiled locally, you will need this)

• java

# Usage

To use this, simply run `automod [APK]`

# What is this

This program makes the app's application class inherit from a custom Application class, which allows us to run our code so we can add theme + extension support to closed-source apps

# TODO
- [x] - Support apps that don't have an Application class, by adding our own in manifest

- [ ] - Sign the output APK

- [ ] - Replace google services with microG in some apps (like YouTube)

- [x] - Properly Zip/Unzip APKs where there are multiple files with same name but different cases - an example (at least for me): https://play.google.com/store/apps/details?id=com.tavultesoft.kmapro

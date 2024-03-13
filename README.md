# What is this

This program makes the app's application class inherit from a custom Application class, which allows us to run our code so we can add theme + extension support to closed-source apps

# TODO
- [x] - Support apps that don't have an Application class, by adding our own in manifest

- [x] - Sign the output APK

- [x] - Properly Zip/Unzip APKs where there are multiple files with same name but different cases - an example (at least for me): https://play.google.com/store/apps/details?id=com.tavultesoft.kmapro
- [ ] - Load plugin
- [x] - Load automod settings by holding back / Dynamic shortcut for devices where holding back fails

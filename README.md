## Tor Android

This is native Android `TorService` built on the Tor shared library built for
Android.  The included `libtor.so` binaries can also be used directly as a tor
daemon.

Currently, Tor Android is built with the following versions of `tor`, `libevent`, `openssl`, `zlib` and `zstd`:

| Component |                                                                           Version |
|:----------|----------------------------------------------------------------------------------:|
| tor       |        [0.4.9.5](https://forum.torproject.org/c/news/tor-release-announcement/28) |
| libevent  | [2.1.12](https://github.com/libevent/libevent/releases/tag/release-2.1.12-stable) |
| OpenSSL   |            [3.5.5](https://github.com/openssl/openssl/releases/tag/openssl-3.5.5) |
| zlib      |                       [1.3.2](https://github.com/madler/zlib/releases/tag/v1.3.2) |
| zstd      |                     [1.5.7](https://github.com/facebook/zstd/releases/tag/v1.5.7) |


First add the repo to your top level Gradle file(settings.gradle.kts or settings.gradle):

```
pluginManagement {
    repositories {
        google ()
        mavenCentral()
        gradlePluginPortal()
    }
}
```

Then add the `tor-android` and `jtorctl` dependencies to your project:
```
dependencies {
    implementation("info.guardianproject:tor-android:0.4.9.5")
    implementation("info.guardianproject:jtorctl:0.4.5.7")
}
```

Apps using tor-android need to declare the `INTERNET` permission in their Android Manifest file:

```xml
    <uses-permission android:name="android.permission.INTERNET"/>
```

Tor protects your privacy on the internet by hiding the connection 
between your Internet address and the services you use. We believe Tor
is reasonably secure, but please ensure you read the instructions and
configure it properly.

## Minimum Requirements 

In order to use tor-android you need to target Android **API 24** or higher. 

It runs on the following hardware architectures:
- `arm64-v8a` 
- `armeabi-v7a`
- `x86`
- `x86_64`






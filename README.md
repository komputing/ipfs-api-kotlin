# [IPFS](http://ipfs.io) API for kotlin to access a IPFS node via http

[![Release](https://jitpack.io/v/ligi/ipfs-api-kotlin.svg)](https://jitpack.io/#ligi/ipfs-api-kotlin)

# Get it

You can get the artifact via jitpack - here you see how to use with gradle - for other build systems have a look [here](https://jitpack.io/#ligi/ipfs-api-kotlin)

``` groovy
repositories {
  maven { url "https://jitpack.io" }
}
```

``` groovy
dependencies {
  compile 'com.github.ligi:ipfs-api-kotlin:$current_version'
}
```

# Use it with kotlin

### add a string
```kotlin
val multihash = IPFS().add.string("test-string").Hash
```

### get a string
```kotlin
val content = IPFS().get.cat(multihash)
```

### get commit from version
```kotlin
val commit = IPFS().info.version().Commit
```

# Use it with java

you can also use it from java - but it is more verbose:

```java
final String multihash = new IPFS().getAdd().string("test-string").getHash();
```

# Apps that use this library

* [IPFSDroid](http://github.com/ligi/IPFSDroid)
* [Kontinuum](http://github.com/ligi/kontinuum)

Please send a message or even PR if you write/find an app that also uses this library.

# Dependencies 

We depend on [okhttp](http://square.github.io/okhttp) and [moshi](https://github.com/square/moshi) which play well together with [okio](https://github.com/square/okio)

# License 

MIT

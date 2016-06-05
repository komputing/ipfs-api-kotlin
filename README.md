# [IPFS](http://ipfs.io) API for kotlin to access a IPFS node via http

[![Build Status](https://snap-ci.com/ligi/ipfs-api-kotlin/branch/master/build_image)](https://snap-ci.com/ligi/ipfs-api-kotlin/branch/master)

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

add a string
```kotlin
  val multihash = IPFS().add.string("test-string").Hash
```

get commit from version

```kotlin
  val commit = IPFS().info.version().Commit
```

# Use it with java

you can also use it from java - but it is more verbose there as e.g. we have no default parameters. 

```java
 final IPFS ipfs = new IPFS("http://127.0.0.1:5001/api/v0/",
                            new OkHttpClient.Builder().build(),
                            new Moshi.Builder().build());
  
  final String multihash = ipfs.getAdd().string("test-string", "string", "string").getHash();
```

# Dependencies 

We depend on [okhttp](http://square.github.io/okhttp) and [moshi](https://github.com/square/moshi) which play well together with [okio](https://github.com/square/okio)

# License 

MIT

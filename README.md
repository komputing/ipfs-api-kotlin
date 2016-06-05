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

# Use it

add a string
```kotlin
  val multihash = IPFS().add.string("test-string").Hash
```

get commit from version

```kotlin
  val commit = IPFS().info.version().Commit
```

# License 

MIT

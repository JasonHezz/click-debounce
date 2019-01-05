| README.md |
|:---|

# asm-clickdebounce

[![](https://jitpack.io/v/SmartDengg/asm-clickdebounce.svg)](https://jitpack.io/#SmartDengg/asm-clickdebounce)

It is a gradle plugin that uses bytecode weaving technology to solve the click debounce problem of Android applications.

Safe, efficient, easy to use, support incremental build to avoid waste of build time.

I also wrote a **[blog](https://www.jianshu.com/p/28751130c038)** to share my ideas to solve the click debounce.

*Note: This repository is just a gradle plugin, responsible for bytecode weaving work, Android runtime library please move [here](https://github.com/SmartDengg/asm-clickdebounce-runtime).*


## Requirements

- JDK 1.7 +
- Gradle 3.0.0 +

## To build

```bash

$ git clone git@github.com:SmartDengg/asm-clickdebounce.git
$ cd asm-clickdebounce/
$ ./gradlew build

```

## Getting Started

**Step 1**. Add the JitPack repository and the plugin to your buildscript:

```groovy

buildscript {

    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        ...
        classpath 'com.github.SmartDengg.asm-clickdebounce:click-debounce-gradle-plugin:1.0.0'
    }
}

```

**Step 2**. Apply it in your module:

*It supports 'com.android.application', 'com.android.library' and 'com.android.feature'*.

```groovy

apply plugin: 'smartdengg.clickdebounce'
// or apply plugin: 'clickdebounce'

```


**Step 3 (Optional)**. Enable logging by adding the following code to your build.gradle:

```groovy

debounce.loggable = true

```

**Step 4 (Optional)**. Set the debounce window time(default is 300 milliseconds):

```java

DebouncedPredictor.FROZEN_WINDOW_MILLIS = 400L

```


## How it works

**Will not intercept the touch event delivery, only intercepted in the callback of the click event, so that it will not be passed to the business logic.**

![](art/clickdebounce.png)

## Support

- [x] [View.OnClickListener](https://developer.android.com/reference/android/view/View.OnClickListener)
- [x] [AdapterView.OnItemClickListener](https://developer.android.com/reference/android/widget/AdapterView.OnItemClickListener)

## R8 / ProGuard (Not Required)

Pure bytecode weaving without any reflections. No Proguard rules are required.

## Bugs and Feedback

For bugs, feature requests, and discussion please use [GitHub Issues](https://github.com/SmartDengg/asm-clickdebounce/issues). Or send email to hi4joker@gmail.com.


## Found this project useful

<p align="center">:heart: Hope this article can help you. Support by clicking the :star:, or share it with people around you. :heart:  </p>


## About me

email : hi4joker@gmail.com

blog  : [小鄧子](https://www.jianshu.com/u/df40282480b4)

weibo : [-小鄧子-](https://weibo.com/5367097592/profile?topnav=1&wvr=6)


## License

See the [LICENSE](LICENSE) file for license rights and limitations (MIT).
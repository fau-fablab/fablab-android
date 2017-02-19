# fablab-android [![Build Status](https://travis-ci.org/fau-fablab/fablab-android.svg?branch=master)](https://travis-ci.org/fau-fablab/fablab-android)

fablab-android is an Android application that uses a [Dropwizard](http://www.dropwizard.io) based [REST-server](https://github.com/fau-fablab/fablab-server).

Development happens at the [@fau-fablab repository](https://github.com/fau-fablab/fablab-android). For historical reasons, releases to the Google Play Store happen via pullrequests to [@FAU-Inf2](https://github.com/FAU-Inf2/fablab-android) (FAU, chair of computer science 2), some automagic travis build and a friendly email to the folks at Inf2.

## Build

To build the app, run:

	./gradlew build

This will also integrate the [app-common](https://github.com/fau-fablab/app-common) repository (formerly called fablab-common) that contains interfaces and entities used for both the server and the Android application.

To make use of the JaxRs interfaces in the Android application, apply the following gradle task:

	./gradlew jaxRs2Retrofit-debug

This will generate a java-gen folder which contains the transformed JaxRs interfaces from the fablab-common repository. These interfaces can be used by Retrofit for implementing a REST-client.


## Libraries
fablab-android uses the following libraries and software:
* [Retrofit](http://square.github.io/retrofit/) License: [Apache License Version 2.0](http://square.github.io/retrofit/#license)
* [JaxRs2Retrofit] (https://github.com/Maddoc42/JaxRs2Retrofit) License: [Apache License Version 2.0](https://github.com/Maddoc42/JaxRs2Retrofit#license)
* [OkHttp](http://square.github.io/okhttp/) License [Apache License Version 2.0](http://square.github.io/okhttp/#license)
* [okio](https://github.com/square/okio) License [Apache License Version 2.0](https://github.com/square/okio/blob/master/LICENSE.txt)

## Licence
    Copyright 2015 MAD FabLab team and others

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


## Contact
Feel free to contact us: app@fablab.fau.de


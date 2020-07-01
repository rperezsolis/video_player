# Video Player

### Description

The app imitates the facebook live story screen UI, this means you can take video from the front camera as well as play a video from a url.

The project make use of some Jetpack libraries and classes, such as: 
- [Navigation component](https://developer.android.com/guide/navigation) which help us to implement our app navigation in a very easy and organized way.
- [Data Binding](https://developer.android.com/topic/libraries/data-binding) which allow us to bind UI components in the layouts to data sources in the app using a declarative format.
- [Livedata](https://developer.android.com/topic/libraries/architecture/livedata) and [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) to program our app in a reactive way.
- To be able of use the device camera, the project make use of [CameraX](https://developer.android.com/training/camerax) which provides a consistent and easy-to-use API surface that works across most Android devices, with backward-compatibility to Android 5.0 (API level 21).

To play video from internet [Exoplayer](https://github.com/google/ExoPlayer) is used. This is an application level media player for Android. It provides an alternative to Android’s MediaPlayer API for playing audio and video both locally and over the Internet. The app supports .mp4 and ​hls streams. 
Both, the broadcasting video screen and the play video screen show a chat just as in the facebook live story screen, to build the list of messages the project uses [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview) which is a more advanced version of ListView.
The project also uses [Glide](https://github.com/bumptech/glide), an image loading and caching library for android.

#### Follow the next instructions to run the application:

- Download the project as a zip and paste it in a location of your preference. 
- Unzip de project.
- Open the project with Android Studio. If you don't have Android Studio installed yet, you can found the instructions to install it [here](https://developer.android.com/studio/install).
- Run the project. (To run the project, it is recommended to use a physical device, since both video playback and video recording are not watched properly using an emulator. In any case, if you do not have a physical device, you can find the instructions to install an emulator [here](https://developer.android.com/studio/run/managing-avds#createavd)).

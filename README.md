[![](https://jitpack.io/v/javaherisaber/LiveX.svg)](https://jitpack.io/#javaherisaber/LiveX)
# LiveX

Collection of LiveData extensions for android to better handle event drivent tasks

LiveData     |    LiveEvent   |   OneShotLiveEvent   |   SingleLiveEvent   
:-----------:|:--------------:|:--------------------:|:--------------------:
Multiple observers can register, all of them receive the event based on lifecycle | Multiple observers can register, each one receive the event only once | Only one observer can register and receive the event only once | Multiple observers can register, only the first one receive the event

# Dependency
Top level build.gradle
```groovy
allprojects {
   repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Module level build.gradle
```groovy
dependencies {
  implementation "com.github.javaherisaber:LiveX:$versions.liveX"
}
```

# How to use
```kotlin
class MyViewModel : ViewModel() {
   private val _data = MutableLiveData<String>()
   val data: LiveData<String> = _data

   private val _clickedOnAButton = OneShotLiveEvent<Unit>()
   val clickedOnAButton: LiveData<Unit> = _clickedOnAButton
   
   private val _filtered = LiveEvent<String>()
   val filtered: LiveData<String> = _filtered
   
   private val _searched = SingleLiveEvent<String>()
   val searched: LiveData<String> = _searched
   
   fun foo() {
      // we need this data on every lifecycle change
      _data.value = "Data to be shown every time"
      
      // clicking on the button to test OneShotLiveEvent
      _clickedOnAButton.call()
      
      // filtering results for multiple listeners of the LiveEvent
      _filtered.value = "Some Filter"
      
      // search result must be shown to the first listener of the SingleLiveEvent
      _searched.value = "Input of the user"
   }
}

class SomeView : Fragment() {
   val viewModel: MyViewModel by activityViewModels()
   
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.data.observe(viewLifecycleOwner) { data ->
           // use data to display information on the screen for every lifecycle change
        }
        
        viewModel.clickedOnAButton.observe(viewLifecycleOwner) {
           // do something for button click
        }
        
        viewModel.filtered.observe(viewLifecycleOwner) { filter ->
           // you can use the [filter] value here
        }
        
        viewModel.searched.observe(viewLifecycleOwner) { query ->
           // the [query] is ready to be applied
        }
    }
}
```

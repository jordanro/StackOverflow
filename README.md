

<h1>StackOverflow Application</h1>
  
  Built as a single Activity application with 2 Fragments:
  
  <h2>QuestionListFragment</h2> - Responsible for showing a paginated list of StackOverflow questions and an ability to filter answered questions.
  
  Data loading and presentation is done using the [Paging Library](https://developer.android.com/topic/libraries/architecture/paging) with the Network & Database approach.
  
  It is implemented by the MVVM design pattern:
  
  <h3>Model Layer</h3>
  
  StackOverflowService -> Handles the loading of data from Stackoverflow API. Implemented using the [Retrofit library](https://square.github.io/retrofit/) 
  
  AppDatabase and QuestionsDao -> Responsible for saving the network data localy and retrieving a filtered and non filtered data. Implemented using the [Room](https://developer.android.com/jetpack/androidx/releases/room) 
  
  
  
  QuestionListFragment -> Observes LiveData of Questions and delgate that to the PagerListAdapter
      
  QuestionListViewModel -> Supplies the fragment with LiveData of questions depending on the state of the filter(All/Ansered only)
  
  QuestionsRepository -> Generates a filter
  
  
  
  **QuestionDetailsFragment** - Showing a selected question inside a webview, supporting page loading indicator and history navigation.
  
  
  
  
  

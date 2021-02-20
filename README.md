

<h1>StackOverflow Application</h1>
  
  Built as a single Activity application with 2 Fragments:
  
  <h2>QuestionListFragment</h2> - Responsible for showing a paginated list of StackOverflow questions and an ability to filter answered questions.
  
  Data loading and presentation is done using the [Paging Library](https://developer.android.com/topic/libraries/architecture/paging) with the Network & Database approach.
  
  It is implemented by the MVVM design pattern:
  
  <h3>Model</h3>
  
  **StackOverflowService:** Responsible for loading the data from Stackoverflow API. Implemented using the [Retrofit library](https://square.github.io/retrofit/) 
  
  **AppDatabase and QuestionsDao:** Responsible for saving the network data localy and retrieving a filtered and non filtered data. Implemented using the [Room](https://developer.android.com/jetpack/androidx/releases/room) 
  
  **QuestionBoundaryCallback:** Handles the orchestration between Network Data and Local storage. 
  Responsible for loading data from the network when needed and saving it to the Database.
  Handles refresh.
  
  **QuestionResult:** An object holding references to the LiveData<PagedList<Question>> generated from the database and the LiveData of Network error that might be comming from the network requests done by the QuestionBoundaryCallback object.
  
  **QuestionsRepository:** Exposes methods for constructing QuestionResult objects based on the filter state
  
 <h3>View Model</h3>   
      
  **QuestionListViewModel:** Supplies the fragment with LiveData of LiveData<PagedList<Question>> retrieved from the QuestionsRepository.
  Handles refresh and filter switches request delegated fromthe fragment 
  
  <h3>View</h3>
  
  **QuestionListFragment:** Observes LiveData of Questions and delgate that to the PagerListAdapter.
  Delegate Question click events to overlaying Activity. 
  
  
  **QuestionDetailsFragment** - Showing a selected question inside a webview, supporting page loading indicator and history navigation.
  
  
  
  
  

# Android # Best practice Challenge for MVVM x RecyclerView

[活動介紹](https://medium.com/gdg-taipei/android-best-practice-challenge-for-mvvm-x-recyclerview-acd9e9ad0dae)

## 特色

- Kotlin 1.4
- `Activity` 用 view binding 取代 `findViewById`
- `RecyclerView`
  - 用 `ListAdapter` 做 `RecyclerView` adapter
  - List item 用 data binding
  - `ViewHolder` 有 `ViewModel` 的 reference
- 用 Dagger Hilt 做 dependency injection
- `@Parcelize`
- MVVM
  - 單一 Gradle module
  - 以 package 分開 UI、domain 和 data layer
  - `ViewModel` 用了 `SavedStateHandle` 儲存選用的排序方式，即使 `Activity` 被 kill 之後重開都能保持之前的設定
  - `ViewModel` 經 `LiveData` 通知 `Activity`；`Activity` 直接 call `ViewModel` method 通知 `ViewModel`
  - `ViewModel` call domain layer (`UseCase`)，domain layer 再 call data layer (`DataSource`) 以取得要顯示的內容

## `RecyclerView`

- 用了 `ListAdapter`，因為它會幫你在開 thread 做 diff，而且 `DiffUtil.ItemCallback` 要自己寫的 code 比 `DiffUtil.Callback` 少
- `ViewHolder` 內有 `bind` method，裏面負責更新 list item UI
- `ViewModel` 和 `LifecycleOwner` 都傳入去 `ViewHolder`，方便 data binding 不用再 call `executePendingBindings`； Click listener 用了
  data binding 直接 call `ViewHolder` 的 method，不用再傳 lambda 或者 callback 出去 `Activity`

## `Activity`

- 用 view binding 是因為本身只使用了 `findViewById`
- 因為這次用了 `Activity` 放 `RecyclerView`，所以不用擔心 configuration change 時會有 memory
  leak，可參考 [Tracing simple memory leak around RecyclerView using LeakCanary](https://yfujiki.medium.com/tracing-simple-memory-leak-around-recyclerview-using-leakcanary-927460532d53)
  - 留意 `Fragment` 用 view binding 或 data binding 都要在 `onDestroyView`
    時[清除 binding 的 reference](https://developer.android.com/topic/libraries/view-binding#fragments)
- `Activity` 主要負責處理 `LiveData` 的改動，例如 employee list 改變、需要顯示 toast 之類

## `ViewModel`

- 負責外露 `LiveData` 給 `Activity` 或 `Fragment`、接駁住 domain layer
- 因為顯示 toast 不會在 configuration change 後再顯示多次，所以用了 `OneOffEvent` 而非單純使用 `LiveData`

## Domain layer

- 這次因為沒有什麼特別功能，所以只是把 data layer 的東西左右交右手去 presentation layer

## Data layer

- 就是包住那個 `DummyEmployeeDataUtils`
- 用了 IO dispatcher 來模仿一般的情況
- 留意 `LiveData` 只在 presentation layer 用，其餘地方應該用 Coroutine

## Dependency injection & testing

- 用了 Dagger Hilt 做 dependency injection，因為方便寫 unit test
- 每個 layer 都有 interface 就是為了方便在 unit testing 可以 mock interface 而不是 mock concrete class
- Coroutine dispatcher 用了 Dagger inject 是為了方便 unit testing 時可以換做其他 dispatcher
- Unit testing 用了 MockK 和 Strikt，assertion library 用其他都沒大問題，都是個人喜好
- UI test 很久沒寫過，所以只寫了一個 test case 就算

## 其他

因為這個 challenge 比較簡單，所以就不搞到太複雜。但如果規模再大的 project 應該要把不同 layer 的 entity class 分開。 如果全部 layer 都共用 `Employee` data class
那只會愈來愈亂。例如加了不同的 annotation（Gson + Room 之類）再加上為了方便在 presentation layer 用再加上一些不會在 backend response 或 database 出現的
property。

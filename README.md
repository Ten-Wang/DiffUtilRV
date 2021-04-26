# Android Best Practice Challenge for MVVM x RecyclerView

https://medium.com/gdg-taipei/android-best-practice-challenge-for-mvvm-x-recyclerview-acd9e9ad0dae

由於題目只是**MVVM x RecyclerView**，所以跳過了Dependency Injection和Architecture layer
只把專案改成了Kotlin及簡單的MVVM

**技術棧：**
- Activity中使用ViewBinding
- List item使用DataBinding
- Adapter使用ListAdapter

# 詳情：

Adapter：
- Adapter屬於View level，我個人喜歡定義一個generic的[adapter](app/src/main/java/com/example/recyclerview/RecyclerViewAdapter.kt)
- Adapter的data type繼承[ListItem](app/src/main/java/com/example/recyclerview/ListItem.kt) interface
- ViewHolder的layout resource由ListItem的`layoutId`提供
- 這樣做的好處是Adapter可以重用

ViewHolder：
- ViewHolder只負責hold住data binding的instance

ListItem:
- ListItem是沒有Android class reference的獨立data class(除了item的layout resource id)
- 容易寫ListItem的unit test
- 如果要set listener，例如個別child view的OnClickListener，可以用DataBinding bind到ListItem的function
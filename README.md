# DiffUtilRV

看了看大家寫的，好像很少人寫java版本的．為了那些跟我一樣平常只跟java版本打交道的夥伴，我就來寫java的版本吧！

話雖如此，我還是憑藉著google寫了一個Kotlin的版本，趕在期限的最後一天寫完了，放在最下面"更多"的部分．

不管怎樣，讓我們先老調重彈，從MVC/MVP/MVVM開始吧．

## MVC,MVP,MVVM

用一個生活上的例子來代表 : 

假設你約好到朋友家去玩，希望到車站時可以接你.... 

#### MVC 
> 朋友回答: 好的，我會開一台1996年由福特生產，內部有配備Ａ牌安全氣囊> > ......由台灣公司代理的白色小汽車，
> 
> 車牌是ACC-1425，我會漢口路右轉，再走民權西路.....最後在車站接你回來．
> 
>>如果朋友這樣回答，你一定是覺得說這麼多幹嘛？車子是怎麼生產，走什麼路來的，對於你來說，
>>
>>根本不是重點．


新人常見的寫法，將所有事情都一鼓腦兒塞進activity中，如何取資料，取完資料要做什麼什麼處

理，怎麼傳遞，如何呈現在畫面上，通通在一個地方做完．

雖然在功能面上是沒有問題，但是在修改及維護上會有許多的麻煩，例如有天api加了一個小

欄位，就要把所有使用到這個api的activity全部都修改一次．隨著功能的增長，

整個activity的混亂程度更是會突破天際．在寫測試時更是會寫到吐出來，最後只能靠手動


#### MVP
>  朋友回答:好的，我會開車去接你回來．
>
>>不用知道車子的生產過程，也不用知道中間的路程．這些過程都交給朋友幫你處理好了．你所需要
>>
>>做的就是等朋友來然後舒舒服服的坐車去朋友家...
>>
>>等等，突然想到還沒買給女朋友的名產...那家店剛好跟你朋友家是反方向
>>

我們將生產與傳遞資料的事物交給一個代理人(Presenter)
全權處理，並不需要知道資料的來源．相信代

理人會處理好這個部分的事物．View所需要做的就是等Presenter根據你的行為提供資料．

但是基本上就是跟這個Presenter綁定了，View需要知道Presenter，而Presenter也需要知道

View．在畫面邏輯新增或修改的時候，需要同時改寫View和Presenter兩方，大幅增加工作量 



#### MVVM
>  朋友回答:好的，會有台車在那．
>
>>到了車站有台車就停在你面前，你想要做什麼都可以....
>>

ViewModel同樣是一個代理人的身份，與Presenter不同的地方在於，ViewModel不在意資料是

如何被使用的．ViewModel只負責提供資料，要拿去顯示在RecyclerView上也好，或是跳出Dialog

也罷，那都是使用資料的View層所決定的．而View與ViewModel可以做的單方面的依賴就是

利用到了觀察者模式的特性．

常常有人問，"使用了DataBinding或是LiveData，是不是就是MVVM了?"，其實這些都是Google官方

輔助的一種MVVM實作，還是要看怎麼使用，我曾看過外包每次使用到LiveData時全部使用getValue

而不是observe來接收數值變化．或是使用DataBinding時把整個View傳進ViewModel裡面，這些都

失去了MVVM初始為了降低耦合程度的設計


## 單一職責與最小知道

在MVVM中，單一職責與最小知道原則是非常重要的．可以說是MVVM的核心概念也不為過．

###### 單一職責

當某個組件需要對很多層級負責時，就很容易膨脹的非常快，成為所謂的God Object，最後甚至變成沒有人可以理解他在做什麼用就對了的黑盒子狀態．在MVC中就

很常發生這個問題．

在開始寫code之前，先問問自己，這行code是要做什麼的，它放在這邊對嘛？

###### 最小知道

當某個組件知道了太多他在使用上不需要知道的東西，就很容易產生side effect ． 明明我這次沒有修改到的東西，但是因為其他地方有引用．結果就壞掉了．

另外就是由於MVVM的設計，不會知道外面是怎麼使用自己，所以如果提供過多的資料，就容易被誤用．因此，下層的組件應該只提供最少的可行的資訊量給上層．

在開始寫code之前，先問問自己，我有必要要知道這個嘛？不知道這個我可以正常使用嘛？


## 實作
基本上都是使用官方的推薦庫．

###### DataBinding:

DataBinding可以協助進行View層與ViewModel的觀察與綁定，減少Activity內不必要的程式碼．不過我個人還是比較喜歡寫在activity內，在追code的時候

只要看一個地方就好．DataBinding的另外一個好處是不會因為生命週期的問題出現npm的閃退，以前使用findViewById都要處理這個View找不到的情況，在使用

DataBinding後就幾乎不會發生這個問題了．

###### ViewModel:

負責管理商業邏輯與畫面溝通的部分，越簡單越好．越簡單就代表耦合性越低． 當一個ViewModel過大時，十之八九就是商業邏輯放在了ViewModel．

在官方推出ViewModel之前的MVVM是非常難實作的，主要是由於Activity的生命週期不能掌控，所以造成很多自製的ViewModel仍然要遷就View的生命週期，

在官方推出了ViewModel與lifecyclerObserver 之後，ViewModel終於可以與View解耦，在activity或是fragment重建後，可以繼承原本的ViewModel．

感謝Google爸爸！

###### UseCases:

處理商業邏輯的地方．並不是MVVM必要的部分，但在大型專案內仍然推薦抽出這層．對於測試也比較方便．

在這層開始應該盡量脫離Android的任何import，只有pure code．商業邏輯一般不會因為平台不同而出現不同結果．

###### Repo與RepoImpl:

資料層，Repo同樣也不是MVVM必要的部分，但官方文件裡面仍然推薦抽出這層，有Repo作為中介，可以避免ViewModel直接接觸到Model層．來減少ViewModel知道

不需要知道的東西，對於ViewModel來說資料來自於Socket或是Pulling都無關緊要，只要能用就好．Repo層可以很好的協助做到這點．

## 細節
#### 依賴注入
###### 為什麼要用依賴注入？

以前我常問這個問題，也很常被人問這個問題，因為Dagger實在是非常非常的難用難學，當團隊有新成員加入也需要重新帶一次，Debug也變得不容易，編譯時間變久．

這些成本能帶來的好處又不是那麼明顯．為了管理生命週期所帶來的麻煩甚至比控制反轉所帶來的好處來多． 有的時候公司連測試都不要求，依賴注入帶來的測試更好寫有什麼意義嘛？

但是官方最近推出了Hilt這個注入框架，使用起來的上手難度大幅的下降了，甚至連生命週期也不用特別的去處理了．在學習成本降低的情境下，依賴注入所帶來的好處就突顯出來了． 

1.完美的符合最小知道原則，由注入框架來提供物件．不用了解建構子內容．

2.更容易抽換的架構，組件都能獨立運行．

3.可以控制作用域．

4.可以降低程式碼混亂程度，提升可讀性．在Android Studio的支援之下追蹤困難的問題也不復存在．

5.測試真的變的好寫

在Hilt容易上手的前提下，極小成本可以帶來如此多好處．

(注意：Hilt的編譯時間比Koin更久．如果你已經習慣使用koin的話不用特意更換，只因Java只有Dagger跟Hilt可以選惹)

#### Repository 與RepositoryImpl
###### 如果要隔離Model與ViewModel 有Repo就夠了吧，為什麼要多一層呢？

官方的做法的確只有一層的Repository，但是偶爾會發生資料來源替換的需要，比如說原本來源是api換成了Db的資料，或是要使用假的資料進行測試，甚至有些資料源在某些特地的地區不能使用．

比如說Google地圖在大陸地區就不能使用．

這時候只要替換掉RepositoryImpl的實體，Repository以上的部分都不需要修改．

#### LiveData 與MutableLiveData
###### 為什麼要特意把MutableLiveData換成LiveData傳出去呢？每次寫getter /setter都要特地改一次

當MutableLive傳出去後，View層就有機會去修改裡面的數值，所以為了避免未來自己或是團隊成員一時糊塗，讓View做了不該做的行為，轉換成LiveData是有必要的．

如果要修改其中的數值，請呼叫ViewModel來處理．


## 更多

"MVVM架構裡面，各層級的耦合性被盡量降低，讓View層去決定收到的資料如何去使用"

那麼我們可以擴展這個View層的定義，從普通的Fragment/Activity 到...不同的平台上面？

只要提供了足夠顯示在View上的資料，那不管是什麼平台，應該都能運用自己的畫面元件，繪製出其同樣的結果．

Kotlin官方也有類似的想法，所以推出了Kotlin跨平台框架，Kotlin Multiplatform Mobile，簡稱KMM

這次專案的KMM版本放在 https://github.com/officeyuli/DiffUtilRVKMMVersion/

裡面主要使用Kotlin，在iOS的View呈現上則是使用了一點點的swift

成果就像下面這樣

![image](https://github.com/officeyuli/DiffUtilRV/blob/b8d470a7065fafe72cd803bef1958a759712ffe5/KMMDemo.jpg)

左邊是iOS版本，右邊是Android版本．

這件事怎麼達成的呢 可以從官方的圖看出些端倪

![image](https://github.com/officeyuli/DiffUtilRV/blob/b8d470a7065fafe72cd803bef1958a759712ffe5/KMM-release-scheme_Blogpost.jpeg)


我們將畫面呈現交給各自平台去實作，專注於將商業邏輯以下的部分共用．

舉例來說，在我的專案內有一個 shared的模組， 內部分別有androidMain/iosMain/commonMain

而在commanMain之中，放著雙平台可以一起共用的商業邏輯．

``` kotlin
class EmployeeUseCase {

    private val mEmployeeRepository: EmployeeRepository by lazy { EmployeeRepositoryImpl() }

    fun getEmployeeListSortedByRole(): List<Employee> {
        return mEmployeeRepository.employeeListSortedByRole()
    }

    fun getEmployeeListSortedByName(): List<Employee> {
        return mEmployeeRepository.employeeListSortedByName()
    }
}
```

有了共用的商業邏輯部分後，接下來就是各平台自己實作畫面呈現了．

android的做法跟原本差不多，ViewModel可以直接呼叫到EmployeeUseCase來做使用．

``` kotlin
class MainViewModel : ViewModel() {
    ．．．
    private val mEmployeeUseCase :EmployeeUseCase by lazy { EmployeeUseCase() }

    fun getUsersWithNameLiveData(): LiveData<List<Employee>> {
        return Transformations.map(mEmployeeSortTypeLiveData) { employeeSortType: EmployeeSortType ->
            when (employeeSortType) {
                EmployeeSortType.ROLE -> mEmployeeUseCase.getEmployeeListSortedByRole()
                EmployeeSortType.NAME -> mEmployeeUseCase.getEmployeeListSortedByName()
            }
        }
    }
  ．．．

}
```

呈現到畫面上跟原本差不多

``` kotlin
class MainActivity : AppCompatActivity() {
   ．．．
   mainViewModel.getUsersWithNameLiveData().observe(this, {
            adapter.submitList(it)
        })
   ．．．
}

```

而iOS的部分 

可以看到同樣可以直接呼叫到EmployeeUseCase來獲得資料

(我直接讓畫面呈現了 在iOS的近MVVM架構下 應該要使用到ViewController來管理資料)

``` swift
···
struct ContentView: View {
    let employee : Array<Employee> = EmployeeUseCase().getEmployeeListSortedByRole()
    var body: some View {
        Spacer()
        ForEach(employee.indices) { index in
                    VStack{
                        Spacer()
                        HStack{
                            Text((employee[index].name ?? "").isEmpty ? "" : employee[index].name!)
                            Spacer()
                            Text(employee[index].role)
                            Spacer()
                        }
                        Spacer()
                    }

                }
        Spacer()
    
    }
}
  ···
```



這樣就完成了一個簡單的KMM專案．

能完成KMM版本真的要感謝GDG的兩位導師 聖佑與阿達 所開辦的KMM Ｗorkshop，收益良多．

如果想要更進一步的話下次GDG開類似活動的話可以多多報名參加．


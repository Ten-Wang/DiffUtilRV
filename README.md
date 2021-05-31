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

View．互相依賴的結果就是Presenter常常不能復用，每個View都有一個Presenter

這時Presenter也常常長成龐然大物....




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

在開始寫code之前，先問問自己，我有必要要知道這個嘛？不知道這個我可以動嘛？


## 實作
基本上都是使用官方的推薦庫．

DataBinding:

DataBinding可以協助進行View層與ViewModel的觀察與綁定，減少Activity內不必要的程式碼．不過我個人還是比較喜歡寫在activity內，在追code的時候

只要看一個地方就好．DataBinding的另外一個好處是不會因為生命週期的問題出現npm的閃退，以前使用findViewById都要處理這個View找不到的情況，在使用

DataBinding後就幾乎不會發剩這個問題了．

ViewModel:

負責管理商業邏輯與畫面溝通的部分，越簡單越好．越簡單就代表耦合性越低． 當一個ViewModel過大時，十之八九就是商業邏輯放在了ViewModel．

在官方推出ViewModel之前的MVVM是非常難實作的，主要是由於Activity的生命週期不能掌控，所以造成很多自製的ViewModel仍然要遷就View的生命週期，

在官方推出了ViewModel與lifecyclerObserver 之後，ViewModel終於可以與View解耦，在activity或是fragment重建後，可以繼承原本的ViewModel．

感謝Google爸爸！

UseCases:

並不是MVVM必要的部分，

Repo與RepoImpl:

基本上都是使用官方的推薦庫．

## 細節
#### 依賴注入
為什麼要用依賴注入

#### Repository 與RepositoryImpl

#### LiveData 與MutableLiveData

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


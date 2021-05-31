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


## 單一職責

## 實作

## 細節
#### 依賴注入
為什麼要用依賴注入

#### Repository 與RepositoryImpl

#### LiveData 與MutableLiveData

## 更多
![image](https://github.com/officeyuli/DiffUtilRV/blob/b8d470a7065fafe72cd803bef1958a759712ffe5/KMMDemo.jpg)

https://github.com/officeyuli/DiffUtilRVKMMVersion/
![image](https://github.com/officeyuli/DiffUtilRV/blob/b8d470a7065fafe72cd803bef1958a759712ffe5/KMM-release-scheme_Blogpost.jpeg)


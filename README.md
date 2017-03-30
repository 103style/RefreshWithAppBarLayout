# RefreshWithAppBarLayout
RefreshWithAppBarLayout


简书链接：http://www.jianshu.com/p/66a4e1377ca8

CSDN链接：http://blog.csdn.net/lxk_1993/article/details/68484922


这是一个实现类似 知乎、Bilibili Android端个人中心界面 添加下拉刷新效果 == 手机QQ好友动态 界面。

### 效果图

先上效果图吧

![效果图](https://github.com/103style/RefreshWithAppBarLayout/blob/master/screenshot.gif/titleshadow_samll.gif)

![效果图](https://github.com/103style/RefreshWithAppBarLayout/blob/master/screenshot.gif/withtab_small.gif)

### 声明

先声明下拉刷新修改自：http://blog.csdn.net/leehong2005/article/details/12567757.

本例是使用AppBarLayout来判断是否可以刷新的，其他的刷新请参考这个链接。

这个效果是最近项目需求中的，然后看了下 知乎 B站的个人中心都没有下拉刷新，然后github上也没找到类似的（有的话 可能是我不会找吧）…

然后就默默开始搜索下拉刷新实现原理，然后就找到了上面链接那篇文章，然后刚好可以实现，废话不多说了，来看怎么实现吧！

转载请以链接形式标明出处：

[http://blog.csdn.net/lxk_1993/article/details/68484922](http://blog.csdn.net/lxk_1993/article/details/68484922)

本文出自:[lxk_1993的博客](http://blog.csdn.net/lxk_1993)

### 实现步骤

* 去这里 [github](https://github.com/103style/RefreshWithAppBarLayout) 或 [csdn](http://download.csdn.net/download/lxk_1993/9798704)下载源码.


* 把项目中的refreshlibray 库添加到项目中，然后添加依赖，或者直接复制里面的 Java、anim、drawable–xxhdpi、layout这几个文件下的文件和values下的pull_refresh_colors和pull_refresh_strings. 如图

![需要的文件](https://github.com/103style/RefreshWithAppBarLayout/blob/master/screenshot.gif/file/1.png)


* 新建一个java类，PullToRefreshBase,泛型一般用FrameLayout,然后实现alt+enter实现构造和需要重写的方法，如图。

![实现构造和需要重写的方法](https://github.com/103style/RefreshWithAppBarLayout/blob/master/screenshot.gif/file/2.png)


* 定义一个boolean类型的变量来判断是否可以刷新（是否拦截点击事件），默认为true,添加set方法，然后在isReadyForPullDown方法中返回这个变量，可以参考下载项目中的 WithBottomContentView.java和NoBottomTabActivityXmlView.java。


* 在createRefreshableView中添加你要刷新的区域布局。

![添加刷新的区域布局](https://github.com/103style/RefreshWithAppBarLayout/blob/master/screenshot.gif/file/3.png)


* 然后基本就完成了，直接去Activity或者Fragment中开用,直接setContentView(刚刚写的类),如果需要使用findViewById,如图中，应写成recyclerview = rootview.findViewById(R.id.recycle_view).

![Activity中使用](https://github.com/103style/RefreshWithAppBarLayout/blob/master/screenshot.gif/file/4.png)


* 然后直接appbarlayout添加滑动监听，当verticalOffset=0的时候，即滑动到顶部，设置拦截点击事件，来实现刷新。

![添加滑动监听](https://github.com/103style/RefreshWithAppBarLayout/blob/master/screenshot.gif/file/5.png)


* 实现刷新和加载监听，此例只实现了刷新。

![刷新和加载监听](https://github.com/103style/RefreshWithAppBarLayout/blob/master/screenshot.gif/file/6.png)

### 扩展

相信大家项目中的刷新视图基本都是和项目icon有关的不会是这个默认的刷新试图，所以需要修改刷新试图的，直接修改refreshlibray中HeaderLoadingLayout及其布局pull_to_refresh_header，然后再图中几个方法中去改变其对应的显示内同即可。

* onStateChanged 状态改变时回调
* onReset 刷新完之后重置状态
* onPullToRefresh 下拉时回调
* onReleaseToRefresh 下拉松开后回调
* onRefreshing 刷新的时候回调

![下拉刷新状态的回调方法](https://github.com/103style/RefreshWithAppBarLayout/blob/master/screenshot.gif/file/7.png)

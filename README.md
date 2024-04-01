TimeFlow by Jetpack Compose in Android.

### Project

- Modified from [TimeFlow](https://github.com/DIPENG-XU/TimeFlow), but the old project coded by
  View, in this new project, I choose Jetpack Compose to build the UI.
- Besides, I use the Automatic injection framework to generate something, which is easier to build
  the code.
- Recode the ViewModel, which is more suitable from Jetpack Compose when I need to use the state in
  UI.

### Why design and develop it?

- The mainly reason is when I stayed at school, there are lots of students used such an app in their
  iPad when studying. I think it's useful when you focus on your work, you can feel how your time
  flow. And that's the reason why I name it as TimeFlow.
- And it's easy to find such an app in App Store but Android any Stores, even you can, the app is
  also full of ads.
  I think it's a bad experience to see ads or other useless information when using the Time app.
  You should only focus on your thing and notice your time.
- So as an Android Tablet user and an Android developer, I though it was a great time that I should
  try to design and develop it.
- If you have any better ideas or find any bugs, you
  can [pull the issue](https://github.com/DIPENG-XU/TimeFlow-By-Compose/issues/new) to me. When
  leisure, I will reply you. If I think it's interesting enough and I happen to be capable, I will
  code it for you.

### UX

- This point is very important, because there are no instructions on how to use it in the APP.
  You can only find and know it from this document.
- It is a simple card consisting of two fields: clock and minute
    - Click on the card of the clock to choose your favourite time format.
    - Click on the minute card to choose whether to display the current date. (Show the date Card)
    - In Version 2.1.0, click on the date card to switch theme. Now there are only two theme, Light and Dark, and I will support more themes next.

### Effect Pictures

There are all the effect pictures.

- Tablet: Time format Base on 12
  ![IMAGE1](show_img/img1.png)

- In addition, [there](show_img) are other effects preview.

#### The following is Chinese document, you can skip if you have read the previous content

### 项目

- 由[TimeFlow](https://github.com/DIPENG-XU/TimeFlow)
  迁移而来，原项目使用使用View体系布局，这种方式已经跟不上时代了，我自己不跟着大家卷两下就难受，
  于是改用Jetpack Compose去重新构建这个新项目。
- 为了跟上时代的脉络，这个项目采用了更加合适于Compose的ViewModel注入方式去创建ViewModel
- 以及使用了State替代了原来的数据通知方式，还有就是调整了一下数据格式，其他不变

### 为什么设计和构建这个APP?

- 起初是因为当时还在仲恺的时候，看到那帮用iPad的同学经常用平板当成时钟放在隔壁，感觉挺有学习氛围的。
- 但苦于我是Android设备爱好者，在Play Store、三星APP商店还有小米APP商店这些地方下载，那些APP花里胡哨的内容和广告都非常多，需要一大堆的权限，超级离谱。
  我个人觉得，（免费无广告的别人写好的软件 > 能够一次性购买的无广告软件 > 自己写的软件 >
  订阅制无广告软件 > 广告软件），在这条优先链下，我只能自己设计和写代码。

### 用户交互

- 就是比较单纯的两张卡片分别包含时钟和分钟两个字段组成
    - 点击时钟的卡片，可以切换时间查看的进制。
    - 点击分钟的卡片，可以选择是否显示当前的年月日（日期卡片出现）。
    - 在2.1.0版本后加入新的交互逻辑，点击日期卡片，可以切换主题，目前仅支持两个主题，亮色主题和暗色主题，后续会在这个基础上做其他的增加。

- 如果你有更好的UX交互逻辑或者什么有意思的想法，随时issue联系我，在[这里](https://github.com/DIPENG-XU/TimeFlow-By-Compose/issues/new)
  提出来就可以了，我有空然后也觉得你的想法有意思而且我又会做的话，我随时会新增加你需要的功能。

### 效果图

- 看上面，语言虽然有差异，但是图片应该也能够传递出去我所想表达的内容了，这里就不复述了
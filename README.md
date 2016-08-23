

### 最终效果图：

![旋转过程的角标](https://raw.githubusercontent.com/lovejjfg/screenshort/master/progress.gif)


![DragBubble](https://raw.githubusercontent.com/lovejjfg/screenshort/master/DragBubble.gif)![PathText](https://raw.githubusercontent.com/lovejjfg/screenshort/master/PathTextgif.gif)

![IndexBar](https://raw.githubusercontent.com/lovejjfg/IndexMasterDemo/master/index.gif)

# CircleProgress && TouchCircleView
[博客地址 戳我戳我](http://www.jianshu.com/p/98bb533afa9c)

#DragBubble
### 基本原理

其实就是使用Path绘制三点的二次方贝塞尔曲线来完成那个妖娆的曲线的。然后根据触摸点不断绘制对应的圆形，根据距离的改变改变原始固定圆形的半径大小。最后就是松手后返回或者爆裂的实现。

1、确定默认圆形的坐标；
2、根据move的情况，实时获取最新的坐标，根据移动的距离（确定出角度）,更新相关的状态，画出相关的Path路径。超出上限，不再画Path路径。
![旋转过程的角标](http://img.blog.csdn.net/20160611113042129)

3、松手时，根据相关的状态，要么带Path路径执行动画返回，要么不带Path路径直接返回，要么直接爆裂！

[博客地址 戳我戳我](http://blog.csdn.net/lovejjfg/article/details/50990604)

# PathText

[博客地址 戳我戳我](http://www.jianshu.com/p/b655981e6ef9)

# IndexBar

[具体项目地址](https://github.com/lovejjfg/IndexBar)

[博客地址 戳我戳我](http://www.jianshu.com/p/d0d3ae674de8)


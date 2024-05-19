# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# 打印混淆信息
-verbose

# 指定代码的压缩级别，值在0-7之间。一般设置5足矣
-optimizationpasses 5

# 代码优化选项，不加该行会将没有用到的类删除，发布的是代码库这个选项需要
# 在做混淆之前最开始会默认对代码进行压缩，为了增加反编译的难度可以选择不压缩
-dontshrink

# 保留参数的名称和方法，该选项可以保留调试级别的属性。
-keepparameternames

# 保护代码中的Annotation不被混淆
-keepattributes *Annotation*

# 指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers

# 指定不去忽略非公共的库类(不跳过library中的非public的类)
-dontskipnonpubliclibraryclasses

# 保留包名
-keeppackagenames com.hi.base.**
# 第三方库，比如支付宝、微信等不混淆
-keep class com.appsflyer.** { *; }
-keep class kotlin.jvm.internal.Intrinsics{ *; }
-keep class kotlin.collections.**{ *; }
-keep class com.facebook.** { *; }
-keep public class com.android.installreferrer.**{ *; }
-keep public class com.adjust.sdk.**{ *; }
-keep class com.google.android.gms.**{ *; }
-keep class com.google.firebase.**{ *; }
-keep class com.google.android.finsky.billing.**{ *; }
-keep class com.google.android.finsky.externalreferrer.**{ *; }
-keep class com.google.android.finsky.externalreferrer.**{ *; }
-keep class com.google.android.finsky.externalreferrer.**{ *; }
# 保留库本身中对外接口类不被混淆
-keep class com.hi.base.app.HiGameApplication
-keep class com.hi.base.app.HiGameApplication{ *; }
-keep class com.hi.base.plugin.IPlugin{ *; }
-keep class com.hi.base.pub.SDKManager
-keep class com.hi.base.pub.HiGameSDK
# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers public class * extends android.view.View {
 public <init>(android.content.Context);
 public <init>(android.content.Context, android.util.AttributeSet);
 public <init>(android.content.Context, android.util.AttributeSet, int);
 public void set*(***);
}

#保持 Serializable 不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context,android.util.AttributeSet);
}
# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context,android.util.AttributeSet,int);
}
# 保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

# 保持枚举 enum 类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# 不混淆R文件中的所有静态字段，我们都知道R文件是通过字段来记录每个资源的id的，字段名要是被混淆了，id也就找不着了。
-keepclassmembers class **.R$* {
    public static <fields>;
}
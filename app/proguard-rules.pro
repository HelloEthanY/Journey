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

#---------------------------------基本指令区---------------------------------
# 抑制警告
-ignorewarnings
#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
 #优化  不优化输入的类文件
-dontoptimize
 #预校验
-dontpreverify
 #混淆时是否记录日志
-verbose
 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#混淆包路径
-repackageclasses ''
-flattenpackagehierarchy ''

#保护注解
-keepattributes *Annotation*

#避免混淆泛型 如果混淆报错建议关掉
-keepattributes Signature

#保留SourceFile和LineNumber属性
-keepattributes SourceFile,LineNumberTable

#忽略警告
#-ignorewarning
#####################记录生成的日志数据,gradle build时在本项目根目录输出################
#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt
#保持实体类、Gson不被混淆
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class org.emergency.app.data.model.**{*;}
# FastJson 混淆代码
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}

#-------------------------------------------主项目混淆规则----------------------------------------------
#---------------------------------1.实体类---------------------------------
-keep class com.journey.org.data.**  { *; } # 要修改的地方
#---------------------------------2.第三方包-------------------------------
#MPAndroidChart
-keep class com.github.mikephil.charting.** { *; }    #不混淆第三方包中的指定内容
-dontwarn com.github.mikephil.charting.**             #去掉警告
#-------------------------------------------weexLibrary混淆规则----------------------------------------------
#知乎
-dontwarn com.bumptech.glide.**
-dontwarn com.squareup.picasso.**
#okio
-dontwarn okio.**
-keep class okio.**{*;}

#-------------------------------------------MVVMHabit混淆规则----------------------------------------------
#---------------------------------1.实体类---------------------------------

-keep class me.goldze.mvvmhabit.http.BaseResponse { *; }
-keep class com.journey.org.app.base.BaseResponse { *; } # 要修改的地方

#--------------------------------2.第三方包-------------------------------
#support
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-dontwarn android.support.**

#databinding
-keep class android.databinding.** { *; }
-dontwarn android.databinding.**

#retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.sunloto.shandong.bean.** { *; }

#glide
-keep public class * implements com.bumptech.glide.module.AppGlideModule
-keep public class * implements com.bumptech.glide.module.LibraryGlideModule
-keep class com.bumptech.glide.** { *; }
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

#glide-transformations
-keep class jp.wasabeef.glide.transformations.** {*;}
-dontwarn jp.wasabeef.glide.transformations.**

#okhttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn com.squareup.okhttp.**

#RxJava RxAndroid
-dontwarn rx.*
-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#RxLifecycle
-keep class com.trello.rxlifecycle.** { *; }
-dontwarn com.trello.rxlifecycle.**

#RxPermissions
-keep class com.tbruyelle.rxpermissions2.** { *; }
-keep interface com.tbruyelle.rxpermissions2.** { *; }

#material-dialogs
-keep class com.afollestad.materialdialogs.** { *; }
-dontwarn om.afollestad.materialdialogs.**

#=====================bindingcollectionadapter=====================
-keep class me.tatarka.bindingcollectionadapter.** { *; }
-dontwarn me.tatarka.bindingcollectionadapter.**
#---------------------------------4.反射相关的类和方法-----------------------
-keep public class * extends me.goldze.mvvmhabit.base.BaseActivity{ *; }
-keep public class * extends me.goldze.mvvmhabit.base.BaseFragment{ *; }
-keep public class * extends me.goldze.mvvmhabit.binding.command.BindingCommand{ *; }
-keep public class * extends me.goldze.mvvmhabit.binding.command.ResponseCommand{ *; }
#---------------------------------5.自定义控件------------------------------
-keep class me.goldze.mvvmhabit.widget.** { *; }

#----------------------------------------------------------------------------
#---------------------------------6.其他定制区-------------------------------
#native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
#Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#Serializable 不被混淆
-keepnames class * implements java.io.Serializable
#Serializable 不被混淆并且enum 类也不被混淆
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
#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}
-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}
#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}
# --------- 保护类中的所有方法名 ------------
-keepclassmembers class * {
    public <methods>;
    private <methods>;
}

#-------------------------------------------阿里云热修复 混淆规则---------------------------------------
    #基线包使用，生成mapping.txt
    -printmapping mapping.txt
    #生成的mapping.txt在app/build/outputs/mapping/release路径下，移动到/app路径下
    #修复后的项目使用，保证混淆结果一致
    #-applymapping mapping.txt
    #hotfix
    -keep class com.taobao.sophix.**{*;}
    -keep class com.ta.utdid2.device.**{*;}
    -dontwarn com.alibaba.sdk.android.utils.**
    #防止inline
    -dontoptimize

#-------------------------------------------skylineLibrary混淆规则----------------------------------------------
# 做个备份
#-dontwarn com.skyline.**
#-keep class com.skyline.** {*;}

#-dontwarn yuku.ambilwarna.**
#-keep class yuku.ambilwarna.** {*;}
#-------------------------------------------xgmapLibrary混淆规则----------------------------------------------
# 做个备份
# -dontwarn com.esri.**
# -keep class com.esri.** {*;}

# -dontwarn org.codehaus.jackson.**
# -keep class org.codehaus.jackson.** {*;}

# -dontwarn org.codehaus.jackson.**
# -keep class org.codehaus.jackson.** {*;}

# -dontwarn jcifs.**
# -keep class jcifs.** {*;}
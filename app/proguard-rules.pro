-printmapping build/mapping.txt

-repackageclasses ''
-allowaccessmodification

-dontwarn javax.annotation.**

# Android architecture components
-keep class android.arch.** { *; }

# RxJava
-dontwarn sun.misc.Unsafe

# Okhttp
-dontwarn okhttp3.**
-dontwarn okio.**

# Kotlin
-dontwarn kotlin.**
-dontwarn org.jetbrains.annotations.**
-keep class kotlin.Metadata { *; }

# Moshi
# https://github.com/square/moshi/issues/402
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}
-keep @com.squareup.moshi.JsonQualifier interface *
-keep public class kotlin.reflect.jvm.internal.impl.builtins.* { public *; }
-keep public class kotlin.reflect.jvm.internal.impl.load.** { public *; }

# Retrofit2
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions
-keepclassmembers class com.consistence.pinyin.domain.pinyin.api.** {
  <init>(...);
  <fields>;
}
-keep class retrofit.** { *; }
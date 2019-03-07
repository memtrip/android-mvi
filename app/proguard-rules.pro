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
-keep class kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoaderImpl
-keep class kotlin.Metadata { *; }
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}

-keepclassmembers class ** {
    @com.squareup.moshi.FromJson *;
    @com.squareup.moshi.ToJson *;
}
-keep @com.squareup.moshi.JsonQualifier interface *
-keep class **JsonAdapter {
    <init>(...);
    <fields>;
}

-keepnames @com.squareup.moshi.JsonClass class *

# Retrofit2
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions
-keepclassmembers class com.consistence.pinyin.domain.** {
  <init>(...);
  <fields>;
}
-keep class retrofit.** { *; }
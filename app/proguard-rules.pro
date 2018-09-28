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
# Retain generic type information for use by reflection by converters and adapters.


# Retain service method parameters when optimizing.



# Ignore annotation used for build tooling.
-keepattributes Exceptions
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3. { *; }
-keep interface okhttp3. { *; }
-dontwarn okhttp3.
-dontnote okhttp3.
-dontwarn okhttp3.internal.platform.*
-keep class com.lounah.yarealty.data.entity. { *; }
# Okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream. { *; }
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn javax.annotation.concurrent.GuardedBy

-dontwarn com.google.errorprone.annotations.**

-keep class com.lounah.yarealty.data.entity.** { *; }
-keep class com.lounah.yarealty.data.network.** { *; }

-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}



# --- uncomment if okhttp warns
# A resource is loaded with a relative path so the package of this class must be preserved.
#-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
#-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
#-dontwarn okhttp3.internal.platform.ConscryptPlatform
# --- uncomment if okhttp warns
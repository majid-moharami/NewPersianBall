# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
-optimizationpasses 5
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-allowaccessmodification
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

-dontusemixedcaseclassnames
-verbose

-keepattributes *Annotation*

-repackageclasses ''

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

-keepclasseswithmembernames,includedescriptorclasses class * {
    native <methods>;
}

-keepclassmembers,allowoptimization enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.app.Fragment
-keepnames class androidx.navigation.fragment.NavHostFragment

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

-keepclassmembers class ir.pattern.persianball.data.model.** {
    <fields>;
}

-keepclassmembers enum  ir.pattern.persianball.data.model.** { *; }


-keepattributes Signature

-keep public class javax.net.ssl.**
-keepclassmembers public class javax.net.ssl.** {
    *;
}
-keep public class org.apache.http.**
-keepclassmembers public class org.apache.http.** {
    *;
}

# Firebase
-keep class com.google.android.gms.** { *; }
-keep class com.google.firebase.** { *; }
# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
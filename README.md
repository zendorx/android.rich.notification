# android.rich.notification

![Example](/rich_example.png)


# Usage

## 1 Include aar file to project or source 

## 2 Add Reciever to AndroidManifest.xml
```xml
      <receiver
            android:name="com.furry.utils.rich.FurryPushReceiver"
            android:permission="com.google.android.c2dm.permission.SEND">
            <intent-filter>
                <action android:name="com.google.android.c2dm.intent.RECEIVE" />
                <category android:name="${applicationId}"/>
            </intent-filter>
        </receiver>
```

## 3 Add background images to manifest meta.
```xml
        <meta-data android:name="furry.rich.1" android:value="red" />
        <meta-data android:name="furry.rich.2" android:value="yellow" />
        <meta-data android:name="furry.rich.3" android:value="green" />
        <meta-data android:name="furry.rich.4" android:value="blue" />
```


## (Optional) Set extra paramater ``rich_back`` to notification to set background image.

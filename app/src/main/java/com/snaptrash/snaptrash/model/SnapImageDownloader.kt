package com.snaptrash.snaptrash.model

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.snaptrash.snaptrash.model.data.Snap
import kotlinx.coroutines.tasks.await
import java.io.File
import java.net.URLDecoder

class SnapImageDownloader {
    companion object{
        suspend fun downloadSnap(context: Context,snap: Snap): Uri {
            var url = Uri.EMPTY
            if (snap.snapImageUrl.isNotEmpty()) {
                val cacheFile = File((context.externalCacheDir ?: context.cacheDir).path + URLDecoder.decode(snap.snapImageUrl,"UTF-8"))
                if(!cacheFile.exists()){
                    cacheFile.parentFile?.mkdirs()
                    val file = Firebase.storage.getReference(
                        URLDecoder.decode(
                            snap.snapImageUrl,
                            "UTF-8"
                        )
                    ).getFile(cacheFile.toUri()).await()
                    url = if(file.error == null){
                        cacheFile.toUri()
                    } else{
                        Uri.parse("android.resource://com.snaptrash.snaptrash/drawable/final_logo.png");
                    }
                }
                else{
                    url = cacheFile.toUri()
                }
            }
            else{
                url = Uri.parse("android.resource://com.snaptrash.snaptrash/drawable/final_logo.png");
            }
           return url
        }
    }
}
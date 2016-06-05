package io.ipfs.kotlin

import okhttp3.*
import java.io.File

class Add(val ipfs: IPFS) {

    fun file(file: File, name: String = "file", filename: String = name): NamedHash? {

        return addGeneric {
            val body = RequestBody.create(MediaType.parse("application/octet-stream"), file)
            it.addFormDataPart(name, filename, body)
        }

    }

    fun string(file: String, name: String = "string", filename: String = name): NamedHash? {

        return addGeneric {
            val body = RequestBody.create(MediaType.parse("application/octet-stream"), file)
            it.addFormDataPart(name, filename, body)
        }

    }

    private fun addGeneric(withBuilder: (MultipartBody.Builder) -> Any): NamedHash? {

        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        withBuilder(builder)
        val requestBody = builder.build();

        val request = Request.Builder()
                .url("${ipfs.base_url}add?stream-channels=true&progress=false")
                .post(requestBody)
                .build();

        try {
            val response = ipfs.okHttpClient.newCall(request).execute();
            return ipfs.adapter.fromJson(response.body().string())
        } catch (e: Exception) {
            return null
        }

    }
}
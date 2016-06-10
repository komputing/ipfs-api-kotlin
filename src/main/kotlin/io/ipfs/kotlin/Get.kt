package io.ipfs.kotlin

class Get(val ipfs: IpfsConnection) {

    fun cat(hash: String): String {
        return ipfs.callURL("${ipfs.base_url}cat/$hash").string();
    }

}
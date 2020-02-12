package com.lucasri.aperomix.models

import java.util.*

class Message {
    var message: String? = null
    var dateCreated: Date? = null
    var userSender: User? = null

    constructor() {}

    constructor(
            message: String?,
            userSender: User?
    ) {
        this.message = message
        this.userSender = userSender
    }
}
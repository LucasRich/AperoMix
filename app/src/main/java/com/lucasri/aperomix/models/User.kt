package com.lucasri.aperomix.models

class User {
    var uid: String? = null
    var userName: String? = null
    var email: String? = null
    var birthDay: String? = null
    var papinGameCounter: Int? = null
    var pmuGameCounter: Int? = null
    var beLuckyGameCounter: Int? = null

    constructor() {}

    constructor(
            uid: String?,
            userName: String?,
            email: String?,
            birthDay: String?,
            papinGameCounter: Int?,
            pmuGameCounter: Int?,
            beLuckyGameCounter: Int?
    ) {
        this.uid = uid
        this.userName = userName
        this.email = email
        this.birthDay = birthDay
        this.papinGameCounter = papinGameCounter
        this.pmuGameCounter = pmuGameCounter
        this.beLuckyGameCounter = beLuckyGameCounter
    }
}
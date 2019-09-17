package com.lucasri.aperomix.model

class Game {
    var name: String? = null
    var image: Int? = null

    constructor(name: String, image: Int) {
        this.name = name
        this.image = image
    }
}
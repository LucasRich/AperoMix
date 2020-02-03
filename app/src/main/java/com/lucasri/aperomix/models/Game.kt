package com.lucasri.aperomix.models

class Game {
    var name: String? = null
    var image: Int? = null

    constructor(name: String, image: Int) {
        this.name = name
        this.image = image
    }
}
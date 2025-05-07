package hu.ait.multidemo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
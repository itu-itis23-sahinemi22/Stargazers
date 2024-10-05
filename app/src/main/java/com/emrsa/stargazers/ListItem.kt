package com.emrsa.stargazers

sealed class ListItem {
    data class BoldDescription(val description: String) : ListItem()
    data class Description(val description: String) : ListItem()
    data class ImageDescription(val imageResId: Int, val description: String) : ListItem()
}

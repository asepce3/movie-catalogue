package com.cahyana.asep.moviecatalogue.ui.home

import com.cahyana.asep.moviecatalogue.data.FavoriteRequest

interface SelectCallback {
    fun onSave(favorite: FavoriteRequest)
    fun onDelete(favorite: FavoriteRequest)
}
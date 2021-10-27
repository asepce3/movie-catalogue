package com.cahyana.asep.moviecatalogue.utils

import android.content.Context
import androidx.core.content.ContextCompat

object ResourceUtils {

    fun btnOn(context: Context) =
        ContextCompat.getDrawable(
            context, getResourceId(
                context, "ic_favorite_red"
            )
        )

    fun btnOff(context: Context) =
        ContextCompat.getDrawable(
            context, getResourceId(
                context, "ic_favorite"
            )
        )

    private fun getResourceId(mContext: Context, resource: String): Int {
        return mContext.resources.getIdentifier(resource, "drawable", mContext.packageName)
    }
}
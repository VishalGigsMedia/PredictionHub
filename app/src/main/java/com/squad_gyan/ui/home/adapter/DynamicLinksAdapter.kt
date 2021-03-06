package com.squad_gyan.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squad_gyan.R
import com.squad_gyan.common_helper.DefaultHelper.decrypt
import com.squad_gyan.databinding.RowItemDynamicLinkBinding
import com.squad_gyan.ui.home.model.MatchDetailsModel


class DynamicLinksAdapter(private val context: Context, private val list: List<MatchDetailsModel.Data.Prediction.FantasyGameLink?>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemDynamicLinkBinding: RowItemDynamicLinkBinding = RowItemDynamicLinkBinding.bind(itemView)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        mcontext = viewGroup.context

        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.row_item_dynamic_link, viewGroup, false
        )
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {

            /*val url = "https://play.google.com/store/apps/details?id=com.predict_squad"
            holder.itemDynamicLinkBinding.tvLinks.text = url*/
            val url = decrypt(list?.get(position)?.link.toString())
            holder.itemDynamicLinkBinding.tvTitle.text = fromHtml(decrypt(list?.get(position)?.title.toString()))//Html.fromHtml()
            holder.itemDynamicLinkBinding.tvDescription.text = fromHtml(decrypt(list?.get(position)?.description.toString()))//Html.fromHtml()
            holder.itemDynamicLinkBinding.tvLinks.text = decrypt(list?.get(position)?.link.toString())

            holder.itemDynamicLinkBinding.tvLinks.setOnClickListener {
                setOpenUrl(url)
            }
        }
    }

    fun fromHtml(html: String?): Spanned? {
        return if (html == null) {
            // return an empty spannable if the html is null
            SpannableString("")
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
            // we are using this flag to give a consistent behaviour
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }


    private fun setOpenUrl(url: String) {
        try {
            if (!checkNull(url)) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                context.startActivity(i)
            }

        } catch (e: Exception) {
            e.message
        }

    }

    private fun checkNull(str: String): Boolean {
        if (str.isEmpty() && str == "null") {
            return true
        }
        return false
    }
}





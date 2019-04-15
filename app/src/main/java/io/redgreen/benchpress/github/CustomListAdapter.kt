package io.redgreen.benchpress.github

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.redgreen.benchpress.R
import io.redgreen.benchpress.github.domain.User

class CustomListAdapter(
    context: Activity,
    private val followers: List<User>
) : ArrayAdapter<User>(context, R.layout.github_list_item_layout) {
    private val layoutInflater = LayoutInflater.from(context)

    override fun getItemId(position: Int): Long {
        return followers[position].username.hashCode().toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val rowView = if (convertView != null) {
            convertView
        } else {
            layoutInflater.inflate(R.layout.github_list_item_layout, parent, false)
        }

        val usernameTextView = rowView.findViewById<TextView>(R.id.username)
        usernameTextView.text = followers[position].username

        val imageView = rowView.findViewById<ImageView>(R.id.user_image)
        val imageUrl = followers[position].avatarUrl
        Picasso.get().load(imageUrl).into(imageView)

        return rowView
    }

    override fun getItem(position: Int): User =
        followers[position]

    override fun getCount(): Int =
        followers.size
}

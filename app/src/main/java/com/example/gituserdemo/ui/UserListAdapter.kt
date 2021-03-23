package com.example.gituserdemo.ui

import android.app.Activity
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.gituserdemo.R
import com.example.gituserdemo.databinding.ItemGitUsersBinding
import com.example.gituserdemo.databinding.ItemLoadingSpinnerBinding
import com.example.gituserdemo.models.UsersItemModel
import de.hdodenhof.circleimageview.CircleImageView


class UserListAdapter(val context: Activity, val loadMore: (Int) -> Unit, val onClick : (name : String?) -> Unit ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = "UserListAdapter"
    private val ITEM_TYPE = 0
    private val SPINNER_TYPE = 1


    var userList = ArrayList<UsersItemModel>()
    var filteredList = ArrayList<UsersItemModel>()
    var isLoading = false
    var isSearching = false
    var isPaginationOver = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == SPINNER_TYPE){
            return SpinnerViewHolder(
                ItemLoadingSpinnerBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                )
            )
        } else {
            return ItemViewHolder(
                ItemGitUsersBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                )
            )
        }


    }


    override fun getItemCount(): Int = if(isLoading) filteredList.size + 1 else filteredList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {



        if(holder is ItemViewHolder){

            ItemGitUsersBinding.bind(holder.itemView).apply {
                itemTvUserName.text = "@" + filteredList[position].login
                // check image is 4th position or not
                if (holder.adapterPosition + 1 % 4 == 0)
                    loadInverseImage(filteredList[position].avatarUrl, itemUserProfile)
                else
                    Glide.with(context).load(filteredList[position].avatarUrl)
                        .placeholder(R.drawable.ic_git_icon).into(itemUserProfile)

                itemTvUserDetails.text = if(filteredList[position].siteAdmin == true) "Admin" else "User"
            }

            holder.itemView.setOnClickListener { onClick(filteredList[position].login) }

        }

        if (!isLoading && !isPaginationOver && !isSearching)
            checkForMoreData(position)

    }



    override fun getItemViewType(position: Int): Int = if(position == filteredList.size) SPINNER_TYPE else ITEM_TYPE




    /*
    For pagination:
    Check if list is only 4 item away to end
    If it is ending trigger load next page
    */
    private fun checkForMoreData(position: Int) {
        if (position  == filteredList.size - 1 && filteredList.last().id != null) {
            isLoading = true
            Handler(context.mainLooper).postDelayed( {
                notifyDataSetChanged()
                loadMore(filteredList.last().id!!)
            }, 2000)
        }
    }



    class SpinnerViewHolder(itemView: ItemLoadingSpinnerBinding) : RecyclerView.ViewHolder(itemView.root)
    class ItemViewHolder(itemView: ItemGitUsersBinding) : RecyclerView.ViewHolder(itemView.root)


    private fun loadInverseImage(
        avatarUrl: String?,
        itemUserProfile: CircleImageView
    ) {

        /*First take bitmap from mentioned url than inverse color*/

        Glide.with(context)
            .asBitmap()
            .load(avatarUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    itemUserProfile.setImageBitmap(invert(resource))
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })

    }


    fun invert(src: Bitmap): Bitmap? {
        val height = src.height
        val width = src.width
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val matrixGrayscale = ColorMatrix()
        matrixGrayscale.setSaturation(0f)
        val matrixInvert = ColorMatrix()
        matrixInvert.set(
            floatArrayOf(
                -1.0f, 0.0f, 0.0f, 0.0f, 255.0f,
                0.0f, -1.0f, 0.0f, 0.0f, 255.0f,
                0.0f, 0.0f, -1.0f, 0.0f, 255.0f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f
            )
        )
        matrixInvert.preConcat(matrixGrayscale)
        val filter = ColorMatrixColorFilter(matrixInvert)
        paint.colorFilter = filter
        canvas.drawBitmap(src, 0f, 0f, paint)
        return bitmap
    }


    fun filterText(filterText : String){

        filteredList.clear()

        if(filterText.isBlank()){
            filteredList = userList.clone()  as ArrayList<UsersItemModel>
        } else{
            for(user : UsersItemModel in userList){
                if(user.login.contains(filterText)){
                    filteredList.add(user)
                }
            }
        }

        notifyDataSetChanged()

    }

    fun setData(newList: List<UsersItemModel>) {
        userList.clear()
        userList.addAll(newList)
        filteredList = userList.clone() as ArrayList<UsersItemModel>
        notifyDataSetChanged()
    }


}
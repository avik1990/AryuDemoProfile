package com.app.aryudemoprofile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.app.aryudemoprofile.R
import com.app.aryudemoprofile.customviews.RobotoSlabBoldTextView
import com.app.aryudemoprofile.customviews.RobotoSlabRegularTextView
import com.app.aryudemoprofile.model.Success
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import de.hdodenhof.circleimageview.CircleImageView

class TrackingAdapter(
    private val context: Context,
    private val sucessList: List<Success>,
    private val onRowSelected: onRowItemSelected
) : androidx.recyclerview.widget.RecyclerView.Adapter<TrackingAdapter.MyViewHolder>() {

    lateinit var rowView: View

    interface onRowItemSelected {
        fun getPosition(pos: Int, imageView: ImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.row_profile, parent, false)

        rowView = itemView
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (!sucessList[position].image.isNullOrBlank()) {
            Glide
                .with(context)
                .load(sucessList[position].image)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.profile_image)

        }

        holder.tvEmpcode.text = sucessList[position].empcode
        holder.tvName.text = sucessList[position].name
        holder.tvCatgory.text = sucessList[position].category
        holder.tvContact.text = sucessList[position].contact
        holder.tvAddress.text = sucessList[position].address

        holder.card_view.setOnClickListener {
            onRowSelected.getPosition(position, holder.profile_image)
        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return sucessList.size
    }

    inner class MyViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var profile_image: CircleImageView
        var tvName: RobotoSlabRegularTextView
        var tvEmpcode: RobotoSlabBoldTextView
        var tvCatgory: RobotoSlabRegularTextView
        var tvContact: RobotoSlabRegularTextView
        var tvAddress: RobotoSlabRegularTextView

        var card_view: androidx.cardview.widget.CardView

        init {
            profile_image = itemView.findViewById(R.id.profile_image)
            tvName = itemView.findViewById(R.id.tvName)
            tvCatgory = itemView.findViewById(R.id.tvCatgory)
            tvContact = itemView.findViewById(R.id.tvContact)
            tvAddress = itemView.findViewById(R.id.tvAddress)
            card_view = itemView.findViewById(R.id.card_view)
            tvEmpcode = itemView.findViewById(R.id.tvEmpcode)
        }

    }

}
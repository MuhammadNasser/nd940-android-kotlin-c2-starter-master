package com.udacity.asteroidradar.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.models.PictureOfDay

// ToDo Udacity REQUIRED to add missing contentDescriptions

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription =
            imageView.resources.getString(R.string.this_is_a_potentially_hazardous_asteroid)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription =
            imageView.resources.getString(R.string.this_is_not_a_potentially_hazardous_asteroid)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription =
            imageView.resources.getString(R.string.this_is_a_potentially_hazardous_asteroid)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription =
            imageView.resources.getString(R.string.this_is_not_a_potentially_hazardous_asteroid)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("imageUrl")
fun bindImageView(imageView: ImageView, pictureOfDay: PictureOfDay?) {
    val context = imageView.context
    pictureOfDay?.let {
        Picasso.with(context).load(pictureOfDay.url).fit().into(imageView)
        imageView.contentDescription = String.format(
            imageView.context.resources.getString(R.string.nasa_picture_of_day_content_description_format),
            it.title
        )
    }
}

@BindingAdapter("bindContentDescription")
fun bindHelpButtonDescription(view: ImageView, codename: String) {
    view.contentDescription = String.format(
        view.context.resources.getString(R.string.this_is_asteroid_double_click_to_open),
        codename
    )
}

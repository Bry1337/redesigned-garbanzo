package bry1337.github.io.newsthings.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by edwardbryan.abergas on 08/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@Entity
data class Article(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Long,
    @SerializedName("author")
    val author: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("content")
    val content: String?,
    val isSaved: Boolean = false,
    val isArchived: Boolean = false) : Parcelable {
  constructor(parcel: Parcel) : this(
      parcel.readLong(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readByte() != 0.toByte(),
      parcel.readByte() != 0.toByte()) {
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeLong(id)
    parcel.writeString(author)
    parcel.writeString(title)
    parcel.writeString(description)
    parcel.writeString(url)
    parcel.writeString(urlToImage)
    parcel.writeString(publishedAt)
    parcel.writeString(content)
    parcel.writeByte(if (isSaved) 1 else 0)
    parcel.writeByte(if (isArchived) 1 else 0)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Article> {
    override fun createFromParcel(parcel: Parcel): Article {
      return Article(parcel)
    }

    override fun newArray(size: Int): Array<Article?> {
      return arrayOfNulls(size)
    }
  }
}
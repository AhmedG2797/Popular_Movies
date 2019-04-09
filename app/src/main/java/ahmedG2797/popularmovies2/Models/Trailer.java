package ahmedG2797.popularmovies2.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ahmedG2797.popularmovies2.Activities.MainActivity;


public class Trailer {
    @SerializedName("id")
    @Expose
    private String trailerId;
    @SerializedName("iso_639_1")
    @Expose
    private String iso_639_1;
    @SerializedName("iso_3166_1")
    @Expose
    private String iso_3166_1;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("site")
    @Expose
    private String site;
    @SerializedName("size")
    @Expose
    private int size;
    @SerializedName("type")
    @Expose
    private String type;

    public Trailer() {
    }

    public static String getUrl(Trailer trailer) {
        if (trailer.getSite().equalsIgnoreCase("YouTube")) {
            return String.format(MainActivity.YOUTUBE_VIDEO_URL, trailer.getKey());
        } else {
            return null;
        }
    }

    public static String getThumbnailUrl(Trailer trailer) {
        if (trailer.getSite().equalsIgnoreCase("YouTube")) {
            return String.format(MainActivity.YOUTUBE_THUMBNAIL_URL, trailer.getKey());
        } else {
            return null;
        }
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

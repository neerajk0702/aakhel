package com.kredivation.aakhale.model;

import org.json.JSONArray;

public class Post_info {
    private String is_active;

    private String is_author_admin;

    private String updated_at;

    private String description;

    private String created_at;

    private String id;

    private String title;

    private String author_id;

    private JSONArray featured_image;

    public String getIs_active ()
    {
        return is_active;
    }

    public void setIs_active (String is_active)
    {
        this.is_active = is_active;
    }

    public String getIs_author_admin ()
    {
        return is_author_admin;
    }

    public void setIs_author_admin (String is_author_admin)
    {
        this.is_author_admin = is_author_admin;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getAuthor_id ()
    {
        return author_id;
    }

    public void setAuthor_id (String author_id)
    {
        this.author_id = author_id;
    }

    public JSONArray getFeatured_image ()
    {
        return featured_image;
    }

    public void setFeatured_image (JSONArray featured_image)
    {
        this.featured_image = featured_image;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [is_active = "+is_active+", is_author_admin = "+is_author_admin+", updated_at = "+updated_at+", description = "+description+", created_at = "+created_at+", id = "+id+", title = "+title+", author_id = "+author_id+", featured_image = "+featured_image+"]";
    }
}

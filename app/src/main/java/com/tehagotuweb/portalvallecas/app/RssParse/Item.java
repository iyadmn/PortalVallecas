package com.tehagotuweb.portalvallecas.app.RssParse;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.Date;

@Root(name = "item", strict = false)
public class Item {

    @Element(name="title")
    private String title;

    @Element(name = "description")
    private String descripcion;

    @Element(name="link")
    private String link;

    @Element(name="content")
    @Namespace(reference="http://search.yahoo.com/mrss/", prefix="media")
    private Content content;

    @Element(name="postid")
    private String postid;


    public Item() {
    }

    public Item(String title, String descripcion, String link, Content content, String postid) {
        this.title = title;
        this.descripcion = descripcion;
        this.link = link;
        this.content = content;
        this.postid = postid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getLink() {
        return link;
    }

    public Content getContent() {
        return content;
    }

    public String getpostid() {
        return postid;
    }
}


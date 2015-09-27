package com.tyaa.photogallery;

public abstract class GalleryItem {
    protected static final String OPRST_ENDPOINT = "http://oprst.com.ua/";
    private int id;
    private String pagetitle;
    private String alias;
    private String caption;
    private String preview;
    private boolean our;

    public boolean isOur() {
        return our;
    }

    public void setOur(boolean our) {
        this.our = our;
    }

    public String getPreview() {
        return OPRST_ENDPOINT+preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }


    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPagetitle() {
        return pagetitle;
    }

    public void setPagetitle(String pagetitle) {
        this.pagetitle = pagetitle;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}

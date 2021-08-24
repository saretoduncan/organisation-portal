package models;

import java.util.Objects;

public abstract class News {
    public String newsHeading;
    public String news;
    public int personId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news1 = (News) o;
        return personId == news1.personId && Objects.equals(newsHeading, news1.newsHeading) && Objects.equals(news, news1.news);
    }
    public String getHeading(){
        return this.newsHeading;
    }
    public void setNewsHeading(String newsHeading){
        this.newsHeading = newsHeading;
    }
    public String getNews(){
        return this.news;
    }
    public void setNews(String news){
        this.news=news;

    }
    public int getPersonId(){
        return this.personId;
    }
    public void setPersonId(int id){
        this.personId= id;
    }


    @Override
    public int hashCode() {
        return Objects.hash(newsHeading, news, personId);
    }

    abstract void save();
  abstract User findPostedBy();

}

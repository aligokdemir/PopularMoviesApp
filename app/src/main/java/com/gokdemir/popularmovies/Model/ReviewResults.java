package com.gokdemir.popularmovies.Model;

import java.util.List;

/**
 * Created by gokde on 4.03.2018.
 */

public class ReviewResults {
    /**
     * id : 83542
     * page : 1
     * results : [{"id":"51910979760ee320eb020fc2","author":"Andres Gomez","content":"Interesting film with an exceptional cast, fantastic performances and characterizations. The story, though, is a bit difficult to follow and, in the end, seems to not have a real point.","url":"https://www.themoviedb.org/review/51910979760ee320eb020fc2"},{"id":"520a8d10760ee32c8718e6c2","author":"Travis Bell","content":"Cloud Atlas was a very well made movie but unlike most of the \"simultaneous stories that all come together at the end\" type of movies, this one just didn't. I'm still unclear as to the point of it all.\r\n\r\nAnother issue I had was a general feeling of goofiness. Sure, the Cavendish story was pure comedy but the rest of the stories just didn't feel serious enough to me.\r\n\r\nIt carried my attention for the 172 minutes well enough and it was entertaining. I just expected more of a pay off at the end.\r\n\r\nAll in all, it's definitely worth seeing but I still haven't made up my mind if I truly liked it or not. What did you think?","url":"https://www.themoviedb.org/review/520a8d10760ee32c8718e6c2"}]
     * total_pages : 1
     * total_results : 2
     */

    private int id;
    private int page;
    private int total_pages;
    private int total_results;
    private List<Review> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }

    public static class Review {
        /**
         * id : 51910979760ee320eb020fc2
         * author : Andres Gomez
         * content : Interesting film with an exceptional cast, fantastic performances and characterizations. The story, though, is a bit difficult to follow and, in the end, seems to not have a real point.
         * url : https://www.themoviedb.org/review/51910979760ee320eb020fc2
         */

        private String id;
        private String author;
        private String content;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

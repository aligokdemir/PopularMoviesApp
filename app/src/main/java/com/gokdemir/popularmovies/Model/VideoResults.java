package com.gokdemir.popularmovies.Model;

import java.util.List;

/**
 * Created by gokde on 22.03.2018.
 */

public class VideoResults {
    /**
     * id : 141052
     * results : [{"id":"59b9112fc3a36813bc011164","iso_639_1":"en","iso_3166_1":"US","key":"3cxixDgHUYw","name":"Official Trailer 1","site":"YouTube","size":1080,"type":"Trailer"},{"id":"59b91f6a9251417e23010fae","iso_639_1":"en","iso_3166_1":"US","key":"gglkYMGRYlE","name":"Special Comic-Con Footage","site":"YouTube","size":1080,"type":"Featurette"},{"id":"59da34afc3a36861df046ee1","iso_639_1":"en","iso_3166_1":"US","key":"r9-DM9uBtVI","name":"Official Heroes Trailer","site":"YouTube","size":1080,"type":"Trailer"},{"id":"5a3d1d5792514154750b2b02","iso_639_1":"en","iso_3166_1":"US","key":"MPuXL8z_e0w","name":"Mind/Event","site":"YouTube","size":1080,"type":"Teaser"},{"id":"5a3d1dbd925141547e0b1cbc","iso_639_1":"en","iso_3166_1":"US","key":"pbnSaCTtZ2Q","name":"The Team","site":"YouTube","size":1080,"type":"Teaser"},{"id":"5a3d1e00925141547b0b4b95","iso_639_1":"en","iso_3166_1":"US","key":"sLobJuwyRtM","name":"Friends","site":"YouTube","size":1080,"type":"Teaser"},{"id":"5a3d1e3dc3a36814b90b5ca8","iso_639_1":"en","iso_3166_1":"US","key":"EFVZConRIZ0","name":"Casting The Flash","site":"YouTube","size":1080,"type":"Featurette"},{"id":"5a3d1e8e925141547e0b1dec","iso_639_1":"en","iso_3166_1":"US","key":"kLkiEdGqiKE","name":"Ezra & Ray","site":"YouTube","size":1080,"type":"Featurette"}]
     */

    private int id;
    private List<Video> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }

    public static class Video {
        /**
         * id : 59b9112fc3a36813bc011164
         * iso_639_1 : en
         * iso_3166_1 : US
         * key : 3cxixDgHUYw
         * name : Official Trailer 1
         * site : YouTube
         * size : 1080
         * type : Trailer
         */

        private String id;
        private String iso_639_1;
        private String iso_3166_1;
        private String key;
        private String name;
        private String site;
        private int size;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
}

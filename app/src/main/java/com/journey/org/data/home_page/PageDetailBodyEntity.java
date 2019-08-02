package com.journey.org.data.home_page;

import java.util.List;

public class PageDetailBodyEntity {

    private List<BodyBean> bodyBeans;
    private String title;

    public List<BodyBean> getBodyBeans() {
        return bodyBeans;
    }

    public void setBodyBeans(List<BodyBean> bodyBeans) {
        this.bodyBeans = bodyBeans;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

   public static class BodyBean {
        private String bodyTitle;
        private String bodyContent;

        public String getBodyTitle() {
            return bodyTitle;
        }

        public void setBodyTitle(String bodyTitle) {
            this.bodyTitle = bodyTitle;
        }

        public String getBodyContent() {
            return bodyContent;
        }

        public void setBodyContent(String bodyContent) {
            this.bodyContent = bodyContent;
        }
    }
}

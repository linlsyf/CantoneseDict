package com.business.provider.bean;

import java.io.Serializable;

/**
 * Project: JAViewer
 */
public class Linkable implements Serializable {

    public String link;

    public String getLink() {
        return link;
    }

    @Override
    public boolean equals(Object linkable) {
        if (!(linkable instanceof Linkable)) {
            return false;
        }

        return true;
//        return Objects_equals(link, ((Linkable) linkable).link);
    }

    @Override
    public String toString() {
        return "Linkable{" +
                "link='" + link + '\'' +
                '}';
    }
}

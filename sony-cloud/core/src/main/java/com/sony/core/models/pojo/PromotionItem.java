package com.sony.core.models.pojo;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public class PromotionItem {

        @ValueMapValue
        private String title;

        @ValueMapValue
        private String description;

        @ValueMapValue
        private String link;

        @ValueMapValue
        private String target;

        @ValueMapValue
        private String image;

        @ValueMapValue
        private String alt;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getLink() {
            return link;
        }

        public String getTarget() {
            return target;
        }

        public String getImage() {
            return image;
        }

        public String getAlt() {
            return alt;
        }
    }
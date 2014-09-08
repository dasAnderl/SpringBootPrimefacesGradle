package com.anderl.hibernate.ext;

import com.commerzbank.aop.utils.SpringUtils;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Comparator;
import java.util.Locale;

/**
 * Created by ga2sunk on 3/28/14.
 */
public class HibernateCriterionWrapperLabelComparator implements Comparator<HibernateCriterionWrapper> {

    public HibernateCriterionWrapperLabelComparator(Locale locale, String resourceBundleName) {
        this.locale = locale;
        this.resourceBundleName = resourceBundleName;
    }

    private Locale locale;
    private String resourceBundleName;

    @Override
    public int compare(HibernateCriterionWrapper first, HibernateCriterionWrapper second) {
        ResourceBundleMessageSource aopMessages = SpringUtils.getSpringBean(resourceBundleName, ResourceBundleMessageSource.class);
        return aopMessages.getMessage(first.getLabelMsgKey(), null, locale).compareToIgnoreCase(aopMessages.getMessage(second.getLabelMsgKey(), null, locale));
    }

}

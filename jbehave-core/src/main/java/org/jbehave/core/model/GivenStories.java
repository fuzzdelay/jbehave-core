package org.jbehave.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GivenStories {

    public static final GivenStories EMPTY = new GivenStories("");

    private final List<GivenStory> stories = new ArrayList<>();
    private final String asString;
    private ExamplesTable examplesTable;

    public GivenStories(String asString) {
        this.asString = asString;
        for (String path : asString.split(",")) {
            if (StringUtils.isNotBlank(path)) {
                stories.add(new GivenStory(path));
            }
        }
    }

    public List<GivenStory> getStories() {
        for (GivenStory story : stories) {
            story.useParameters(examplesTable.parametersByAnchor(story.getAnchor(), this));
        }
        return stories;
    }

    public List<String> getPaths() {
        List<String> paths = new ArrayList<>();
        for (GivenStory story : stories) {
            paths.add(story.asString().trim());
        }
        return Collections.unmodifiableList(paths);
    }

    public boolean requireParameters() {
        for (GivenStory story : stories) {
            if (story.hasAnchor()) {
                return true;
            }
        }
        return false;
    }

    public void useExamplesTable(ExamplesTable examplesTable) {
        this.examplesTable = examplesTable;
    }
    
    public String asString() {
        return asString;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

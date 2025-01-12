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

<<<<<<< HEAD
    private Map<String, String> parametersByAnchor(String anchor) {
        int examplesRow = -1;
        examplesRow = getExamplesRow(anchor, examplesRow);
        Map<String, String> parameters = null;
        if ( examplesRow > -1 && examplesTable != null && examplesRow < examplesTable.getRowCount() ){
             parameters = examplesTable.getRow(examplesRow);
        }
        if (getStringStringHashMap(parameters == null)) return new HashMap<>();
        return parameters;
    }

    private boolean getStringStringHashMap(boolean b) {
        if (b) {
            return true;
        }
        return false;
    }

    private int getExamplesRow(String anchor, int examplesRow) {
        if ( !StringUtils.isBlank(anchor) ){
            try {
                examplesRow = Integer.parseInt(anchor);
            } catch (NumberFormatException e) {
                // continue
            }
        }
        return examplesRow;
    }

=======
>>>>>>> e0a49cc04fe54967b2bcaf1848bfe394aa90c68a
    public List<String> getPaths() {
        List<String> paths = new ArrayList<>();
        for (GivenStory story : stories) {
            paths.add(story.asString().trim());
        }
        return Collections.unmodifiableList(paths);
    }

    public boolean requireParameters() {
        for (GivenStory story : stories) {
            if (getStringStringHashMap(story.hasAnchor())) return true;
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

package org.jbehave.core.embedder;

import org.jbehave.core.model.Meta;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;

import java.util.Map;

public class performableScenarioParamObj {
    private final PerformableTree.RunContext context;
    private final Story story;
    private final Map<String, String> storyParameters;
    private final FilteredStory filterContext;
    private final Meta storyMeta;
    private final boolean runBeforeAndAfterScenarioSteps;
    private final Scenario scenario;

    public performableScenarioParamObj(PerformableTree.RunContext context, Story story, Map<String, String> storyParameters, FilteredStory filterContext, Meta storyMeta, boolean runBeforeAndAfterScenarioSteps, Scenario scenario) {
        this.context = context;
        this.story = story;
        this.storyParameters = storyParameters;
        this.filterContext = filterContext;
        this.storyMeta = storyMeta;
        this.runBeforeAndAfterScenarioSteps = runBeforeAndAfterScenarioSteps;
        this.scenario = scenario;
    }

    public PerformableTree.RunContext getContext() {
        return context;
    }

    public Story getStory() {
        return story;
    }

    public Map<String, String> getStoryParameters() {
        return storyParameters;
    }

    public FilteredStory getFilterContext() {
        return filterContext;
    }

    public Meta getStoryMeta() {
        return storyMeta;
    }

    public boolean isRunBeforeAndAfterScenarioSteps() {
        return runBeforeAndAfterScenarioSteps;
    }

    public Scenario getScenario() {
        return scenario;
    }
}

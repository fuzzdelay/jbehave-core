package org.jbehave.core.steps;

import java.lang.reflect.Method;

public class StepCandidateParamObj {
    private final String patternAsString;
    private final int priority;
    private final StepType stepType;
    private final Method method;
    private final Class<?> stepsType;
    private final InjectableStepsFactory stepsFactory;

    public StepCandidateParamObj(String patternAsString, int priority, StepType stepType, Method method, Class<?> stepsType, InjectableStepsFactory stepsFactory) {
        this.patternAsString = patternAsString;
        this.priority = priority;
        this.stepType = stepType;
        this.method = method;
        this.stepsType = stepsType;
        this.stepsFactory = stepsFactory;
    }

    public String getPatternAsString() {
        return patternAsString;
    }

    public int getPriority() {
        return priority;
    }

    public StepType getStepType() {
        return stepType;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getStepsType() {
        return stepsType;
    }

    public InjectableStepsFactory getStepsFactory() {
        return stepsFactory;
    }
}

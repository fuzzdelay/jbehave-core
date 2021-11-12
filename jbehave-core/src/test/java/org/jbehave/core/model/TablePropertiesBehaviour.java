package org.jbehave.core.model;

import org.jbehave.core.model.ExamplesTable.TableProperties;
import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Valery Yatsynovich
 */
public class TablePropertiesBehaviour {

    private TableProperties createTablePropertiesWithDefaultSeparators(String propertiesAsString) {
        return new TableProperties(propertiesAsString, "|", "|", "|--");
    }

    @Test
    public void canGetCustomProperties() {
        TableProperties properties = createTablePropertiesWithDefaultSeparators(
                "ignorableSeparator=!--,headerSeparator=!,valueSeparator=!,"
                        + "commentSeparator=#,trim=false,metaByRow=true,transformer=CUSTOM_TRANSFORMER");
        assertThat(properties.getRowSeparator(), equalTo("\n"));
        assertThat(properties.getHeaderSeparator(), equalTo("!"));
        assertThat(properties.getValueSeparator(), equalTo("!"));
        assertThat(properties.getIgnorableSeparator(), equalTo("!--"));
        assertThat(properties.getCommentSeparator(), equalTo("#"));
        assertThat(properties.isTrim(), is(false));
        assertThat(properties.isMetaByRow(), is(true));
        assertThat(properties.getTransformer(), equalTo("CUSTOM_TRANSFORMER"));
    }

    @Test
    public void canSetPropertiesWithBackwardSlash() {
        TableProperties properties = createTablePropertiesWithDefaultSeparators("custom=\\");
        assertThat(properties.getProperties().getProperty("custom"), equalTo("\\"));
    }

    @Test
    public void canSetPropertiesWithSpecialCharsInValues() {
        TableProperties properties = createTablePropertiesWithDefaultSeparators("withSpecialChars=value;/=:*$\\");
        assertThat(properties.getProperties().getProperty("withSpecialChars"), equalTo("value;/=:*$\\"));
    }

    @Test
    public void canSetPropertiesWithWhitespaceInValue() {
        TableProperties properties = createTablePropertiesWithDefaultSeparators("withWhitespace=a value");
        assertThat(properties.getProperties().getProperty("withWhitespace"), equalTo("a value"));
    }

    @Test
    public void canSetPropertiesWithMixedCharsInValues() {
        TableProperties properties = createTablePropertiesWithDefaultSeparators("withMixedChars=value1;value2:*");
        assertThat(properties.getProperties().getProperty("withMixedChars"), equalTo("value1;value2:*"));
    }

    @Test
    public void canSetPropertiesWithSpecialCharsInName() {
        TableProperties properties = createTablePropertiesWithDefaultSeparators("p.r:o*p$e|r;t#y=value");
        assertThat(properties.getProperties().getProperty("p.r:o*p$e|r;t#y"), equalTo("value"));
    }

    @Test
    public void canSetPropertiesStartingWithSpecialCharsAndContainingBracketsInValue() {
        TableProperties properties = createTablePropertiesWithDefaultSeparators("placeholderKey=${placeholderValue}");
        assertThat(properties.getProperties().getProperty("placeholderKey"), equalTo("${placeholderValue}"));
    }

    @Test
    public void canGetDefaultProperties() {
        TableProperties properties = new TableProperties(new Properties());
        assertThat(properties.getHeaderSeparator(), equalTo("|"));
        assertThat(properties.getValueSeparator(), equalTo("|"));
        assertThat(properties.getIgnorableSeparator(), equalTo("|--"));
        assertThat(properties.getCommentSeparator(), equalTo("#"));
        assertThat(properties.isTrim(), is(true));
        assertThat(properties.isMetaByRow(), is(false));
        assertThat(properties.getTransformer(), is(nullValue()));
    }

    @Test
    public void canGetAllProperties() {
        Properties properties = new Properties();
        properties.setProperty("key", "value");
        TableProperties tableProperties = new TableProperties(properties);
        assertThat(tableProperties.getProperties().containsKey("key"), is(true));
    }

    @Test
    public void canGetPropertiesWithNestedTransformersWithoutEscaping() {
        TableProperties properties = new TableProperties("transformer=CUSTOM_TRANSFORMER, " +
                "tables={transformer=CUSTOM_TRANSFORMER\\, parameter1=value1}");
        assertThat(properties.isTrim(), is(true));
        assertThat(properties.isMetaByRow(), is(false));
        assertThat(properties.getTransformer(), equalTo("CUSTOM_TRANSFORMER"));
        assertThat(properties.getProperties().getProperty("tables"),
                equalTo("{transformer=CUSTOM_TRANSFORMER, parameter1=value1}"));
    }

    @Test
    public void canDecoratePropertyValuesToTrimOrKeepVerbatim() {
        TableProperties properties = new TableProperties("{key1|trim}= surroundedWithSpaces, {key2|verbatim}= surroundedWithSpaces ");
        assertThat(properties.getProperties().getProperty("key1"), equalTo("surroundedWithSpaces"));
        assertThat(properties.getProperties().getProperty("key2"), equalTo(" surroundedWithSpaces "));
    }

    @Test
    public void canDecoratePropertyValuesToUpperAndLowerCase() {
        TableProperties properties = new TableProperties("{key1|uppercase}=toUpper, {key2|lowercase}=toLower");
        assertThat(properties.getProperties().getProperty("key1"), equalTo("TOUPPER"));
        assertThat(properties.getProperties().getProperty("key2"), equalTo("tolower"));
    }

    @Test
    public void canTrimPropertyValuesByDefault() {
        TableProperties properties = new TableProperties("key1= surroundedWithSpaces , key2= ");
        assertThat(properties.getProperties().getProperty("key1"), equalTo("surroundedWithSpaces"));
        assertThat(properties.getProperties().getProperty("key2"), equalTo(""));
    }

    @Test
    public void canChainDecoratorsToDecoratePropertyValues() {
        TableProperties properties = new TableProperties("{key1|uppercase|verbatim}= toUpper , {key2|lowercase|trim}= toLower ");
        assertThat(properties.getProperties().getProperty("key1"), equalTo(" TOUPPER "));
        assertThat(properties.getProperties().getProperty("key2"), equalTo("tolower"));
    }

}

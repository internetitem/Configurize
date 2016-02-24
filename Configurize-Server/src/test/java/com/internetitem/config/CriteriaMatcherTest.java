package com.internetitem.config;

import com.internetitem.config.dataModel.ConfigFile;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CriteriaMatcherTest {

    private CriteriaMatcher matcher = new CriteriaMatcher();

    @Test
    public void testMatchesSimple() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("Environment", "Dev");

        Map<String, Set<String>> criteria = new HashMap<>();
        criteria.put("Datacenter", Collections.singleton("D1"));
        criteria.put("Environment", Collections.singleton("Dev"));

        assertFalse(matcher.matches(input, criteria));

        input.put("Datacenter", "D1");
        assertTrue(matcher.matches(input, criteria));

        input.put("Version", "v1");
        assertTrue(matcher.matches(input, criteria));
    }

    @Test
    public void testMatchesMultipleValues() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("Datacenter", "D1");
        input.put("Environment", "Dev");

        Map<String, Set<String>> criteria = new HashMap<>();
        criteria.put("Datacenter", Collections.singleton("D1"));

        Set<String> envs = new HashSet<>();
        envs.add("Prod");
        criteria.put("Environment", envs);

        assertFalse(matcher.matches(input, criteria));

        envs.add("Dev");
        assertTrue(matcher.matches(input, criteria));
    }

    @Test
    public void testMatchesConfigFile() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("Datacenter", "D1");
        input.put("Environment", "Dev");

        ConfigFile file = ConfigFile.createApplicationConfig("app1", 1, "app1");
        Map<String, Set<String>> criteria = file.getCriteria();
        criteria.put("Datacenter", Collections.singleton("D1"));

        Set<String> envs = new HashSet<>();
        envs.add("Prod");
        criteria.put("Environment", envs);

        assertFalse(matcher.matches(input, file));

        envs.add("Dev");
        assertTrue(matcher.matches(input, file));
    }

    @Test
    public void testGetMatchingConfigFiles() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("Datacenter", "D1");
        input.put("Environment", "Dev");

        List<ConfigFile> inFiles = new ArrayList<>();

        ConfigFile matchFile = ConfigFile.createApplicationConfig("app1", 1, "app1");
        Map<String, Set<String>> criteria1 = matchFile.getCriteria();
        criteria1.put("Datacenter", Collections.singleton("D1"));
        criteria1.put("Environment", Collections.singleton("Dev"));
        inFiles.add(matchFile);

        ConfigFile noMatchFile = ConfigFile.createApplicationConfig("app1", 1, "app1");
        Map<String, Set<String>> criteria2 = noMatchFile.getCriteria();
        criteria2.put("Datacenter", Collections.singleton("D1"));
        criteria2.put("Environment", Collections.singleton("Prod"));
        inFiles.add(noMatchFile);

        List<ConfigFile> outFiles = matcher.getMatchingConfigFiles(input, inFiles);
        assertEquals(1, outFiles.size());
        assertSame(matchFile, outFiles.get(0));
    }
}
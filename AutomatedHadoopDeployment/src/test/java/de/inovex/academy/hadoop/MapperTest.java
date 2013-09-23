package de.inovex.academy.hadoop;

import org.junit.Test;

public class MapperTest {

    /**
     * Tests the Regular Expression extracting state names from values.
     *
     * @throws Exception The usual JUnit test exception
     */
    @Test
    public void testName() throws Exception {
        assert "baden-wuerttemberg".equals(Central.extractStateNameByPattern("baden-wuerttemberg-nodes.txt"));
        assert "bayern".equals(Central.extractStateNameByPattern("bayern-nodes.txt"));
    }
}

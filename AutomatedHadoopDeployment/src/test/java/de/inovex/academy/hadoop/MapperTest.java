package de.inovex.academy.hadoop;

import org.junit.Test;

public class MapperTest {

    @Test
    public void testName() throws Exception {
        assert "baden-wuerttemberg".equals(Central.extractStateNameByPattern("baden-wuerttemberg-nodes.txt"));
        assert "bayern".equals(Central.extractStateNameByPattern("bayern-nodes.txt"));
    }
}

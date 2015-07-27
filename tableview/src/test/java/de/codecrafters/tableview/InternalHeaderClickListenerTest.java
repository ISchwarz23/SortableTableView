package de.codecrafters.tableview;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import de.codecrafters.tableview.listeners.TableHeaderClickListener;


/**
 * Test for {@link InternalHeaderClickListener} class.
 *
 * @author ISchwarz
 */
public class InternalHeaderClickListenerTest {

    private static final int TEST_ROW_INDEX = 3;

    @Mock
    private TableHeaderClickListener firstListenerMock;
    @Mock
    private TableHeaderClickListener secondListenerMock;

    private Set<TableHeaderClickListener> listeners = new HashSet<>();

    private InternalHeaderClickListener cut;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        listeners.add(firstListenerMock);
        cut = new InternalHeaderClickListener(TEST_ROW_INDEX, listeners);
    }

    @Test
    public void shouldInformListenersOnClick() throws Exception {
        // given
        listeners.add(firstListenerMock);
        listeners.add(secondListenerMock);

        // when
        cut.onClick(null);

        // then
        Mockito.verify(firstListenerMock, Mockito.times(1)).onHeaderClicked(TEST_ROW_INDEX);
        Mockito.verify(secondListenerMock, Mockito.times(1)).onHeaderClicked(TEST_ROW_INDEX);
    }
}

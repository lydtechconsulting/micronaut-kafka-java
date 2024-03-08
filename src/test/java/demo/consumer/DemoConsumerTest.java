package demo.consumer;

import demo.event.DemoInboundEvent;
import demo.service.DemoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DemoConsumerTest {

    @InjectMocks
    DemoConsumer demoConsumer;

    @Mock
    DemoService demoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReceive_Success() {
        DemoInboundEvent demoInboundEvent = new DemoInboundEvent("1", "Test Data");
        doNothing().when(demoService).process(demoInboundEvent);
        demoConsumer.receive(demoInboundEvent);
        verify(demoService, times(1)).process(demoInboundEvent);
    }

    @Test
    void testReceive_Exception() {
        DemoInboundEvent demoInboundEvent = new DemoInboundEvent("1", "Test Data");
        RuntimeException exception = new RuntimeException("Test Exception");
        doThrow(exception).when(demoService).process(demoInboundEvent);
        demoConsumer.receive(demoInboundEvent);
        verify(demoService, times(1)).process(demoInboundEvent);
    }
}

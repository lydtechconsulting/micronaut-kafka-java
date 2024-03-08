package demo.service;

import demo.event.DemoInboundEvent;
import demo.event.DemoOutboundEvent;
import demo.producer.DemoProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class DemoServiceTest {

    @Mock
    private DemoProducer demoProducer;

    @InjectMocks
    private DemoService demoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess() {
        DemoInboundEvent demoInboundEvent = new DemoInboundEvent("1", "Test Data");
        demoService.process(demoInboundEvent);
        verify(demoProducer).sendOutbound(any(DemoOutboundEvent.class));
    }
}

package edu.ut.grouper.component;


import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import org.springframework.stereotype.Component;

@Component
public class APNsComponent {

    ApnsService service ;//= APNS.newService().withCert("/Users/limeng/Desktop/aps.p12", "").withSandboxDestination().build();

    public void push() {
        String payload = APNS.newPayload().alertBody("Test!").sound("default").build();
        String token = "b61ee2b647c14991cd7738f74ae1d3727aa899275c3804cdf81775afb3bbc9d8";
        service.push(token, payload);
    }
}

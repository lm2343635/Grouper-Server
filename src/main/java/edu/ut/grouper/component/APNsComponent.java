package edu.ut.grouper.component;


import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;

public class APNsComponent {

    private String p12;
    private String password;
    private boolean distribution;

    public void setP12(String p12) {
        this.p12 = p12;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDistribution(boolean distribution) {
        this.distribution = distribution;
    }

    private ApnsService service = null;

    public ApnsService getService() {
        if (service == null) {
            ApnsServiceBuilder builder = APNS.newService().withCert(p12, password);
            if (distribution) {
                service = builder.withSandboxDestination().build();
            } else {
                service = builder.build();
            }
        }
        return service;
    }

    public void push(String deviceToken, String alertBody) {
        if (deviceToken == null || deviceToken.equals("")) {
            return;
        }
        String payload = APNS.newPayload().alertBody(alertBody).sound("default").build();
        getService().push(deviceToken, payload);
    }

}

package mg.x261.mybulletinboard;

public class Message {
    private String device_id;
    private String ip_location;
    private String timer_device;
    private String timer_server;
    private String message;

    public String getDeviceId() {
        return device_id;
    }

    public void setDeviceId(String device_id) {
        this.device_id = device_id;
    }

    public String getIpLocation() {
        return ip_location;
    }

    public void setIpLocation(String ip_location) {
        this.ip_location = ip_location;
    }

    public String getTimerDevice() {
        return timer_device;
    }

    public void setTimerDevice(String timer_device) {
        this.timer_device = timer_device;
    }

    public String getTimerServer() {
        return timer_server;
    }

    public void setTimerServer(String timer_server) {
        this.timer_server = timer_server;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

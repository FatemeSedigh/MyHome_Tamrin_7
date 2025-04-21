package devices;

public abstract class Device {

    protected String name;
    protected String protocol;
    protected String status;

    public Device(String name, String protocol, String status){
        this.name = name;
        this.protocol = protocol;
        this.status = "off";
    }



}

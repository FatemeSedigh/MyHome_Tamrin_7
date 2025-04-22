package devices;

public abstract class Device {

    protected String name;
    protected String protocol;
    protected String status;

    public Device(String name, String protocol, String status){
        this.name = name;
        this.protocol = protocol;
        this.status = status;
    }

    public String getName(){ return name; }
    public String getStatus(){ return status; }
    public String getProtocol(){ return protocol; }

    public void setStatus(String status){ this.status = status; }

    public abstract boolean setProperty(String property, String value);
    public abstract String getInfo();

}

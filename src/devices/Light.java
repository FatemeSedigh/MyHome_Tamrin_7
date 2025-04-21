package devices;

public class Light extends Device{

    private int brightness;

    public Light(String name, String protocol){
        super(name, protocol);
        this.brightness = 50;
    }



}

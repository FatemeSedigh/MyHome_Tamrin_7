package devices;

public class Light extends Device{

    private int brightness;

    public Light(String name, String protocol){
        super(name, protocol);
        this.brightness = 50;
    }

    @Override
    public boolean setProperty(String property, String value){
        if(property.equals("status")){
            if(value.equals("on") || value.equals("aff")){
                setStatus(value);
                return true;
            }
            return false;
        }
    }

}

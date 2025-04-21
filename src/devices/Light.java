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
        } else if (property.equals("brightness")){
            try {
                int brightness = Integer.parseInt(value);
                if(brightness >= 0 && brightness <=100 ){
                    this.brightness = brightness;
                    return true;
                }
                return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public String getInfo(){
        return String.format("light: %s %s %d%% %s", name, status, brightness, protocol);
    }

}

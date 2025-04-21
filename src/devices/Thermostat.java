package devices;

public class Thermostat extends Device{

    private int temperature;

    public Thermostat(String name, String protocol) {
        super(name, protocol);
        this.temperature = 20;
    }

    @Override
    public boolean setProperty(String property, String value){
        if (property.equals("status")){
            if(value.equals("on") || value.equals("off")){
                setProperty(value);
                return true;
            }
            return false;
        } else if(property.equals("temperature")){
            try {
                int temp = Integer.parseInt(value);
                if (temp >= 10 && temp <= 30){
                    this.temperature = temp;
                    return true;
                }
                return false;
            } catch (NumberFormatException) {
                return false;
            }
        }
        return false;
    }

    @Override
    public String getInfo(){
        return String.format("thermostat: %s %s %dC %s", name, status, temperature, protocol);
    }

}

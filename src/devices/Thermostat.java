package devices;

public class Thermostat extends Device{

    private int temperature;

    public Thermostat(String name, String protocol) {
        super(name, protocol);
        this.temperature = 20;
    }



}

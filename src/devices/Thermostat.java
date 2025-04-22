package devices;

public class Thermostat extends Device{

    private int temperature;

    public Thermostat(String name, String protocol) {
        super(name, protocol, "off");
        this.temperature = 20;
    }

    @Override
    public boolean setProperty(String property, String value) {
        switch (property) {
            case "status":
                if (value.equals("on") || value.equals("off")) {
                    setStatus(value); // اینجا باید setStatus فراخوانی شود نه setProperty
                    return true;
                }
                return false;
            case "temperature":
                try {
                    int temp = Integer.parseInt(value);
                    if (temp >= 10 && temp <= 30) {
                        this.temperature = temp;
                        return true;
                    }
                    return false;
                } catch (NumberFormatException e) {
                    return false;
                }
            default:
                return false;
        }
    }

    @Override
    public String getInfo(){
        return String.format("thermostat: %s %s %dC %s", name, status, temperature, protocol);
    }

}

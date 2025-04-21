import devices.Device;
import devices.Light;
import devices.Thermostat;
import rules.Rule;
import exceptions.*;

import java.util.*;

public class Main {
    private static Map<String, Device> devices = new LinkedHashMap<>();
    private static List<Rule> rules = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int q = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < q; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            String command = parts[0];

            try {
                switch (command) {
                    case "add_device":
                        handleAddDevice(parts);
                        break;
                    case "set_device":
                        handleSetDevice(parts);
                        break;
                    case "remove_device":
                        handleRemoveDevice(parts);
                        break;
                    case "list_devices":
                        handleListDevices();
                        break;
                    case "add_rule":
                        handleAddRule(parts);
                        break;
                    case "check_rules":
                        handleCheckRules(parts);
                        break;
                    case "list_rules":
                        handleListRules();
                        break;
                    default:
                        System.out.println("invalid command");
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            } catch (DeviceException e) {
                System.out.println(e.getMessage());
            } catch (RuleException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("invalid input");
            }
        }
    }

    private static void handleAddDevice(String[] parts) throws InvalidInputException, DeviceException {
        if (parts.length != 4) {
            throw InvalidInputException.forCommand("add_device");
        }

        String type = parts[1];
        String name = parts[2];
        String protocol = parts[3];

        if (devices.containsKey(name)) {
            throw DeviceException.duplicateDeviceName();
        }

        if (!protocol.equals("WiFi") && !protocol.equals("Bluetooth")) {
            throw InvalidInputException.general();
        }

        Device device;
        if (type.equals("light")) {
            device = new Light(name, protocol);
        } else if (type.equals("thermostat")) {
            device = new Thermostat(name, protocol);
        } else {
            throw InvalidInputException.general();
        }

        devices.put(name, device);
        System.out.println("device added successfully");
    }

    private static void handleSetDevice(String[] parts) throws InvalidInputException, DeviceException {
        if (parts.length != 4) {
            throw InvalidInputException.forCommand("set_device");
        }

        String name = parts[1];
        String property = parts[2];
        String value = parts[3];

        if (!devices.containsKey(name)) {
            throw DeviceException.deviceNotFound();
        }

        Device device = devices.get(name);
        if (!device.setProperty(property, value)) {
            if (property.equals("status") || property.equals("brightness") || property.equals("temperature")) {
                throw DeviceException.invalidValue();
            } else {
                throw DeviceException.invalidProperty();
            }
        }

        System.out.println("device updated successfully");
    }

    private static void handleRemoveDevice(String[] parts) throws InvalidInputException, DeviceException {
        if (parts.length != 2) {
            throw InvalidInputException.forCommand("remove_device");
        }

        String name = parts[1];

        if (!devices.containsKey(name)) {
            throw DeviceException.deviceNotFound();
        }

        devices.remove(name);
        rules.removeIf(rule -> rule.getDeviceName().equals(name));
        System.out.println("device removed successfully");
    }

    private static void handleListDevices() {
        if (devices.isEmpty()) {
            System.out.println();
            return;
        }

        for (Device device : devices.values()) {
            System.out.println(device.getInfo());
        }
    }

    private static boolean isValidTime(String time) {
        if (time.length() != 5 || time.charAt(2) != ':') {
            return false;
        }

        try {
            int hour = Integer.parseInt(time.substring(0, 2));
            int minute = Integer.parseInt(time.substring(3));

            return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void handleAddRule(String[] parts) throws InvalidInputException, DeviceException, RuleException {
        if (parts.length != 4) {
            throw InvalidInputException.forCommand("add_rule");
        }

        String name = parts[1];
        String time = parts[2];
        String action = parts[3];

        if (!devices.containsKey(name)) {
            throw DeviceException.deviceNotFound();
        }

        if (!isValidTime(time)) {
            throw RuleException.invalidTime();
        }

        if (!action.equals("on") && !action.equals("off")) {
            throw RuleException.invalidAction();
        }

        for (Rule rule : rules) {
            if (rule.getDeviceName().equals(name) && rule.getTime().equals(time)) {
                throw RuleException.duplicateRule();
            }
        }

        rules.add(new Rule(name, time, action));
        System.out.println("rule added successfully");
    }

    private static void handleCheckRules(String[] parts) throws InvalidInputException, RuleException {
        if (parts.length != 2) {
            throw InvalidInputException.forCommand("check_rules");
        }

        String time = parts[1];

        if (!isValidTime(time)) {
            throw RuleException.invalidTime();
        }

        for (Rule rule : rules) {
            if (rule.getTime().equals(time)) {
                Device device = devices.get(rule.getDeviceName());
                if (device != null) {
                    device.setProperty("status", rule.getAction());
                }
            }
        }

        System.out.println("rules checked");
    }

    private static void handleListRules() {
        if (rules.isEmpty()) {
            System.out.println();
            return;
        }

        for (Rule rule : rules) {
            System.out.println(rule.getInfo());
        }
    }
}

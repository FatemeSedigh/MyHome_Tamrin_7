import devices.Device;
import devices.Light;
import devices.Thermostat;
import rules.Rule;

import java.util.*;

public class Main {
    private static Map<String, Device> devices = new HashMap<>();
    private static List<Device> deviceOrder = new ArrayList<>();
    private static List<Rule> rules = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int q = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < q; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            String command = parts[0];

            switch (command) {
                case "add_device":
                    addDevice(parts);
                    break;
                case "set_device":
                    setDevice(parts);
                    break;
                case "remove_device":
                    removeDevice(parts);
                    break;
                case "list_devices":
                    listDevices();
                    break;
                case "add_rule":
                    addRule(parts);
                    break;
                case "check_rules":
                    checkRules(parts);
                    break;
                case "list_rules":
                    listRules();
                    break;
            }
        }
    }

    private static void addDevice(String[] parts) {
        if (parts.length != 4) {
            System.out.println("invalid input");
            return;
        }

        String type = parts[1];
        String name = parts[2];
        String protocol = parts[3];

        if (devices.containsKey(name)) {
            System.out.println("duplicate device name");
            return;
        }

        if (!protocol.equals("WiFi") && !protocol.equals("Bluetooth")) {
            System.out.println("invalid input");
            return;
        }

        Device device;
        if (type.equals("light")) {
            device = new Light(name, protocol);
        } else if (type.equals("thermostat")) {
            device = new Thermostat(name, protocol);
        } else {
            System.out.println("invalid input");
            return;
        }

        devices.put(name, device);
        deviceOrder.add(device);
        System.out.println("device added successfully");
    }

    private static void setDevice(String[] parts) {
        if (parts.length != 4) {
            System.out.println("invalid input");
            return;
        }

        String name = parts[1];
        String property = parts[2];
        String value = parts[3];

        if (!devices.containsKey(name)) {
            System.out.println("device not found");
            return;
        }

        Device device = devices.get(name);
        if (!device.setProperty(property, value)) {
            if (property.equals("status") || property.equals("brightness") || property.equals("temperature")) {
                System.out.println("invalid value");
            } else {
                System.out.println("invalid property");
            }
        } else {
            System.out.println("device updated successfully");
        }
    }

    private static void removeDevice(String[] parts) {
        if (parts.length != 2) {
            System.out.println("invalid input");
            return;
        }

        String name = parts[1];

        if (!devices.containsKey(name)) {
            System.out.println("device not found");
            return;
        }

        devices.remove(name);
        deviceOrder.removeIf(d -> d.getName().equals(name));
        rules.removeIf(r -> r.getDeviceName().equals(name));
        System.out.println("device removed successfully");
    }

    private static void listDevices() {
        if (deviceOrder.isEmpty()) {
            System.out.println();
            return;
        }

        for (Device device : deviceOrder) {
            System.out.println(device.getInfo());
        }
    }

    private static void addRule(String[] parts) {
        if (parts.length != 4) {
            System.out.println("invalid input");
            return;
        }

        String name = parts[1];
        String time = parts[2];
        String action = parts[3];

        if (!devices.containsKey(name)) {
            System.out.println("device not found");
            return;
        }

        if (!isValidTime(time)) {
            System.out.println("invalid time");
            return;
        }

        if (!action.equals("on") && !action.equals("off")) {
            System.out.println("invalid action");
            return;
        }

        for (Rule rule : rules) {
            if (rule.getDeviceName().equals(name) && rule.getTime().equals(time)) {
                System.out.println("duplicate rule");
                return;
            }
        }

        rules.add(new Rule(name, time, action));
        System.out.println("rule added successfully");
    }

    private static void checkRules(String[] parts) {
        if (parts.length != 2) {
            System.out.println("invalid input");
            return;
        }

        String time = parts[1];

        if (!isValidTime(time)) {
            System.out.println("invalid time");
            return;
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

    private static void listRules() {
        if (rules.isEmpty()) {
            System.out.println();
            return;
        }

        for (Rule rule : rules) {
            System.out.println(rule.getInfo());
        }
    }

    private static boolean isValidTime(String time) {
        if (!time.matches("\\d{2}:\\d{2}")) {
            return false;
        }

        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
    }
}
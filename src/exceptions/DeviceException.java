package exceptions;

class DeviceException extends Exception {
    public DeviceException(String message) { super(message); }
    public static DeviceException duplicateDeviceName() { return new DeviceException("duplicate device name"); }
    public static DeviceException deviceNotFound() { return new DeviceException("device not found"); }
    public static DeviceException invalidProperty() { return new DeviceException("invalid property"); }
    public static DeviceException invalidValue() { return new DeviceException("invalid value"); }
}

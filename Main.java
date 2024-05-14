import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Observer interface
interface Observer {
    void update(Subject subject);
}

// Concrete Observers
class Logger implements Observer {
    @Override
    public void update(Subject subject) {
        System.out.println("Logger: Device '" + subject.getName() + "' state changed");
    }
}

class SecurityNotifier implements Observer {
    @Override
    public void update(Subject subject) {
        System.out.println("Security Notifier: Device '" + subject.getName() + "' state changed");
    }
}

class UserNotifier implements Observer {
    @Override
    public void update(Subject subject) {
        System.out.println("User Notifier: Device '" + subject.getName() + "' state changed");
    }
}

// Subject interface
interface Subject {
    void attach(Observer observer);

    void detach(Observer observer);

    void notifyObservers();

    String getName();

    Boolean getOn();
}

// Concrete Subject
class Device implements Subject {
    private String name;
    private boolean isOn;

    private List<Observer> observers = new ArrayList<>();

    public Device(String name) {
        this.name = name;
        this.isOn = false; // By default device is off
    }

    public void turnOn() {
        this.isOn = true;
        System.out.println(name + " is turned on");
        notifyObservers();
    }

    public void turnOff() {
        this.isOn = false; // By default device is off
        System.out.println(name + " Device is turned off");
        notifyObservers();
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Boolean getOn() {
        return isOn;
    }
}

// Concrete Devices
class Light extends Device {

    public Light(String name) {
        super(name);
    }
}

class Thermostat extends Device {

    public Thermostat(String name) {
        super(name);
    }
}

class Camera extends Device {

    public Camera(String name) {
        super(name);
    }
}

// Room class
class Room {
    private String name;
    private List<Device> devices = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void removeDevice(Device device) {
        devices.remove(device);
    }

    public void performOperationOnDevices(boolean turnOffOn) {
        System.out.println("Performing operation in " + name);
        for (Device device : devices) {
            if (turnOffOn) {
                if (!device.getOn()) {
                    device.turnOn();
                }
            } else {
                if (device.getOn()) {
                    device.turnOff();
                }
            }
        }
    }
}

/// DeviceType interface
interface DeviceType {

    void attachAdapter(DeviceAdapter adapter);

    void performGroupOperation(boolean turnOn);
}

// Concrete device type for lights
class LightType implements DeviceType {
    private List<DeviceAdapter> adapters = new ArrayList<>();

    @Override
    public void attachAdapter(DeviceAdapter adapter) {
        adapters.add(adapter);
    }

    @Override
    public void performGroupOperation(boolean turnOn) {
        for (DeviceAdapter adapter : adapters) {
            adapter.performOperation(turnOn);
        }
    }
}

// Concrete device type for thermostats
class ThermostatType implements DeviceType {
    private List<DeviceAdapter> adapters = new ArrayList<>();

    @Override
    public void attachAdapter(DeviceAdapter adapter) {
        adapters.add(adapter);
    }

    @Override
    public void performGroupOperation(boolean turnOn) {
        for (DeviceAdapter adapter : adapters) {
            adapter.performOperation(turnOn);
        }
    }
}

// Concrete device type for cameras
class CameraType implements DeviceType {
    private List<DeviceAdapter> adapters = new ArrayList<>();

    @Override
    public void attachAdapter(DeviceAdapter adapter) {
        adapters.add(adapter);
    }

    @Override
    public void performGroupOperation(boolean turnOn) {
        for (DeviceAdapter adapter : adapters) {
            adapter.performOperation(turnOn);
        }
    }
}

// Adapter pattern
interface DeviceAdapter {
    void performOperation(boolean turnOn);
}

class LightAdapter implements DeviceAdapter {
    private Light light;

    public LightAdapter(Light light) {
        this.light = light;
    }

    @Override
    public void performOperation(boolean turnOn) {
        if (turnOn) {
            light.turnOn();
        } else {
            light.turnOff();
        }
    }
}

class CameraAdapter implements DeviceAdapter {
    private Camera camera;

    public CameraAdapter(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void performOperation(boolean turnOn) {
        if (turnOn) {
            camera.turnOn();
        } else {
            camera.turnOff();
        }
    }
}

class ThermostatAdapter implements DeviceAdapter {
    private Thermostat thermostat;

    public ThermostatAdapter(Thermostat thermostat) {
        this.thermostat = thermostat;
    }

    @Override
    public void performOperation(boolean turnOn) {
        if (turnOn) {
            thermostat.turnOn();
        } else {
            thermostat.turnOff();
        }
    }
}

// Client code
public class Main {
    public static void main(String[] args) {
        // Create concrete subjects
        Light light = new Light("Living Room Light");
        Thermostat thermostat = new Thermostat("Living Room Thermostat");
        Camera camera = new Camera("Living Camera");

        // Create concrete observers
        Logger logger = new Logger();
        SecurityNotifier securityNotifier = new SecurityNotifier();
        UserNotifier userNotifier = new UserNotifier();

        // Attach observers to subjects
        light.attach(logger);
        light.attach(securityNotifier);
        light.attach(userNotifier);

        // Attach observers to subjects
        thermostat.attach(logger);
        thermostat.attach(securityNotifier);
        thermostat.attach(userNotifier);

        // Attach observers to subjects
        camera.attach(logger);
        camera.attach(securityNotifier);
        camera.attach(userNotifier);

        // Create rooms and add devices to them
        Room livingRoom = new Room("Living Room");
        livingRoom.addDevice(light);
        livingRoom.addDevice(camera);
        livingRoom.addDevice(thermostat);

        // Perform operations on individual devices
        light.turnOn();
        thermostat.turnOff();

        // Perform operations on groups of devices (rooms)
        livingRoom.performOperationOnDevices(true);

        // Create adapters and attach them to device types(groups of similar use)
        LightAdapter lightAdapter = new LightAdapter(light);
        ThermostatAdapter thermostatAdapter = new ThermostatAdapter(thermostat);
        CameraAdapter cameraAdapter = new CameraAdapter(camera);

        LightType lightType = new LightType();
        lightType.attachAdapter(lightAdapter);

        ThermostatType thermostatType = new ThermostatType();
        thermostatType.attachAdapter(thermostatAdapter);

        CameraType cameraType = new CameraType();
        cameraType.attachAdapter(cameraAdapter);

        // Perform operations on groups of devices (devicetype)
        lightType.performGroupOperation(true);
        thermostatType.performGroupOperation(true);
        cameraType.performGroupOperation(true);
    }
}

import java.util.Scanner;

class Main {

  static char ast = '*';

  public static void showWelcome() {
    
    System.out.println("Welcome to the Crash Landing simulator.\nTry not to die.\n" + ast + ast + ast + ast + ast + ast + ast + ast + ast + ast + ast + ast + ast + ast);
  }

  public static int getFuel (Scanner userInput) {
    System.out.println("Enter your initial amount of fuel:");
    int userInt = userInput.nextInt();
    while (userInt <= 0) {
      System.out.println("" + ast + ast + ast + "ERROR: You must enter in an integer greater than zero" + ast + ast + ast);
      userInt = userInput.nextInt();
    }
    return userInt;
  }

  public static double getAltitude(Scanner userInput) {
    System.out.println("Enter your initial altitude:");
    double userInt = userInput.nextDouble();
    while (userInt <= 0 || userInt > 9999) {
      System.out.println("" + ast + ast + ast + "ERROR: You must enter in an integer between 1 and 9999 (inclusive)" + ast + ast + ast);
      userInt = userInput.nextDouble();
    }
    return userInt;
  }

  public static void displayState(int elapsedTime, double altitude, double velocity, int fuelAmount, int fuelRate) {
    System.out.println("" + ast + ast + ast + "SHIP STATUS" + ast + ast + ast);
    System.out.println("current time: " + elapsedTime + " seconds");
    System.out.println("current altitude: " + altitude + " meters");
    System.out.println("current velocity: " + velocity + " m/s");
    System.out.println("current fuel amount: " + fuelAmount);
    System.out.println("current fuel rate: " + fuelRate);
  }

  public static int getFuelRate(int currentFuel, Scanner userInput) {
    System.out.println("Enter your amount of fuel you want to use:");
    int userInt = userInput.nextInt();
    int current = currentFuel;
    while (userInt < 0 || userInt > 9 || userInt > current) {
      System.out.println("" + ast + ast + ast + "ERROR: You must enter in an integer greater than zero yet less than 9. There is currently " + current + " fuel left on the ship." + ast + ast + ast);
      userInt = userInput.nextInt();
    }
    if ( userInt < current) {
    return userInt;
    } else {
      return current;
    }
  }

  public static void displayShipLandingStatus(double velocity) {
    if (velocity > -3 && velocity < 1) {
      System.out.println("" + ast + ast + ast + "Status at landing: The eagle has landed safely!" + ast + ast + ast);
    } else if (velocity > -10 && velocity < -2) {
      System.out.println("" + ast + ast + ast + "Status at landing: You’re alive… but that ship is done for" + ast + ast + ast);
    } else if (velocity <= -10) {
      System.out.println("" + ast + ast + ast + "Status at landing: RIP" + ast + ast + ast);
    } else {
      System.out.println("Error");
    }
  }

  public static double updateAcceleration(double gravity, int fuelRate) {
    final double gravConst = 9.81;
    double cfr = fuelRate;

    double accel = gravity * (((double)fuelRate / 5.0) - 1.0);
    return accel;
  }

  public static double updateAltitude(double altitude, double velocity, double acceleration) {
    double alt2 = altitude + velocity + (acceleration / 2);
    if (alt2 > 0) {
    return alt2;
    } else {
      return 0;
    } 
  }

  public static double updateVelocity(double velocity, double acceleration) {
    double vel2 = velocity + acceleration;
    return vel2;
  }

  public static int updateFuel(int fuel, int fuelRate) {
    int newFuel = fuel - fuelRate;
    return newFuel;
  }

  public static void main(String[] args) {
    Scanner scnr = new Scanner(System.in);
    int time = 0;
    double currVelocity = 0.0;
    int currRate = 0;
    int initRate = 0;
    double currAcc;

    showWelcome();
    int currFuel = getFuel(scnr);
    double currAlt = getAltitude(scnr);
    
    displayState(time, currAlt, currVelocity, currFuel, initRate);
    while (currAlt > 0) {
      currRate = getFuelRate(currFuel, scnr);
      currAcc = updateAcceleration(9.81, currRate);
      currAlt = updateAltitude(currAlt, currVelocity, currAcc);
      currVelocity = updateVelocity(currVelocity, currAcc);
      currFuel = updateFuel(currFuel, currRate);
      if (currFuel == 0) {
        System.out.println("You have ran out of fuel and plummeted to the Earth!");
        break;
      }
      ++time;
      displayState(time, currAlt, currVelocity, currFuel, currRate);
    }

    displayShipLandingStatus(currVelocity);
    
  }
}

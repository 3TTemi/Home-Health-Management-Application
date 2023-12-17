// package HomeHealthManagement;

import java.util.ArrayList;
import java.util.Arrays;

public class Patient
{

    // delcare private variables
    private int numCalories;
    private String[] calorieDiet = new String[3];
    private int daysTillAppointment;
    private ArrayList<String> houseList;
    private String[] treatmentMethods = new String[4];

    public Patient()
    {
        // set private variables
        numCalories = 0;
        Arrays.fill(calorieDiet, "");
        daysTillAppointment = 0;
        Arrays.fill(treatmentMethods, "");
    }
// calcualte calories with weight and age

    public int calculateCalorie(int age, String gender)
    {
        int numCalorie = 0;

        if (gender.equalsIgnoreCase("Male"))
        {
            if (age == 2 || age == 3)
            {
                numCalorie = 1000;
            }
            if (age >= 4 && age <= 8)
            {
                numCalorie = 1200;
            }
            if (age >= 9 && age <= 13)
            {
                numCalorie = 1600;
            }
            if (age >= 14 && age <= 18)
            {
                numCalorie = 2000;
            }
            if (age >= 19 && age <= 30)
            {
                numCalorie = 2400;
            }
            if (age >= 31 && age <= 50)
            {
                numCalorie = 2200;
            }
            if (age > 51)
            {
                numCalorie = 2000;
            }
        } else if (gender.equalsIgnoreCase("Female"))
        {
            if (age == 2 || age == 3)
            {
                numCalorie = 1000;
            }
            if (age >= 4 && age <= 8)
            {
                numCalorie = 1200;
            }
            if (age >= 9 && age <= 13)
            {
                numCalorie = 1400;
            }
            if (age >= 14 && age <= 18)
            {
                numCalorie = 1800;
            }
            if (age >= 19 && age <= 30)
            {
                numCalorie = 1800;
            }
            if (age >= 31 && age <= 50)
            {
                numCalorie = 1800;
            }
            if (age > 51)
            {
                numCalorie = 1600;
            }
        }

        return numCalorie;

    }
// calculate diet depending with calorie count input

    public String[] calculateDiet(int calorieCount)
    {
        String[] diet = new String[3];

        if (calorieCount == 1000)
        {
            diet[0] = "Breakfast: Ham, Egg Beaters, and Mushroom Scramble, Stawberries - 318 Calories";
            diet[1] = "Lunch: Red Bell Pepper and Hummus - 278 Calories";
            diet[2] = "Dinner: Oven-Baked Rutabaga Wedges - 385 Calories";
        } else if (calorieCount == 1200)
        {
            diet[0] = "Breakfast: Breakfast Parfait - 333 Calories";
            diet[1] = "Lunch: Carrots with Hummus - 349 Calories ";
            diet[2] = "Dinner: Arugula Chicken Salad - 520 Calories ";

        } else if (calorieCount == 1400)
        {
            diet[0] = "Breakfast: Vanilla Banana Milkshake - 364 Calories";
            diet[1] = "Lunch: Tuna Stuffed Pepper & Carrots - 384 Calories";
            diet[2] = "Dinner: Chicken Rollatini & Lemon Roasted Cauliflower - 640 Calories";
        } else if (calorieCount == 1600)
        {
            diet[0] = "Breakfast: Gordan Ramsay's Scrambled Eggs - 445 Calories";
            diet[1] = "Lunch: Tuna and White Bean Salad & Carrots with Hummus - 560 Calories ";
            diet[2] = "Dinner: Curry Shrimp Roll - 597 Calories";
        } else if (calorieCount == 1800)
        {
            diet[0] = "Breakfast: Greek Yogurt & Fruit Salad - 528 Calories";
            diet[1] = "Lunch: Turkey Salad & Cinnamon Honey Cottage Cheese - 504 Calories";
            diet[2] = "Dinner: Lentil Salad - 769 Calories";
        } else if (calorieCount == 2000)
        {
            diet[0] = "Breakfast: Peanut Butter and Apricot Jam Toast with Cottage Cheese - 606 Calories";
            diet[1] = "Lunch:Cucumber Tomato Salad with Tuna & Banana - 579 Calories ";
            diet[2] = "Dinner: Beef and Spinach Meatballs & Peanut Butter and Peach Toast - 813 Calories ";
        } else if (calorieCount == 2200)
        {
            diet[0] = "Breakfast: Egg, Turkey, and Cheese Wrap & NonFat yogurt - 706 Calories";
            diet[1] = "Lunch: Tuna and Avocado Wrap & Carrots - 689 Calories";
            diet[2] = "Dinner: Simple Spaghetti and Meat Sauce & 3 Ingredient Peanut Butter Cookies - 802 Calories";
        } else if (calorieCount == 2400)
        {
            diet[0] = "Breakfast: Tomato, Cream Cheese, and Pepper Sandwich - 725 Calories ";
            diet[1] = "Lunch: Big PB&J Sandwich & Arugula Salad - 736 Calories";
            diet[2] = "Dinner: Easy Cheesy Mixed Vegetables - 933 Calories";
        }

        return diet;
    }

    // calculate days tilln ext appointment
    public int calculateDays(String disability, int sinceLast)
    {
        int daysTill = 0;

        if (disability.equals("Language") || disability.equals("Learning") || disability.equals("Memory"))
        {
            daysTill = 30 - sinceLast;
        } else if (disability.equals("Mobility") || disability.equals("Sensory"))
        {
            daysTill = 90 - sinceLast;
        }

        return daysTill;
    }

    // calculate house distribution
    public ArrayList<String> distributeHouse(int numHouses, int houseOccupancy, int patientCount)
    {
        ArrayList<String> houseSelection = new ArrayList<String>();

        for (int i = 0; i < numHouses; i++)
        {
            for (int j = 0; j < houseOccupancy; j++)
            {
                houseSelection.add("Group Creek " + i);

            }
        }

        for (int k = 0; k < patientCount; k++)
        {
            if (houseSelection.size() < patientCount)
            {
                houseSelection.add("No Home Available");
            }

        }
        return houseSelection;

    }

    // calculate patient treament methods
    public String[] calculateTreatment(String disability)
    {
        String[] treatmentList = new String[4];

        if (disability.equalsIgnoreCase("Language"))
        {
            treatmentList[0] = "None";
            treatmentList[1] = "Normal Shots";
            treatmentList[2] = "AAC devices";
            treatmentList[3] = "Speech Therapy";

        } else if (disability.equalsIgnoreCase("Mobility"))
        {
            treatmentList[0] = "Painkillers, Anti Seizures, Muscle Relaxants";
            treatmentList[1] = "Normal Shots";
            treatmentList[2] = "Wheel Chair, Walkers, Prosthetics, Electrical Assist Devices";
            treatmentList[3] = "Physical Therapy";
        } else if (disability.equalsIgnoreCase("Learning"))
        {
            treatmentList[0] = "Attention/Concentration Medication";
            treatmentList[1] = "Normal Shots";
            treatmentList[2] = "Colors, Recorders, Graphics, Computer Assistance";
            treatmentList[3] = "Special Education";

        } else if (disability.equalsIgnoreCase("Sensory"))
        {
            treatmentList[0] = "None";
            treatmentList[1] = "Normal Shots";
            treatmentList[2] = "Hearing Aids, Implamnts, Service Animal, Writing Tools, Glasses ";
            treatmentList[3] = "Occupational Therapy";

        } else if (disability.equalsIgnoreCase("Memory"))
        {
            treatmentList[0] = "Cholinesterase Inhibitors and Memantine ";
            treatmentList[1] = "Normal Shots";
            treatmentList[2] = "Calendar, Notes, Smart Devices, Reminders, Locator Device";
            treatmentList[3] = "Cognitive Therapy, Surgery, Switching Supplements";

        }
        return treatmentList;
    }

    // get and setters
    public int getNumCalories()
    {
        return numCalories;
    }

    public void setNumCalories(int numCalories)
    {
        this.numCalories = numCalories;
    }

    public String[] getCalorieDiet()
    {
        return calorieDiet;
    }

    public void setCalorieDiet(String[] calorieDiet)
    {
        this.calorieDiet = calorieDiet;
    }

    public int getDaysTillAppointment()
    {
        return daysTillAppointment;
    }

    public void setDaysTillAppointment(int daysTillAppointment)
    {
        this.daysTillAppointment = daysTillAppointment;
    }

    public ArrayList<String> getHouseList()
    {
        return houseList;
    }

    public void setHouseList(ArrayList<String> houseList)
    {
        this.houseList = houseList;
    }

    public String[] getTreatmentMethods()
    {
        return treatmentMethods;
    }

    public void setTreatmentMethods(String[] treatmentMethods)
    {
        this.treatmentMethods = treatmentMethods;
    }

    public static void main(String[] args)
    {
        Patient patientObj = new Patient();

        String[] treatmentData = new String[4];

        treatmentData = patientObj.getTreatmentMethods();

    }

}

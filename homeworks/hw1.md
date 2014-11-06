Homework 1
THIS IS A DRAFT
==========
## Homework information
Due date: 23:59:59, 2 weeks from somewhen.
Upload in module.
You are to submit a single **.zip** archive named **groupNumber.fn.hw1.zip**
where **groupNumber** is your group and **fn** is your faculty number.

This is ok: ***group1.66666.hw1.zip***
This is not: ***domashno1.rar***

Your zip archive must contain only **.java** files - no folder, no other files.

## Task
Implement a Datetime class with the following interface:

```java
// A standard date + time keeper class.
// Assume that this class is using GMT+2 (Sofia, Bulgaria) as its timezone.
public class Datetime {
    // Constructs a default Datetime that represents 01.01.1970, 00:00
    public Datetime();

    // Constructs a new Datetime with the given parameters
    // Explicitly checks whether each argument is valid.
    // Throws DatetimeException otherwise.
    public Datetime(int year, // Negative values represent years BC
                    int month, // Must be between 1 and 12
                    int day, // Must be in the bounds of the month
                    int hours, // Must be between 0 and 23
                    int minutes, // Must be between 0 and 59
                    int seconds // Must be between 0 and 59
                    );

    // Constructs a new Datetime by parsing the given string.
    // Acceptable strings are in the format: 03/11/2014 12:34:56
    // Throws DatetimeException if the String is invalid.
    public Datetime(String datetime);

    // Gets the respective component of the current Datetime.
    public int getYear();
    public int getMonth();
    public int getDay();
    public int getHours();
    public int getMinutes();
    public int getSeconds();

    // Returns the current day of the week.
    public int getWeekday();

    // Converts the current time to Coordinated Universal Time
    public Datetime toUTC();

    // Prints the current datetime in the format: dd/MM/yyyy hh:mm:ss
    public String toString();

    // Returns true iff the current datetime equals @other.
    public boolean equals(Datetime other);

    // Computes the difference between the two datetimes.
    // See below for the Timespan clas
    public Timespan differenceTo(Datetime other);

    // Adds @interval to the current Datetime and returns the resulting date.
    public Datetime add(Timespan interval);
}

// A class representing difference between Datetimes
public class Timespan {
    // Constructs a new Timespan from the given milliseconds.
    // You are free to other constructors.
    public Timespan(long milliseconds);

    // Converts this Timespan to milliseconds.
    public long asMilliseconds();
    // Converts this Timespan to the respective unit.
    public double asSeconds();
    public double asHours();
    public double asDays();
    public double asYears();

    // Prints this datetime in the format:
    // YY years, MM months, DD days, hh hours, mm minutes
    public String toString();
}

public class DatetimeException extends RuntimeException { }
```


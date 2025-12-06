# PPO Project - CKOALA: Functional Kernel

**Project developed by Soufiane Derouich and Yassine Bourhaba as part of the Object-Oriented Programming (OOP) module.**

---

## Project Description

This project implements the functional kernel of the **CKOALA** (*C'est Quoi Là?* / *What is that there?*) application, a digital assistant designed to help classify natural observations.

It models a hierarchical typology (categories, characteristics, value domains) and allows for the dynamic classification of observations input by the user.

### Core Concepts
The application relies on the following concepts:

* **Typology:** A tree structure grouping various categories.
* **Category:** An entity defined by a set of characteristics (e.g., `Conifer` inherits from `Tree`).
* **Value Domain:** Constraints associated with characteristics.
    * *Numerical Intervals:* For continuous data (e.g., trunk size `[0.5 ; 3.0]`).
    * *Symbol Sets:* For textual data (e.g., shape `{conical, rounded, irregular}` or bark `{smooth, fissured, scaly}`).
* **Observation:** Data entered by the user to be classified (e.g., `shape=conical`, `size=12.5`).

## Project Structure

* `src/`: Contains the Java source code for the functional kernel.
* `tests/`: Contains the unit test classes.
* `lib/`: Contains external dependencies (JUnit).

## Compilation

Compilation is performed via `javac`, ensuring the test library is included in the classpath.

Run the following commands from the project root:

```bash
mkdir -p out
javac -encoding UTF-8 -cp lib/junit-platform-console-standalone.jar -d out/ src/*.java tests/*.java

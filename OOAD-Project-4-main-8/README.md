# OOAD Project 4
 OOAD Project 4

This is an assignment for our Object Oriented Analysis and Design course in which we will be applying object oriented design principles to create a simulation of a music shop. When ran, the user will be able to choose how many days the simulation will be ran for. As the simulation is running, announcements of all actions that occurs during each day will be printed to the terminal. The output.txt file in this repository contains the terminal output from running the simulation for 10 days.

**How to Run:**
Run in IntelliJ. Run the Main.java in src/abhi/ooad folder.Also dont forget to include xchart version 3.8.
<ul>
 <li>Both the stores will each run for 10 days</li>
 <li>At the end of 10 days an interactive terminal will be displayed to the user</li>
 <li>The user will have to enter 1 or 2 to choose Northside or Southside store</li>
 <li>The user can choose this option of store 1 and 2 at any time</li>
  <li>The user when enters "Name" in the console the name of the clerk will be displayed </li>
  <li>The user when he enters "Time" on the console, the clerk will return the time to the user</li>
  <li>The user when he enters "Sell" on the console, the clerk will try to sell an item to the user.The user will have to enter the item he can sell.The item entered should be from the class ItemType.java.The clerk will give a prize for the item and if the user says yes the clerk will sell the item else the clerk will increase the prize of teh item and again ask the user whether he wants to sell the item.If the user says yes the item is sold to the user else a message that the user left without selling will be displayed</li>
     <li>When the user enters "Buy" , then the clerk will ask the user what item to buy.Again the item entered should be from the class ItemType.java.The clerk will display the prize of the item to the user and the user will have to accept the prize by saying "Yes"  or "no".If the user saya no then the clerk will reduce the prize of the item and sell it to the user.If the user says "Yes" the item is sold else the item is not sold and the user leaves without buying an item.
<li>The user can choose Toggle anytime to switch stores</li>
 <li>The user when he enters "Guitar Kit" the user will be given with a set of choices to choose from for parts of the guitar kit.There are a total of 6 parts as shown in the problem statement.Each type has 3 units and hence a factory will have a total of 18 units.The units will differ in both the stores Northside and Southside</li>
 <li>All the screenshots are the bonus question output and we have an output.txt file for the outputs captured</li>
 <li>Have added the output of junit in junit_output file</li>
 <li>Have flipped the axis in count the items and items damaged and items sold in both northside and southside store screenshots for the graphs.So the x axis should have been y and y axis should have been x.So it should be total count on the y axis and day number on the x axis</li>
 <li>Similarly for value of items sold and items in inventory for bith northside and southside store where i fliped the axis.So it should be total cost on y axis and day number on x axis.</li>
 
</ul>
Creators: 
--------
Abhinav Gupta, Alexa Rinard, Varun Manjunath

Language: 
--------
We are using Java version 17.0.2 for this project

Class Heirarchy:
------------

<a href = "https://lucid.app/lucidchart/4bec437b-3542-4f9a-b3f0-1b8d1d7ad336/edit?invitationId=inv_dde5e83c-e87b-4a5d-843b-9044843acab7&page=0_0#"> Link to UML Diagram </a>

Assumptions:
------------

<ul>
  <li>Every 7th day is considered to be Sunday</li>
  <li>Purchase price for all items is a random number between 1-50</li>
  <li>When placeAnOrder occurs, items purchased are always in New condition</li>
</ul>

Implementation Comments:
------------------------

<ul>
  <li>Condition is represented as a number from 1-5</li>
  <li>Customers buy and sell items in a random order each new day</li>
</ul>
Updated Uml diagram changes from the original diagram:
<ul>
  <li>Made most of the variables,arrays and methods as private in the Store, Item Class and all of the subclasses of Item Class</li>
  <li>Moved the functions in the Store class to Staff Class.Before most of the methods like buy and sell an item were a part of store.Added some new methods to the Staff Class</li>
  <li>New methods added to the Staff class are create and client_code to return the guitar to the Staff.</li>
  <li>Used Arraylist for items,arrivingItems;soldItems,discardedItems;</li>
  <li>Added a summary() method in simulation class to get the summary of the items</li>
 <li>Used Abstractguitarkit interface to implement abstract factory.</li>
 <li>Created two objects for Northside store and southside store and ran them for each day</li>
 <li>In the simulation.java class added a while loop to implement command pattern</li>
</ul>

*********************************************************Details about this project*********************************************************

Website Used : EMICalculator.net
Browser : Firefox/Chrome
Framework Used : TestNG Maven
Technology Used : Jenkins, Selenium_Grid

***********************************************************Run this Project***********************************************************

1. Open cmd and go to folder where selenium-server-standlone-jar is present
2. Make sure all webdrivers and jar are at same location
3. On cmd pass this command
	java -jar selenium-server-standalone-3.141.59 -role hub

4. Open another cmd  and go to folder where selenium-server-standlone-jar is present
	java -jar selenium-server-standalone-3.141.59 -role node -hub http://{YOUR_IP_ADDRESS}/grid/register

5. Open cmd and go to project folder location
	mvn clean install

This will run your project

***************************************************************************************************************************************************

1. On Successful Build HTML Report will be created at folder HTMLReports

***********************************************************About the Java files***********************************************************

1. BaseUi.java
	File where all global methods are declared
	Location : src\main\java\BaseClass\BaseUi.java

2. Pom Pages
	Location : src\main\java\Pages

3. Test Cases
	Location : src\main\java\TestCases

4. Utilities
	Location : src\main\java\Utilities

5. WebDrivers
	Location : Resources\Drivers

6. Excel files and config.property file
	Location : Resources\Repositiries

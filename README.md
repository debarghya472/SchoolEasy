# SchoolEasy


## Installation

1. If you are going to run the app in emulator, do not change anything as it has been setup for an emulator.

2. However to run the app on your mobile phone, you need to do the following:

1:Go to app then res then xml then open the network-security-config.xml file.
  In this file remove "10.0.2.2" which is the domain and add the IP address of the wifi at your home.

2:Go to app then java then android.example.schooleasy then network and open the RetrofitClientInstance class.
  In this class change the part "10.0.2.2" of the BASE_URL to the IP address of the wifi at your home.
  
 Now the app will successfully work on your mobile.


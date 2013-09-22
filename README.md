AutomatedHadoopDeployment
=========================

A basic MapReduce-Job getting up and running straight from your client's maven build

For the maven project to work you want to merge the information provided in 
AutomatedHadoopDeployment/src/main/resources/settings.xml into your currently 
active maven settings.xml -- by default this is ~/.m2/settings.xml.

Please note that this works in maven 3.0.5 but may encounter problems injecting 
the ssh plugin on site phase.

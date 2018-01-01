/**
 *  Netatmo Thermostat     Date: 31.12.2017
 *
 *  Copyright 2017 Daniel Jackson
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *
 */
metadata {
	definition (name: "Netatmo Thermostat", namespace: "danyulsan91", author: "Daniel Jackson") {
        capability "Battery"
		capability "Temperature Measurement"
        capability "Refresh"
        capability "Thermostat" 
        
        attribute "lastupdate", "string"
        attribute "setpoint_mode", "string"
        attribute "setpoint_temp", "string"
        attribute "rf_status", "string"
	}

	simulator {
		// TODO: define status and reply messages here
	}
    preferences {
        input title: "Settings", description: "To change units and time format, go to the Netatmo Connect App", displayDuringSetup: false, type: "paragraph", element: "paragraph"
        input title: "Information", description: "Your Netatmo Thermostat updates the Netatmo servers approximately every 10 minutes. The Netatmo Connect app polls these servers every 5 minutes. If the time of last update is equal to or less than 10 minutes, pressing the refresh button will have no effect", displayDuringSetup: false, type: "paragraph", element: "paragraph"
    }    
    

	tiles (scale: 2) {
		multiAttributeTile(name:"main", type:"thermostat", width:6, height:4) {
			tileAttribute("temperature", key: "PRIMARY_CONTROL") {
            	attributeState "temperature", label:'${currentValue}°', backgroundColors:[
 				[value: 31, color: "#153591"],
 				[value: 44, color: "#1e9cbb"],
 				[value: 59, color: "#90d2a7"],
 				[value: 74, color: "#44b621"],
 				[value: 84, color: "#f1d801"],
 				[value: 95, color: "#d04e00"],
 				[value: 96, color: "#bc2323"]
 			]
            }   
            tileAttribute("device.battery", key: "SECONDARY_CONTROL") {
       			attributeState("battery_percent", label:'Battery: ${currentValue}%', unit:"%", defaultState: true)
    		}
		}        
        valueTile("setpoint_temp", "setpoint_temp", width: 3, height: 1, inactiveLabel: false) { 			
            state "default", label:"Setpoint Temp: " + '${currentValue}°' 		
            }   
        valueTile("lastupdate", "last_updated", width: 3, height: 1, inactiveLabel: false) { 			
            state "default", label:"Last Updated: " + '${currentValue}' 		
            }         
        valueTile("setpoint_mode", "setpoint_mode", width: 3, height: 1, inactiveLabel: false) { 			
            state "default", label:"Setpoint Mode: " + '${currentValue}' 		
            }   
        valueTile("setpoint_endtime", "setpoint_endtime", width: 3, height: 1, inactiveLabel: false) { 			
            state "default", label:"Setpoint End Time: " + '${currentValue}' 		
            }   
        valueTile("rf_status", "rf_status", width: 3, height: 1, inactiveLabel: false) { 			
            state "default", label:"WiFi Signal: " + '${currentValue}' 		
            }   
 		standardTile("refresh", "device.refresh", width: 3, height: 1, inactiveLabel: false, decoration: "flat") {
 			state "default", action:"refresh.refresh", icon:"st.secondary.refresh"
 		}              
               
        main "main"
 		details(["main","setpoint_mode","setpoint_endtime","setpoint_temp","lastupdate","rf_status","refresh"])
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"

}

def poll() {
	log.debug "Polling"
    parent.poll()
}

def refresh() {
    log.debug "Refreshing"
	parent.poll()
}
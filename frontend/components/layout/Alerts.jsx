import axios from "axios"
import React from "react"
import { useEffect } from "react"

export default function Alerts() {

    const [alerts, setAlerts] = React.useState([
        {
            "timestamp": "2023-03-19T12:30:00Z",
            "severity": "high",
            "alert_message": "High CPU Usage Detected",
            "description": "The CPU usage on server1 has exceeded 90%",
            "server_name": "server1",
            "alert_type": "CPU Usage"
         },
         {
            "timestamp": "2023-03-19T23:45:00Z",
            "severity": "low",
            "alert_message": "Failed Login Attempts",
            "description": "There have been multiple failed login attempts from IP address 10.0.0.2",
            "server_name": "server3",
            "alert_type": "Security"
         }
         
         
    ])

    useEffect(() => {
        console.log(localStorage.getItem('token'))
        axios.get('https://evil-evaluators-spring-evil-evaluators-3.azuremicroservices.io/alert', {}).then(res => {
            console.log(res.data)
            //setAlerts(res.data.data)
        })
        .catch(err => {
            console.log(err)
        })
    }, [])
    

    return (
        <div className="flex flex-col items-center w-full h-full ">
            <div className="mt-8 mb-8 rounded">
                <h1 className="text-2xl font-bold text-center">Alerts</h1>
            </div>
            {alerts.map((alert, index) => {
                return (
                    <div key={index} className="mt-8 mb-8 rounded">
                        <h1 className="text-2xl font-bold text-center text-white">{alert.name}</h1>
                        <h1 className="text-2xl font-bold text-center text-white">{alert.description}</h1>
                    </div>
                )
            })}
        </div>
    )
}
import axios from "axios"
import React from "react"


export default function Records() {
    const [records, setRecords] = React.useState([
        
            {
              id: 1,
              name: "Low disk space",
              source: "github.com",
              severity: "High",
              potentialImpact: 0.85,
              time: "18/3/2023",
            },
            {
              id: 2,
              name: "SQL Injection Vulnerability",
              source: "OWASP",
              severity: "Critical",
              potentialImpact: 0.95,
              time: "17/3/2023",
            },
        
            {
              id: 3,
              name: "Cross-Site Scripting (XSS) Attack",
              source: "NIST",
              severity: "High",
              potentialImpact: 0.8,
              time: "16/3/2023",
            },
        
            {
              id: 4,
              name: "Broken Authentication and Session Management",
              source: "SANS Institute",
              severity: "Medium",
              potentialImpact: 0.7,
              time: "15/3/2023",
            },
        
            {
              id: 5,
              name: "Denial-of-Service (DoS) Attack",
              source: "CERT/CC",
              severity: "High",
              potentialImpact: 0.9,
              time: "14/3/2023",
            },

    ]);

    // React.useEffect(() => {
    //     axios.get('https://evil-evaluators-spring-evil-evaluators-3.azuremicroservices.io/records', {}).then(res => {
    //         console.log(res.data)
    //     })
    //     .catch(err => {
    //         console.log(err)
    //     })
    // }, [])

    return (
        <div className="flex flex-col items-center w-full h-full bg-gray-50 rounded">
            {records.map((record, index) => {
                return (
                    <div key={index} className="mt-8 mb-8 text-black rounded w-5/6 m-auto">
                        <h1 className="text-2xl font-bold text-center">{record.name}</h1>
                        <p>{record.source}</p>
                        <p>{record.severity}</p>
                        <p>{record.time}</p>
                    </div>
                )
            })}
        </div>
    )
}
import axios from "axios"
import React from "react"

export default function UserInfo() {

    const [user, setUser] = React.useState({})
    const [firstName, setFirstName] = React.useState("")
    const [lastName, setLastName] = React.useState("")
    const [email, setEmail] = React.useState("")
    const [phoneNumber, setPhoneNumber] = React.useState("")

    React.useEffect(() => {
        axios.get('https://evil-evaluators-spring-evil-evaluators-3.azuremicroservices.io/member', {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
            }
        }).then(res => {
            console.log(res.data)
            setFirstName(res.data.data.firstName)
            setLastName(res.data.data.lastName)
            setEmail(res.data.data.email)
            setPhoneNumber(res.data.data.phoneNumber)
        })
        .catch(err => {
            console.log(err)
        })
    }, [])

    return (
        <div class="flex font-sans bg-gray-50 rounded">
            <div class="flex-none w-48 relative">
                <img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png" alt="" class="absolute inset-0 w-full h-full object-cover" loading="lazy" />
            </div>
            <form class="flex-auto p-6">
                <div class="flex flex-wrap">
                    <h1 class="flex-auto text-lg font-semibold text-slate-900">
                        {firstName == "" ? "No first name" : firstName}
                    </h1>
                    <div class="w-full flex-none text-sm font-medium text-slate-700 mt-2">
                        {lastName == "" ? "No last name" : lastName}
                    </div>
                    <div class="w-full flex-none text-sm font-medium text-slate-700 mt-2">
                        {email == "" ? "No email" : email}
                    </div>
                    <div class="w-full flex-none text-sm font-medium text-slate-700 mt-2">
                        {phoneNumber == "" ? "No phone number" : phoneNumber}
                    </div>
                </div>
            </form>
        </div>
    )
}
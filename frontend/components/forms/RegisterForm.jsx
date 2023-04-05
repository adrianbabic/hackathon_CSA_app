import { useForm, Controller } from "react-hook-form"
import axios from "axios"

export default function RegisterForm(props) {
    const {register, control, handleSubmit, formState: {errors}} = useForm({
        defaultValues: {
            checkbox: false
        }
    })
    const headers = {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
    }
    const onSubmit = (data) => {
        const newData = {
            "email" : data.email,
            "password" : data.password,
            "firstName" : data.name,
            "lastName" : data.surname,
            "phoneNumber" : data.phone,
            "minorAlerts": data.checkbox
        }
        axios.post('https://evil-evaluators-spring-evil-evaluators-3.azuremicroservices.io/register/member', newData, {headers})
        .then(response => {
            if(response.status === 200) {
                document.querySelector("#registration").classList.add("hidden")
                props.handleShowVerify()
            } else {
                alert("Something went wrong")
            }
        })
    }

    return(
        <div id="registration" class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
            <div class="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
                <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
                    <h1 class="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                        Register new account
                    </h1>
                    <form class="space-y-4 md:space-y-6" onSubmit={handleSubmit(onSubmit)}>
                        <div>
                            <label for="name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your Name</label>
                            <input type="text" {...register('name', {required: true})} name="name" id="name" placeholder="John" class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required=""/>
                        </div>
                        <div>
                            <label for="surname" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your Surname</label>
                            <input type="text" {...register('surname', {required: true})} name="surname" id="surname" placeholder="Doe" class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required=""/>
                        </div>
                        <div>
                            <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your email</label>
                            <input type="email" {...register('email', {required: true, pattern: {value: /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/, message: 'Please enter a valid email',},})} name="email" id="email" class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="name@company.com" required=""/>
                        </div>
                        <div>
                            <label for="phone" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your phone number</label>
                            <input type="phone" {...register('phone', {required: true})} name="phone" id="phone" placeholder="+385 97 1231231" class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required=""/>
                        </div>
                        <div>
                            <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
                            <input type="password" {...register('password', {required: true, minLength: 8})} name="password" id="password" placeholder="••••••••" class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required=""/>
                        </div>
                        <div>
                            <div className="flex flex-row">
                                <label for="checkbox" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white mr-8">Subscribe to minor alerts</label>
                                <Controller
                                    name="checkbox"
                                    control={control}
                                    render={({ field }) => (
                                        <input type="checkbox" {...field} />
                                    )}
                                />
                            </div>
                        </div>
                        <button type="submit" value="submit" class="w-full text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800">Register</button>
                    </form>
                </div>
            </div>
        </div>
    )
}
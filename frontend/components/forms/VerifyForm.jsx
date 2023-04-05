import { useForm } from "react-hook-form"
import {useRouter} from 'next/router'
import axios from "axios"

export default function VerifyForm(props) {
    const {register, handleSubmit, formState: {errors}} = useForm()
    const router = useRouter()
    const headers = {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
    }
    const onSubmit = (data) => {
        axios.post(`https://evil-evaluators-spring-evil-evaluators-3.azuremicroservices.io/login?code=${data.verify}`, {}, {auth: {username: props.email, password: props.password}}
        ).then(res => {
            if (res.status === 200) {
                const token = res.headers.getAuthorization()
                localStorage.setItem('token', token)
                router.push('/dashboard')
            } else {
                alert("Incorrect code")
            }
        })
    }
    
    if (props.showVerify) {
        document.querySelector("#verification").classList.remove("hidden")
    }

    return (
        <div id="verification" class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0 hidden">
            <div class="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
                <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
                    <h1 class="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                        Check your Email
                    </h1>
                    <form class="space-y-4 md:space-y-6" onSubmit={handleSubmit(onSubmit)}>
                        <div>
                            <label for="verify" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your verification code</label>
                            <input type="text" {...register('verify')} name="verify" id="verify" placeholder="1234" class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required=""/>
                        </div>
                        <button type="submit" value="submit" class="w-full text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    )
}
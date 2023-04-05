import { useState } from "react";
import LoginForm from "@/components/forms/LoginForm";
import VerifyForm from "@/components/forms/VerifyForm";
import { useRouter } from "next/router";

export default function Login() {
    const [showVerify, setShowVerify] = useState(false);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleShowVerify = () => {
        setShowVerify(!showVerify);
    }

    const handleIncomingData = (data) => {
        console.log(data);
        setEmail(data.email);
        setPassword(data.password);
    }
    const router = useRouter();

    if(typeof window !== 'undefined' && localStorage.getItem("token") != null) {
        router.push("/dashboard");
    }
    return (
        <>
            <LoginForm handleShowVerify={handleShowVerify} handleIncomingData={handleIncomingData}/>
            <VerifyForm showVerify={showVerify} email={email} password={password}/>
        </>
    )

}